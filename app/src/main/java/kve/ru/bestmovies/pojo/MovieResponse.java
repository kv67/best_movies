package kve.ru.bestmovies.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
  @SerializedName("page")
  @Expose
  private Integer page;
  @SerializedName("total_results")
  @Expose
  private Integer totalResults;
  @SerializedName("total_pages")
  @Expose
  private Integer totalPages;
  @SerializedName("results")
  @Expose
  private List<BestMovie> movies = null;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(Integer totalResults) {
    this.totalResults = totalResults;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public List<BestMovie> getMovies() {
    return movies;
  }

  public void setMovies(List<BestMovie> movies) {
    this.movies = movies;
  }

}
