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
    private Integer price;
    private String datePublished;
    private String link;
    @Lob private String thumbnail;

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
    public App(String text, String title, Set<Tag> tags, Integer price, String datePublished, String link, String thumbnail) {
        this.text = text;
        this.title = title;
        this.tags = tags;
        this.price = price;
        this.datePublished = datePublished;
        this.link = link;
        this.thumbnail = thumbnail;
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

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public double getRating() {
        return 3;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
