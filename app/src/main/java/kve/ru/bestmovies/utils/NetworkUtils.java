package kve.ru.bestmovies.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import kve.ru.bestmovies.MainActivity;

public class NetworkUtils {

  private static final String TAG = "NetworkUtils";

  private static final String BASIC_URL = "https://api.themoviedb.org/3/discover/movie";
  private static final String BASIC_URL_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos";
  private static final String BASIC_URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";
  private static final String BASIC_URL_CAST = "https://api.themoviedb.org/3/movie/%s/credits";
  private static final String BASIC_URL_COUNTRIES = "https://api.themoviedb.org/3/movie/%s";

  private static final String PARAMS_API_KEY = "api_key";
  private static final String PARAMS_LANGUAGE = "language";
  private static final String PARAMS_SORT_BY = "sort_by";
  private static final String PARAMS_PAGE = "page";
  private static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";

  private static final String API_KEY = "1b097d2d522fb2f73b520ed253664d65";
  private static final String SORT_BY_POPULARITY = "popularity.desc";
  private static final String SORT_BY_TOP_RATED = "vote_average.desc";
  private static final String MIN_VOTE_COUNT_VALUE = "1000";

  public static final int POPULARITY = 0;
  public static final int TOP_RATED = 1;

  private NetworkUtils() {
     throw new IllegalStateException("Utility class");
  }

