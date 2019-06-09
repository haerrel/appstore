package base.appstore.controller;

import base.appstore.exception.AppNotFoundException;
import base.appstore.exception.ProblemNotFoundException;
import base.appstore.model.App;
import base.appstore.model.Problem;
import base.appstore.model.Tag;
import base.appstore.repository.AppRepository;
import base.appstore.repository.ProblemRepository;
import base.appstore.repository.RatingRepository;
import base.appstore.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/problem")
@CrossOrigin
public class ProblemController {

    @Autowired
    private ProblemRepository problemRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Problem> all() {
        return problemRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public Problem find(@PathVariable Long id) {
        return problemRepository.findById(id).orElseThrow(() -> new ProblemNotFoundException(id));
    }

    @PostMapping
    public Problem create(@RequestBody Problem problem) {
        return problemRepository.save(problem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        Problem toDelete = problemRepository.findById(id).orElseThrow(() -> new ProblemNotFoundException(id));
        problemRepository.delete(toDelete); //App cant be null!
    }
}
