package kve.ru.bestmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.adapters.MovieAdapter;
import kve.ru.bestmovies.data.MainViewModel;
import kve.ru.bestmovies.pojo.BestMovie;

public class FavouriteActivity extends AppCompatActivity {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite);

    MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    MovieAdapter adapter = new MovieAdapter();
    RecyclerView recyclerViewFavourite = findViewById(R.id.recyclerViewFavourite);
    recyclerViewFavourite.setLayoutManager(new GridLayoutManager(this,2));
    recyclerViewFavourite.setAdapter(adapter);
    adapter.setOnPosterClickListener(position -> {
      BestMovie movie = adapter.getMovies().get(position);
      Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
      intent.putExtra("id", movie.getId());
      intent.putExtra("isFavourite", true);
      startActivity(intent);
    });

    viewModel.getFavouriteMovies().observe(this, favouriteMovies -> {
      if (favouriteMovies != null) {
        List<BestMovie> movies = new ArrayList<>();
        movies.addAll(favouriteMovies);
        for (BestMovie movie : movies){
          Log.i("Favor", movie.getTitle());
        }
        adapter.setMovies(movies);
      }
    });

  }
}
