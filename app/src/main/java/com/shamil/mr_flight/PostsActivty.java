package com.shamil.mr_flight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shamil.mr_flight.Adapter.PostsViewAdapter;
import com.shamil.mr_flight.Model.ModelItem;
import com.tbuonomo.morphbottomnavigation.MorphBottomNavigationView;

import java.util.ArrayList;

public class PostsActivty extends AppCompatActivity {

    MorphBottomNavigationView bottomNavigationView;
    ArrayList<ModelItem> arrayList;
    PostsViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    TextView postText;
    ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_activty);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.teal_700));
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.recyclerView);
        postText = findViewById(R.id.text);
        progressBar = findViewById(R.id.prgBar);

        recyclerView.setVisibility(View.GONE);

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);


        bottomNavigationView.getMenu().getItem(3).setChecked(true);
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
                Intent i = new Intent(getApplicationContext(),StatusActivity.class);
                startActivity(i);
                return true;
            }
            if (itemId == R.id.item3){
                Toast.makeText(this, "Posts", Toast.LENGTH_SHORT).show();
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

        arrayList = new ArrayList<>();

        if(!isInternetConnected()){
            recyclerView.setVisibility(View.GONE);
            postText.setText("");
            postText.append("No Internet Connection Available");
            postText.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        else {
            postText.setText("");
            postText.append("Loading.......!");
            loadPosts();
        }

    }

    private void loadPosts() {
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("News");
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    ModelItem postsModel = ds.getValue(ModelItem.class);
                    arrayList.add(postsModel);
                    assert postsModel != null;
                    Log.d("DB", "/" + snapshot.getChildrenCount() + "" + postsModel.getDATE());
                }
                if(arrayList.size()==0) {
                    recyclerView.setVisibility(View.GONE);
                    postText.setText("");
                    postText.append("No New Posts Available...!");
                    progressBar.setVisibility(View.GONE);
                }
                if (arrayList != null) {
                    adapter = new PostsViewAdapter(PostsActivty.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    postText.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostsActivty.this, "Cancelled DB Error", Toast.LENGTH_SHORT).show();
            }
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