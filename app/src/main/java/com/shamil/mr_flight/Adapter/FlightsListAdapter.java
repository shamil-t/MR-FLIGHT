package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Model.FlightModel;
import com.shamil.mr_flight.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlightsListAdapter extends RecyclerView.Adapter<FlightsListAdapter.ViewHolder> {

    Context ctx;
    ArrayList<FlightModel> flightsList;

    public FlightsListAdapter(Context ctx, ArrayList<FlightModel> flightsList) {
        this.ctx = ctx;
        this.flightsList = flightsList;
    }

    @NonNull
    @Override
    public FlightsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.flight_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightsListAdapter.ViewHolder holder, int position) {

        FlightModel flightModel = flightsList.get(position);

        holder.head.setText(flightModel.getHEAD());
        holder.desc.setText(flightModel.getDESC());
        holder.locH.append(flightModel.getHEAD());
        holder.site.setText(flightModel.getSITE());
        holder.location.setText(flightModel.getLOCATION());

        Picasso.get()
                .load(flightModel.getIMAGE())
                .placeholder(R.drawable.progress_img)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return flightsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView head,desc,location,site,locH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            head = itemView.findViewById(R.id.hName);
            desc = itemView.findViewById(R.id.desc);
            locH = itemView.findViewById(R.id.loactH);
            location = itemView.findViewById(R.id.location);
            site = itemView.findViewById(R.id.site);
        }
    }
}
