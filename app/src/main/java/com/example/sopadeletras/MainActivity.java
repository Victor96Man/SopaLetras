package com.example.sopadeletras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {


    Context contexto;
    String json_url= "http://cpd.iesgrancapitan.org:9101/~gomuvi/sopadeletras/categorias.php";
    ArrayList<ListaItem> myDataset = new ArrayList<ListaItem>();
    Spinner spinner;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contexto = this;
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner1);
        mRecyclerView = findViewById(R.id.recyclerView);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ListarCategorias();


    }

    public void ListarCategorias() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(json_url,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0 ; i < response.length(); i++) {
                            JSONObject obj = null;
                            try {
                                obj = response.getJSONObject(i);
                                String capital = obj.getString("nombre");
                                String id = obj.getString("id");
                                myDataset.add(new ListaItem(capital, id));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adaptador();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonArrayRequest);
    }

    public void adaptador(){

        mAdapter = new Adaptador(myDataset);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(contexto, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String categoiaNombre = myDataset.get(position).getId();
                        String numeroP = spinner.getSelectedItem().toString();

                        Intent intent1 = new Intent(MainActivity.this, Sopa.class);
                        intent1.putExtra("Categoria", categoiaNombre);
                        intent1.putExtra("numeroP", numeroP);
                        startActivity(intent1);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                })
        );
        mRecyclerView.setAdapter(mAdapter);
    }

}