package com.example.mibitaferrynew;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.Adapters.ManifestListAdapter;
import com.example.mibitaferrynew.Model.Manifests;
import com.example.mibitaferrynew.TableModel.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nbbse.printapi.Printer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManifestActivity extends AppCompatActivity {

    //    String ref;
    Button btnlocal;
    TextView tetxadult, txtbiganimals, textbig_truck, txtchildren;
    TextView txtluggage, txtmotorcycle, txtother, txtsalooncar;
    TextView texttuktuk, txtstationwagon, txtsmalltruck, txtsmallanimals;
    LinearLayout linearLayout,online_layout;
    FloatingActionButton btnmanifestprint;
    int manifest_total_cost;
    int cost_1, biganimal_cost, biga_truck_cost, child_cost, luggae_cost, bike_cost, other_cost, saloon_car_cost,
            tuktuk_cost, station_wagon_cost, small_truck_cost, small_animal_cost;
    int count, big_count, small_truck_count, small_animal_count, child_count, tuktuk_count,
            big_truck_count, bikecount, luggage_count, saloon_car_count, station_wagon_count,other_count;
    private RadioGroup radioGroup;

    MyApplication app;

    ProgressDialog progressDialog;
    String dbDate, today;
    ArrayList<Manifests> mytripsDetails;
    ManifestListAdapter manifestListAdapter;



    ListView mytripslistView;



    int  items=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);


        app=(MyApplication) getApplication();

        btnlocal = findViewById(R.id.btnmanifestdate);

        tetxadult = findViewById(R.id.tetxadult);
        txtbiganimals = findViewById(R.id.txtbiganimals);
        textbig_truck = findViewById(R.id.textbig_truck);
        txtchildren = findViewById(R.id.txtchildren);
        txtmotorcycle = findViewById(R.id.txtmotorcycle);

        txtluggage = findViewById(R.id.txtluggage);
        txtsalooncar = findViewById(R.id.txtsalooncar);
        txtother = findViewById(R.id.txtother);


        btnmanifestprint = findViewById(R.id.btnmanifestprint);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        today = dateFormat.format(new Date());

        mytripslistView = (ListView) findViewById(R.id.manifest_list_items);


        texttuktuk = findViewById(R.id.texttuktuk);
        txtstationwagon = findViewById(R.id.txtstationwagon);
        txtsmalltruck = findViewById(R.id.txtsmalltruck);
        txtsmallanimals = findViewById(R.id.txtsmallanimals);


        linearLayout = findViewById(R.id.linearLayout_local);
        online_layout = findViewById(R.id.online_layout);



        linearLayout.setVisibility(View.GONE);
        online_layout.setVisibility(View.GONE);

        radioGroup = findViewById(R.id.radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.lacalRadio:
//                        showInventoryList();
                        loadLocalManifest();
                        linearLayout.setVisibility(View.VISIBLE);
                        online_layout.setVisibility(View.GONE);

                        break;
                    case R.id.online:
                        // do operations specific to this selection

                        online_Cloud();
                        online_layout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);

                        break;

                }
            }
        });


        btnmanifestprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print_manifest();
            }
        });


    }


    private List<Ticket> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(Ticket.class)
                .orderBy("Ticket_type ASC")
                .groupBy("reference")
                .execute();
    }


    public void loadLocalManifest() {


        From from_Adult = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Adult")
                .orderBy("Ticket_type ASC");

        count = from_Adult.count();
        cost_1 = count * 150;

        Log.e("Count", String.valueOf(count));
        Log.e("Cost", String.valueOf(cost_1));

        tetxadult.setText(String.format("Total Items %d\nCost %d", count, cost_1));


        From from_Big_animal = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Big Animal")
                .orderBy("Ticket_type ASC");

        big_count = from_Big_animal.count();
        biganimal_cost = big_count * 300;

        Log.e("Big Animals", String.valueOf(big_count));
        Log.e("Cost", String.valueOf(biganimal_cost));

        txtbiganimals.setText(String.format("Total Items %d\nCost %d", big_count, biganimal_cost));


        From from_Big_truck = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Big Truck")
                .orderBy("Ticket_type ASC");
        big_truck_count = from_Big_truck.count();
        biga_truck_cost = big_truck_count * 2320;

        Log.e("Big Truck", String.valueOf(big_truck_count));
        Log.e("Cost", String.valueOf(biga_truck_cost));

        textbig_truck.setText(String.format("Total Items %d\nCost %d", big_truck_count, biga_truck_cost));


        From from_child = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Child")
                .orderBy("Ticket_type ASC");

        child_count = from_child.count();
        child_cost = child_count * 50;

        Log.e("Child", String.valueOf(child_count));
        Log.e("Cost", String.valueOf(child_cost));

        txtchildren.setText(String.format("Total Items %d\nCost %d", child_count, child_cost));


        From from_luggage = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Luggage")
                .orderBy("Ticket_type ASC");

        luggage_count = from_luggage.count();
        luggae_cost = luggage_count * 60;

        Log.e("Luggage", String.valueOf(luggage_count));
        Log.e("Cost", String.valueOf(luggae_cost));

        txtluggage.setText(String.format("Total Items %d\nCost %d", luggage_count, luggae_cost));


        From from_motor_cycle = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Motor Cycle")
                .orderBy("Ticket_type ASC");

        bikecount = from_motor_cycle.count();
        bike_cost = bikecount * 250;

        Log.e("Motor Cycle", String.valueOf(bikecount));
        Log.e("Cost", String.valueOf(bike_cost));

        txtmotorcycle.setText(String.format("Total Items %d\nCost %d", bikecount, bike_cost));


        From from_other = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Other")
                .orderBy("Ticket_type ASC");

       other_count = from_other.count();
        other_cost = other_count * (Integer.valueOf(app.getOtherprice()));

        Log.e("Other", String.valueOf(other_cost));
        Log.e("Cost", String.valueOf(other_cost));

        txtother.setText(String.format("Total Items %d\nCost %d", other_count, other_cost));


        From from_saloon_car = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Saloon Car")
                .orderBy("Ticket_type ASC");

        saloon_car_count = from_saloon_car.count();
        saloon_car_cost = saloon_car_count * 930;

        Log.e("Saloon Car", String.valueOf(saloon_car_count));
        Log.e("Cost", String.valueOf(saloon_car_cost));

        txtsalooncar.setText(String.format("Total Items %d\nCost %d", saloon_car_count, saloon_car_cost));


        From from_Tuk_tuk = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Tuk Tuk")
                .orderBy("Ticket_type ASC");

        tuktuk_count = from_Tuk_tuk.count();
        tuktuk_cost = tuktuk_count * 500;

        texttuktuk.setText(String.format("Total Items %d\nCost %d", tuktuk_count, tuktuk_cost));


        From from_Station_wagon = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Station Wagon")
                .orderBy("Ticket_type ASC");

        station_wagon_count = from_Station_wagon.count();
        station_wagon_cost = station_wagon_count * 1160;


        txtstationwagon.setText(String.format("Total Items %d\nCost %d", station_wagon_count, station_wagon_cost));


        From from_Small_truck = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Small Truck")
                .orderBy("Ticket_type ASC");

        small_truck_count = from_Small_truck.count();
        small_truck_cost = small_truck_count * 1740;


        txtsmalltruck.setText(String.format("Total Items %d\nCost %d", small_truck_count, small_truck_cost));


        From from_Small_animal = new Select()
                .from(Ticket.class)
                .where(" Ticket_type= ?", "Small Animal")
                .orderBy("Ticket_type ASC");

        small_animal_count = from_Small_animal.count();
        small_animal_cost = small_animal_count * 200;
        txtsmallanimals.setText(String.format("Total Items %d\nCost %d", small_animal_count, small_animal_cost));


        manifest_total_cost = cost_1 + biganimal_cost + biga_truck_cost + child_cost + luggae_cost + bike_cost + other_cost + saloon_car_cost + tuktuk_cost +
                station_wagon_cost + small_truck_cost + small_animal_cost;


//        final List<Ticket> list = from.execute();
//        for(int i=0;i<list.size();i++) {
//            Log.e("List", String.valueOf(list.get(i).ref_no));
//        }


    }


    public void print_manifest() {

        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        if (Build.MODEL.equals("MobiPrint")) {
             items = cost_1
                    + big_count
                    + big_truck_count
                    + child_count
                    + luggage_count
                    + bikecount
                    + saloon_car_count

                    + small_animal_count

                    + small_truck_count

                    + station_wagon_count

                    + tuktuk_count;


            Toast.makeText(getApplicationContext(), "Manifest Printing", Toast.LENGTH_LONG).show();
            Printer print = Printer.getInstance();
            print.printFormattedText();
            print.printText("------Mbita Ferry Services-----");
            print.printText("..........Mbita,KENYA..........");
            print.printText("............Manifest........");
            print.printText("Total: " + manifest_total_cost + " ksh");

            print.printText("Item      Quantity    Cost\n");

            if (count > 0) {
                print.printText("Adults    " + count + "     " + cost_1 + "\n");
            }
            if (big_count > 0) {
                print.printText("Big Animals    " + big_count + "    " + biganimal_cost + "\n");
            }
            if (big_truck_count > 0) {
                print.printText("Big Trucks    " + big_truck_count + "  " + biga_truck_cost + "\n");
            }
            if (child_count > 0) {
                print.printText("Child(s)      " + child_count + "     " + child_cost + "\n");
            }
            if (luggage_count > 0) {
                print.printText("Luggage     " + luggage_count + "   " + luggae_cost + "\n");
            }

            if (bikecount > 0) {
                print.printText("Motor Cycles    " + bikecount + "   " + bike_cost + "\n");
            }

            if (saloon_car_count > 0) {
                print.printText("Saloon Cars   " + saloon_car_count + "   " + saloon_car_cost + "\n");
            }
            if (small_animal_count > 0) {
                print.printText("Small Animals    " + small_animal_count + "  " + small_animal_cost + "\n");
            }
            if (small_truck_count > 0) {
                print.printText("Small trucks    " + small_truck_count + "   " + small_truck_cost + "\n");
            }
            if (station_wagon_count > 0) {
                print.printText("Station Wagons    " + station_wagon_count + "  " + station_wagon_cost + "\n");
            }
            if (tuktuk_count > 0) {
                print.printText("Tuk Tuks    " + tuktuk_count + "       " + tuktuk_cost + "\n");
            }
            print.printText("Printed On :" + currentDateandTime);
            print.printBitmap(getResources().openRawResource(R.raw.payment_methods_old));
            print.printBitmap(getResources().openRawResource(R.raw.powered_by_mobiticket));
            print.printEndLine();


        }
    }



    public  void online_Cloud(){


        progressDialog = ProgressDialog.show(this, "Loading Manifest", "Please Wait...", true);


        RequestQueue batchreserve = Volley.newRequestQueue(ManifestActivity.this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", app.getUsername());
        params.put("api_key", app.getApi_key());
        params.put("action", "PassengerManifest");
        params.put("travel_date", today);
        params.put("route", "Ferry 1: Mbita - Mfangano");



                JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("response_code") == 0) {
                                JSONArray jsonArray = response.getJSONArray("bus");
                                Log.i("Response:", jsonArray.toString());
                                progressDialog.dismiss();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String car_name = jsonObject1.getString("route");
                                    String travel_from = jsonObject1.getString("total_seats");
                                    String travel_to = jsonObject1.getString("seats_available");


                                    mytripsDetails.add(new Manifests(car_name, travel_from, travel_to));

                                }


                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }

                            manifestListAdapter = new ManifestListAdapter(ManifestActivity.this, mytripsDetails);

                            mytripslistView.setAdapter(manifestListAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }


        };
        req.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        batchreserve.add(req);



    }



}
