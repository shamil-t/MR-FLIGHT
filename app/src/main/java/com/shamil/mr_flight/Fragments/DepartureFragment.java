package com.shamil.mr_flight.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shamil.mr_flight.Adapter.AirportStatusViewAdpater;
import com.shamil.mr_flight.Model.AirportStatusViewModel;
import com.shamil.mr_flight.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartureFragment extends Fragment {

    RecyclerView dRecycler;
    LottieAnimationView lottieAnimationView;
    ArrayList<AirportStatusViewModel> arrayList;
    AirportStatusViewAdpater adapter;

    public DepartureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departure, container, false);
        dRecycler = view.findViewById(R.id.airportStatusRecyclerDeparture);
        lottieAnimationView = view.findViewById(R.id.loadingLottie);

        arrayList = new ArrayList<>();
        dRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Bundle bundle = getArguments();
        assert bundle != null;
        String Query = bundle.getString("Query");
        loadDeparture(Query,view.getContext());
        return view;
    }

    private void loadDeparture(String query, Context ctx) {
        lottieAnimationView.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        StringRequest request = new StringRequest(Request.Method.GET, query, response -> {
            try {
                Log.d("Response", "" + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("Departure");

                Log.d("ARRAY", "" + jsonArray.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    AirportStatusViewModel model = new AirportStatusViewModel();

                    model.setFLlGHT_NO(jsonObject1.getString("FlightNum"));
                    model.setAIRLINE(jsonObject1.getString("Aname"));
                    model.setFROM(jsonObject1.getString("From"));
                    model.setSCHEDULE(jsonObject1.getString("Schedule"));
                    model.setARRIVAL(jsonObject1.getString("Arrival"));
                    model.setSTATUS(jsonObject1.getString("Status"));

                    arrayList.add(model);

                }
                adapter = new AirportStatusViewAdpater(ctx, arrayList,"TO :-");
                dRecycler.setAdapter(adapter);
                lottieAnimationView.setVisibility(View.GONE);

            } catch (Exception e) {
                Toast.makeText(ctx, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(ctx, "Error" + error, Toast.LENGTH_SHORT).show();
            //noNetworkLay.setVisibility(View.VISIBLE);
        }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}