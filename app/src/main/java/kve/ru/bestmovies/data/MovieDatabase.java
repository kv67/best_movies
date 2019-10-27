package kve.ru.bestmovies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kve.ru.bestmovies.pojo.BestMovie;

@Database(entities = {BestMovie.class, FavouriteMovie.class}, version = 20, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

  private static final Object LOCK = new Object();
  private static final String DB_NAME = "movies.db";
  private static MovieDatabase database;

  public static MovieDatabase getInstance(Context context){
    synchronized (LOCK) {
      if (database == null) {
        database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
            .fallbackToDestructiveMigration().build();
      }
    }

    return database;
  }

  public abstract MovieDao movieDao();

}
