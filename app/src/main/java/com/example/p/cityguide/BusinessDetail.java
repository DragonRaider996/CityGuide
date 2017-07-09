package com.example.p.cityguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class BusinessDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewPagerAdapter viewPagerAdapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;
    BusinessData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbar = (Toolbar) findViewById(R.id.businesstoolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        recyclerView.setAdapter(viewPagerAdapter);
        data = (BusinessData) getIntent().getSerializableExtra("data");
        getSupportActionBar().setTitle(data.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String disp[] = data.getDisplayphoto();
        for (int i = 0; i < disp.length; i++) {
            viewPagerAdapter.setData(disp[i]);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PagerAdapter(getSupportFragmentManager(), data, this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}