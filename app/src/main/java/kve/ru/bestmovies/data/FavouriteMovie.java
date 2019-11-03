package kve.ru.bestmovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import kve.ru.bestmovies.pojo.movie.BestMovie;

@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends BestMovie {

  public FavouriteMovie() {
  }

  @Ignore
  public FavouriteMovie(BestMovie movie){
    super.setPopularity(movie.getPopularity());
    super.setVoteCount(movie.getVoteCount());
    super.setVideo(movie.getVideo());
    super.setPosterPath(movie.getPosterPath());
    super.setId(movie.getId());
    super.setAdult(movie.getAdult());
    super.setBackdropPath(movie.getBackdropPath());
    super.setOriginalLanguage(movie.getOriginalLanguage());
    super.setOriginalTitle(movie.getOriginalTitle());
    super.setGenreIds(movie.getGenreIds());
    super.setTitle(movie.getTitle());
    super.setVoteAverage(movie.getVoteAverage());
    super.setOverview(movie.getOverview());
    super.setReleaseDate(movie.getReleaseDate());
    super.setBigPosterPath(movie.getBigPosterPath());
  }

}
