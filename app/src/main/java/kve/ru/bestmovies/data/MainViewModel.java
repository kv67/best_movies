package kve.ru.bestmovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kve.ru.bestmovies.api.ApiFactory;
import kve.ru.bestmovies.api.ApiService;
import kve.ru.bestmovies.pojo.BestMovie;
import kve.ru.bestmovies.utils.NetworkUtils;

public class MainViewModel extends AndroidViewModel {

  private static MovieDatabase database;
  private LiveData<List<BestMovie>> movies;
  private LiveData<List<FavouriteMovie>> favouriteMovies;
  private MutableLiveData<Throwable> errors =  new MutableLiveData<>();
  private CompositeDisposable compositeDisposable;

  private static void setDataBase(MovieDatabase database){
    MainViewModel.database = database;
  }

  public MainViewModel(@NonNull Application application) {
    super(application);
    setDataBase(MovieDatabase.getInstance(application));
    movies = database.movieDao().getAllMovies();
    favouriteMovies = database.movieDao().getAllFavouriteMovies();
  }

  public LiveData<List<BestMovie>> getMovies() {
    return movies;
  }

  public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
    return favouriteMovies;
  }

  public MutableLiveData<Throwable> getErrors() {
    return errors;
  }

  public void clearErrors(){
    errors.setValue(null);
  }

  public void loadData(String lang, int sortMethod, int page){
    compositeDisposable = new CompositeDisposable();
    ApiFactory apiFactory = ApiFactory.getInstance();
    ApiService apiService = apiFactory.getApiService();
    String methodOfSort;
    if (sortMethod == NetworkUtils.POPULARITY){
      methodOfSort = NetworkUtils.SORT_BY_POPULARITY;
    } else {
      methodOfSort = NetworkUtils.SORT_BY_TOP_RATED;
    }
    Disposable disposable = apiService.getMovies(NetworkUtils.API_KEY, lang,
        methodOfSort,
        NetworkUtils.MIN_VOTE_COUNT_VALUE, String.valueOf(page))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieResponse -> {
          if (page == 1){
            deleteAllMovies();
          }
          insertMovies(movieResponse.getMovies());
        }, throwable -> errors.setValue(throwable));
    compositeDisposable.add(disposable);
  }

  @Override
  protected void onCleared() {
    if (compositeDisposable != null){
      compositeDisposable.dispose();
    }
    super.onCleared();
  }

  public BestMovie getMovieById(int id){
    try {
      return new GetMovieTask().execute(id).get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    return null;
  }

  public FavouriteMovie getFavouriteMovieById(int id){
    try {
      return new GetFavouriteMovieTask().execute(id).get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
    return null;
  }

  public void insertMovie(BestMovie movie){
    new InsertMovieTask().execute(movie);
  }

  public void deleteMovie(BestMovie movie){
    new DeleteMovieTask().execute(movie);
  }

  public void insertFavouriteMovie(FavouriteMovie movie){
    new InsertFavouriteMovieTask().execute(movie);
  }

  public void deleteFavouriteMovie(FavouriteMovie movie){
    new DeleteFavouriteMovieTask().execute(movie);
  }

  public void deleteAllMovies(){
    new DeleteAllMoviesTask().execute();
  }

  @SuppressWarnings("unchecked")
  public void insertMovies(List<BestMovie> movies){
    new InsertMoviesTask().execute(movies);
  }

  private static class InsertMoviesTask extends AsyncTask<List<BestMovie>, Void, Void>{
    @SafeVarargs
    @Override
    protected final  Void doInBackground(List<BestMovie>... lists) {
      if (lists != null && lists.length > 0){
        database.movieDao().insertMovies(lists[0]);
      }
      return null;
    }
  }

  private static class GetMovieTask extends AsyncTask<Integer, Void, BestMovie>{
    @Override
    protected BestMovie doInBackground(Integer... integers) {
      if (integers != null && integers.length > 0){
        return database.movieDao().getMovieById(integers[0]);
      }
      return null;
    }
  }

  private static class GetFavouriteMovieTask extends AsyncTask<Integer, Void, FavouriteMovie>{
    @Override
    protected FavouriteMovie doInBackground(Integer... integers) {
      if (integers != null && integers.length > 0){
        return database.movieDao().getFavouriteMovieById(integers[0]);
      }
      return null;
    }
  }

  private static class InsertMovieTask extends AsyncTask<BestMovie, Void, Void>{
    @Override
    protected Void doInBackground(BestMovie... movies) {
      if (movies != null && movies.length > 0){
        database.movieDao().insertMovie(movies[0]);
      }
      return null;
    }
  }

  private static class DeleteMovieTask extends AsyncTask<BestMovie, Void, Void>{
    @Override
    protected Void doInBackground(BestMovie... movies) {
      if (movies != null && movies.length > 0){
        database.movieDao().deleteMovie(movies[0]);
      }
      return null;
    }
  }

  private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{
    @Override
    protected Void doInBackground(FavouriteMovie... movies) {
      if (movies != null && movies.length > 0){
        database.movieDao().insertFavouriteMovie(movies[0]);
      }
      return null;
    }
  }

  private static class DeleteFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void>{
    @Override
    protected Void doInBackground(FavouriteMovie... movies) {
      if (movies != null && movies.length > 0){
        database.movieDao().deleteFavouriteMovie(movies[0]);
      }
      return null;
    }
  }

  private static class DeleteAllMoviesTask extends AsyncTask<Void, Void, Void>{
    @Override
    protected Void doInBackground(Void... voids) {
      database.movieDao().deleteAllMovies();
      return null;
    }
  }
}
