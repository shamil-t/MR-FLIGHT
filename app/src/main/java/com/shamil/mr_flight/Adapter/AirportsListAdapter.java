package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Model.AirportModel;
import com.shamil.mr_flight.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AirportsListAdapter extends RecyclerView.Adapter<AirportsListAdapter.ViewHolder> {

    Context ctx;
    ArrayList<AirportModel> arrayList;

    public AirportsListAdapter(Context ctx, ArrayList<AirportModel> arrayList) {
        this.ctx = ctx;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AirportsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.airport_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportsListAdapter.ViewHolder holder, int position) {

        AirportModel airportModel = arrayList.get(position);

        holder.head.setText(airportModel.getNAME());
        holder.desc.setText(airportModel.getDESC());
        holder.loaction.setText(airportModel.getLOCATION());

        Picasso.get()
                .load(airportModel.getIMAGE())
                .placeholder(R.drawable.progress_img)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView head,desc,loaction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            head = itemView.findViewById(R.id.hName);
            desc = itemView.findViewById(R.id.desc);
            loaction = itemView.findViewById(R.id.location);
        }
    }
}
