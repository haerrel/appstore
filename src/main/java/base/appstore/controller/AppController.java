package base.appstore.controller;

import base.appstore.repository.AppRepository;
import base.appstore.model.Rating;
import base.appstore.repository.RatingRepository;
import base.appstore.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import base.appstore.exception.AppNotFoundException;
import base.appstore.model.Tag;
import base.appstore.repository.AppRepository;
import base.appstore.model.App;
import base.appstore.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private RatingRepository ratingRepository;

    //allowed for all in WebSecurityConfig
  @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
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

    /*
=======
    private TagRepository tagRepository;

    //allowed for all in WebSecurityConfig
    @GetMapping
    public List<App> all() {
        return appRepository.findAll();
    }
>>>>>>> 4f5691a4039a76656fac5b0557f8c952bfe81865
*/


    //allowed for all in WebSecurityConfig
    @GetMapping("{id}")
    public App find(@PathVariable Long id) {
        return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
    }


    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public App create(@RequestBody App input) {
        return appRepository.save(new App(input.getText(), input.getTags(), input.getTitle(), input.getPrice(), input.getDatePublished()));
    }

    /*
=======
    //allowed for developer and admin
    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public App create(@RequestBody App input) {
        //create new App
        App newApp = new App(input.getText(), input.getTitle());
        //check if Tags are already in repo
        Set<Tag> inputTags = new HashSet<>(input.getTags());
        Iterator<Tag> tagIterator = inputTags.iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            Tag savedTag = tagRepository.findOneByText(tag.getText())
                    .map(existingTag -> {existingTag.getApps().add(newApp); return existingTag;})
                    .orElseGet(() -> tagRepository.save(tag));

            newApp.getTags().add(savedTag);
            savedTag.getApps().add(newApp);
        }
        return appRepository.save(newApp);
    }
*/
    //allowed for admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        appRepository.deleteById(id);
    }

    //allowed for developer and admin
    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
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
    /*
<<<<<<< HEAD

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

=======
>>>>>>> 4f5691a4039a76656fac5b0557f8c952bfe81865
    */
}