  public static URL buildURLToCountries(int id){
    Uri uri = Uri.parse(String.format(BASIC_URL_COUNTRIES, id)).buildUpon()
        .appendQueryParameter(PARAMS_API_KEY, API_KEY)
        .appendQueryParameter(PARAMS_LANGUAGE, MainActivity.getLang())
        .build();
    try {
      return new URL(uri.toString());
    } catch (MalformedURLException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return null;
  }

  public static URL buildURLToCredits(int id){
    Uri uri = Uri.parse(String.format(BASIC_URL_CAST, id)).buildUpon()
        .appendQueryParameter(PARAMS_API_KEY, API_KEY)
        .build();
    try {
      return new URL(uri.toString());
    } catch (MalformedURLException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return null;
  }

  public static URL buildURLToVideos(int id){
    Uri uri = Uri.parse(String.format(BASIC_URL_VIDEOS, id)).buildUpon()
        .appendQueryParameter(PARAMS_API_KEY, API_KEY)
        .appendQueryParameter(PARAMS_LANGUAGE, MainActivity.getLang())
        .build();
    try {
      return new URL(uri.toString());
    } catch (MalformedURLException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return null;
  }

  public static URL buildURLToReviews(int id){
    Uri uri = Uri.parse(String.format(BASIC_URL_REVIEWS, id)).buildUpon()
        .appendQueryParameter(PARAMS_API_KEY, API_KEY)
        .appendQueryParameter(PARAMS_LANGUAGE, MainActivity.getLang())
        .build();
    try {
      return new URL(uri.toString());
    } catch (MalformedURLException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }
    return null;
  }

  public static URL buildURL(int sortBy, int page){
    URL result = null;
    String methodOfSort;
    if (sortBy == POPULARITY){
      methodOfSort = SORT_BY_POPULARITY;
    } else {
      methodOfSort = SORT_BY_TOP_RATED;
    }
    Uri uri = Uri.parse(BASIC_URL).buildUpon()
        .appendQueryParameter(PARAMS_API_KEY, API_KEY)
        .appendQueryParameter(PARAMS_LANGUAGE, MainActivity.getLang())
        .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
        .appendQueryParameter(PARAMS_MIN_VOTE_COUNT, MIN_VOTE_COUNT_VALUE)
        .appendQueryParameter(PARAMS_PAGE, String.valueOf(page))
        .build();

    try {
      result = new URL(uri.toString());
    } catch (MalformedURLException e) {
      Log.i(TAG, e.getLocalizedMessage());
    }

    return result;
  }

  public static JSONObject getJSONForCountries(int id){
    JSONObject result = null;
    URL url = buildURLToCountries(id);
    try {
      result = new JSONLoadTask().execute(url).get();
    } catch (ExecutionException e) {
      Log.i(TAG, e.getLocalizedMessage());
    } catch (InterruptedException e) {
      Log.i(TAG, e.getLocalizedMessage());
      Thread.currentThread().interrupt();
    }

    return result;
  }

  public static JSONObject getJSONForCredits(int id){
    JSONObject result = null;
    URL url = buildURLToCredits(id);
    try {
      result = new JSONLoadTask().execute(url).get();
    } catch (ExecutionException e) {
      Log.i(TAG, e.getLocalizedMessage());
    } catch (InterruptedException e) {
      Log.i(TAG, e.getLocalizedMessage());
      Thread.currentThread().interrupt();
    }

    return result;
  }

  public static JSONObject getJSONForVideos(int id){
    JSONObject result = null;
    URL url = buildURLToVideos(id);
    try {
      result = new JSONLoadTask().execute(url).get();
    } catch (ExecutionException e) {
      Log.i(TAG, e.getLocalizedMessage());
    } catch (InterruptedException e) {
      Log.i(TAG, e.getLocalizedMessage());
      Thread.currentThread().interrupt();
    }

    return result;
  }

  public static JSONObject getJSONForReviews(int id){
    JSONObject result = null;
    URL url = buildURLToReviews(id);
    try {
      result = new JSONLoadTask().execute(url).get();
    } catch (ExecutionException e) {
      Log.i(TAG, e.getLocalizedMessage());
    } catch (InterruptedException e) {
      Log.i(TAG, e.getLocalizedMessage());
      Thread.currentThread().interrupt();
    }

    return result;
  }

  public static JSONObject getJSONFromNetwork(int sortBy, int page){
    JSONObject result = null;
    URL url = buildURL(sortBy, page);
    try {
      result = new JSONLoadTask().execute(url).get();
    } catch (ExecutionException e) {
      Log.i(TAG, e.getLocalizedMessage());
    } catch (InterruptedException e) {
      Log.i(TAG, e.getLocalizedMessage());
      Thread.currentThread().interrupt();
    }

    return result;
  }

  public static class JSONLoader extends AsyncTaskLoader<JSONObject>{

    private OnStartLoadingListener onStartLoadingListener;
    private Bundle bundle;

    public interface OnStartLoadingListener{
      void onStartLoading();
    }

    public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
      this.onStartLoadingListener = onStartLoadingListener;
    }

    public JSONLoader(@NonNull Context context, Bundle bundle) {
      super(context);
      this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
      super.onStartLoading();
      if (onStartLoadingListener != null){
        onStartLoadingListener.onStartLoading();
      }
      forceLoad();
    }

    @Nullable
    @Override
    public JSONObject loadInBackground() {
      JSONObject result = null;
      if (bundle != null) {
        HttpURLConnection connection = null;
        try {
          connection = (HttpURLConnection) new URL( bundle.getString("url")).openConnection();
          try (
              BufferedReader reader = new BufferedReader(
              new InputStreamReader(connection.getInputStream()));) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
              builder.append(line);
              line = reader.readLine();
            }
            result = new JSONObject(builder.toString());
          }
        } catch (IOException | JSONException e) {
           Log.i(TAG, e.getLocalizedMessage());
        } finally {
          if (connection != null) {
            connection.disconnect();
          }
        }
      }
      return result;
    }
  }

  private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject>{
    @Override
    protected JSONObject doInBackground(URL... urls) {
      JSONObject result = null;

      if (urls != null && urls.length > 0) {
        HttpURLConnection connection = null;
        try {
          connection = (HttpURLConnection) urls[0].openConnection();
          try (BufferedReader reader = new BufferedReader(
              new InputStreamReader(connection.getInputStream()));) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
              builder.append(line);
              line = reader.readLine();
            }
            result = new JSONObject(builder.toString());
          }
        } catch (IOException | JSONException e) {
          Log.i(TAG, e.getLocalizedMessage());
        } finally {
          if (connection != null) {
            connection.disconnect();
          }
        }
      }
      return result;
    }
  }

}
