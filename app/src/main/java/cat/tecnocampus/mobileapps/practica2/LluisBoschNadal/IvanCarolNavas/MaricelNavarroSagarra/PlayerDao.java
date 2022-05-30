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

    @Query("SELECT p1.nickname, p1.puntuacio+p2.puntuacio, p1.partides FROM player p1 INNER JOIN player p2 ON p1.nickname=p2.nickname")
    List<Player> getAllPuntuacionsSumades();

    @Query("SELECT * FROM player WHERE uid IN (:playerIds)")
    List<Player> loadAllByIds(int[] playerIds);

    @Insert
    void insert(Player player);

    @Delete
    void delete(Player player);
}
