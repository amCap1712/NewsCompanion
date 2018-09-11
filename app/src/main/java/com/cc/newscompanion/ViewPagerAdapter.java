package com.cc.newscompanion;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cc.newscompanion.fragments.FragmentTemplate;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<FragmentTemplate> fragments = new ArrayList<>();
    private ArrayList<String> fragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragment(FragmentTemplate fragment, String fragmentTitle){
        fragments.add(fragment);
        fragmentTitles.add(fragmentTitle);
    }
}
