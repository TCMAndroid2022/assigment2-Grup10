package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_nickname;
    PlayerController playerController;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nickname = (EditText) findViewById(R.id.et_nickname);

        playerController = new PlayerController(getApplication());

        //guarda correctament a la base de dades
        List<Player> players = playerController.listPlayers();
        Player getPlayer = players.get(0);
        Log.v("TEST", getPlayer.getNickname()+" "+getPlayer.getPuntuacio()+" "+getPlayer.getPartides());
    }

    public void startGame(View view) {
        player = new Player(String.valueOf(et_nickname.getText()));
        playerController.insertPlayer(player);

        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.ranking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                Intent intent = new Intent(this, Ranking.class);
                startActivity(intent);
                //aqui tiene que poner en la base de datos un (nickname, 0 , 0)
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}