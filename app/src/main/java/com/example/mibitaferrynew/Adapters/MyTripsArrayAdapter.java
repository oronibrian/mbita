package com.example.mibitaferrynew.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mibitaferrynew.Model.MytripsDetails;
import com.example.mibitaferrynew.MyApplication;
import com.example.mibitaferrynew.R;

import java.util.ArrayList;

public class MyTripsArrayAdapter extends ArrayAdapter<MytripsDetails> {
    private MyApplication app;

    public MyTripsArrayAdapter(Activity context, ArrayList<MytripsDetails> packages) {


        super(context, 0, packages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        app = (MyApplication) getContext().getApplicationContext();

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.manifest_list_item, parent, false);
        }

        MytripsDetails mytripsDetails = getItem(position);



        TextView numberTextView = listItemView.findViewById(R.id.type_list_item);

        numberTextView.setText(mytripsDetails.getTravel_from());


        TextView fromTextView = listItemView.findViewById(R.id.number_list_item);

        fromTextView.setText(mytripsDetails.getTravel_date());


        TextView tooTextView = listItemView.findViewById(R.id.amount_list_item);

        tooTextView.setText(mytripsDetails.getTravel_to());


        return listItemView;
    }





}
