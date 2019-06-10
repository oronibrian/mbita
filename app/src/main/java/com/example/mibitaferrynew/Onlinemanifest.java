package com.example.mibitaferrynew;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.congfandi.simpledatepicker.Picker;
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.Adapters.MyTripsArrayAdapter;
import com.example.mibitaferrynew.Model.MytripsDetails;
import com.example.mibitaferrynew.utils.Tools;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Onlinemanifest extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ProgressDialog progressDialog;
    MyApplication app;

    ArrayList<MytripsDetails> mytripsDetails;
    MyTripsArrayAdapter manifestListAdapter;

    ListView mytripslistView;
    String dbDate;


    MaterialButton  btnsetdate;
    Toolbar mActionBarToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinemanifest);

        app = (MyApplication) getApplication();


        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Online Manifest");

        mytripsDetails = new ArrayList<MytripsDetails>();

        mytripslistView = findViewById(R.id.online_manifest_listview);


       btnsetdate=findViewById(R.id.btnmanifestdate);

        ((MaterialButton)findViewById(R.id.btnmanifestdate)).setOnClickListener(new View.OnClickListener() {
//       btnsetdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDatePickerDark((MaterialButton)v);
//                new Picker().show(getSupportFragmentManager(), null);


            }
        });

    }

    private void dialogDatePickerDark(final MaterialButton bt) {
        Calendar cur_calender = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePicker = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        ((MaterialButton) findViewById(R.id.btnmanifestdate)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark theme
        datePicker.setThemeDark(true);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.setCancelColor(getResources().getColor(R.color.grey_900));
        datePicker.setOkColor(getResources().getColor(R.color.grey_900));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getSupportFragmentManager(), "Datepickerdialog");
//        datePicker.show(getFragmentManager(), "Datepickerdialog");
    }
    private void getManifestClick() {

        Log.e("Route", app.getRoute());

        progressDialog = ProgressDialog.show(this, "Loading Manifest", "Please Wait...", true);


        RequestQueue batchreserve = Volley.newRequestQueue(Onlinemanifest.this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", "rWIv7GWzSp");
        params.put("api_key", "831b238c5cd73308520e38bbc6c1774f470a89e96d07a5bb6bcac3b86456f889");
        params.put("route", app.getRoute());
        params.put("action", "PassengerManifest");
        params.put("travel_date", dbDate);

        JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("response_code") == 0) {
                                JSONArray jsonArray = response.getJSONArray("tickets");
                                Log.i("Response:", jsonArray.toString());
                                progressDialog.dismiss();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String car_name = jsonObject1.getString("reference_number");
                                    String travel_from = jsonObject1.getString("travel_from");
                                    String travel_to = jsonObject1.getString("travel_to");

                                    Log.e("Car", car_name);

                                    mytripsDetails.add(new MytripsDetails(car_name, travel_from, travel_to, travel_from, travel_from));


                                }

                            } else {

                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }

                            manifestListAdapter = new MyTripsArrayAdapter(Onlinemanifest.this, mytripsDetails);


                            mytripslistView.setAdapter(manifestListAdapter);
                            manifestListAdapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }


        };

        batchreserve.add(req);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            String dateString = dateFormat.format(calendar.getTime());

        Toast.makeText(this, dateString, Toast.LENGTH_SHORT).show();


        btnsetdate.setText(dateString);
        dbDate = btnsetdate.getText().toString();



        getManifestClick();


    }
}
