package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import android.app.Application;

import androidx.room.Room;

import java.util.List;

public class PlayerController {
    private static  PlayerController playerController;
    private PlayerDao playerDao;

    public PlayerController(Application application) {
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "database-name").allowMainThreadQueries().build();
        playerDao = db.playerDao();
    }

    public void insertPlayer (Player player){playerDao.insert(player);}
    public List<Player> listPlayers(){return playerDao.getAll();}

   // public List<Player> listPlayersPuntuacioSumada(){return playerDao.getAllPuntuacionsSumades();}
}
