package com.example.pokemonrv.Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pokemonrv.Graficas.Destadisticas;
import com.example.pokemonrv.R;
import com.example.pokemonrv.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    Button estadisticas;
    TextView txtValor, txtPromedio, txtPeligro;
    Double valuee;
    int A = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distancia);

        txtValor = findViewById(R.id.Valor);

        txtPromedio = findViewById(R.id.txtValorPormedio);

        txtPeligro = findViewById(R.id.txtPeligro);

        new CountDownTimer(10000000, 1000){

            @Override
            public void onTick(long l) {
                String url1 = "https://io.adafruit.com/api/v2/fermurilllo/feeds/distancia/data/last";

                JsonObjectRequest sendata1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            A = response.getInt("value");
                            txtValor.setText(A+"");
                            txtPromedio.setText(A+"");

                            if (A <= 5){
                                txtPeligro.setText("HAY ALGUIEN CON EL BEBE!!!");
                                txtPeligro.setTextColor(Color.WHITE);
                                txtPeligro.setBackgroundColor(Color.RED);
                            }
                            else{
                                txtPeligro.setText("No Hay Nadie Cerca Del Bebe");
                                txtPeligro.setTextColor(Color.WHITE);
                                txtPeligro.setBackgroundColor(Color.BLACK);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-AIO-Key", "aio_gDNI19Zu9dxGd2ArScFLLQxbMFmt");
                        return headers;

                    }
                };
                MySingleTon.getInstance(getApplicationContext()).addToRequestQue(sendata1);
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }
}