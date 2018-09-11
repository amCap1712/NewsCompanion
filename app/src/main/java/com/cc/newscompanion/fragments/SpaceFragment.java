package com.cc.newscompanion.fragments;

import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

public class SpaceFragment extends FragmentTemplate {

    public SpaceFragment(){
        ID = 0;
        tabColor = R.color.space_normal;
        statusBarColor = R.color.space_dark;
        backgroundColor = R.color.space_light;
    }

    @Override
    public void addQueryTag(){queryUri = QueryUtils.addRequiredQueryParameters(queryUri,"science/space");}

    @Override
    public void setHeaderData(){
        sectionDescriptionView.setText(R.string.space_description);
        sectionImageView.setImageDrawable(getResources().getDrawable(R.drawable.space_bg));
    }
}
