package com.candea.aniro.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.candea.aniro.R;
import com.candea.aniro.TopListsQuery;
import com.candea.aniro.adapters.ViewPagerAdapter;
import com.candea.aniro.dataSource.ApolloConnector;
import com.candea.aniro.fragments.PopularFragment;
import com.candea.aniro.fragments.TrendingFragment;
import com.candea.aniro.models.Anime;
import com.candea.aniro.type.MediaSort;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ProgressBar progressBar;
    private static ArrayList<Anime> animeTrendingList = new ArrayList<>();
    private static ArrayList<Anime> animePopularList = new ArrayList<>();
    private static final String TAG = "DiscoverActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        progressBar = findViewById(R.id.progress_bar);

        //bottom navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.discover);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.discover:
                        return true;

                    case R.id.tracker:
                        startActivity(new Intent(getApplicationContext(), TrackerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        QueryAnilistApiTask queryAnilistApiTask = new QueryAnilistApiTask();
        queryAnilistApiTask.execute();
    }

    private void getMedia(int page, int year, List<MediaSort> mediaSorts, final String listName) {
        ApolloConnector.setupApollo().query(
                TopListsQuery
                .builder()
                .page(page)
                .year(year)
                .sort(mediaSorts)
                .build()
        ).enqueue(new ApolloCall.Callback<TopListsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<TopListsQuery.Data> response) {

                for (TopListsQuery.Medium medium : response.data().Page().media()) {

                    Anime anime = new Anime(medium.id(), medium.title().userPreferred(), medium.coverImage().large());

                    if (listName.equals("popular")) {
                        animePopularList.add(anime);
                    } else {
                        animeTrendingList.add(anime);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Log.d(TAG, "Exception " + e.getMessage(), e);
            }
        });
    }

    private class QueryAnilistApiTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (animePopularList.isEmpty() || animeTrendingList.isEmpty()) {
                try {
                    int page = 0;
                    int year = Calendar.getInstance().get(Calendar.YEAR) - 1;

                    List<MediaSort> mediaSortsPopular = new ArrayList<>();
                    mediaSortsPopular.add(MediaSort.POPULARITY_DESC);
                    List<MediaSort> mediaSortsTrending = new ArrayList<>();
                    mediaSortsTrending.add(MediaSort.TRENDING_DESC);

                    String listNamePopular = "popular";
                    String listNameTrendig = "trending";

                    for (int i = 0; i < 2; i++) {
                        page++;

                        getMedia(page, year, mediaSortsTrending, listNameTrendig);
                        Thread.sleep(1500);

                        getMedia(page, year, mediaSortsPopular, listNamePopular);
                        Thread.sleep(1500);
                    }

                } catch (Exception e) {
                    Log.d(TAG, "Exception " + e.getMessage(), e);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);

            tabLayout = findViewById(R.id.tabLayoutDiscover);
            viewPager = findViewById(R.id.viewPagerDiscover);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

            Bundle bundleTrending = new Bundle();
            bundleTrending.putParcelableArrayList("animeList", animeTrendingList);
            Bundle bundlePopular = new Bundle();
            bundlePopular.putParcelableArrayList("animeList", animePopularList);

            TrendingFragment trendingFragment = new TrendingFragment();
            trendingFragment.setArguments(bundleTrending);
            PopularFragment popularFragment = new PopularFragment();
            popularFragment.setArguments(bundlePopular);

            viewPagerAdapter.AddFragment(trendingFragment, "Trending");
            viewPagerAdapter.AddFragment(popularFragment, "Populare");

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
