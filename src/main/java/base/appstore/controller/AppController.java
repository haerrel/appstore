package base.appstore.controller;

import base.appstore.exception.AppNotFoundException;
import base.appstore.repository.AppRepository;
import base.appstore.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/apps")
@CrossOrigin
public class AppController {

    @Autowired
    private AppRepository appRepository;



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
        App newApp = new App(input.getText(), input.getTags(), input.getTitle());
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
