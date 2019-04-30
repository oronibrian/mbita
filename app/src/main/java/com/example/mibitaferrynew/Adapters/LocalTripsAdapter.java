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

public class LocalTripsAdapter extends ArrayAdapter<MytripsDetails> {


    public LocalTripsAdapter(Activity context, ArrayList<MytripsDetails> packages) {


        super(context, 0, packages);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_trip_list, parent, false);
        }

        MytripsDetails mytripsDetails = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.routeTextView);

        nameTextView.setText(mytripsDetails.getTravel_from());

        TextView numberTextView = listItemView.findViewById(R.id.travel_date);

        numberTextView.setText(mytripsDetails.getReference_number());


        TextView fromTextView = listItemView.findViewById(R.id.manifestavilableseats);

        fromTextView.setText(mytripsDetails.getTravel_date());


        TextView tooTextView = listItemView.findViewById(R.id.too);

        tooTextView.setText(mytripsDetails.getTravel_to());



        TextView seateTextView = listItemView.findViewById(R.id.seatsbooked);

        seateTextView.setText(mytripsDetails.getAmount());



//        TextView passengenameTextView = listItemView.findViewById(R.id.passengername);
//
//        passengenameTextView.setText(mytripsDetails.getName());
//
//
//        TextView phoneTextView = listItemView.findViewById(R.id.phonenum);
//
//        phoneTextView.setText(mytripsDetails.getPhone());
//
//        TextView issuedTextView = listItemView.findViewById(R.id.issuedon);
//
//        issuedTextView.setText(mytripsDetails.getDate_issued());
//
//        TextView seatTextView = listItemView.findViewById(R.id.seat);
//
//        seatTextView.setText(mytripsDetails.getVehicle_name());

        return listItemView;
    }




}
