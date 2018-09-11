package com.cc.newscompanion.fragments;

import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

public class TechFragment extends FragmentTemplate {

    public TechFragment(){
        ID = 1;
        tabColor = R.color.tech_normal;
        statusBarColor = R.color.tech_dark;
        backgroundColor = R.color.tech_light;
    }
    @Override
    public void addQueryTag(){
        queryUri = QueryUtils.addRequiredQueryParameters(queryUri,"technology/technology");
    }
    @Override
    public void setHeaderData(){
        sectionDescriptionView.setText(R.string.tech_description);
        sectionImageView.setImageDrawable(getResources().getDrawable(R.drawable.tech_bg));
    }
}