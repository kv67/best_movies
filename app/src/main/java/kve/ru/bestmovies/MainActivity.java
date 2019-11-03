package kve.ru.bestmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import kve.ru.bestmovies.adapters.MovieAdapter;
import kve.ru.bestmovies.data.MainViewModel;
import kve.ru.bestmovies.pojo.movie.BestMovie;
import kve.ru.bestmovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity  {

  private static String lang = Locale.getDefault().getLanguage();
  private static int page;
  private static int methodOfSort = 0;
  private static boolean isLoading = false;

  private Switch switchSort;
  private TextView textViewPopularity;
  private TextView textViewTopRated;
  private MainViewModel viewModel;
  private ProgressBar progressBarLoading;

  public static String getLang() {
    return lang;
  }

  private static void setPage(int page) {
    MainActivity.page = page;
  }

  private static void setMethodOfSort(int methodOfSort) {
    MainActivity.methodOfSort = methodOfSort;
  }

  private static void setIsLoading(boolean isLoading) {
    MainActivity.isLoading = isLoading;
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
        default:
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
    return width / 185 > 2 ? width / 185 : 2;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    textViewPopularity = findViewById(R.id.textViewPopularity);
    textViewTopRated = findViewById(R.id.textViewTopRated);
    switchSort = findViewById(R.id.switchSort);
    progressBarLoading = findViewById(R.id.progressBarLoading);
    RecyclerView recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
    recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, getColumnCount()));
    MovieAdapter adapter = new MovieAdapter();
    recyclerViewPosters.setAdapter(adapter);

    switchSort.setChecked(true);
    switchSort.setOnCheckedChangeListener((buttonView, isChecked) -> {
      setPage(1);
      setMethodOfSort(isChecked);
    });
    switchSort.setChecked(false);

    adapter.setOnPosterClickListener(position -> {
      BestMovie movie = adapter.getMovies().get(position);
      Intent intent = new Intent(MainActivity.this, DetailActivity.class);
      intent.putExtra("id", movie.getId());
      intent.putExtra("isFavourite", false);
      startActivity(intent);
    });
    adapter.setOnReachEndListener(() -> {
      if (!isLoading) {
        downloadData(methodOfSort, page);
      }
    });

    viewModel.getMovies().observe(this, new Observer<List<BestMovie>>() {
      @Override
      public void onChanged(List<BestMovie> bestMovies) {
        if (bestMovies != null && !bestMovies.isEmpty()) {
          adapter.setMovies(bestMovies);
          setPage(page + 1);
        }
        setIsLoading(false);
        progressBarLoading.setVisibility(View.INVISIBLE);
      }
    });

    viewModel.getErrors().observe(this, throwable -> {
      if (throwable != null) {
        Toast.makeText(MainActivity.this, "Error: " + throwable.getLocalizedMessage(),
            Toast.LENGTH_SHORT).show();
        Log.i("MOVIE_ERR", "Error: " + throwable.getLocalizedMessage());
        viewModel.clearErrors();
        setIsLoading(false);
        progressBarLoading.setVisibility(View.INVISIBLE);
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
      setMethodOfSort(NetworkUtils.TOP_RATED);
    } else {
      textViewPopularity.setTextColor(getResources().getColor(R.color.colorAccent));
      textViewTopRated.setTextColor(getResources().getColor(R.color.color_white));
      setMethodOfSort(NetworkUtils.POPULARITY);
    }
    downloadData(methodOfSort, page);
  }

  private void downloadData(int methodOfSort, int page) {
    progressBarLoading.setVisibility(View.VISIBLE);
    setIsLoading(true);
    viewModel.loadData(lang, methodOfSort, page);
  }
}
