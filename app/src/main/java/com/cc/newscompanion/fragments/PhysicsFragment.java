package com.cc.newscompanion.fragments;

import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

public class PhysicsFragment extends FragmentTemplate {

    public PhysicsFragment(){
        ID = 2;
        tabColor = R.color.physics_normal;
        statusBarColor = R.color.physics_dark;
        backgroundColor = R.color.physics_light;
    }
    @Override
    public void addQueryTag(){
        queryUri = QueryUtils.addRequiredQueryParameters(queryUri,"science/physics");
    }
    @Override
    public void setHeaderData(){
        sectionDescriptionView.setText(R.string.physics_description);
        sectionImageView.setImageDrawable(getResources().getDrawable(R.drawable.physics_bg));
    }
}
