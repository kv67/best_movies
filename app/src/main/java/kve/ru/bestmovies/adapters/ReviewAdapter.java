package kve.ru.bestmovies.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.R;
import kve.ru.bestmovies.data.Review;
import kve.ru.bestmovies.pojo.review.MovieReview;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

  private List<MovieReview> reviews = new ArrayList<>();
  public void setReviews(List<MovieReview> reviews) {
    this.reviews = reviews;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent,
        false);
    return new ReviewViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
    MovieReview review = reviews.get(position);
    holder.textViewAuthor.setText(review.getAuthor());
    holder.textViewContent.setText(review.getContent());
  }

  @Override
  public int getItemCount() {
    return reviews.size();
  }

  class ReviewViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewAuthor;
    private TextView textViewContent;

    public ReviewViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
      textViewContent = itemView.findViewById(R.id.textViewContent);
    }
  }
}
