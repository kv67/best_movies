package kve.ru.bestmovies.adapters;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kve.ru.bestmovies.R;
import kve.ru.bestmovies.data.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

  List<Trailer> trailers;

  private OnTrailerClickListener listener;

  public interface OnTrailerClickListener{
    void onTrailerClick(String url);
  }

  public void setTrailers(List<Trailer> trailers) {
    this.trailers = trailers;
    notifyDataSetChanged();
  }

  public void setListener(OnTrailerClickListener listener) {
    this.listener = listener;
  }

  @NonNull
  @Override
  public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent,
        false);
    return new TrailerViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
    Trailer trailer = trailers.get(position);
    holder.textViewNameOfVideo.setText(trailer.getName());
  }

  @Override
  public int getItemCount() {
    return trailers.size();
  }

  class TrailerViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewNameOfVideo;

    public TrailerViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewNameOfVideo = itemView.findViewById(R.id.textViewNameOfVideo);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null){
            listener.onTrailerClick(trailers.get(getAdapterPosition()).getKey());
          }
        }
      });
    }
  }
}
