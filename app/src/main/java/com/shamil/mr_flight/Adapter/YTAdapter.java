package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Model.YoutubeModel;
import com.shamil.mr_flight.R;
import com.shamil.mr_flight.Videos.YTVideoWebView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YTAdapter extends RecyclerView.Adapter<YTAdapter.ViewHolder> {

    Context ctx;
    ArrayList<YoutubeModel> youtubeModels;

    public YTAdapter(Context ctx, ArrayList<YoutubeModel> youtubeModels) {
        this.ctx = ctx;
        this.youtubeModels = youtubeModels;
    }

    @NonNull
    @Override
    public YTAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.youtube_video_list_single, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YTAdapter.ViewHolder holder, int position) {
        YoutubeModel youtubeModel = youtubeModels.get(position);

        holder.Title.setText(youtubeModel.getTITLE());
        holder.Desc.setText(youtubeModel.getDESC());

        Picasso.get()
                .load(youtubeModel.getURL())
                .placeholder(R.drawable.progress_img)
                .into(holder.Thumb);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, YTVideoWebView.class);
            i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            i.putExtra("ID",youtubeModel.getVIDEOID());
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return youtubeModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Desc;
        ImageView Thumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.YtTitle);
            Desc = itemView.findViewById(R.id.YtDesc);
            Thumb = itemView.findViewById(R.id.thumbImg);
        }
    }
}
