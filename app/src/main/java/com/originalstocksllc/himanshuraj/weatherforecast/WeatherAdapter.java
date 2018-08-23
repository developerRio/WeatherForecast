package com.originalstocksllc.himanshuraj.weatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends ArrayAdapter<TemperatureModel> {


    public WeatherAdapter(@NonNull Context context, ArrayList<TemperatureModel> weatherArrayList) {
        super(context, 0, weatherArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TemperatureModel weatherForecast = getItem(position);
        Context context;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        context = convertView.getContext();
        TextView dateTextView = convertView.findViewById(R.id.dateText);
        TextView minTempView = convertView.findViewById(R.id.tempLowText);
        TextView maxTempView = convertView.findViewById(R.id.tempHighText);
        TextView linkText = convertView.findViewById(R.id.linkText);

        if (weatherForecast != null) {
            dateTextView.setText(weatherForecast.getmDate());
            maxTempView.setText(weatherForecast.getMaxTemp());
            minTempView.setText(weatherForecast.getMinTemp());
            linkText.setText(weatherForecast.getmLink());
        }

        return convertView;
    }
}
