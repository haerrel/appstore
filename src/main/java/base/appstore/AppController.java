package base.appstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        if (Objects.isNull(search) && Objects.isNull(filter) && Objects.isNull(tag) && Objects.isNull(tag)) {
            return appRepository.findAll();
        }

        Stream<App> appStream = appRepository.findAll().stream();
        if (!Objects.isNull(search)) {
            appStream = appStream.filter(app -> app.getTitle().startsWith(search));
        }
        if (!Objects.isNull(tag)) {
            appStream = appStream.filter(app -> {
                List<String> tags = Arrays.asList(app.getTags().split(","));
                tags.retainAll(tag); //TODO throws exception
                return !tags.isEmpty();
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

    @GetMapping("/{id}/rating/{userID}")
    public List<Rating> find(@PathVariable Long id, @PathVariable Long userID) {
        return ratingRepository.findByAppAndUserId(id, userID);
    }

}
