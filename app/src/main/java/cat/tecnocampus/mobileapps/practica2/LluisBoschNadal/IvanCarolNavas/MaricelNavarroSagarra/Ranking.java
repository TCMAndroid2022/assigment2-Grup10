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

        playersGlobal.add(new PlayerGlobal(players.get(0).getNickname(), 1, players.get(0).getPuntuacio()));

        for(int i=0; i< players.size(); i++) {
            Player getPlayer = players.get(i);

            //playersGlobal.add(new PlayerGlobal(getPlayer.getNickname(), 1 , getPlayer.getPuntuacio()));

            for( int j = 0; j < playersGlobal.size(); j++){
                PlayerGlobal playerGlobal = playersGlobal.get(j);
                if(getPlayer.getNickname().equals(playerGlobal.getNickname())){
                    playersGlobal.set(j,new PlayerGlobal("patata"/*getPlayer.getNickname()*/, 1 , getPlayer.getPuntuacio()));
                }else{
                    playersGlobal.add(new PlayerGlobal(getPlayer.getNickname(), 1 , getPlayer.getPuntuacio()));
                }

            }




            /*PlayerGlobal playerGlobal = new PlayerGlobal(getPlayer.getNickname(), getPlayer.getPuntuacio(), getPlayer.getPartides());
            if(!dataSet.contains(playerGlobal)){
                dataSet.add(playerGlobal);
                //searchAndRepalceDataSet(playerGlobal.getNickname(), playerGlobal.getPuntuacio());
            }else{
                PlayerGlobal p  = dataSet.get(i);
                p.setPuntuacio(p.getPuntuacio()+playerGlobal.getPuntuacio());
                p.setPartides(p.getPartides()+playerGlobal.getPartides());
                dataSet.set(i, p);
            }*/

        }
        for(int i = 0; i < playersGlobal.size(); i++) {
            Log.v("PlayersGlobal", playersGlobal.get(i).getNickname());
        }

        dataSet.addAll(playersGlobal);
        playerAdapter.notifyDataSetChanged();
        printData();
    }

    private void searchAndRepalceDataSet(String nickname, int puntuacio){

        for(int i = 0; i < dataSet.size(); i++){
            PlayerGlobal playerGlobal = dataSet.get(i);
            if(playerGlobal.getNickname().equals(nickname)){
                dataSet.set(i,new PlayerGlobal(nickname,1, puntuacio));
            }else{
                dataSet.add(new PlayerGlobal(nickname,1, puntuacio));
            }
        }
        /*Iterator<PlayerGlobal> it = dataSet.iterator();
        while(it.hasNext()) {
            PlayerGlobal i = it.next();
            if(i.getNickname().equals(nickname)) {
                it.remove();
                it.
            }
        }*/
    }

    private void printData(){
        for(int i = 0; i < dataSet.size(); i++) {
            Log.v("SET", dataSet.get(i).getNickname());
        }
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