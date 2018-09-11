package com.cc.newscompanion;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cc.newscompanion.fragments.BusinessFragment;
import com.cc.newscompanion.fragments.EnvironmentFragment;
import com.cc.newscompanion.fragments.FragmentTemplate;
import com.cc.newscompanion.fragments.OnListFragmentInteractionListener;
import com.cc.newscompanion.fragments.PhysicsFragment;
import com.cc.newscompanion.fragments.SpaceFragment;
import com.cc.newscompanion.fragments.TechFragment;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener, TabLayout.OnTabSelectedListener{
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private View view;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));
        toolbar.setTitleTextAppearance(this,R.style.TitleFont);

        view = findViewById(R.id.no_connection);
        tabLayout = findViewById(R.id.tabs);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnected()) {
            view.setVisibility(View.GONE);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.space_dark));
            addFragments();
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setOnTabSelectedListener(this);
            tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.space_normal));
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.space_normal));
        }else {
            view.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }
    }

    private void addFragments(){
        viewPagerAdapter.addFragment(new SpaceFragment(),"Space");
        viewPagerAdapter.addFragment(new TechFragment(),"Tech");
        viewPagerAdapter.addFragment(new PhysicsFragment(),"Physics");
        viewPagerAdapter.addFragment(new EnvironmentFragment(),"Environment");
        viewPagerAdapter.addFragment(new BusinessFragment(),"Business");
    }

    @Override
    public void onListFragmentInteraction(Article item, int fragmentID) {
        Intent intent = new Intent(this,NewsArticleActivity.class);
        intent.putExtra("headline",item.getHeadline());
        intent.putExtra("image_url",item.getImageURI());
        intent.putExtra("text",item.getText());
        intent.putExtra("author",item.getAuthor());
        intent.putExtra("date",item.getDate());
        intent.putExtra("webUrl",item.getWebUrl());
        intent.putExtra("fragmentID",fragmentID);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        FragmentTemplate fragment = (FragmentTemplate) viewPagerAdapter.getItem(tabLayout.getSelectedTabPosition());
       // tabLayout.setSelectedTabIndicatorColor();
        tabLayout.setBackgroundColor(ContextCompat.getColor(this,fragment.tabColor));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,fragment.statusBarColor));
        toolbar.setBackgroundColor(ContextCompat.getColor(this,fragment.tabColor));

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
