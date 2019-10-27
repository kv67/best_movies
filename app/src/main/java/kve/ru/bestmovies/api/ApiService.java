package kve.ru.bestmovies.api;

import io.reactivex.Observable;
import kve.ru.bestmovies.pojo.MovieResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("discover/movie")
  Observable<MovieResponse> getMovies(@Query("api_key") String apiKey,
      @Query("language") String lang, @Query("sort_by") String sortMethod,
      @Query("vote_count.gte") String voteCount, @Query("page") String page);

}
