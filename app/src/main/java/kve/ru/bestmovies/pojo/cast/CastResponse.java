package kve.ru.bestmovies.pojo.cast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
  @SerializedName("cast")
  @Expose
  private List<Cast> cast = null;

  public List<Cast> getCast() {
    return cast;
  }

  public void setCast(List<Cast> cast) {
    this.cast = cast;
  }

}
