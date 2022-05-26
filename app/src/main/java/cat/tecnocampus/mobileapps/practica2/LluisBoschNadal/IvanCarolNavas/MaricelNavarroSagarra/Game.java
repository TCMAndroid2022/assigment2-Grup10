package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Game extends AppCompatActivity {
    TextView textView;
    TextView text_InputLetter;
    TextView text_WordToGuess;
    String url = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    RequestQueue queue;
    String wordToGuess = "Patataaaaaa";
    int partides;
    int puntuacio;
    char[] wordDisplayedCharArray;
    String wordDisplayedString;


    void revealLetterInWord(char letter){
        int indexOfLetter = wordToGuess.indexOf(letter);
        // loop if index is positive or0
        while(indexOfLetter >= 0){
            wordDisplayedCharArray[indexOfLetter]=wordToGuess.charAt(indexOfLetter);
            indexOfLetter=wordToGuess.indexOf(letter,indexOfLetter + 1);
        }
        // update the string as well
        wordDisplayedString=String.valueOf(wordDisplayedCharArray);
    }

    void displayWordOnScreen() {
        String formattedString="";
        for(char character : wordDisplayedCharArray){
            formattedString += character + "";
        }
        text_WordToGuess.setText(formattedString);
    }

    void initializeGame(){
        //1. WORD

        // initialize char array
        wordDisplayedCharArray = wordToGuess.toCharArray();

        // add underscores
        for(int i = 0; i < wordDisplayedCharArray.length; i++){
            wordDisplayedCharArray[i] = '_';
        }

        // reveal all occurrences of first character
        revealLetterInWord(wordDisplayedCharArray[0]);
        // reveal all occurrences of last character
        revealLetterInWord(wordDisplayedCharArray[wordDisplayedCharArray.length - 1]);
        // initializeastring from this char array(for search purposes)
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
        // display word
        displayWordOnScreen();
        //2.INPUT
        // clear input field
        text_InputLetter.setText("");
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.paraula);
        text_InputLetter = (TextView) findViewById(R.id.inputLetter);
        text_WordToGuess = (TextView) findViewById(R.id.wordToGuess);

        queue = Volley.newRequestQueue(getApplicationContext());

        jsonWordObject();

        /*while(wordToGuess == null){

        }*/
        initializeGame();
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

    private void checkIfLetterIsInWord(char letter){
        // if the letter was found inside the word to be guessed
        if(wordToGuess.indexOf(letter) >= 0){
            // if the letter was NOT displayed yet
            if(wordDisplayedString.indexOf(letter) < 0){
                // replace the underscores with that letter
                revealLetterInWord(letter);
                // update the changes on screen
                displayWordOnScreen();
                // check if the game is won
                if(!wordDisplayedString.contains("_")){
                    //txtTriesLeft.setText(WINNING_MESSAGE);
                }
            }
        }
        // otherwise,if the letter was NOT found inside the word to be guessed
        else{

        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void getWords(View view) {
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

    public void jsonWordObject() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    wordToGuess = response.getJSONObject("body").getString("Word");
                    textView.setText(wordToGuess);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR", error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }


}