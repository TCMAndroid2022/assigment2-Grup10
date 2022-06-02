package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RankingInfo extends AppCompatActivity {
    TextView textView_nickname;
    TextView textView_puntuacio;

    String nickname;

    ArrayList<PlayerGlobal> dataSet;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PlayerInfoAdapter playerInfoAdapter;

    PlayerController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView_nickname = (TextView) findViewById(R.id.info_nickname);
        textView_puntuacio = (TextView) findViewById(R.id.info_puntuacio);
        recyclerView = (RecyclerView) findViewById(R.id.info_recycleView);

        playerController = new PlayerController(getApplication());

        if(savedInstanceState == null){
            dataSet = new ArrayList<>();
            playerInfoAdapter = new PlayerInfoAdapter(dataSet);
            recyclerView.setAdapter(playerInfoAdapter);
        }

        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nickname = extras.getString("nickname_info");
        }

        textView_nickname.setText(new StringBuilder().append(textView_nickname.getText()).append(nickname).toString());

        omplirRecylcerView();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void omplirRecylcerView() {
        List<Player> players = playerController.listPlayers();
        Log.v("SIZE", String.valueOf(players.size()));
        for (int i = 0; i < players.size(); i++) {
            Player getPlayer = players.get(i);
            PlayerGlobal playerGlobal = new PlayerGlobal(getPlayer.getNickname(), getPlayer.getPuntuacio(), getPlayer.getPartides());
            if(getPlayer.getNickname().equals(nickname)){
                Log.v("EUQALS", String.valueOf(i));
                dataSet.add(playerGlobal);
                playerInfoAdapter.notifyDataSetChanged();
            }
        }
    }
}