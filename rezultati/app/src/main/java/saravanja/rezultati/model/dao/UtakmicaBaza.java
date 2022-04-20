package saravanja.rezultati.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import saravanja.rezultati.model.Utakmica;

@Database(entities = {Utakmica.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class UtakmicaBaza extends RoomDatabase {

    public abstract UtakmicaDAO utakmicaDAO();

    private static UtakmicaBaza instance;

    public static UtakmicaBaza getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UtakmicaBaza.class,
                    "utakmica_baza"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
