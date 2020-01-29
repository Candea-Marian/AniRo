package com.candea.aniro.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.candea.aniro.R;
import com.candea.aniro.activities.AnimeActivity;
import com.candea.aniro.models.Anime;

import java.util.List;

public class RecyclerViewAdapterAnime extends RecyclerView.Adapter<RecyclerViewAdapterAnime.ViewHolder> {

    private Context mContext;
    private List<Anime> mData;
    private RequestOptions option;

    public RecyclerViewAdapterAnime(List<Anime> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        option = new RequestOptions();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title_show.setText(mData.get(position).getTitle());

        Glide.with(mContext).load(mData.get(position).getCover_url()).apply(option).into(holder.image_show);

        holder.list_element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent animeActivityIntent = new Intent(v.getContext(), AnimeActivity.class);
                animeActivityIntent.putExtra("AnilistAnimeID", mData.get(holder.getAdapterPosition()).getId());
                v.getContext().startActivity(animeActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_show;
        TextView title_show;
        CardView list_element;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_show = itemView.findViewById(R.id.image_show);
            title_show = itemView.findViewById(R.id.title_show);
            list_element = itemView.findViewById(R.id.anime_parent_layout);
        }
    }
}
