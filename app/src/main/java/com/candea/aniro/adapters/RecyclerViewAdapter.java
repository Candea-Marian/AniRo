package com.candea.aniro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.candea.aniro.R;
import com.candea.aniro.models.Anime;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Anime> mData;
    private RequestOptions option;

    public RecyclerViewAdapter(ArrayList<Anime> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;

        option = new RequestOptions();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_layout_grid, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_show.setText(mData.get(position).getTitle());

        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(option).into(holder.image_show);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_show;
        TextView title_show;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_show = itemView.findViewById(R.id.image_show);
            title_show = itemView.findViewById(R.id.title_show);
        }
    }

}
