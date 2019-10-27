package kve.ru.bestmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.R;
import kve.ru.bestmovies.pojo.BestMovie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

  private List<BestMovie> movies;
  private OnPosterClickListener onPosterClickListener;
  private OnReachEndListener onReachEndListener;

  public interface OnPosterClickListener{
    void onPosterClick(int position);
  }

  public interface OnReachEndListener{
    void onReachEnd();
  }

  public MovieAdapter() {
    movies = new ArrayList<>();
  }

  public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
    this.onPosterClickListener = onPosterClickListener;
  }

  public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
    this.onReachEndListener = onReachEndListener;
  }

  public List<BestMovie> getMovies() {
    return movies;
  }

  public void setMovies(List<BestMovie> movies) {
    this.movies = movies;
    notifyDataSetChanged();
  }

  public void addMovies(List<BestMovie> movies){
    this.movies.addAll(movies);
    notifyDataSetChanged();
  }

  public void clear(){
    this.movies.clear();
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent,
        false);
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    if (movies.size() >= 20 &&  position == movies.size() - 8 && onReachEndListener != null){
      onReachEndListener.onReachEnd();
    }
    BestMovie movie = movies.get(position);
    Picasso.get().load(movie.getPosterPath()).into(holder.imageViewSmallPoster);
  }

  @Override
  public int getItemCount() {
    return movies.size();
  }

  class MovieViewHolder extends RecyclerView.ViewHolder{

    private ImageView imageViewSmallPoster;

    public MovieViewHolder(@NonNull View itemView) {
      super(itemView);
      imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
      itemView.setOnClickListener(v -> {
        if (onPosterClickListener != null){
          onPosterClickListener.onPosterClick(getAdapterPosition());
        }
      });
    }
  }
}
