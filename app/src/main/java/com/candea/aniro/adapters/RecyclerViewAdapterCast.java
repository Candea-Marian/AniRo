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
import com.candea.aniro.AnilistFullMediaQuery;
import com.candea.aniro.R;

import java.util.List;

public class RecyclerViewAdapterCast extends RecyclerView.Adapter<RecyclerViewAdapterCast.ViewHolder> {

    private Context mContext;
    private List<AnilistFullMediaQuery.Edge> mData;

    public RecyclerViewAdapterCast(Context mContext, List<AnilistFullMediaQuery.Edge> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterCast.ViewHolder holder, int position) {

        if (mData.get(position).voiceActors().size() != 0) {
            holder.actorName.setText(mData.get(position).voiceActors().get(0).name().full());
        } else {
            holder.actorName.setText("");
        }

        holder.characterName.setText(mData.get(position).node().name().full());

        if (mData.get(position).voiceActors().size() != 0) {
            Glide.with(mContext).load(mData.get(position).voiceActors().get(0).image().large()).into(holder.actorImg);
        } else {
            Glide.with(mContext).load(mData.get(position).node().image().large()).into(holder.actorImg);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView actorImg;
        TextView actorName;
        TextView characterName;

         ViewHolder(@NonNull View itemView) {
            super(itemView);

            actorImg = itemView.findViewById(R.id.actor_img);
            actorName = itemView.findViewById(R.id.actor_name);
            characterName = itemView.findViewById(R.id.character_name);
        }
    }
}
