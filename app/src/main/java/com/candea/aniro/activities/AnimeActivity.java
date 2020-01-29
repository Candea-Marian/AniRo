package com.candea.aniro.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.candea.aniro.AnilistFullMediaQuery;
import com.candea.aniro.R;
import com.candea.aniro.adapters.RecyclerViewAdapterCast;
import com.candea.aniro.dataSource.ApolloConnector;
import com.candea.aniro.models.Anime;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import org.jetbrains.annotations.NotNull;

public class AnimeActivity extends AppCompatActivity {

    private int ID;
    private Anime selectedMedia;
    private ProgressBar progressBar;
    private static final String TAG = "AnimeActivity";

    TextView tvTitle;
    TextView tvDescription;
    TextView tvScore;
    ImageView tvBanner;
    ImageView tvCover;
    FrameLayout castBgLayout;
    TextView type;
    TextView format;
    TextView status;
    TextView genres;

    RecyclerView castRv;
    RecyclerView episodesRv;
    RecyclerView.Adapter adapterCastRv;
    RecyclerView.Adapter adapterEpisodesRv;
    RecyclerView.LayoutManager castLayoutManager;
    RecyclerView.LayoutManager episodesLayoutManager;

    ExpandingList expandingListEpisodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        selectedMedia = new Anime();

        castBgLayout = findViewById(R.id.castLayoutBG);
        progressBar = findViewById(R.id.progress_bar_animeActivity);
        tvTitle = findViewById(R.id.title_animeAct);
        tvDescription = findViewById(R.id.description_animeAct);
        tvScore = findViewById(R.id.anime_score);
        tvBanner = findViewById(R.id.banner_aniAct);
        tvCover = findViewById(R.id.cover_aniAct);
        type = findViewById(R.id.type);
        format = findViewById(R.id.format);
        status = findViewById(R.id.status);
        genres = findViewById(R.id.genres);

        Intent i = getIntent();
        ID = i.getIntExtra("AnilistAnimeID", 0);

