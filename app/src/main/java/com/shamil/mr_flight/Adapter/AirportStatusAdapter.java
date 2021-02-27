package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.AirportStatusViewActivity;
import com.shamil.mr_flight.Model.AirportStatusModel;
import com.shamil.mr_flight.Model.AirportStatusViewModel;
import com.shamil.mr_flight.R;

import java.util.ArrayList;

public class AirportStatusAdapter extends RecyclerView.Adapter<AirportStatusAdapter.ViewHolder> {

    Context ctx;
    ArrayList<AirportStatusModel> list;

    public AirportStatusAdapter(Context ctx, ArrayList<AirportStatusModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public AirportStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.airport_status_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportStatusAdapter.ViewHolder holder, int position) {

        AirportStatusModel airportStatusModel = list.get(position);

        holder.name.setText(airportStatusModel.getNAME());
        holder.code.setText(airportStatusModel.getCODE());

        holder.statusBtn.setOnClickListener(v -> {
            Intent i = new Intent(ctx, AirportStatusViewActivity.class);
            i.putExtra("Q",airportStatusModel.getLINK());
            i.putExtra("N",airportStatusModel.getNAME());
            ctx.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,code;
        Button statusBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.flightTitle);
            code = itemView.findViewById(R.id.airportCode);
            statusBtn = itemView.findViewById(R.id.checkStatusBtn);
        }
    }
}
