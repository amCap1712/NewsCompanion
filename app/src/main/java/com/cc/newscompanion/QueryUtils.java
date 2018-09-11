package com.cc.newscompanion;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class QueryUtils {

    private static final int TIMEOUT = 10000;

    public static Uri createBaseUri(String queryUrl){
        return Uri.parse(queryUrl);
    }

    public static Uri addRequiredQueryParameters(Uri baseUri, String tag){
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("tag",tag);
        builder.appendQueryParameter("show-fields","thumbnail,bodyText,headline,byline");
        builder.appendQueryParameter("api-key","dabc1fac-a56b-49cb-bd5d-0670072cf40c");
        builder.appendQueryParameter("pages","1");
        builder.appendQueryParameter("from-date","2018-05-01");
        Log.d("URL Queried",builder.build().toString());
        return builder.build();
    }

    public static Uri addOptionalQueryParameter(Uri baseUri, String parameter, String value){
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter(parameter,value);
        return builder.build();
    }

    public static ArrayList<Article> extractArticlesFromJSON(String jsonResponse){
        ArrayList<Article> articles = new ArrayList<>();
        if(jsonResponse == null || jsonResponse.length() == 0)
            return articles;
        else{
            try {
                JSONObject root = new JSONObject(jsonResponse).getJSONObject("response");
                JSONArray jsonArticles = root.optJSONArray("results");
                int length = jsonArticles.length();
                for (int i = 0;i<length;i++){
                    JSONObject articleData = jsonArticles.optJSONObject(i);
                    JSONObject fields = articleData.getJSONObject("fields");
                    Article article = new Article(fields.optString("headline"),
                            fields.optString("bodyText"),
                            Uri.parse(fields.optString("thumbnail")),
                            obtainDate(articleData.optString("webPublicationDate")),
                            fields.optString("byline"),
                            articleData.optString("webUrl"));
                    articles.add(article);
                    Log.d("QueryUtils.class",article.toString());
                }
            }catch (JSONException e){
                Log.e("QueryUtils.class",e.getMessage());
            }
            return articles;
        }
    }
    public static String requestArticles(Uri uri) throws IOException{
        URL url;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        String response = "";
        try {
            url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(TIMEOUT);
            urlConnection.setConnectTimeout(TIMEOUT);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                response = extractJSONResponse(inputStream);
            } else
                Log.d("QueryUtils.class", "Error Response Code: " + urlConnection.getResponseCode());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return response;
    }

    private static String extractJSONResponse(InputStream inputStream) throws IOException{
        StringBuilder builder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line=reader.readLine())!=null)
            builder.append(line);
        reader.close();
        inputStreamReader.close();
        return builder.toString();
    }

    private static String obtainDate(String dateString){
        String formattedDate="";
        SimpleDateFormat dateFormat = null;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString);
            dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (dateFormat != null)
                formattedDate = dateFormat.format(date);
        }
        return formattedDate;
    }
}
