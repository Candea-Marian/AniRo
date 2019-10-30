package com.example.aniro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniro.R;
import com.example.aniro.adapters.RecyclerViewAdapter;
import com.example.aniro.models.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Anime> animeList = new ArrayList<>();

        String jsonUrl = "https://api.themoviedb.org/3/trending/tv/week?api_key=ff87d2f705446c73180377ac9b5e4b3e";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                jsonUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray results = null;
                        try {
                            results = response.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject jsonObject = null;

                        for(int i = 0; i < 100; i++) {

                            try {
                                jsonObject = results.getJSONObject(i);
                                Anime anime = new Anime();
                                anime.setTitle(jsonObject.getString("original_name"));
                                anime.setImage_url("https://image.tmdb.org/t/p/w1000_and_h563_face" + jsonObject.getString("poster_path"));
                                anime.setStudio("first aired: " + jsonObject.getString("first_air_date"));
                                anime.setRating(jsonObject.getDouble("vote_average") + " stars");
                                animeList.add(anime);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest response", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(animeList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
