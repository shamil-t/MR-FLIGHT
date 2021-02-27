package com.shamil.mr_flight;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Adapter.AirportStatusAdapter;
import com.shamil.mr_flight.Model.AirportStatusModel;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<AirportStatusModel> airportStatusModels;
    AirportStatusAdapter airportStatusAdapter;
    EditText searchEdit;
    MorphBottomNavigationView bottomNavigationView;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_200));
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.airportRecycler);
        searchEdit = findViewById(R.id.searchLay);

        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item0){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return true;
            }
            if(itemId == R.id.item1){
                Intent i = new Intent(getApplicationContext(),VideosActivity.class);
                startActivity(i);
                return true;
            }
            if(itemId == R.id.item2){
                Toast.makeText(StatusActivity.this, "Status", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (itemId == R.id.item3){
                Intent i = new Intent(getApplicationContext(),PostsActivty.class);
                startActivity(i);
                return true;
            }
            if(itemId == R.id.item4){
                Intent i = new Intent(getApplicationContext(),LiveTrackActivity.class);
                startActivity(i);
                return true;
            }
            return false;
        });

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        airportStatusModels = new ArrayList<>();
        loadData();

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                loadData();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String Search = s.toString();

                searchAirport(Search);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    loadData();
                } else {
                    searchAirport(s.toString());
                }
            }
        });
    }

    private void searchAirport(String search) {
        airportStatusModels.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("AirportsIndia"));
            JSONArray m_jArry = obj.getJSONArray("Airports");
            AirportStatusModel statusModel = new AirportStatusModel();
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject list = m_jArry.getJSONObject(i);

                if (list.getString("name").toLowerCase().contains(search)
                        || list.getString("code").toLowerCase().contains(search)) {
                    statusModel.setNAME(list.getString("name"));
                    statusModel.setCODE(list.getString("code"));
                    statusModel.setLINK(list.getString("link"));

                    airportStatusModels.add(statusModel);
                }
            }
            airportStatusAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            //
        }
    }

    private void loadData() {
        airportStatusModels.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("AirportsIndia"));
            JSONArray m_jArry = obj.getJSONArray("Airports");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject list = m_jArry.getJSONObject(i);

                AirportStatusModel statusModel = new AirportStatusModel();

                statusModel.setNAME(list.getString("name"));
                statusModel.setCODE(list.getString("code"));
                statusModel.setLINK(list.getString("link"));

                airportStatusModels.add(statusModel);
            }

            airportStatusAdapter = new AirportStatusAdapter(this, airportStatusModels);
            recyclerView.setAdapter(airportStatusAdapter);
        } catch (Exception e) {
            //
        }
    }

    public String loadJSONFromAsset(String name) {
        String json = null;
        try {
            InputStream is = getAssets().open(name + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "tap BACK again to EXIT", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}