package kve.ru.bestmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.adapters.MovieAdapter;
import kve.ru.bestmovies.data.FavouriteMovie;
import kve.ru.bestmovies.data.MainViewModel;
import kve.ru.bestmovies.data.Movie;

public class FavouriteActivity extends AppCompatActivity {

  private RecyclerView recyclerViewFavourite;
  private MainViewModel viewModel;
  private MovieAdapter adapter;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite);

    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    adapter = new MovieAdapter();
    recyclerViewFavourite = findViewById(R.id.recyclerViewFavourite);
    recyclerViewFavourite.setLayoutManager(new GridLayoutManager(this,2));
    recyclerViewFavourite.setAdapter(adapter);
    adapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
      @Override
      public void onPosterClick(int position) {
        Movie movie = adapter.getMovies().get(position);
        Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("isFavourite", true);
        startActivity(intent);
      }
    });

    LiveData<List<FavouriteMovie>> moviesFromLiveData = viewModel.getFavouriteMovies();
    moviesFromLiveData.observe(this, new Observer<List<FavouriteMovie>>() {
      @Override
      public void onChanged(List<FavouriteMovie> favouriteMovies) {
        if (favouriteMovies != null) {
          List<Movie> movies = new ArrayList<>();
          movies.addAll(favouriteMovies);
          adapter.setMovies(movies);
        }
      }
    });
  }
}
