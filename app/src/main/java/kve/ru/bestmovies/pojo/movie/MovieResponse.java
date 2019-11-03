package kve.ru.bestmovies.pojo.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
  @SerializedName("results")
  @Expose
  private List<BestMovie> movies = null;

  public List<BestMovie> getMovies() {
    return movies;
  }

  public void setMovies(List<BestMovie> movies) {
    this.movies = movies;
  }

}
