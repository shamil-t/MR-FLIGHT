package com.shamil.mr_flight;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Adapter.AirportsListAdapter;
import com.shamil.mr_flight.Adapter.FlightsListAdapter;
import com.shamil.mr_flight.Model.AirportModel;
import com.shamil.mr_flight.Model.FlightModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {

    Button backBtn;
    TextView headTxt;
    RecyclerView listRecycler;
    LinearLayoutManager linearLayoutManager;
    ArrayList<FlightModel> arrayList;
    ArrayList<AirportModel> modelArrayList;
    FlightsListAdapter adapter;
    AirportsListAdapter airportsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String HEAD = Objects.requireNonNull(getIntent().getExtras()).getString("HEAD");
        String KEY = Objects.requireNonNull(getIntent().getExtras()).getString("KEY");

        backBtn = findViewById(R.id.backBtn);
        headTxt = findViewById(R.id.head);
        headTxt.setText(HEAD);
        backBtn.setOnClickListener(v -> finish());
        listRecycler = findViewById(R.id.listRecycler);

        linearLayoutManager = new LinearLayoutManager(this);
        listRecycler.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        modelArrayList = new ArrayList<>();

        assert KEY != null;
        if (KEY.equals("A")){
            getAirports();
        }
        else {
            getFlights();
        }
    }

    public void getAirports(){
        arrayList.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("Airports"));
            JSONArray m_jArry = obj.getJSONArray("Airports");

            for (int i=0;i<m_jArry.length();i++){
                JSONObject list = m_jArry.getJSONObject(i);

                AirportModel airportModel = new AirportModel();

                airportModel.setNAME(list.getString("name"));
                airportModel.setDESC(list.getString("desc"));
                airportModel.setLOCATION(list.getString("location"));
                airportModel.setIMAGE(list.getString("image"));

                modelArrayList.add(airportModel);
            }
            airportsListAdapter = new AirportsListAdapter(this,modelArrayList);
            listRecycler.setAdapter(airportsListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getFlights(){
        arrayList.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("Flights"));
            JSONArray m_jArry = obj.getJSONArray("Flights");

            for (int i=0;i<m_jArry.length();i++){
                JSONObject list = m_jArry.getJSONObject(i);

                FlightModel flightModel = new FlightModel();

                flightModel.setHEAD(list.getString("name"));
                flightModel.setDESC(list.getString("desc").replaceAll("\\s"," "));
                flightModel.setIMAGE(list.getString("image"));
                flightModel.setSITE(list.getString("site"));
                flightModel.setLOCATION(list.getString("dest"));

                arrayList.add(flightModel);
            }
            adapter = new FlightsListAdapter(this,arrayList);
            listRecycler.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String name) {
        String json = null;
        try {
            InputStream is = getAssets().open(name+".json");
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
}