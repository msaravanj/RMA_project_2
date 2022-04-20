package saravanja.rezultati.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import saravanja.rezultati.model.Utakmica;

@Dao
public interface UtakmicaDAO {

    @Query("select * from utakmica order by id")
    LiveData<List<Utakmica>> getUtakmica();

    @Insert
    void addNewUtakmica(Utakmica utakmica);

    @Update
    void changeUtakmica(Utakmica utakmica);

    @Delete
    void deleteUtakmica(Utakmica utakmica);
}
