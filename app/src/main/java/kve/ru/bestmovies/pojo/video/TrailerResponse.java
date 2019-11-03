package kve.ru.bestmovies.pojo.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("results")
  @Expose
  private List<VideoTrailer> trailers = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<VideoTrailer> getTrailers() {
    return trailers;
  }

  public void setTrailers(List<VideoTrailer> trailers) {
    this.trailers = trailers;
  }
}
