package kve.ru.bestmovies.data;

public class Country {

  private String name;
  private String iso;

  public Country(String name, String iso) {
    this.name = name;
    this.iso = iso;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }
}
