package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.text.Normalizer;

public class Game extends AppCompatActivity {
    EditText text_InputLetter;
    TextView text_WordToGuess;

    //String url = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    String url = "https://random-word-api.herokuapp.com/word";
    RequestQueue queue;

    int puntuacio = 0;
    int lettersTried = 0;
    boolean win;

    String wordToGuess = "";
    String wordDisplayedString;
    char[] wordDisplayedCharArray;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conection();

        text_InputLetter = (EditText) findViewById(R.id.inputLetter);
        text_WordToGuess = (TextView) findViewById(R.id.wordToGuess);

        queue = Volley.newRequestQueue(getApplicationContext());

        //jsonWordObject();
        stringWordObject();

        // setup the text changed listener for the edit text
        text_InputLetter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if there is some letter on the input field
                if (s.length() != 0) {
                    checkIfLetterIsInWord(s.charAt(0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        intent = new Intent(this, MainActivity.class);
    }

    private void initializeGame() {
        win = false;

        wordDisplayedCharArray = wordToGuess.toCharArray();
        for (int i = 0; i < wordDisplayedCharArray.length; i++) {
            wordDisplayedCharArray[i] = '_';
        }

        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
        displayWordOnScreen();

        text_InputLetter.setText("");
    }

    private void checkIfLetterIsInWord(char letter) {
        lettersTried++;

        if (wordToGuess.indexOf(letter) >= 0) {
            if (wordDisplayedString.indexOf(letter) < 0) {
                revealLetterInWord(letter);
                displayWordOnScreen();
                if (!wordDisplayedString.contains("_")) {
                    loseDialog();
                }
            }
        }
    }

    private void displayWordOnScreen() {
        text_WordToGuess.setText(wordDisplayedString);
    }

    private void revealLetterInWord(char letter) {
        int indexOfLetter = wordToGuess.indexOf(letter);

        while (indexOfLetter >= 0) {
            wordDisplayedCharArray[indexOfLetter] = wordToGuess.charAt(indexOfLetter);
            indexOfLetter = wordToGuess.indexOf(letter, indexOfLetter + 1);
        }

        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void conection() {
        if (isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            //jsonWordObject(view);
        } else
            Toast.makeText(this, "NOT Connected", Toast.LENGTH_LONG).show();

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void stringWordObject() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    wordToGuess = response.getString(0);
                    wordToGuess = Normalizer.normalize(wordToGuess, Normalizer.Form.NFD);
                    wordToGuess = wordToGuess.replaceAll("[^\\p{ASCII}]", "");
                    Log.v("PARAULA", wordToGuess);
                    initializeGame();
                } catch (Exception ex) {
                    Log.d("SwA", "Error parsing json array");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }


    public void onSolve(View view) {
        SolucioButton();
    }

    private void SolucioButton() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        LayoutInflater inflater = Game.this.getLayoutInflater();
        View view = getLayoutInflater().inflate(R.layout.solucio_dialog, null, false);
        EditText solucio = view.findViewById(R.id.et_solucio);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (solucio.getText().toString().equals(wordToGuess)) {
                            win = true;
                            puntuacio = (wordToGuess.length() - lettersTried) * 10;
                            winDialog();
                        } else {
                            puntuacio = 0;
                            loseDialog();
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    private void winDialog() {
        if (win) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
            builder.setTitle("WIN");
            builder.setMessage("LENGTH WORD " + String.valueOf(wordToGuess.length()) + "  LETTER TRIED " + String.valueOf(lettersTried) + "\nPUNTUACIO " + String.valueOf(puntuacio));
            builder.setNeutralButton("NEW GAME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

    private void loseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        LayoutInflater inflater = Game.this.getLayoutInflater();
        View view = getLayoutInflater().inflate(R.layout.lose_dialog, null, false);

        Button new_game = view.findViewById(R.id.lose_new_game);
        TextView lose_length_word = view.findViewById(R.id.lose_lenght);
        TextView lose_letters_tries = view.findViewById(R.id.lose_letters);
        TextView lose_puntuacio = view.findViewById(R.id.lose_puntuacio);

        lose_length_word.setText(new StringBuilder().append(lose_length_word.getText()).append(String.valueOf(wordToGuess.length())).toString());
        lose_letters_tries.setText(new StringBuilder().append(lose_letters_tries.getText()).append(String.valueOf(lettersTried)).toString());
        lose_puntuacio.setText(new StringBuilder().append(lose_puntuacio.getText()).append(String.valueOf(puntuacio)).toString());

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        builder.setView(view).show();
    }
}