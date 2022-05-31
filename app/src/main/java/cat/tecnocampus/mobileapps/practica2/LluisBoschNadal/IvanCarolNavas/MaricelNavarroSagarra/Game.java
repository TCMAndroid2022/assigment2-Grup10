package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;

public class Game extends AppCompatActivity {
    TextView textView;
    EditText text_InputLetter;
    TextView text_WordToGuess;

    //String url = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    String url = "https://random-word-api.herokuapp.com/word";
    RequestQueue queue;

    int partides=0;
    int puntuacio=0;
    int lettersTried=0;

    String wordToGuess = "";
    String wordDisplayedString;
    char[] wordDisplayedCharArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        conection();

        textView = (TextView) findViewById(R.id.paraula);
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
                if(s.length()!=0){
                    checkIfLetterIsInWord(s.charAt(0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializeGame(){
        //1. WORD
        // initialize char array from the word add underscores
        wordDisplayedCharArray = wordToGuess.toCharArray();
        for(int i = 0; i < wordDisplayedCharArray.length; i++){
            wordDisplayedCharArray[i] = '_';
        }
        // initialize a string from this char array(for search purposes)
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
        displayWordOnScreen();

        //2.INPUT
        // clear input field
        text_InputLetter.setText("");
    }

    private void checkIfLetterIsInWord(char letter){
        lettersTried++;
        // if the letter was found inside the word to be guessed
        if(wordToGuess.indexOf(letter) >= 0){
            // if the letter was NOT displayed yet
            if(wordDisplayedString.indexOf(letter) < 0){
                revealLetterInWord(letter);
                displayWordOnScreen();
                if(!wordDisplayedString.contains("_")){ //win?
                    Log.v("WIN", "win win");
                    puntuacio = 0;
                }
            }
        }

    }

    private void displayWordOnScreen() {
        text_WordToGuess.setText(wordDisplayedString);
    }

    private void revealLetterInWord(char letter){
        int indexOfLetter = wordToGuess.indexOf(letter);

        while(indexOfLetter >= 0){
            wordDisplayedCharArray[indexOfLetter]=wordToGuess.charAt(indexOfLetter);
            indexOfLetter=wordToGuess.indexOf(letter,indexOfLetter + 1);
        }

        wordDisplayedString=String.valueOf(wordDisplayedCharArray);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void conection() {
        if(isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            //jsonWordObject(view);
        }
        else
            Toast.makeText(this,"NOT Connected", Toast.LENGTH_LONG).show();

    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

//    public void jsonWordObject() {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    wordToGuess = response.getJSONObject("body").getString("Word");
//                    wordToGuess = Normalizer.normalize(wordToGuess, Normalizer.Form.NFD);
//                    wordToGuess = wordToGuess.replaceAll("[^\\p{ASCII}]", "");
//                    textView.setText(wordToGuess);
//                    initializeGame();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("ERROR", error.getMessage());
//            }
//        });
//        queue.add(jsonObjectRequest);
//    }

    public void stringWordObject(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    wordToGuess = response.getString(0);
                    wordToGuess = Normalizer.normalize(wordToGuess, Normalizer.Form.NFD);
                    wordToGuess = wordToGuess.replaceAll("[^\\p{ASCII}]", "");
                    textView.setText(wordToGuess);
                    initializeGame();
                }catch(Exception ex){
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
        AlertDialog.Builder mydialog = new AlertDialog.Builder(Game.this);
        mydialog.setTitle("SOLUCIO FINAL");

        final EditText solucio = new EditText(Game.this);
        solucio.setInputType(InputType.TYPE_CLASS_TEXT);
        solucio.setHint("solucio aqui");
        mydialog.setView(solucio);

        mydialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(solucio.getText().toString().equals(wordToGuess)){
                    puntuacio = (wordToGuess.length()-lettersTried)*10;
                    Log.v("A", "LENGTH "+String.valueOf(wordToGuess.length())+"  LETTER TRIED "+String.valueOf(lettersTried));
                    Log.v("PUNTUACIO", String.valueOf(puntuacio));
                    Log.v("WIN", "ERES MUY LISTO");
                }
            }
        });

        mydialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        mydialog.show();
    }
}