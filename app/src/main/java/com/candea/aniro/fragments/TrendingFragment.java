package com.candea.aniro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.candea.aniro.FeedResultTrendingQuery;
import com.candea.aniro.R;
import com.candea.aniro.adapters.RecyclerViewAdapter;
import com.candea.aniro.models.Anime;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class TrendingFragment extends Fragment {

    private View view;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApolloClient mApolloClient;
    private static final String BASE_URL = "https://graphql.anilist.co";

    public TrendingFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.trending_fragment, container, false);

        final ArrayList<Anime> animeList = new ArrayList<>();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        mApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        mApolloClient.query(FeedResultTrendingQuery.builder().build()).enqueue(new ApolloCall.Callback<FeedResultTrendingQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<FeedResultTrendingQuery.Data> response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i = 0; i < 50; i++) {
                            Anime anime = new Anime();
                            anime.setImage_url(response.data().Page().media().get(i).coverImage().large());
                            anime.setTitle(response.data().Page().media().get(i).title().romaji());
                            anime.setDescription(response.data().Page().media().get(i).description());
                            anime.setBanner_url(response.data().Page().media().get(i).bannerImage());
                            if(response.data().Page().media().get(i).episodes() != null)
                                anime.setNrEpisodes(response.data().Page().media().get(i).episodes());
                            if(response.data().Page().media().get(i).averageScore() != null)
                                anime.setRating(response.data().Page().media().get(i).averageScore().toString());

                            animeList.add(anime);
                        }

                        mRecyclerView = view.findViewById(R.id.recyclerViewTrending);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        mAdapter = new RecyclerViewAdapter(animeList, getContext());

                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    }
                });

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });

        return view;
    }
}
