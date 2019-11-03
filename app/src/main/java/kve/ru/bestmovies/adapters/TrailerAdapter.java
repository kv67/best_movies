package kve.ru.bestmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kve.ru.bestmovies.R;
import kve.ru.bestmovies.data.Trailer;
import kve.ru.bestmovies.pojo.video.VideoTrailer;
import kve.ru.bestmovies.utils.JSONUtils;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

  List<VideoTrailer> trailers = new ArrayList<>();

  private OnTrailerClickListener listener;

  public interface OnTrailerClickListener{
    void onTrailerClick(String url);
  }

  public void setTrailers(List<VideoTrailer> trailers) {
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
    VideoTrailer trailer = trailers.get(position);
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
      itemView.setOnClickListener(v -> {
        if (listener != null){
          listener.onTrailerClick(JSONUtils.BASE_YOUTUBE_URL +
              trailers.get(getAdapterPosition()).getKey());
        }
      });
    }
  }
}
