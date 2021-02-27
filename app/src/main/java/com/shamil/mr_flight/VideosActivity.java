package com.shamil.mr_flight;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shamil.mr_flight.Adapter.YTAdapter;
import com.shamil.mr_flight.Model.YoutubeModel;
import com.shamil.mr_flight.Videos.ApiKey;
import com.shamil.mr_flight.Videos.EndlessRecyclerViewScrollListener;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {

    MorphBottomNavigationView bottomNavigationView;
    String API = ApiKey.YOUTUBE_API_KEY;
    ArrayList<YoutubeModel> youtubeModels;
    String Url = "https://www.googleapis.com/youtube/v3/playlistItems?playlistId=PL8luVVvjZwx9s9tzU9KjMBQIWsugvk3M0&key=AIzaSyAtV_uCIWxr3_0N2hMpLkol8wXBW-i_n44&part=snippet&maxResults=10";
    boolean doubleBackToExitPressedOnce = false;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    YTAdapter adapter;

    RelativeLayout noNetworkLay;
    String NEXT_PAGE = "";
    boolean flag = true;
    Button retryBtn;
    private EndlessRecyclerViewScrollListener scrollListener;

    ProgressBar progressIndicator;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.videosRecycler);
        noNetworkLay = findViewById(R.id.noNetworkLay);
        retryBtn = findViewById(R.id.retryBtn);
        progressIndicator = findViewById(R.id.prgbar);
        lottieAnimationView = findViewById(R.id.loadingLottie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }



        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.getStackFromEnd();

        recyclerView.setLayoutManager(linearLayoutManager);

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            }
            if (itemId == R.id.item1) {
                Toast.makeText(VideosActivity.this, "VIDEOS", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (itemId == R.id.item2) {
                Intent i = new Intent(getApplicationContext(), StatusActivity.class);
                startActivity(i);
                return true;
            }
            if (itemId == R.id.item3){
                Intent i = new Intent(getApplicationContext(),PostsActivty.class);
                startActivity(i);
                return true;
            }
            if (itemId == R.id.item4) {
                Intent i = new Intent(getApplicationContext(), LiveTrackActivity.class);
                startActivity(i);
                return true;
            }
            return false;
        });

        youtubeModels = new ArrayList<>();

        retryBtn.setOnClickListener(v -> getVideos(Url));

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Url = "https://www.googleapis.com/youtube/v3/playlistItems?playlistId=PL8luVVvjZwx9s9tzU9KjMBQIWsugvk3M0&key=AIzaSyAtV_uCIWxr3_0N2hMpLkol8wXBW-i_n44&part=snippet&maxResults=10&pageToken=" + NEXT_PAGE;
                getMoreVideos(Url);
            }
        };

        if (!isInternetConnected()) {
            noNetworkLay.setVisibility(View.VISIBLE);
        }
        else {
            getVideos(Url);
        }

        recyclerView.addOnScrollListener(scrollListener);
    }

    public void getVideos(String Url) {
        youtubeModels.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                NEXT_PAGE = jsonObject.getString("nextPageToken");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                    JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails");

                    YoutubeModel youtubeModel = new YoutubeModel();

                    if (jsonObjectDefault.optJSONObject("medium") != null) {
                        String VideoID = jsonObjectSnippet.getJSONObject("resourceId").getString("videoId");
                        youtubeModel.setVIDEOID(VideoID);
                        youtubeModel.setTITLE(jsonObjectSnippet.getString("title"));
                        youtubeModel.setDESC(jsonObjectSnippet.getString("description"));
                        youtubeModel.setURL(jsonObjectDefault.getJSONObject("medium").getString("url"));
                        youtubeModels.add(youtubeModel);
                    }

                }
                adapter = new YTAdapter(this, youtubeModels);
                recyclerView.setAdapter(adapter);
                progressIndicator.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);


            } catch (Exception e) {
                Toast.makeText(this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Toast.makeText(VideosActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
            //noNetworkLay.setVisibility(View.VISIBLE);
        }
        );

        requestQueue.add(request);
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void getMoreVideos(String Url) {
        progressIndicator.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                NEXT_PAGE = jsonObject.getString("nextPageToken");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                    JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails");

                    YoutubeModel youtubeModel = new YoutubeModel();

                    if (jsonObjectDefault.optJSONObject("medium") != null) {
                        String VideoID = jsonObjectSnippet.getJSONObject("resourceId").getString("videoId");
                        youtubeModel.setVIDEOID(VideoID);
                        youtubeModel.setTITLE(jsonObjectSnippet.getString("title"));
                        youtubeModel.setDESC(jsonObjectSnippet.getString("description"));
                        youtubeModel.setURL(jsonObjectDefault.getJSONObject("medium").getString("url"));

                        youtubeModels.add(youtubeModel);
                    }
                }
                adapter.notifyDataSetChanged();
                progressIndicator.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);

            } catch (Exception e) {
                Toast.makeText(this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, error -> {
            Toast.makeText(VideosActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
            //noNetworkLay.setVisibility(View.VISIBLE);
        }
        );
        requestQueue.add(request);
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