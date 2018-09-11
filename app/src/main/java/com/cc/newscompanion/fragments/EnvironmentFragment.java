package com.cc.newscompanion.fragments;

import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

public class EnvironmentFragment extends FragmentTemplate {

    public EnvironmentFragment(){
        ID = 3;
        tabColor = R.color.environment_normal;
        statusBarColor = R.color.environment_dark;
        backgroundColor = R.color.environment_light;
    }
    @Override
    public void addQueryTag(){
        queryUri = QueryUtils.addRequiredQueryParameters(queryUri,"environment/environment");
    }
    @Override
    public void setHeaderData(){
        sectionDescriptionView.setText(R.string.environment_description);
        sectionImageView.setImageDrawable(getResources().getDrawable(R.drawable.environment_bg));
    }
}
