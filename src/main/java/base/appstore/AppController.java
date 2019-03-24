package base.appstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apps")
public class AppController {

  @Autowired
  private AppRepository appRepository;


  @GetMapping
  public List<App> all() {
    return appRepository.findAll();
  }

  @GetMapping("{id}")
  public App find(@PathVariable Long id) {
    return appRepository.findById(id).orElseThrow(() -> new AppNotFoundException(id));
  }

  @PostMapping
  public App create(@RequestBody App input) {
    return appRepository.save(new App(input.getText(), input.getTags(), input.getTitle()));
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
      input.setId(id);;
      return appRepository.save(input);
    });
  }
}
