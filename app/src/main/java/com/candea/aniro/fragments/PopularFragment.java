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
import com.candea.aniro.FeedResultPopularQuery;
import com.candea.aniro.R;
import com.candea.aniro.adapters.RecyclerViewAdapter;
import com.candea.aniro.models.Anime;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class PopularFragment extends Fragment {

    View view;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApolloClient mApolloClient;
    private static final String BASE_URL = "https://graphql.anilist.co";

    public PopularFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popular_fragment, container, false);

        final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();
        final ArrayList<Anime> animeArrayList = new ArrayList<>();

        mApolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(mOkHttpClient)
                .build();

        mApolloClient.query(FeedResultPopularQuery.builder().build()).enqueue(new ApolloCall.Callback<FeedResultPopularQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<FeedResultPopularQuery.Data> response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i = 0; i < 50; i++) {
                            Anime anime = new Anime();
                            anime.setImage_url(response.data().Page().media().get(i).coverImage().large());
                            anime.setTitle(response.data().Page().media().get(i).title().userPreferred());

                            animeArrayList.add(anime);
                        }

                        mRecyclerView = view.findViewById(R.id.recyclerViewPopular);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        mAdapter = new RecyclerViewAdapter(animeArrayList, getContext());

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
