package kve.ru.bestmovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kve.ru.bestmovies.pojo.BestMovie;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM movies")
  LiveData<List<BestMovie>> getAllMovies();

  @Query("SELECT * FROM favourite_movies")
  LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

  @Query("SELECT * FROM movies WHERE id = :movieId")
  BestMovie getMovieById(int movieId);

  @Query("SELECT * FROM favourite_movies WHERE id = :movieId")
  FavouriteMovie getFavouriteMovieById(int movieId);

  @Query("DELETE FROM movies")
  void deleteAllMovies();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMovies(List<BestMovie> movies);

  @Insert
  void insertMovie(BestMovie movie);

  @Delete
  void deleteMovie(BestMovie movie);

  @Insert
  void insertFavouriteMovie(FavouriteMovie movie);

  @Delete
  void deleteFavouriteMovie(FavouriteMovie movie);
}
