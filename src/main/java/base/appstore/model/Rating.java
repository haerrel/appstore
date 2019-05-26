package base.appstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
  private Double value;
  private String text;
  private Long userId;
  private Long appId;


  /**
   * Constructor used to initialize App object based on HTTP POST.
  */
  public Rating(Long id, String datePublished, Double rating, String text, Long userId, Long appId) {
    this.id = id;
    this.datePublished = datePublished;
    this.value = rating;
    this.text = text;
    this.userId = userId;
    this.appId = appId;
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
    return value;
  }

  public void setRating(Double rating) {
    this.value = rating;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getAppId() {
    return appId;
  }

  public void setAppId(Long appId) {
    this.appId = appId;
  }
}