        QueryAnilistApiTask queryAnilistApiTask = new QueryAnilistApiTask();
        queryAnilistApiTask.execute();
    }

    private AnimeActivity getContext() {
        return this;
    }

    private void getMediaById(int id) {
        ApolloConnector.setupApollo().query(
                AnilistFullMediaQuery
                .builder()
                .id(id)
                .build()
        ).enqueue(new ApolloCall.Callback<AnilistFullMediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<AnilistFullMediaQuery.Data> response) {

                AnilistFullMediaQuery.Media media = response.data().Media();

                selectedMedia.setId(ID);

                if (media.title().userPreferred() != null) {
                    selectedMedia.setTitle(media.title().userPreferred());
                }

                if (media.coverImage().large() != null) {
                    selectedMedia.setCover_url(media.coverImage().large());
                }

                if (media.bannerImage() != null) {
                    selectedMedia.setBanner_url(media.bannerImage());
                }

                if (media.episodes() != null) {
                    selectedMedia.setNrEpisodes(media.episodes());
                }

                if (media.description() != null) {
                    selectedMedia.setDescription(media.description());
                }

                if (media.type().rawValue() != null) {
                    selectedMedia.setType(media.type().rawValue());
                }

                if (media.format().rawValue() != null) {
                    selectedMedia.setFormat(media.format().rawValue());
                }

                if (media.status().rawValue() != null) {
                    selectedMedia.setStatus(media.status().rawValue());
                }

                if (media.genres().size() != 0) {
                    selectedMedia.setGenres(media.genres());
                }

                if(media.averageScore() != null) {
                    selectedMedia.setRating(media.averageScore() / (float)10);
                }

                if (!media.streamingEpisodes().isEmpty()) {
                    selectedMedia.setEpisodeList(media.streamingEpisodes());
                }

                if (!media.characters().edges().isEmpty()) {
                    selectedMedia.setCast(media.characters().edges());
                }

                if (media.staff() != null) {
                    selectedMedia.setStaff(media.staff());
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
            castBgLayout.setVisibility(View.GONE);
            tvScore.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                getMediaById(ID);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.d(TAG, "Exception " + e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressBar.setVisibility(View.GONE);
            castBgLayout.setVisibility(View.VISIBLE);
            tvScore.setVisibility(View.VISIBLE);

            tvTitle.setText(selectedMedia.getTitle());
            tvDescription.setText(selectedMedia.getDescription());
            tvScore.setText(Float.toString(selectedMedia.getRating()));
            type.setText(String.format("Type: %s", selectedMedia.getType()));
            format.setText(String.format("Format: %s", selectedMedia.getFormat()));
            status.setText(String.format("Status: %s", selectedMedia.getStatus()));

            //concatenation of the genre list elements to fit in the textview
            StringBuilder showGenres = new StringBuilder();

            if (selectedMedia.getGenres() != null) {
                for (String genre : selectedMedia.getGenres()) {
                    showGenres.append(" ").append(genre);
                }
            }

            genres.setText(String.format("Genres: %s", showGenres.toString()));

            //load images from url
            Glide.with(getContext()).load(selectedMedia.getCover_url()).apply(new RequestOptions().circleCrop()).into(tvCover);
            Glide.with(getContext()).load(selectedMedia.getBanner_url()).into(tvBanner);

            //convert from html for viewing
            if (selectedMedia.getDescription() != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(selectedMedia.getDescription(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvDescription.setText(Html.fromHtml(selectedMedia.getDescription()));
                }
            } else {
                String description = "not available description";
                tvDescription.setText(description);
            }

            //setting up the recyclerView for cast
            castRv = findViewById(R.id.cast);
            castRv.setHasFixedSize(true);

            adapterCastRv = new RecyclerViewAdapterCast(getContext(), selectedMedia.getCast());
            castLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            castRv.setLayoutManager(castLayoutManager);
            castRv.setAdapter(adapterCastRv);

            //expandable list of episodes
            expandingListEpisodes = findViewById(R.id.exanding_list_episodes);
            ExpandingItem episodesExpandableItem = expandingListEpisodes.createNewItem(R.layout.expanding_layout);

            TextView listHeaderEpisodes = expandingListEpisodes.getItemByIndex(0).findViewById(R.id.expanding_item_header);
            listHeaderEpisodes.setText("Episodes");

            episodesExpandableItem.setIndicatorColorRes(R.color.pink);
            episodesExpandableItem.setIndicatorIconRes(R.drawable.tv);

            episodesExpandableItem.createSubItems(selectedMedia.getEpisodeList().size());

            if (selectedMedia.getEpisodeList().size() != 0) {
                for (int i = 0; i < selectedMedia.getEpisodeList().size(); i++) {
                    View view = episodesExpandableItem.getSubItemView(i);

                    TextView episodeTitle = view.findViewById(R.id.item_title);
                    episodeTitle.setText(selectedMedia.getEpisodeList().get(i).title());

                    ImageView episodeThumbnail = view.findViewById(R.id.item_thumbnail);
                    Glide.with(getContext()).load(selectedMedia.getEpisodeList().get(i).thumbnail()).into(episodeThumbnail);
                }
            } else {
                episodesExpandableItem.createSubItem();

                View viewEp = episodesExpandableItem.getSubItemView(0);

                String missingEp = "Missing episodes in database.";
                TextView episodeTitle = viewEp.findViewById(R.id.item_title);
                episodeTitle.setText(missingEp);

                ImageView episodeThumbnail = viewEp.findViewById(R.id.item_thumbnail);
                episodeThumbnail.setImageResource(R.drawable.depressedtom);
            }

            //staff expandable list
            ExpandingItem staffExpandableItem = expandingListEpisodes.createNewItem(R.layout.expanding_layout);

            TextView listHeaderStaff = expandingListEpisodes.getItemByIndex(1).findViewById(R.id.expanding_item_header);
            listHeaderStaff.setText("Staff");

            staffExpandableItem.setIndicatorColor(R.color.darkBlue);
            staffExpandableItem.setIndicatorIconRes(R.drawable.group);

            staffExpandableItem.createSubItems(selectedMedia.getStaff().edges().size());

            for (int i = 0; i < selectedMedia.getStaff().edges().size(); i++) {
                View viewStaff = staffExpandableItem.getSubItemView(i);

                TextView personName = viewStaff.findViewById(R.id.item_title);
                personName.setText(selectedMedia.getStaff().edges().get(i).node().name().full());

                TextView personRole = viewStaff.findViewById(R.id.role);
                personRole.setText(selectedMedia.getStaff().edges().get(i).role());

                ImageView personImg = viewStaff.findViewById(R.id.item_thumbnail);
                Glide.with(getContext()).load(selectedMedia.getStaff().edges().get(i).node().image().large()).into(personImg);
            }

        }
    }
}
