package com.senacor.devconfapp.handlers;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.senacor.devconfapp.R;
import com.senacor.devconfapp.activities.EventActivity;
import com.senacor.devconfapp.adapters.SpeechAdapter;
import com.senacor.devconfapp.clients.AsynchRestClient;
import com.senacor.devconfapp.models.Speech;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by saba on 11.12.16.
 */

public class SpeechHandler {

    private Activity activity;


    public SpeechHandler(Activity activity) {
        this.activity = activity;
    }

    public void getSpeeches(String speechesUrl) {

        AsynchRestClient.get(activity, speechesUrl, null, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        System.out.println("in getSpeeches");
                        ArrayList<Speech> speechArray = new ArrayList<>();
                        SpeechAdapter speechAdapter = new SpeechAdapter(activity, speechArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                speechAdapter.add(new Speech(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ListView speechlist = (ListView) activity.findViewById(R.id.list_speeches);
                        speechlist.setAdapter(speechAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                    }
                });
    }



    public void addSpeech(RequestParams params) {

        final String url = EventActivity.URL + "/speeches/createSpeech";

        AsynchRestClient.post(activity, url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.i("Information", "in speechhandler add speech method");
                Log.i("Information", "speeches were successfully updated");
                getSpeeches(EventActivity.URL+"/speeches");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String error, Throwable throwable) {
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });





    }

}
