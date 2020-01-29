package com.candea.aniro.dataSource;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class ApolloConnector {

    private static final String ANILIST_API_URL = "https://graphql.anilist.co";

    public static ApolloClient setupApollo() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        return ApolloClient.builder()
                .serverUrl(ANILIST_API_URL)
                .okHttpClient(okHttpClient)
                .build();
    }
}
