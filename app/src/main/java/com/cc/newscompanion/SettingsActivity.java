package com.cc.newscompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class NewsPreference extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference numberOfArticles = findPreference(getString(R.string.preference_articles_number_key));
            bindPreferenceSummaryToValue(numberOfArticles);
            Preference numberSearchResults = findPreference(getString(R.string.preference_search_articles_number_key));
            bindPreferenceSummaryToValue(numberSearchResults);
            Preference orderBy = findPreference(getString(R.string.preference_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
            Preference searchCategory = findPreference(getString(R.string.preference_search_categories_key));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int preferenceIndex = listPreference.findIndexOfValue(newValue.toString());
                if(preferenceIndex >= 0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[preferenceIndex]);
                }
            } else if(!(preference instanceof CheckBoxPreference))
                preference.setSummary(value);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference){
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(),"");
            onPreferenceChange(preference,preferenceString);
        }
    }
}
