package com.candea.aniro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.candea.aniro.R;
import com.candea.aniro.adapters.RecyclerViewAdapterAnime;
import com.candea.aniro.models.Anime;

import java.util.ArrayList;
import java.util.Objects;


public class TrendingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.trending_fragment, container, false);

        final ArrayList<Anime> animeArrayList = getArguments().getParcelableArrayList("animeList");

        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewTrending);
                mRecyclerView.setHasFixedSize(true);

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
                RecyclerView.Adapter mAdapter = new RecyclerViewAdapterAnime(animeArrayList, getContext());

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        return view;
    }
}
