package kve.ru.bestmovies.pojo.countries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {
  @SerializedName("production_countries")
  @Expose
  private List<ProductionCountry> productionCountries = null;

  public List<ProductionCountry> getProductionCountries() {
    return productionCountries;
  }

  public void setProductionCountries(List<ProductionCountry> productionCountries) {
    this.productionCountries = productionCountries;
  }
}
