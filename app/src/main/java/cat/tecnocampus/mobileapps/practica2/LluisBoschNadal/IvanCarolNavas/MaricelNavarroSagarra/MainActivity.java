package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.internal.TextWatcherAdapter;

public class MainActivity extends AppCompatActivity {
    EditText et_nickname;
    Button btn_play;
    String nicknameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nickname = (EditText) findViewById(R.id.et_nickname);
        btn_play = (Button) findViewById(R.id.btn_play);

        et_nickname.addTextChangedListener(loginPlayerTextWatcher);

    }

    private TextWatcher loginPlayerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
             nicknameInput = et_nickname.getText().toString().trim();

             btn_play.setEnabled(!nicknameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        intent.putExtra("nickname_player",nicknameInput);
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