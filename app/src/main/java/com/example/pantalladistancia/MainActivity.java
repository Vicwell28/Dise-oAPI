
package com.example.mydiseoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button btnEstadisitcas, btnPrenderFoc;
    TextView txtTempPromedio, txtTempPromCHc, txtTempPromCHf;
    int O = 0;
    ProgressBar PBc, PBf;
    int TemperaturaSensorc = 26;
    int TemperaturaSensorf = 89;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnEstadisitcas = findViewById(R.id.btn1);
        btnEstadisitcas.setOnClickListener(this);

        btnPrenderFoc = findViewById(R.id.btn2);
        btnPrenderFoc.setOnClickListener(this);

        txtTempPromedio = findViewById(R.id.txt1);
        txtTempPromCHc = findViewById(R.id.txt2);
        txtTempPromCHf = findViewById(R.id.txt3);

        PBc = findViewById(R.id.progressBar);
        PBc.setProgress(TemperaturaSensorc);

        PBf = findViewById(R.id.progressBar2);
        PBf.setProgress(TemperaturaSensorf);

        new CountDownTimer(1000000000, 1000){

            @Override
            public void onTick(long l) {
                String url1 = "https://io.adafruit.com/api/v2/fermurilllo/feeds/temperaturac/data/last";

                JsonObjectRequest sendata1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int A = response.getInt("value");
                            txtTempPromCHc.setText(A+"°C");
                            txtTempPromedio.setText(A+"°C");
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
                        //return super.getHeaders();
                    }
                };
                MySingleTon.getInstance(getApplicationContext()).addToRequestQue(sendata1);

                String url2 = "https://io.adafruit.com/api/v2/fermurilllo/feeds/temperaturaf/data/last";

                JsonObjectRequest sendata2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int A = response.getInt("value");
                            txtTempPromCHf.setText(A+"°F");
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
                        //return super.getHeaders();
                    }
                };
                MySingleTon.getInstance(getApplicationContext()).addToRequestQue(sendata2);

            }

            @Override
            public void onFinish() {

            }
        }.start();




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //BOTON ESTADISTICAS
            case R.id.btn1:
                Intent I = new Intent(MainActivity2.this, MainActivity3.class);
                MainActivity2.this.startActivity(I);
                break;

            //BOTON PRENDER VENTILADOR
            case R.id.btn2:
                String url = "https://io.adafruit.com/api/v2/fermurilllo/feeds/relevador/data";

                JSONObject datos = new JSONObject();

                if(O == 0){
                    try {
                        datos.put("value", "ON");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnPrenderFoc.setText("ON");
                    btnPrenderFoc.setBackgroundColor(Color.CYAN);
                    btnPrenderFoc.setTextColor(Color.WHITE);
                    O = 1;
                }
                else if(O == 1){
                    try {
                        datos.put("value", "OFF");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnPrenderFoc.setText("OFF");
                    btnPrenderFoc.setBackgroundColor(Color.WHITE);
                    btnPrenderFoc.setTextColor(Color.BLACK);
                    O = 0;
                }

                JsonObjectRequest sendata = new JsonObjectRequest(Request.Method.POST, url, datos, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("X-AIO-Key", "aio_gDNI19Zu9dxGd2ArScFLLQxbMFmt");
                        return headers;
                        //return super.getHeaders();
                    }
                };
                MySingleTon.getInstance(this).addToRequestQue(sendata);
                break;
        }

    }
}