package kve.ru.bestmovies.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Converter {

  @TypeConverter
  public String listIntegerToString(List<Integer> genreIds){
      return new Gson().toJson(genreIds);
  }

  @TypeConverter
  public List<Integer> stringToListInteger(String strGenre){
      Gson gson = new Gson();
      ArrayList objects = gson.fromJson(strGenre, ArrayList.class);
      List<Integer> genreIds = new ArrayList<>();
      for (Object o : objects) {
        genreIds.add(gson.fromJson(o.toString(), Integer.class));
      }
      return genreIds;

  }

}
