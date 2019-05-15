package base.appstore.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * App.
 * Model class persisted using JPA
 *
 * @author Gudrun Socher
 * @version 2.0
 */
@Entity
//@Table(name = "apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "app_tags",
            joinColumns = {@JoinColumn(name = "app_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    private Set<Tag> tags = new HashSet<>();

    public App() {
    }

    /**
     * Constructor used to initialize App object based on HTTP POST.
     *
     * @param text  Text of an App.
     * @param title Title of an App.
     */
    public App(String text, String title) {
        this.text = text;
        this.title = title;
        this.tags = new HashSet<>();
    }

    /**
     * Constructor used to initialize App object based on HTTP POST.
     *
     * @param text  Text of an App.
     * @param title Title of an App.
     */
    public App(String text, String title, Set<Tag> tags) {
        this.text = text;
        this.title = title;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
