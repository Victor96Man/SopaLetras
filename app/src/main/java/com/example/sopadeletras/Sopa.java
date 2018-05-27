package com.example.sopadeletras;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sopa extends AppCompatActivity {
    private final int ids[][] = {
            {R.id.boton00, R.id.boton01,R.id.boton02,R.id.boton03,R.id.boton04,R.id.boton05, R.id.boton06, R.id.boton07,R.id.boton08,R.id.boton09},
            {R.id.boton10, R.id.boton11,R.id.boton12,R.id.boton13,R.id.boton14,R.id.boton15, R.id.boton16, R.id.boton17,R.id.boton18,R.id.boton19},
            {R.id.boton20, R.id.boton21,R.id.boton22,R.id.boton23,R.id.boton24,R.id.boton25, R.id.boton26, R.id.boton27,R.id.boton28,R.id.boton29},
            {R.id.boton30, R.id.boton31,R.id.boton32,R.id.boton33,R.id.boton34,R.id.boton35, R.id.boton36, R.id.boton37,R.id.boton38,R.id.boton39},
            {R.id.boton40, R.id.boton41,R.id.boton42,R.id.boton43,R.id.boton44,R.id.boton45, R.id.boton46, R.id.boton47,R.id.boton48,R.id.boton49},
            {R.id.boton50, R.id.boton51,R.id.boton52,R.id.boton53,R.id.boton54,R.id.boton55, R.id.boton56, R.id.boton57,R.id.boton58,R.id.boton59},
            {R.id.boton60, R.id.boton61,R.id.boton62,R.id.boton63,R.id.boton64,R.id.boton65, R.id.boton66, R.id.boton67,R.id.boton68,R.id.boton69},
            {R.id.boton70, R.id.boton71,R.id.boton72,R.id.boton73,R.id.boton74,R.id.boton75, R.id.boton76, R.id.boton77,R.id.boton78,R.id.boton79},
            {R.id.boton80, R.id.boton81,R.id.boton82,R.id.boton83,R.id.boton84,R.id.boton85, R.id.boton86, R.id.boton87,R.id.boton88,R.id.boton89},
            {R.id.boton90, R.id.boton91,R.id.boton92,R.id.boton93,R.id.boton94,R.id.boton95, R.id.boton96, R.id.boton97,R.id.boton98,R.id.boton99}
    };
    private Game juego;
    private int filaInicial;
    private int columnaInicial;
    private int filaFinal;
    private int columnaFinal;
    private boolean primero = true;
    String Categoria, numeroP, json_url;
    private Palabras[] palabras;
    TextView contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sopa);
        contador = findViewById(R.id.textNumeroPalabras);
        Bundle extras = getIntent().getExtras();
        Categoria= extras.getString("Categoria");
        numeroP = extras.getString("numeroP");
        palabras = new Palabras[Integer.parseInt(numeroP)];
        json_url= "http://cpd.iesgrancapitan.org:9101/~gomuvi/sopadeletras/sopa.php/palabras/"+Categoria+"/"+numeroP;
        contador.setText(numeroP);
        PedirPalabras();

    }
    public void inicio(){
        juego = new Game(palabras, this);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView img = findViewById( ids[i][j] );
                String imageName = String.valueOf(juego.getLetra(i,j));
                int resID = getResources().getIdentifier(imageName, "drawable",  getPackageName());
                img.setImageResource(resID);
            }
        }
    }


    public void PedirPalabras() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(json_url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(i);

                        palabras[i] = new Palabras(obj.getString("palabra"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                inicio();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(Sopa.this).addToRequestQueue(jsonArrayRequest);

    }



    public void onClick(View v){
        int id = v.getId();
        int[] selected = coorJuego(id);
        findViewById(id).setAlpha(Float.parseFloat("0.5"));

        if(primero){
            filaInicial = selected[0];
            columnaInicial = selected[1];
            primero = false;
        }else{
            filaFinal = selected[0];
            columnaFinal = selected[1];

            String solucion = juego.compruebaAcierto(filaInicial,columnaInicial,filaFinal,columnaFinal);
            if (solucion.equals("no")){
                Toast.makeText(this,"Te has equivocado",Toast.LENGTH_LONG).show();
            }else{
               contador.setText(Integer.toString(Integer.parseInt(contador.getText().toString())-1));
                tacharLetra();
               if (contador.getText().toString().equals("0")){
                   Toast.makeText(this,"Has encontrado todas las palabras",Toast.LENGTH_SHORT).show();

                   final Handler h = new Handler();
                   h.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           finish();
                       }
                   }, 1000);
               }
            }

            findViewById(ids[filaInicial][columnaInicial]).setAlpha(1);
            findViewById(ids[filaFinal][columnaFinal]).setAlpha(1);

            filaInicial = -1;
            columnaInicial = -1;
            filaFinal = -1;
            columnaFinal = -1;

            primero = true;
        }


    }
    public void tacharLetra(){

        if (columnaInicial==columnaFinal){
            //palabra vertical
            if (filaInicial<filaFinal) {
                for (int i = filaInicial; i <= filaFinal; i++) {
                    findViewById(ids[i][columnaInicial]).setBackground(getDrawable(R.drawable.lineavertical));
                }
            }else{
                for (int i = filaFinal; i <= filaInicial; i++) {
                    findViewById(ids[i][columnaInicial]).setBackground(getDrawable(R.drawable.lineavertical));
                }
            }
        } else if (filaInicial == filaFinal){
            //palabra horizontal
            if (columnaInicial<columnaFinal) {
                for (int i = columnaInicial; i <= columnaFinal; i++) {
                    findViewById(ids[filaInicial][i]).setBackground(getDrawable(R.drawable.lineahorizontal));
                }
            }else{
                for (int i = columnaFinal; i <= columnaInicial; i++) {
                    findViewById(ids[filaInicial][i]).setBackground(getDrawable(R.drawable.lineahorizontal));
                }
            }
        }else{
            //palabra diagonal
            if(filaInicial<filaFinal) {
                int j = filaInicial;
                for (int i = columnaInicial; i <= columnaFinal; i++) {
                        findViewById(ids[j][i]).setBackground(getDrawable(R.drawable.lineadiagonal));
                j++;
                }
            }else{
                int j = filaFinal;
                for (int i = columnaFinal; i <= columnaInicial; i++) {
                    findViewById(ids[j][i]).setBackground(getDrawable(R.drawable.lineadiagonal));
                    j++;
                }
            }
        }
    }


    public int[] coorJuego(int id){
        int[] coord = new int[2];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if(ids[i][j] == id){
                    coord[0] = i;
                    coord[1] = j;
                }
            }
        }
        return coord;
    }
}