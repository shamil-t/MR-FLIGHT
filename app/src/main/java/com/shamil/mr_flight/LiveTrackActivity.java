package com.shamil.mr_flight;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

public class LiveTrackActivity extends AppCompatActivity {

    WebView webView;
    Button infoBtn;
    MorphBottomNavigationView bottomNavigationView;
    LottieAnimationView lottieAnimationView;
    boolean doubleBackToExitPressedOnce = false;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_track);

        lottieAnimationView = findViewById(R.id.loadingLottie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.live_activity));
        }



        webView = findViewById(R.id.webView);
        infoBtn = findViewById(R.id.infoBtn);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                lottieAnimationView.setVisibility(View.GONE);
            }
        });

        WebSettings ws = webView.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setJavaScriptEnabled(true);

        if (!isInternetConnected()){
            Toast.makeText(this, "No internet Connection..!", Toast.LENGTH_SHORT).show();
        }
        else {
            webView.loadUrl("https://mr-flight-9ff22.web.app/");
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.getMenu().getItem(4).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item0) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return true;
            }
            if (itemId == R.id.item1) {
                Intent i = new Intent(getApplicationContext(), VideosActivity.class);
                startActivity(i);
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
                Toast.makeText(LiveTrackActivity.this, "Live Map", Toast.LENGTH_SHORT).show();

            }
            return false;
        });

        infoBtn.setOnClickListener(v -> {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this,R.style.MyRounded_MaterialComponents_MaterialAlertDialog);
            dialog.setView(R.layout.map_info);
            dialog.show();
        });
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
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