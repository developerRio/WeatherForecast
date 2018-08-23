package com.originalstocksllc.himanshuraj.weatherforecast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity OnCreate";
    private ArrayList<TemperatureModel> weatherArrayList = new ArrayList<>();
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL weatherURL = NetworkUtility.buildURLForWeather();
        //fetch weather
        new FetchWeatherDetails().execute(weatherURL);

        listView = findViewById(R.id.forecastList);
        Log.i(TAG, "onCreate Weather URL: " + weatherURL);

    }

    private ArrayList<TemperatureModel> parseJSON(String weatherSearchResults) {
        if (weatherArrayList != null) {
            weatherArrayList.clear();
        }

        if (weatherSearchResults != null) {
            try {
                JSONObject rootObject = new JSONObject(weatherSearchResults);

                JSONArray dailyForecast = rootObject.getJSONArray("DailyForecasts");
                for (int i = 0; i < dailyForecast.length(); i++) {

                    TemperatureModel weather = new TemperatureModel();
                    JSONObject resultantObject = dailyForecast.getJSONObject(i);

                    String mDates = resultantObject.getString("Date");
                    weather.setmDate(mDates);

                    // Log.i(TAG, "parseJSON: Dates" + mDates);

                    JSONObject temperatureObj = resultantObject.getJSONObject("Temperature");
                    String minTemp = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemp);
                    // Log.i(TAG, "parseJSON: minimumTemperature :" + minTemp);

                    String maxTemp = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemp);
                    //  Log.i(TAG, "parseJSON: MAXtemp: " + maxTemp);

                    String mLinks = resultantObject.getString("Link");
                    weather.setmLink(mLinks);
                    // Log.i(TAG, "parseJSON: Links" + mLinks);

                    weatherArrayList.add(weather);
                }

                if (weatherArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherArrayList);
                    listView.setAdapter(weatherAdapter);
                }


                return weatherArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(URL... urls) {

            URL weatherURL = urls[0];
            String weatherSearchResults = null;

            try {
                weatherSearchResults = NetworkUtility.getResponseFromHttpUrl(weatherURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackGround : weatherSearchResults" + weatherSearchResults);

            return weatherSearchResults;
        }

        @Override
        protected void onPostExecute(String weatherSearchResults) {

            if (weatherSearchResults != null && !weatherSearchResults.equals("")) {
                weatherArrayList = parseJSON(weatherSearchResults);

                // verify array list contents use Iterator just to check
                Iterator itr = weatherArrayList.iterator();
                while (itr.hasNext()) {

                    TemperatureModel weatherITR = (TemperatureModel) itr.next();
                    Log.i(TAG, "onPostExecute: ITR" + weatherITR.getmDate() + " "
                            + weatherITR.getMaxTemp() + " " + weatherITR.getMinTemp() + " "
                            + weatherITR.getmLink());

                }

            }

            super.onPostExecute(weatherSearchResults);


        }
    }
}
