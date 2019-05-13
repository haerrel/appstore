package base.appstore.controller;

import base.appstore.exception.AppNotFoundException;
import base.appstore.repository.AppRepository;
import base.appstore.model.Rating;
import base.appstore.repository.RatingRepository;
import base.appstore.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private RatingRepository ratingRepository;

  @GetMapping
  public List<App> all(@RequestParam(required = false) String search,
                       @RequestParam(required = false) List<String> tag,
                       @RequestParam(required = false) Integer limit,
                       @RequestParam(required = false) String filter
  ) {
    if (Objects.isNull(search) && Objects.isNull(filter) && Objects.isNull(tag) && Objects.isNull(limit)) {
        return appRepository.findAll();
    }

        Stream<App> appStream = appRepository.findAll().stream();
        if (!Objects.isNull(search)) {
            appStream = appStream.filter(app -> app.getTitle().startsWith(search));
        }
        if (!Objects.isNull(tag) && !tag.isEmpty()) {
            appStream = appStream.filter(app -> {
                List<String> appTags = new ArrayList<>(Arrays.asList(app.getTags().split(",")));
                appTags.retainAll(tag);
                return appTags.size() == tag.size();
            });
        }
        if (!Objects.isNull(filter)) {
            switch (filter.toLowerCase()) {
                case "cheapest":
                    appStream = appStream.sorted(Comparator.comparing(App::getPrice));
                    break;
                case "newest":
                    appStream = appStream.sorted(Comparator.comparing(App::getDatePublished));
                    break;
                case "famous":
                    appStream = appStream.sorted(Comparator.comparing(App::getRating));
                    break;
            }
        }
        if (!Objects.isNull(limit)) {
            appStream = appStream.limit(limit);
        }
        return appStream.collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public App find(@PathVariable Long id) {
        return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
    }

    @PostMapping
    public App create(@RequestBody App input) {
        return appRepository.save(new App(input.getText(), input.getTags(), input.getTitle(), input.getPrice(), input.getDatePublished()));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        appRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public App update(@PathVariable Long id, @RequestBody App input) {
        return appRepository.findById(id).map(app -> {
            app.setId(id);
            app.setTitle(input.getTitle());
            app.setText(input.getText());
            app.setTags(input.getTags());
            return appRepository.save(app);
        }).orElseGet(() -> {
            input.setId(id);
            return appRepository.save(input);
        });
    }

    @GetMapping("/{id}/rating/")
    public List<Rating> search(@PathVariable Long id) {
        return ratingRepository.findAllRatingsByAppId(id);
    }

    @GetMapping("/{id}/rating/{userID}")
    public List<Rating> search(@PathVariable Long id, @PathVariable Long userID) {
        return ratingRepository.findByAppIdAndUserId(id, userID);
    }

    @PostMapping("/rating")
    public Rating create(@RequestBody Rating input) {
        return ratingRepository.save(new Rating(input.getId(), input.getDatePublished(), input.getRating(), input.getText(), input.getUserId(), input.getAppId()));
    }

}
