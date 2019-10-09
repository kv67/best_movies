package kve.ru.bestmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import kve.ru.bestmovies.adapters.MovieAdapter;
import kve.ru.bestmovies.data.MainViewModel;
import kve.ru.bestmovies.data.Movie;
import kve.ru.bestmovies.utils.JSONUtils;
import kve.ru.bestmovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

  private static final int LOADER_ID = 133;

  private static String lang;
  private static int page = 1;
  private static int methodOfSort;
  private static boolean isLoading = false;

  private LoaderManager loaderManager;
  private Switch switchSort;
  private RecyclerView recyclerViewPosters;
  private MovieAdapter adapter;
  private TextView textViewPopularity;
  private TextView textViewTopRated;
  private MainViewModel viewModel;
  private ProgressBar progressBarLoading;

  public static String getLang() {
    return lang;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    Intent intent = null;
    switch (id){
      case R.id.menuMain:
        intent = new Intent(this, MainActivity.class);
        break;
      case R.id.menuFavourite:
        intent = new Intent(this, FavouriteActivity.class);

        break;
    }
    if (intent != null){
      startActivity(intent);
    }
    return super.onOptionsItemSelected(item);
  }

  private int getColumnCount(){
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
    return width / 185 > 3 ? width / 185 : 3;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lang = Locale.getDefault().getLanguage();

    loaderManager = LoaderManager.getInstance(this);
    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    textViewPopularity = findViewById(R.id.textViewPopularity);
    textViewTopRated = findViewById(R.id.textViewTopRated);
    switchSort = findViewById(R.id.switchSort);
    progressBarLoading = findViewById(R.id.progressBarLoading);
    recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
    recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
    adapter = new MovieAdapter();
    recyclerViewPosters.setAdapter(adapter);
    switchSort.setChecked(true);
    switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        page = 1;
        setMethodOfSort(isChecked);
      }
    });
    switchSort.setChecked(false);
    adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
      @Override
      public void onPosterClick(int position) {
        Movie movie = adapter.getMovies().get(position);
        // Log.i("MyLog", "" + movie.getId());
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("isFavourite", false);
        startActivity(intent);
      }
    });
    adapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
      @Override
      public void OnReachEnd() {
        if (!isLoading) {
          downloadData(methodOfSort, page);
        }
      }
    });

    LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
    moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
      @Override
      public void onChanged(List<Movie> movies) {
        if (page == 1){
          adapter.setMovies(movies);
        }
      }
    });
  }

  public void onClickPopularity(View view) {
    setMethodOfSort(false);
    switchSort.setChecked(false);
  }

  public void onClickTopRated(View view) {
    setMethodOfSort(true);
    switchSort.setChecked(true);
  }

  private void setMethodOfSort(boolean isTopRated){
    if (isTopRated){
      textViewPopularity.setTextColor(getResources().getColor(R.color.color_white));
      textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
      methodOfSort = NetworkUtils.TOP_RATED;
    } else {
      textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
      textViewTopRated.setTextColor(getResources().getColor(R.color.color_white));
      methodOfSort = NetworkUtils.POPULARITY;
    }
    downloadData(methodOfSort, page);
  }

  private void downloadData(int methodOfSort, int page){
    URL url = NetworkUtils.buildURL(methodOfSort, page);
    Bundle bundle = new Bundle();
    bundle.putString("url", url.toString());
    loaderManager.restartLoader(LOADER_ID, bundle, this);
  }

  @NonNull
  @Override
  public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle bundle) {
    NetworkUtils.JSONLoader loader = new NetworkUtils.JSONLoader(this, bundle);
    loader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
      @Override
      public void onStartLoading() {
        progressBarLoading.setVisibility(View.VISIBLE);
        isLoading = true;
      }
    });
    return loader;
  }

  @Override
  public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject jsonObject) {
    List<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
    if (movies != null && !movies.isEmpty()){
      if (page == 1) {
        viewModel.deleteAllMovies();
        adapter.clear();
      }
      for (Movie movie : movies){
        viewModel.insertMovie(movie);
      }
      adapter.addMovies(movies);
      page++;
    }
    isLoading = false;
    progressBarLoading.setVisibility(View.INVISIBLE);
    loaderManager.destroyLoader(LOADER_ID);
  }

  @Override
  public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

  }
}
