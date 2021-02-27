package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamil.mr_flight.Model.ModelItem;
import com.shamil.mr_flight.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsViewAdapter extends RecyclerView.Adapter<PostsViewAdapter.ViewHolder> {

    Context ctx;
    ArrayList<ModelItem> list;

    public PostsViewAdapter(Context ctx, ArrayList<ModelItem> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public PostsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.posts_item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewAdapter.ViewHolder holder, int position) {

        ModelItem postModel = list.get(position);

        holder.Date.setText(postModel.getDATE());

        Picasso.get()
                .load(postModel.getURL())
                .placeholder(R.drawable.progress_img)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            Date = itemView.findViewById(R.id.dateTxt);
        }
    }
}
