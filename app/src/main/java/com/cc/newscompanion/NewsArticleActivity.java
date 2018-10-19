package com.cc.newscompanion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

public class NewsArticleActivity extends AppCompatActivity {
    private String webUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_article);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextAppearance(this,R.style.TitleFont);

        Bundle extras = getIntent().getExtras();
        TextView headlineView = findViewById(R.id.article_headline);
        TextView authorView = findViewById(R.id.article_author);
        TextView dateView = findViewById(R.id.article_date);
        TextView textView = findViewById(R.id.article_text);

        headlineView.setText(extras.getString("headline"));
        authorView.setText(extras.getString("author"));
        dateView.setText(extras.getString("date"));
        textView.setText(extras.getString("text"));

        Glide.with(this).load(extras.get("image_url"))
                .into((ImageView) findViewById(R.id.article_image));

        webUrl = extras.getString("webUrl");

        int fragmentID = extras.getInt("fragmentID",0);

        toolbar.setBackgroundColor(ContextCompat.getColor(this,chooseBackgroundColor(fragmentID)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,chooseStatusBarColor(fragmentID)));
        findViewById(R.id.container).setBackgroundColor(ContextCompat.getColor(this,chooseTextBackgroundColor(fragmentID)));
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));
        getSupportActionBar().setTitle(setToolbarTitle(fragmentID));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news_article,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.read_on_web){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(webUrl));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private int chooseBackgroundColor(int id){
        int color = R.color.dark_black;
        switch (id){
            case 0: color = R.color.space_normal;
                break;
            case 1: color = R.color.tech_normal;
                break;
            case 2: color = R.color.physics_normal;
                break;
            case 3: color = R.color.environment_normal;
                break;
            case 4: color = R.color.business_normal;
                break;
        }
        return color;
    }

    private int chooseStatusBarColor(int id){
        int color = R.color.dark_black;
        switch (id){
            case 0: color = R.color.space_dark;
                break;
            case 1: color = R.color.tech_dark;
                break;
            case 2: color = R.color.physics_dark;
                break;
            case 3: color = R.color.environment_dark;
                break;
            case 4: color = R.color.business_dark;
                break;
        }
        return color;
    }

    private int chooseTextBackgroundColor(int id){
        int color = R.color.light_black;
        switch (id){
            case 0: color = R.color.space_light;
                break;
            case 1: color = R.color.tech_light;
                break;
            case 2: color = R.color.physics_light;
                break;
            case 3: color = R.color.environment_light;
                break;
            case 4: color = R.color.business_light;
                break;
        }
        return color;
    }

    private String setToolbarTitle(int id){
        switch (id){
            case 0: return "Space";
            case 1: return "Tech";
            case 2: return "Physics";
            case 3: return "Environment";
            case 4: return "Business";
        }
        return "News Companion";
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
