package com.example.fetchingtesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
    private static final String HI ="http://127.0.0.1/~andrewchao/phpMyAdmin-5.0.4/Appi.php" ;
    private List<List_Data> list_data;
    private RecyclerView rv;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=(RecyclerView)findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new MyAdapter(list_data);
        rv.setAdapter(adapter);
        getMovieData();

    }

    private void getMovieData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, response -> {
            try {
                //JSONObject jsonObject=new JSONObject(response);
                //JSONArray array=jsonObject.getJSONArray("data");
                JSONArray array=new JSONArray(response);
                for (int i=0; i<array.length(); i++ ){
                    JSONObject ob=array.getJSONObject(i);
                    List_Data listData=new List_Data(ob.getString("name"),ob.getString("moivename"));
                    list_data.add(listData);
                }
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}