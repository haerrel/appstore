package base.appstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * App.
 * Model class persisted using JPA
 * 
 * @author Gudrun Socher
 *
 * @version 1.0
 *
 */
@Entity
public class App {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String text;
  private String tags;
  private String title;
  private Integer price;
  private String datePublished;

  public App() {}

  /**
   * Constructor used to initialize App object based on HTTP POST.
   * 
   * @param text Text of an App.
   * @param tags Tags of an App as a list of strings.
   * @param title Title of an App.
   */
  public App(String text, String tags, String title, Integer price, String datePublished) {
    this.text = text;
    this.tags = tags;
    this.title = title;
    this.price = price;
    this.datePublished = datePublished;
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

  public String getTags() {
//    return Arrays.asList(tags.split(","));
      return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getDatePublished() {
    return datePublished;
  }

  public void setDatePublished(String datePublished) {
    this.datePublished = datePublished;
  }

  public double getRating() {
    return 0; //TODO
  }
}
