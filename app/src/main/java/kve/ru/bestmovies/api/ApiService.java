package kve.ru.bestmovies.api;

import io.reactivex.Observable;
import kve.ru.bestmovies.pojo.cast.CastResponse;
import kve.ru.bestmovies.pojo.countries.CountriesResponse;
import kve.ru.bestmovies.pojo.movie.MovieResponse;
import kve.ru.bestmovies.pojo.review.ReviewResponse;
import kve.ru.bestmovies.pojo.video.TrailerResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

  @GET("discover/movie")
  Observable<MovieResponse> getMovies(@Query("api_key") String apiKey,
      @Query("language") String lang, @Query("sort_by") String sortMethod,
      @Query("vote_count.gte") String voteCount, @Query("page") String page);

  @GET("movie/{id}/credits")
  Observable<CastResponse> getCast(@Path("id") int id, @Query("api_key") String apiKey);

  @GET("movie/{id}/videos")
  Observable<TrailerResponse> getVideo(@Path("id") int id, @Query("api_key") String apiKey,
      @Query("language") String lang);

  @GET("movie/{id}/reviews")
  Observable<ReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey,
      @Query("language") String lang);

  @GET("movie/{id}")
  Observable<CountriesResponse> getCountries(@Path("id") int id, @Query("api_key") String apiKey,
      @Query("language") String lang);
}
