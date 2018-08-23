package com.originalstocksllc.himanshuraj.weatherforecast;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtility {

    private static final String WEATHER_URL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/206678";

    private static final String API_KEY = "Q6RS6Dfi4Ln2Ifkd59AvjtDjaVLujlpT";

    private static final String PARAM_API_KEY = "apikey";

    private static final String PARAM_UNIT = "metric";

    private static final String METRIC_VALUE = "true";

    public static URL buildURLForWeather() {

        Uri builtUri = Uri.parse(WEATHER_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_UNIT, METRIC_VALUE)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("NETWORK URL ...:", "Check : " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }


}
