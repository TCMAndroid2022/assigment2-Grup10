package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Ranking extends AppCompatActivity {
   // RecyclerView recyclerView;
   // RecyclerView.LayoutManager layoutManager;
   // PlayerAdapter playerAdapter;
   // ArrayList<Player> dataSet;
    //String nickname;
    TextView textView;

    ActivityResultLauncher<Intent> myActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.titulo);

//        recyclerView = (RecyclerView) findViewById(R.id.rv_items);
//
//        if(savedInstanceState == null) {
//            dataSet = new ArrayList<>();
//            playerAdapter = new PlayerAdapter(dataSet);
//            recyclerView.setAdapter(playerAdapter);
//        }
//
//        layoutManager = new LinearLayoutManager((this));
//        recyclerView.setLayoutManager(layoutManager);
//
//        //ho llegeix de la base de dades
//        Bundle extras = getIntent().getExtras();
//        if(extras != null) {
//            nickname = extras.getString("llistat_nickname");
//            dataSet.add(new Player(nickname));
//            playerAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}