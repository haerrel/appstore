package base.appstore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
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
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String datePublished;
  private Double rating;
  private String text;
  private Long userId;


  /**
   * Constructor used to initialize App object based on HTTP POST.
  */
  public Rating(Long id, String datePublished, Double rating, String text, Long userId) {
    this.id = id;
    this.datePublished = datePublished;
    this.rating = rating;
    this.text = text;
    this.userId = userId;
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

  public String getDatePublished() {
    return datePublished;
  }

  public void setDatePublished(String datePublished) {
    this.datePublished = datePublished;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
