package kve.ru.bestmovies.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.data.Movie;
import kve.ru.bestmovies.data.Review;
import kve.ru.bestmovies.data.Trailer;

public class JSONUtils {

  private static final String TAG = "JSONUtils";

  private static final String KEY_RESULTS = "results";

  // Для актеров
  private static final String KEY_CAST = "cast";

  // Для отзывов
  private static final String KEY_AUTHOR = "author";
  private static final String KEY_CONTENT = "content";

  // Для видео
  private static final String KEY_KEY_OF_VIDEO ="key";
  private static final String KEY_NAME = "name";
  private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

  // Вся информация о фильмах
  private static final String KEY_ID = "id";
  private static final String KEY_VOTE_COUNT = "vote_count";
  private static final String KEY_TITLE = "title";
  private static final String KEY_ORIGINAL_TITLE = "original_title";
  private static final String KEY_OVERVIEW = "overview";
  private static final String KEY_POSTER_PATH = "poster_path";
  private static final String KEY_BACKDROP_PATH = "backdrop_path";
  private static final String KEY_VOTE_AVERAGE = "vote_average";
  private static final String KEY_RELEASE_DATE = "release_date";

  public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
  public static final String SMALL_POSTER_SIZE = "w185";
  public static final String BIG_POSTER_SIZE = "w780";


  private JSONUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getCastFromJSON(JSONObject jsonObject){
    String result = null;
    if (jsonObject == null){
      return result;
    }
    try {
      JSONArray cust = jsonObject.getJSONArray(KEY_CAST);
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < KEY_CAST.length(); i++) {
        String name = cust.getJSONObject(i).getString(KEY_NAME);
        if (name != null && !name.isEmpty()) {
          if (builder.length() == 0) {
            builder.append(name);
          } else {
            builder.append(", " + name);
          }
        }
      }
      result = builder.toString();
    } catch (JSONException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return result;
  }

  public static List<Review> getReviewsFromJSON(JSONObject jsonObject){
    List<Review> result = new ArrayList<>();
    if (jsonObject == null){
      return result;
    }
    try {
      JSONArray reviews = jsonObject.getJSONArray(KEY_RESULTS);
      for (int i = 0; i < reviews.length(); i++) {
        Review review = new Review(reviews.getJSONObject(i).getString(KEY_AUTHOR),
            reviews.getJSONObject(i).getString(KEY_CONTENT));
        result.add(review);
      }
    } catch (JSONException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return result;
  }

  public static List<Trailer> getTrailersFromJSON(JSONObject jsonObject){
    List<Trailer> result = new ArrayList<>();
    if (jsonObject == null){
      return result;
    }
    try {
      JSONArray trailers = jsonObject.getJSONArray(KEY_RESULTS);
      for (int i = 0; i < trailers.length(); i++) {
        Trailer trailer =
            new Trailer(BASE_YOUTUBE_URL + trailers.getJSONObject(i).getString(KEY_KEY_OF_VIDEO),
            trailers.getJSONObject(i).getString(KEY_NAME));
        result.add(trailer);
      }
    } catch (JSONException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return result;
  }

  public static List<Movie> getMoviesFromJSON(JSONObject jsonObject){
    List<Movie> result = new ArrayList<>();
    if (jsonObject == null){
      return result;
    }
    try {
      JSONArray movies = jsonObject.getJSONArray(KEY_RESULTS);
      for (int i = 0; i < movies.length(); i++) {
        Movie movie = new Movie(movies.getJSONObject(i).getInt(KEY_ID),
            movies.getJSONObject(i).getInt(KEY_VOTE_COUNT),
            movies.getJSONObject(i).getString(KEY_TITLE),
            movies.getJSONObject(i).getString(KEY_ORIGINAL_TITLE),
            movies.getJSONObject(i).getString(KEY_OVERVIEW),
            BASE_POSTER_URL + SMALL_POSTER_SIZE +
                movies.getJSONObject(i).getString(KEY_POSTER_PATH),
            BASE_POSTER_URL + BIG_POSTER_SIZE +
                movies.getJSONObject(i).getString(KEY_POSTER_PATH),
            movies.getJSONObject(i).getString(KEY_BACKDROP_PATH),
            movies.getJSONObject(i).getDouble(KEY_VOTE_AVERAGE),
            movies.getJSONObject(i).has(KEY_RELEASE_DATE) ?
            movies.getJSONObject(i).getString(KEY_RELEASE_DATE) : "");
        result.add(movie);
      }
    } catch (JSONException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }

    return result;
  }

}
