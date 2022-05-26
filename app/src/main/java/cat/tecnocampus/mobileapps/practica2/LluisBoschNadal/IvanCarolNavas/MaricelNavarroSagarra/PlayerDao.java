package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player WHERE uid IN (:playerIds)")
    List<Player> loadAllByIds(int[] playerIds);

    @Insert
    void insert(Player player);

    @Delete
    void delete(Player player);
}
