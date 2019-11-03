package kve.ru.bestmovies.pojo.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
  @SerializedName("results")
  @Expose
  private List<MovieReview> reviews = null;

  public List<MovieReview> getReviews() {
    return reviews;
  }

  public void setReviews(List<MovieReview> reviews) {
    this.reviews = reviews;
  }
}
