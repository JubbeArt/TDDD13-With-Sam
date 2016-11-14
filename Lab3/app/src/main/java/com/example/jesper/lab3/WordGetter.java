package com.example.jesper.lab3;

/**
 * Created by Jesper on 2016-11-14.
 */

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WordGetter extends AsyncTask<String, Void, String> {
    private InteractiveSearcher searcher;

    public WordGetter(InteractiveSearcher searcher) {
        this.searcher = searcher;
    }

    protected String doInBackground(String... urls) {
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String input;
            while((input = in.readLine()) != null) {
                result += input;
            }

            System.out.println("Result: " + result);

        } catch (Exception e){
            System.out.println(e);
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return result;
    }

    protected void onPostExecute(String results) {
        try {
            JSONObject json = new JSONObject(results);
            int id = json.getInt("id");
            JSONArray names = json.getJSONArray("result");

            if(id > searcher.getMaxId()){
                //Suggestiongrejer
                searcher.setMaxId(id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}