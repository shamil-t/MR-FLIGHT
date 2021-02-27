package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Model.AirportStatusViewModel;
import com.shamil.mr_flight.R;

import java.util.ArrayList;

public class AirportStatusViewAdpater extends RecyclerView.Adapter<AirportStatusViewAdpater.ViewHolder> {
    Context ctx;
    ArrayList<AirportStatusViewModel> arrayList;
    String fromTo;

    public AirportStatusViewAdpater(Context ctx, ArrayList<AirportStatusViewModel> arrayList,String fromTo) {
        this.ctx = ctx;
        this.arrayList = arrayList;
        this.fromTo = fromTo;
    }

    @NonNull
    @Override
    public AirportStatusViewAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.airport_status_single,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportStatusViewAdpater.ViewHolder holder, int position) {

        AirportStatusViewModel airportStatusViewModel = arrayList.get(position);

        holder.flightNo.setText(airportStatusViewModel.getFLlGHT_NO());
        holder.airline.setText(airportStatusViewModel.getAIRLINE());
        holder.from.setText(airportStatusViewModel.getFROM());
        holder.schedule.setText(airportStatusViewModel.getSCHEDULE());
        holder.arrival.setText(airportStatusViewModel.getARRIVAL());
        holder.status.setText(airportStatusViewModel.getSTATUS());
        holder.fromToTxt.setText(fromTo);

        if(airportStatusViewModel.getSTATUS().equals("Landed")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#0a9400"));
        }
        if(airportStatusViewModel.getSTATUS().equals("Scheduled")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#006394"));
        }
        if(airportStatusViewModel.getSTATUS().equals("Landed Late")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#d95f02"));
        }
        if(airportStatusViewModel.getSTATUS().equals("En-Route")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#3c9ade"));
        }
        if(airportStatusViewModel.getSTATUS().equals("CANCELLED")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#d10000"));
        }
        if(airportStatusViewModel.getSTATUS().startsWith("Delayed")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#e3c519"));
        }
        if(airportStatusViewModel.getSTATUS().startsWith("Departed Late")){
            holder.statusLay.setBackgroundColor(Color.parseColor("#ffa203"));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView flightNo,airline,from,schedule,arrival,status,fromToTxt;
        RelativeLayout statusLay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flightNo = itemView.findViewById(R.id.flightNoEdit);
            airline = itemView.findViewById(R.id.AirlineEdit);
            from = itemView.findViewById(R.id.fromEdit);
            schedule = itemView.findViewById(R.id.scheduleEdit);
            arrival = itemView.findViewById(R.id.ArrivalEdit);
            status = itemView.findViewById(R.id.flightStatus);
            statusLay = itemView.findViewById(R.id.statusLay);
            fromToTxt = itemView.findViewById(R.id.fromTxt);

        }
    }
}
