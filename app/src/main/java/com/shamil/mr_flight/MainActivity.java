package com.shamil.mr_flight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shamil.mr_flight.Model.CarouselModel;
import com.shamil.mr_flight.Videos.YTVideoWebView;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MorphBottomNavigationView bottomNavigationView;
    LinearLayout airportsListBtn,flighgtlistBtn;
    ImageCarousel carousel;
    ArrayList<CarouselModel> carouselModels;
    List<CarouselItem> YTData;
    String Url = "https://www.googleapis.com/youtube/v3/playlistItems?playlistId=PL8luVVvjZwx9s9tzU9KjMBQIWsugvk3M0&key=AIzaSyAtV_uCIWxr3_0N2hMpLkol8wXBW-i_n44&part=snippet&maxResults=5";
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        airportsListBtn = findViewById(R.id.airportLay);
        flighgtlistBtn = findViewById(R.id.flightLay);
        carousel = findViewById(R.id.carousel);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item0){
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                return true;
            }
            if(itemId == R.id.item1){
                Intent i = new Intent(getApplicationContext(),VideosActivity.class);
                startActivity(i);
                return true;
            }
            if(itemId == R.id.item2){
                Intent i = new Intent(getApplicationContext(),StatusActivity.class);
                startActivity(i);
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

        airportsListBtn.setOnClickListener(v->{
            Intent i = new Intent(getApplicationContext(),ListActivity.class);
            i.putExtra("HEAD","AIRPORTS IN INDIA");
            i.putExtra("KEY","A");
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
        });

        flighgtlistBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),ListActivity.class);
            i.putExtra("HEAD","FLIGHTS IN INDIA");
            i.putExtra("KEY","F");
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
        });


        YTData = new ArrayList<>();
        carouselModels = new ArrayList<>();

        carousel.setShowNavigationButtons(false);
        getYTData();

    }

    public void getYTData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, Url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                    JSONObject jsonObjectDefault = jsonObjectSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                    CarouselModel carouselModel = new CarouselModel();

                    String VideoID = jsonObjectSnippet.getJSONObject("resourceId").getString("videoId");

                    carouselModel.setID(VideoID);
                    carouselModel.setTITLE(jsonObjectSnippet.getString("title"));
                    carouselModel.setIMAGE(jsonObjectDefault.getString("url"));

                    carouselModels.add(carouselModel);
                }
                Log.d("MODELS",""+carouselModels);
                loadCarousal();
            } catch (Exception e) {
                Toast.makeText(this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }, error -> Toast.makeText(this, "Error" + error, Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(request);
    }

    private void loadCarousal() {
        for (int i=0;i<carouselModels.size();i++){
            CarouselModel carouselModel = carouselModels.get(i);
            YTData.add(new CarouselItem(
                    carouselModel.getIMAGE(),carouselModel.getTITLE()
                    ));
        }
        carousel.addData(YTData);

        carousel.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int i, CarouselItem carouselItem) {
                CarouselModel carouselModel = carouselModels.get(i);
                Intent web = new Intent(getApplicationContext(), YTVideoWebView.class);
                web.putExtra("ID",carouselModel.getID());
                web.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(web);
            }

            @Override
            public void onLongClick(int i, CarouselItem carouselItem) {

            }
        });
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