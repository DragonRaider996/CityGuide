package com.example.p.cityguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BusinessList extends AppCompatActivity {


    RecyclerView recyclerView;
    Toolbar toolbar;
    final String urlrest = "http://139.59.70.9/api/restaurants";
    final String urlprof = "http://139.59.70.9/api/professionals";
    final String urlheath = "http://139.59.70.9/api/health_care";
    String restuarant = "586b8264dfdefc0d7f276f98";
    String professional = "586b8264dfdefc0d7f276f99";
    String healthcare = "586b8264dfdefc0d7f276f9a";
    ViewListAdapter adapter;
    String url = null;
    String id = null;
    int flag = 0;
    int temp1 = 0;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        bar = (ProgressBar) findViewById(R.id.progressBarList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences preferences = getSharedPreferences("id", MODE_PRIVATE);
        if (getIntent().getStringExtra("category") == null) {
            if (preferences.getString("pause", "0").equals("1")) {
                this.id = preferences.getString("id1", "N/A");
                preferences.edit().putString("pause", "0").apply();
                Log.i("called", preferences.getString("pause", "0"));
            } else {
                id = getIntent().getStringExtra("category");
            }
        } else {
            id = getIntent().getStringExtra("category");
        }
        Log.i("called", preferences.getString("pause", "N/A"));
        if (id.equals(restuarant)) {
            url = urlrest;
            getSupportActionBar().setTitle("Restaurants");
            flag = 0;
        } else if (id.equals(professional)) {
            url = urlprof;
            getSupportActionBar().setTitle("Professionals");
            flag = 1;
        } else if (id.equals(healthcare)) {
            url = urlheath;
            getSupportActionBar().setTitle("Health Care");
            flag = 0;
        }
        Log.i("called", id);
        Log.i("called", url);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        adapter = new ViewListAdapter(this, id);
        recyclerView.setAdapter(adapter);
        function();

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                bar.setVisibility(View.INVISIBLE);
                adapter.unregisterAdapterDataObserver(this);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.i("called", "onBack");
            temp1 = 1;
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        temp1 = 1;
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        SharedPreferences preferences = getSharedPreferences("id", MODE_PRIVATE);
        preferences.edit().putString("id1", id).apply();
        preferences.edit().putString("pause", "1").apply();
        Log.i("called", "Pause");
        Log.i("called", preferences.getString("pause", "N/A"));
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (temp1 == 1) {
            SharedPreferences preferences = getSharedPreferences("id", MODE_PRIVATE);
            preferences.edit().putString("pause", "0").apply();
            Log.i("called", "finish");
        }
        Log.i("called", String.valueOf(temp1) + "temp");
        super.onDestroy();
    }


    public void function() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        final String refreshToken = sharedPreferences.getString("refreshToken", "");
        final String auth = "Bearer " + refreshToken;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject object = data.getJSONObject(i);
                        String id = object.getString("_id");
                        String name = object.getString("name");
                        String abs = object.getString("abstract");
                        String address = object.getString("address");
                        String contact = object.getString("mobile");
                        String image = object.getString("profile_photos");
                        String lat = object.getString("lat");
                        String lng = object.getString("lng");
                        String web = object.getString("website");
                        String city = object.getString("city");
                        JSONArray display = object.getJSONArray("display_photos");
                        String disp[] = new String[display.length()];
                        for (int j = 0; j < display.length(); j++) {
                            disp[j] = display.getString(j);
                        }
                        String mon, sat;
                        if (flag == 0) {
                            JSONObject timing = object.getJSONObject("timings");
                            JSONObject day = timing.getJSONObject("day");
                            mon = day.getString("MON-FRI");
                            sat = day.getString("SAT-SUN");
                        } else {
                            mon = "N/A";
                            sat = "N/A";
                        }
                        BusinessData temp = new BusinessData(id, name, abs, address, contact, lat, lng, web, city, image, disp, mon, sat);
                        adapter.setData(temp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String body = "body";
                if (error instanceof NetworkError || error instanceof NoConnectionError) {
                    Toast.makeText(BusinessList.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                } else {
                    String statuscode = String.valueOf(error.networkResponse.statusCode);
                    if (error.networkResponse.data != null) {
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            Toast.makeText(BusinessList.this, body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                    if (statuscode.equals("422")) {
                        Toast.makeText(BusinessList.this, "E-Mail already used!!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonRequest.getInstance(this.getApplicationContext()).addtoRequestQueue(request);
    }


}
