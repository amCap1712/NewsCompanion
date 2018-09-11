package com.cc.newscompanion.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cc.newscompanion.Article;
import com.cc.newscompanion.ArticleLoader;
import com.cc.newscompanion.NewsAdapter;
import com.cc.newscompanion.QueryUtils;
import com.cc.newscompanion.R;

import java.util.ArrayList;
import java.util.List;


public abstract class FragmentTemplate extends Fragment implements LoaderManager.LoaderCallbacks<List<Article>>{

    private ArrayList<Article> articles;
    private OnListFragmentInteractionListener mListener;
    private String baseUrl = "https://content.guardianapis.com/search?";
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyView;
    protected Uri queryUri;
    TextView sectionDescriptionView;
    ImageView sectionImageView;
    public int ID;
    public int tabColor;
    public int statusBarColor;
    public int backgroundColor;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentTemplate() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryUri = QueryUtils.createBaseUri(baseUrl);
        addQueryTag();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String numberOfArticles = preferences.getString(getString(R.string.preference_articles_number_key),
                getString(R.string.preference_articles_number_default));
        String orderBy = preferences.getString(getString(R.string.preference_order_by_key),
                getString(R.string.preference_order_by_newest));
        queryUri = QueryUtils.addOptionalQueryParameter(queryUri,getString(R.string.preference_articles_number_key),numberOfArticles);
        queryUri = QueryUtils.addOptionalQueryParameter(queryUri, getString(R.string.preference_order_by_key),orderBy);
        articles = new ArrayList<>();
        adapter = new NewsAdapter(articles, mListener, backgroundColor,getContext(),ID);
        getLoaderManager().initLoader(ID,null,this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template_list, container, false);
        Context context = view.getContext();
        view.setBackgroundColor(ContextCompat.getColor(context,backgroundColor));
        recyclerView =  view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.progress_bar);
        sectionImageView = view.findViewById(R.id.section_image);
        sectionDescriptionView = view.findViewById(R.id.section_description);
        emptyView = view.findViewById(R.id.empty_view);

        setHeaderData();

        emptyView.setVisibility(View.INVISIBLE);
        Drawable progressBarDrawable = progressBar.getIndeterminateDrawable().mutate();
        progressBarDrawable.setColorFilter(ContextCompat.getColor(context,tabColor),
                android.graphics.PorterDuff.Mode.SRC_IN);

        progressBar.setProgressDrawable(progressBarDrawable);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
             throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        Log.d("FragmentTemplate.class","Loader Created");
        return new ArticleLoader(getActivity().getApplicationContext(),queryUri);
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<Article>> loader, List<Article> data) {
        progressBar.setVisibility(View.GONE);
        if(data == null){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
            articles.addAll(data);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }
        Log.d("FragmentTemplate.class","Adapter Notified");
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<Article>> loader) {
    }

    public abstract void addQueryTag();
    public abstract void setHeaderData();
}
