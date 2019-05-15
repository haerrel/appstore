package base.appstore.controller;

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
    private TagRepository tagRepository;

    //allowed for all in WebSecurityConfig
    @GetMapping
    public List<App> all() {
        return appRepository.findAll();
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
        //check if Tags are already in repo
        Set<Tag> inputTags = new HashSet<>(input.getTags());
        Iterator<Tag> tagIterator = inputTags.iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            Tag savedTag = tagRepository.findOneByText(tag.getText())
                    .map((existingTag) -> {existingTag.getApps().add(newApp); return existingTag;})
                    .orElseGet(() -> {return tagRepository.save(tag);});

            newApp.getTags().add(savedTag);
            //savedTag.getApps().add(newApp);
        }
        return appRepository.save(newApp);
    }

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
}
