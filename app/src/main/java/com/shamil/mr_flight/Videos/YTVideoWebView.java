package com.shamil.mr_flight.Videos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.shamil.mr_flight.R;

import java.util.Objects;

public class YTVideoWebView extends AppCompatActivity {

    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_t_video_web_view);

        String VIDEO_ID  = Objects.requireNonNull(getIntent().getExtras()).getString("ID");

        String Url = "https://www.youtube.com/embed/"+VIDEO_ID;

        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());

        WebSettings ws = webView.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setJavaScriptEnabled(true);

        webView.loadUrl(Url);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}