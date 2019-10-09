package kve.ru.bestmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import kve.ru.bestmovies.adapters.ReviewAdapter;
import kve.ru.bestmovies.adapters.TrailerAdapter;
import kve.ru.bestmovies.data.FavouriteMovie;
import kve.ru.bestmovies.data.MainViewModel;
import kve.ru.bestmovies.data.Movie;
import kve.ru.bestmovies.data.Review;
import kve.ru.bestmovies.data.Trailer;
import kve.ru.bestmovies.utils.JSONUtils;
import kve.ru.bestmovies.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

  private ImageView imageViewBigPoster;
  private ImageView imageViewAddToFavour;
  private TextView textViewTitle;
  private TextView textViewOriginalTitle;
  private TextView textViewRate;
  private TextView textViewReleaseDate;
  private TextView textViewOverview;
  private ScrollView scrollViewInfo;

  RecyclerView recyclerViewTrailers;
  RecyclerView recyclerViewReviews;
  ReviewAdapter reviewAdapter;
  TrailerAdapter trailerAdapter;


  private int id;
  private MainViewModel viewModel;
  private Movie movie;
  private FavouriteMovie favouriteMovie;

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
    setContentView(R.layout.activity_detail);

    imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
    imageViewAddToFavour = findViewById(R.id.imageViewAddToFavour);
    textViewTitle = findViewById(R.id.textViewTitle);
    textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
    textViewRate = findViewById(R.id.textViewRate);
    textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
    textViewOverview = findViewById(R.id.textViewOverview);
    scrollViewInfo = findViewById(R.id.scrollViewInfo);

    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

    Intent intent = getIntent();
    if (intent != null && intent.hasExtra("id")){
      id = intent.getIntExtra("id", -1);
      if (intent.hasExtra("isFavourite") && intent.getBooleanExtra("isFavourite",false)){
        movie = viewModel.getFavouriteMovieById(id);
      } else {
        movie = viewModel.getMovieById(id);
      }
    } else {
      finish();
    }

    Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.cadr).into(imageViewBigPoster);
    textViewTitle.setText(movie.getTitle());
    textViewOriginalTitle.setText(movie.getOriginalTitle());
    textViewRate.setText(String.valueOf(movie.getVoteAverage()));
    textViewReleaseDate.setText(movie.getReleaseDate());
    textViewOverview.setText(movie.getOverview());

    setFavourite();

    recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
    recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
    reviewAdapter = new ReviewAdapter();
    trailerAdapter = new TrailerAdapter();
    trailerAdapter.setListener(new TrailerAdapter.OnTrailerClickListener() {
      @Override
      public void onTrailerClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
      }
    });

    recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
    recyclerViewTrailers.setAdapter(trailerAdapter);
    recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
    recyclerViewReviews.setAdapter(reviewAdapter);

    JSONObject jsonTrailers = NetworkUtils.getJSONForVideos(movie.getId());
    JSONObject jsonReviews = NetworkUtils.getJSONForReviews(movie.getId());
    List<Trailer> trailers = JSONUtils.getTrailersFromJSON(jsonTrailers);
    List<Review> reviews = JSONUtils.getReviewsFromJSON(jsonReviews);
    trailerAdapter.setTrailers(trailers);
    reviewAdapter.setReviews(reviews);

    scrollViewInfo.smoothScrollTo(0, 0);
  }

  public void onClickChangeFavour(View view) {
    if (favouriteMovie == null){
      viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
      Toast.makeText(this, getString(R.string.msg_added), Toast.LENGTH_SHORT).show();
    } else {
      viewModel.deleteFavouriteMovie(favouriteMovie);
      Toast.makeText(this, getString(R.string.msg_removed), Toast.LENGTH_SHORT).show();
    }
    setFavourite();
  }

  private void setFavourite(){
    favouriteMovie = viewModel.getFavouriteMovieById(id);
    if (favouriteMovie == null){
      imageViewAddToFavour.setImageResource(R.drawable.star_silver);
    } else {
      imageViewAddToFavour.setImageResource(R.drawable.star_big);
    }
  }
}
