package com.example.connertolley.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.*;
import org.json.*;
import cz.msebera.android.httpclient.Header;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //populate the information
        try {
            populateInfo(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateInfo(String message) throws JSONException {
        WeatherUndergroundRestClient.get("api/13d19c5275b320e1/conditions/q/" + message + ".json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject currentObservation = response.getJSONObject("current_observation");
                    JSONObject location = currentObservation.getJSONObject("display_location");
                    String cityName = location.getString("city");
                    String stateName = location.getString("state");
                    String temperature = currentObservation.getString("temp_f");
                    TextView textViewCity = (TextView) findViewById(R.id.city);
                    textViewCity.setText(cityName);
                    TextView textViewState = (TextView) findViewById(R.id.state);
                    textViewState.setText(stateName);
                    TextView textViewTemp = (TextView) findViewById(R.id.temperature);
                    textViewTemp.setText(temperature);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // If response is a json array
                System.out.println("This is a json array");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable e) {
                System.out.println(responseString);
            }
        });
    }
}
