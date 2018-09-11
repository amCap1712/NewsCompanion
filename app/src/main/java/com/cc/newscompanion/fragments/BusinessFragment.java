package com.cc.newscompanion.fragments;

import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

public class BusinessFragment extends FragmentTemplate {
    public BusinessFragment(){
        ID = 4;
        tabColor = R.color.business_normal;
        statusBarColor = R.color.business_dark;
        backgroundColor = R.color.business_light;
    }
    @Override
    public void addQueryTag(){
        queryUri = QueryUtils.addRequiredQueryParameters(queryUri,"business/business");
    }
    @Override
    public void setHeaderData(){
        sectionDescriptionView.setText(R.string.business_description);
        sectionImageView.setImageDrawable(getResources().getDrawable(R.drawable.business_bg));
    }
}
