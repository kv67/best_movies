package kve.ru.bestmovies.pojo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kve.ru.bestmovies.converters.Converter;

import static kve.ru.bestmovies.utils.JSONUtils.BASE_POSTER_URL;
import static kve.ru.bestmovies.utils.JSONUtils.BIG_POSTER_SIZE;
import static kve.ru.bestmovies.utils.JSONUtils.SMALL_POSTER_SIZE;

@Entity(tableName = "movies")
public class BestMovie {
  @PrimaryKey(autoGenerate = true)
  private int uniqueId;
  @SerializedName("popularity")
  @Expose
  private Double popularity;
  @SerializedName("vote_count")
  @Expose
  private Integer voteCount;
  @SerializedName("video")
  @Expose
  private Boolean video;
  @SerializedName("poster_path")
  @Expose
  private String posterPath;
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("adult")
  @Expose
  private Boolean adult;
  @SerializedName("backdrop_path")
  @Expose
  private String backdropPath;
  @SerializedName("original_language")
  @Expose
  private String originalLanguage;
  @SerializedName("original_title")
  @Expose
  private String originalTitle;
  @SerializedName("genre_ids")
  @Expose
  @TypeConverters(value = Converter.class)
  private List<Integer> genreIds = null;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("vote_average")
  @Expose
  private Double voteAverage;
  @SerializedName("overview")
  @Expose
  private String overview;
  @SerializedName("release_date")
  @Expose
  private String releaseDate;

  private String bigPosterPath;

  public int getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(int uniqueId) {
    this.uniqueId = uniqueId;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

  public Boolean getVideo() {
    return video;
  }

  public void setVideo(Boolean video) {
    this.video = video;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getAdult() {
    return adult;
  }

  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public List<Integer> getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    if (posterPath.startsWith(BASE_POSTER_URL + SMALL_POSTER_SIZE)){
      this.posterPath = posterPath;
    } else {
      this.posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + posterPath;
      this.bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + posterPath;
    }
  }

  public String getBigPosterPath() {
    return bigPosterPath;
  }

  public void setBigPosterPath(String bigPosterPath) {
    this.bigPosterPath = bigPosterPath;
  }
}
