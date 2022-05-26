package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {
    TextView textView;
    PlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.titulo);
        playerController = new PlayerController(getApplication());

        //guarda correctament a la base de dades i les mostra per consola
        List<Player> players = playerController.listPlayers();
        Player getPlayer = players.get(0);
        Log.v("TEST", getPlayer.getNickname()+" "+getPlayer.getPuntuacio()+" "+getPlayer.getPartides());
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}