package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {
    TextView textView;
    PlayerController playerController;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PlayerAdapter playerAdapter;
    ArrayList<Player> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.titulo);
        playerController = new PlayerController(getApplication());
        recyclerView = (RecyclerView) findViewById(R.id.rv_items);

        if(savedInstanceState == null){
            dataSet=new ArrayList<>();
            playerAdapter = new PlayerAdapter(dataSet);
            recyclerView.setAdapter(playerAdapter);
        }

        layoutManager = new LinearLayoutManager((this));
        recyclerView.setLayoutManager(layoutManager);

        //guarda correctament a la base de dades i les mostra per consola
        List<Player> players = playerController.listPlayers();
        Log.v("TEST", String.valueOf(players.size()));
        for(int i=0; i< players.size(); i++) {
            Player getPlayer = players.get(i);
            dataSet.add(new Player(getPlayer.getNickname()));
            playerAdapter.notifyDataSetChanged();
            Log.v("TEST2", getPlayer.getNickname() + " " + getPlayer.getPuntuacio() + " " + getPlayer.getPartides());
        }


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}