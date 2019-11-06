package com.candea.aniro.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.candea.aniro.R;
import com.candea.aniro.adapters.ViewPagerAdapter;
import com.candea.aniro.fragments.PopularFragment;
import com.candea.aniro.fragments.TrendingFragment;
import com.google.android.material.tabs.TabLayout;

public class DiscoverActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        tabLayout = findViewById(R.id.tabLayoutDiscover);
        viewPager = findViewById(R.id.viewPagerDiscover);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPagerAdapter.AddFragment(new TrendingFragment(), "Trending");
        viewPagerAdapter.AddFragment(new PopularFragment(), "Populare");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
