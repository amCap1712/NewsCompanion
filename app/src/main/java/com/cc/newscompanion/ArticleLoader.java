package com.cc.newscompanion;

import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {
    private Uri queryURI;
    private List<Article> data;

    public ArticleLoader(Context context, Uri uri) {
        super(context);
        queryURI = uri;
    }

    @Override
    public List<Article> loadInBackground() {
        Log.d("Loader Started","Loading Started");
        ArrayList<Article> articles = null;
        try {
            String response = QueryUtils.requestArticles(queryURI);
            articles = QueryUtils.extractArticlesFromJSON(response);
            Log.d("ArticleLoader.class","Articles Loaded");
        }catch (Exception e){
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(data == null)
            forceLoad();
        else
            deliverResult(data);
    }

    @Override
    public void deliverResult(@Nullable List<Article> data) {
        this.data = data;
        super.deliverResult(data);
    }
}
