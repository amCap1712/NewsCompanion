package com.cc.newscompanion;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cc.newscompanion.fragments.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity implements OnListFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String baseUrl = "https://content.guardianapis.com/search?";
    public static final String ALL_TAGS = "technology/technology|science/space|" +
            "science/physics|environment/environment|business/business";
    private ArrayList<Article> articles;
    private NewsAdapter adapter;
    private static int ID = 1712;
    private Uri queryUri;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private TextView emptyView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.dark_black));
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));
        toolbar.setTitleTextAppearance(this,R.style.TitleFont);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        articles = new ArrayList<>();
        adapter = new NewsAdapter(articles, this ,R.color.light_black,this,ID);

        if(Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_SEARCH))
            doSearch(intent.getStringExtra(SearchManager.QUERY));


        progressBar = findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView =  findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.INVISIBLE);

        refreshLayout = findViewById(R.id.swipe_for_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshArticles();
            }
        });

        emptyView = findViewById(R.id.empty_view);
        emptyView.setVisibility(View.INVISIBLE);
    }

    public void refreshArticles(){
        getSupportLoaderManager().restartLoader(ID,null,this);
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

    @NonNull
    @Override
    public android.support.v4.content.Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.d("SearchActivity.class","Loader Created");
        return new ArticleLoader(this,queryUri);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Article>> loader,
                               List<Article> data) {
        progressBar.setVisibility(View.GONE);
        if(data == null){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            articles.addAll(data);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            Log.d("SearchActivity.class","Adapter Notified");
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Article>> loader) {
    }

    public void doSearch(String query){
        queryUri = QueryUtils.createBaseUri(baseUrl);
        queryUri = QueryUtils.addSearchQueryParameters(queryUri,query);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String numberOfArticles = preferences.getString(
                getString(R.string.preference_search_articles_number_key),
                getString(R.string.preference_search_articles_number_default));
        boolean searchAllCategories = preferences.getBoolean(
                getString(R.string.preference_search_categories_key),true);
        if(!searchAllCategories)
            queryUri = QueryUtils.addOptionalQueryParameter(queryUri,"tag",ALL_TAGS);
        queryUri = QueryUtils.addOptionalQueryParameter(queryUri,
                getString(R.string.preference_articles_number_key),
                numberOfArticles);

        getSupportLoaderManager().initLoader(ID,null,this);
    }
}
