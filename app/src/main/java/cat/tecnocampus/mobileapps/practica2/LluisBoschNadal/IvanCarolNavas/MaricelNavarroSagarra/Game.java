package cat.tecnocampus.mobileapps.practica2.LluisBoschNadal.IvanCarolNavas.MaricelNavarroSagarra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
    String url = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    RequestQueue queue;
    int partides;
    int puntuacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.paraula);
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void getWords(View view) {
        if(isConnected()) {
            Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            jsonJokeObject(view);
        }
        else
            Toast.makeText(this,"NOT Connected", Toast.LENGTH_LONG).show();

    }
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void jsonJokeObject(View view) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    textView.setText(response.getJSONObject("body").getString("Word"));
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