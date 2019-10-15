package kve.ru.bestmovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kve.ru.bestmovies.R;
import kve.ru.bestmovies.data.Country;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

  private List<Country> countries;

  public void setCountries(List<Country> countries) {
    this.countries = countries;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent,
        false);
    return new CountryViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
    Country country = countries.get(position);
    holder.textViewCountry.setText(country.getName());
  }

  @Override
  public int getItemCount() {
    return countries.size();
  }

  class CountryViewHolder extends RecyclerView.ViewHolder{

    private TextView textViewCountry;

    public CountryViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewCountry = itemView.findViewById(R.id.textViewCountry);
    }
  }
}
