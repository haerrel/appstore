package base.appstore.controller;

import base.appstore.exception.AppNotFoundException;
import base.appstore.model.Tag;
import base.appstore.repository.AppRepository;
import base.appstore.model.App;
import base.appstore.repository.RatingRepository;
import base.appstore.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private TagRepository tagRepository;

    //allowed for all in WebSecurityConfig
    @GetMapping
    public List<App> all(@RequestParam(required = false) String search,
                         @RequestParam(required = false) Set<String> tags,
                         @RequestParam(required = false) Integer limit,
                         @RequestParam(required = false) String filter
    ) {
        if (Objects.isNull(search) && Objects.isNull(filter) && Objects.isNull(tags) && Objects.isNull(limit)) {
            return appRepository.findAll();
        }

        Stream<App> appStream = appRepository.findAll().stream();
        if (!Objects.isNull(search)) {
            appStream = appStream.filter(app -> app.getTitle().toLowerCase().startsWith(search.toLowerCase()));
        }
        if (!Objects.isNull(tags) && !tags.isEmpty()) {
            appStream = appStream.filter(app -> {
                Set<String> appTags = app.getTags().stream().map(Tag::getText).collect(Collectors.toSet());
                return appTags.containsAll(tags);
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
                default:
                    break;
            }
        }
        if (!Objects.isNull(limit)) {
            appStream = appStream.limit(limit);
        }
        return appStream.collect(Collectors.toList());
    }

    //allowed for all in WebSecurityConfig
    @GetMapping("{id}")
    public App find(@PathVariable Long id) {
        return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
    }

    //allowed for developer and admin
    @PreAuthorize("hasRole('DEVELOPER') or hasRole('ADMIN')")
    @PostMapping
    public App create(@RequestBody App input) {
        //create new App
        App newApp = new App(input.getText(), input.getTitle());
        newApp.setPrice(input.getPrice());
        newApp.setDatePublished(input.getDatePublished());
        newApp.setLink(input.getLink());
        newApp.setThumbnail(input.getThumbnail());

        //check if Tags are already in repo
        Set<Tag> inputTags = new HashSet<>(input.getTags());
        Iterator<Tag> tagIterator = inputTags.iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            Tag savedTag = tagRepository.findOneByText(tag.getText()).orElseGet(() -> tagRepository.save(tag));
            newApp.getTags().add(savedTag);
            savedTag.getApps().add(newApp);
        }
        return appRepository.save(newApp);
    }

    //allowed for admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        App toDelete = appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
        toDelete.getTags().forEach((Tag tag) -> tag.getApps().remove(toDelete));
        appRepository.delete(toDelete); //App cant be null!
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
            app.setLink(input.getLink());
            app.setThumbnail(input.getThumbnail());
            return appRepository.save(app);
        }).orElseGet(() ->
            create(input)
        );
    }
}
