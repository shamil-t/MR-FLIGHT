package com.shamil.mr_flight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.shamil.mr_flight.Adapter.AirportFragmentAdapter;
import com.shamil.mr_flight.Adapter.AirportStatusViewAdpater;
import com.shamil.mr_flight.Model.AirportStatusViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class AirportStatusViewActivity extends AppCompatActivity {

    TextView headTxt;
    TabLayout tabLayout;
    ViewPager viewPager;
    String Query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_status_view);

        String Link = Objects.requireNonNull(getIntent().getExtras()).getString("Q");
        String Head = getIntent().getExtras().getString("N");
        Query = "https://webscraper-mrflight.herokuapp.com/getdata?s="+Link;

        headTxt = findViewById(R.id.HeadTxt);

        tabLayout = findViewById(R.id.tabsLay);
        viewPager = findViewById(R.id.viewPager);

        headTxt.setText(Head);

        tabLayout.addTab(tabLayout.newTab().setText("ARRIVAL"));
        tabLayout.addTab(tabLayout.newTab().setText("DEPARTURE"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Bundle bundle = new Bundle();
        bundle.putString("Query",Query);

        final AirportFragmentAdapter adapter = new
                AirportFragmentAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount(),bundle);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}