package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Ranking extends AppCompatActivity implements PlayerAdapter.OnItemClickListener{
    TextView textView;
    PlayerController playerController;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PlayerAdapter playerAdapter;
    ArrayList<PlayerGlobal> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.titulo);
        playerController = new PlayerController(getApplication());
        recyclerView = (RecyclerView) findViewById(R.id.rv_items);

        if(savedInstanceState == null) {
            dataSet = new ArrayList<>();
            playerAdapter = new PlayerAdapter(dataSet, this);
            recyclerView.setAdapter(playerAdapter);
        }

        layoutManager = new LinearLayoutManager((this));
        recyclerView.setLayoutManager(layoutManager);

        //mostra correctament a la base de dades i les mostra per consola
        List<Player> players = playerController.listPlayers();
        List<PlayerGlobal> playersGlobal = new ArrayList<>();
        HashMap<String,List<Integer>> mapStats = new HashMap<>(); //pos 0 = puntuacio, pos 1 = partides


        for(int i=0; i < players.size(); i++) {
            Player getPlayer = players.get(i);
            if(mapStats.containsKey(getPlayer.getNickname())){
                ArrayList<Integer> info = new ArrayList<>();
                int puntuacio_old = mapStats.get(getPlayer.getNickname()).get(0);
                int partides_old = mapStats.get(getPlayer.getNickname()).get(1);
                info.add(0, getPlayer.getPuntuacio()+puntuacio_old);
                info.add(1, partides_old+1);
                mapStats.put(getPlayer.getNickname(), info);

            }else{
                ArrayList<Integer> info = new ArrayList<>();
                info.add(0, getPlayer.getPuntuacio());
                info.add(1, 1);
                mapStats.put(getPlayer.getNickname(), info);
            }

        }
        for(String nickname : mapStats.keySet()){
            playersGlobal.add(new PlayerGlobal(nickname, mapStats.get(nickname).get(0),mapStats.get(nickname).get(1)));
        }

        for(int i = 0; i < playersGlobal.size(); i++) {
            Log.v("PlayersGlobal", playersGlobal.get(i).getNickname());
        }

        dataSet.addAll(playersGlobal);

        playerAdapter.notifyDataSetChanged();
        
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    //preguntar com vol que surti quan estigui en orientatio landscape
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new LinearLayoutManager(this);
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new LinearLayoutManager(this);
        }
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent_info = new Intent(this,RankingInfo.class);
        intent_info.putExtra("nickname_info", dataSet.get(position).getNickname());
        Log.v("NICK", dataSet.get(position).getNickname());
        startActivity(intent_info);
    }
}