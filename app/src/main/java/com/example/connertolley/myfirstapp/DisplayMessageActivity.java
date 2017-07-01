package com.example.connertolley.myfirstapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.loopj.android.http.*;

import org.apache.commons.lang3.StringUtils;
import org.json.*;
import cz.msebera.android.httpclient.Header;

public class DisplayMessageActivity extends BaseActivity {

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
                    populateJsonWeather(response);
                    populateWarnings(response);
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
                TextView textViewTemp = (TextView) findViewById(R.id.temperature);
                textViewTemp.setText(responseString);
            }
        });
    }

    private void populateWarnings(JSONObject response) throws JSONException {
        JSONObject currentObservation = response.getJSONObject("current_observation");
        String heatIndexString = currentObservation.getString("heat_index_f");
        String temperature = currentObservation.getString("temp_f");

        int heatTemp = 0;

        TextView textViewWarningLabel = (TextView) findViewById(R.id.warningLabel);
        TextView textViewWarning = (TextView) findViewById(R.id.warning);

        if (StringUtils.equalsIgnoreCase(heatIndexString, "NA")) {
            Double value = Double.parseDouble(temperature);
            heatTemp = value.intValue();
        } else {
            heatTemp = Integer.parseInt(heatIndexString);
        }


        if (heatTemp < 80) {
            //it's fine
            textViewWarningLabel.setText("No Issues");
            textViewWarning.setText("Take your dog out and play until you can play no more.");
        } else if (heatTemp >= 80 && heatTemp < 91) {
            //caution
            textViewWarningLabel.setText(R.string.caution);
            textViewWarning.setText(R.string.cautionMessage);
        } else if (heatTemp >= 91 && heatTemp < 104) {
            //extreme caution
            textViewWarningLabel.setText(R.string.extremeCaution);
            textViewWarning.setText(R.string.extremeCautionMessage);
        } else if (heatTemp >= 104 && heatTemp < 126) {
            //danger
            textViewWarningLabel.setText(R.string.danger);
            textViewWarning.setText(R.string.dangerMessage);
        } else {
            //extreme dange
            textViewWarningLabel.setText(R.string.extremeDanger);
            textViewWarning.setText(R.string.extremeDangerMessage);
        }
    }

    private void populateJsonWeather(JSONObject response) throws JSONException {
        JSONObject currentObservation = response.getJSONObject("current_observation");
        JSONObject location = currentObservation.getJSONObject("display_location");
        String cityName = location.getString("city");
        String stateName = location.getString("state");
        String temperature = currentObservation.getString("temperature_string");
        String weather = currentObservation.getString("weather");
        String heatIndex = currentObservation.getString("heat_index_string");
        TextView textViewCity = (TextView) findViewById(R.id.city);
        textViewCity.setText(cityName + ", " + stateName);
        TextView textViewTemp = (TextView) findViewById(R.id.temperature);
        textViewTemp.setText(temperature);
        TextView textViewWeather = (TextView) findViewById(R.id.weather);
        textViewWeather.setText(weather);
        TextView textViewHeatIndex = (TextView) findViewById(R.id.heatIndex);
        textViewHeatIndex.setText(heatIndex);

        String iconUrl = currentObservation.getString("icon_url");
        ImageView weatherImageView = (ImageView) findViewById(R.id.weatherImage);
        Ion.with(weatherImageView)
                .placeholder(R.drawable.dogpaw)
                .error(R.drawable.dogpaws)
                .load(iconUrl);
    }
}
