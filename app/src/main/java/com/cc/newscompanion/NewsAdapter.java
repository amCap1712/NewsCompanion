package com.cc.newscompanion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.newscompanion.fragments.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final List<Article> mValues;
    private final OnListFragmentInteractionListener mListener;
    private int backgroundColor;
    private Context context;
    private int fragmentID;

    public NewsAdapter(List<Article> items, OnListFragmentInteractionListener listener, int backgroundColor,
                Context context, int fragmentID) {
        mValues = items;
        mListener = listener;
        this.backgroundColor = backgroundColor;
        this.context = context;
        this.fragmentID = fragmentID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Article article = mValues.get(position);
        holder.mView.setBackgroundColor(ContextCompat.getColor(context,backgroundColor));
        holder.headlineView.setText(article.getHeadline());
        holder.authorView.setText(article.getAuthor());
        holder.dateView.setText(article.getDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                    mListener.onListFragmentInteraction(article, fragmentID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        TextView headlineView;
        TextView authorView;
        TextView dateView;

         ViewHolder(View view) {
            super(view);
            mView = view;
            headlineView = view.findViewById(R.id.headline);
            authorView = view.findViewById(R.id.author);
            dateView = view.findViewById(R.id.date);
        }
    }
}
