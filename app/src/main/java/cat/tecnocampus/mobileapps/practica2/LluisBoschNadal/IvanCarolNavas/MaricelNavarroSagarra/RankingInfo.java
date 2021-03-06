package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    int puntuacio;

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

        textView_nickname = (TextView) findViewById(R.id.info_nick);
        textView_puntuacio = (TextView) findViewById(R.id.info_punt);
        recyclerView = (RecyclerView) findViewById(R.id.info_recycleView);

        playerController = new PlayerController(getApplication());

        if(savedInstanceState == null){
            dataSet = new ArrayList<>();
            playerInfoAdapter = new PlayerInfoAdapter(dataSet);
            recyclerView.setAdapter(playerInfoAdapter);
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nickname = extras.getString("nickname_info");
            puntuacio = extras.getInt("puntuacio_info");
        }

        textView_nickname.setText(new StringBuilder().append(textView_nickname.getText()).append(nickname).toString());
        textView_puntuacio.setText(new StringBuilder().append(textView_puntuacio.getText()).append(String.valueOf(puntuacio)).toString());

        omplirRecylcerView();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void omplirRecylcerView() {
        int numPartida = 0;
        List<Player> players = playerController.listPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player getPlayer = players.get(i);
            if(getPlayer.getNickname().equals(nickname)){
                numPartida++;
                dataSet.add(new PlayerGlobal(getPlayer.getNickname(), getPlayer.getPuntuacio(), numPartida));
                playerInfoAdapter.notifyDataSetChanged();
            }
        }
    }
}