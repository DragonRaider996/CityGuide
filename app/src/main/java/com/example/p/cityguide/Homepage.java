package com.example.p.cityguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView recyclerView;
    Toolbar toolbar;
    ViewAdapter adapter;
    ArrayList<Data> data = new ArrayList<>();
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerHome);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        String[] business = getResources().getStringArray(R.array.business);
        String[] category = getResources().getStringArray(R.array.category);

        for (int i = 0; i < business.length; i++) {
            Data temp = new Data(business[i], category[i]);
            data.add(temp);
        }
        adapter = new ViewAdapter(this, data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Toast.makeText(Homepage.this, "Home Selected", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        } else if (item.getItemId() == R.id.favourites) {
            Toast.makeText(Homepage.this, "Favourite", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        } else if (item.getItemId() == R.id.aboutus) {
            Toast.makeText(Homepage.this, "About us", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawers();
        } else if (item.getItemId() == R.id.logout) {
            Toast.makeText(Homepage.this, "Logout", Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("category");
            editor.remove("name");
            editor.remove("refreshToken");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        } else if (item.getItemId() == R.id.exit) {
            drawerLayout.closeDrawers();
            this.finish();
        }
        return false;
    }

}
