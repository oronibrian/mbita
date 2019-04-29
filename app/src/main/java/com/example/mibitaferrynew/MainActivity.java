package com.example.mibitaferrynew;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.ModelPojo.Adult;
import com.example.mibitaferrynew.TableModel.RefferenceNumber;
import com.example.mibitaferrynew.TableModel.Ticket;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.nbbse.printapi.Printer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Number";

    Ticket ticket;
    Adult adult;
    String date = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date());

    ElegantNumberButton btnadult, btnBigAnumal, btnBigTruck, btnchild, btnLuggage, btnMotorCycle,
            btnOther, btnSaloonCar, btnSmallAnimal, btnSmallTruck, btnStationWagon, btnTuktuk;
    int number_of_seates;
    Button btnprocess;
    CheckBox chkAdult, chkBigAnumal, chkBigTruck, chkChild, chkLuggage, chkMotorCycle,
            chkOther, chkSaloonCar, chkSmallAnimal, chkSmallTruck, chkStationWagon, chkTuktuk;

    int adultnum, biganimalnum, bigtrucknum, childnum, luggagenum, motorCyclenum,
            othernum, saloonCarnum, smallAnimalnum, smallTrucknum, stationWagonnum, tuktuknum;

    TextView txtadlt, txtbiganiamal, txtbigtruck, txtchild, txtluggage, txtmotorcycle,
            txtother, txtsalooncar, txtsmallaminal, txtsmalltruck, txtstationwagon, txttuktuk;


    String ref;
    MyApplication app;
    String refno;


    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (MyApplication) getApplication();

        adult = new Adult("Adult", "", 0);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        showRefsList();


        txtadlt = findViewById(R.id.text_view);
        txtbiganiamal = findViewById(R.id.text_view_big_animal);
        txtbigtruck = findViewById(R.id.text_view_bg_truck);
        txtchild = findViewById(R.id.text_viewchild);
        txtluggage = findViewById(R.id.text_view_luggage);
        txtmotorcycle = findViewById(R.id.text_view_motor_cycle);
        txtother = findViewById(R.id.text_view_other);
        txtsalooncar = findViewById(R.id.text_view_btnSalooncar);
        txtsmallaminal = findViewById(R.id.txtsmallanimal);
        txtsmalltruck = findViewById(R.id.text_view_small_truck);
        txtstationwagon = findViewById(R.id.text_view_station_wagon);
        txttuktuk = findViewById(R.id.text_view_tuktuk);
        btnprocess = findViewById(R.id.btnprocess);


        List<RefferenceNumber> items = new Select().from(RefferenceNumber.class).orderBy("name ASC").limit(1).execute();
        for (int i = 0; i < items.size(); i++) {
            ref = items.get(i).name;
            Log.e("Ref Number", ref);
        }





        final List<Ticket> check = new Select().from(Ticket.class).orderBy("date ASC").execute();

        if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

            if(check.size()>0){
                batch_reserve();

            }else {

                Toast.makeText(getApplicationContext(), "No Tickets Locally", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Show disconnected screen
        }




        btnprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAndSave();
                print();

            }
        });


        //Adult ********************************************

        chkAdult = findViewById(R.id.chkAdult);
        chkAdult.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {

                int cost = 150;
                adult = new Adult("Adult", "", cost);


            } else {
            }
        });


        btnadult = findViewById(R.id.btnadult);
        btnadult.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String adultnum = btnadult.getNumber();
        });

        btnadult.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d("", String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            adultnum = newValue;


        });


        chkAdult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnadult.setVisibility(View.GONE);

                txtadlt.setText(String.format("%d Adult(s) selected", adultnum));
            } else {
                btnadult.setVisibility(View.VISIBLE);
                txtadlt.setText("Adult");

            }
        });


        //Big Animal ************************************************************************************************************

        btnBigAnumal = findViewById(R.id.btnBigAnumal);
        chkBigAnumal = findViewById(R.id.chkBigAnumal);


        btnBigAnumal.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnBigAnumal.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            biganimalnum = newValue;

        });

        chkBigAnumal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnBigAnumal.setVisibility(View.GONE);

                txtbiganiamal.setText(biganimalnum + "Animal(s) selected");
            } else {
                btnBigAnumal.setVisibility(View.VISIBLE);
                txtbiganiamal.setText("Big Animal");

            }
        });


        //End Big Animal *************************************************************************************************


        /// Big Truck------------------------------------------------------------

        chkBigTruck = findViewById(R.id.chkBigTruck);

        btnBigTruck=findViewById(R.id.btnBigTruck);
        btnBigTruck.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnBigTruck.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            bigtrucknum = newValue;

        });
        chkBigTruck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnBigTruck.setVisibility(View.GONE);

                txtbigtruck.setText(bigtrucknum + " Truck(s) selected");
            } else {
                btnBigTruck.setVisibility(View.VISIBLE);
                txtbigtruck.setText("Big Truck");

            }
        });

        /// End Big Truck------------------------------------------------------------

/// Child-----------------------------------------------------------


        chkChild = findViewById(R.id.chkChild);

        btnchild = findViewById(R.id.btnchild);
        btnchild.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnchild.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            childnum = newValue;

        });
        chkChild.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnchild.setVisibility(View.GONE);

                txtchild.setText(childnum + " Child(ren) selected");
            } else {
                btnchild.setVisibility(View.VISIBLE);
                txtchild.setText("Child");

            }
        });


        /// End Child------------------------------------------------------------


///Luggage ------------------------------------

        chkLuggage = findViewById(R.id.chkLuggage);
        btnLuggage = findViewById(R.id.btnluggage);
        btnLuggage.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnLuggage.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            luggagenum = newValue;

        });
        chkLuggage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnLuggage.setVisibility(View.GONE);

                txtluggage.setText(luggagenum + " Luggage selected");
            } else {
                btnLuggage.setVisibility(View.VISIBLE);
                txtluggage.setText("Luggage");

            }
        });
        ////End Luggage---------------------------------

        ////MotorCycle---------------------
        chkMotorCycle = findViewById(R.id.chkMotor_cycle);

        btnMotorCycle = findViewById(R.id.btnmotor_cycle);
        btnMotorCycle.setOnClickListener((ElegantNumberButton.OnClickListener) view ->

        {

        });

        btnMotorCycle.setOnValueChangeListener((view, oldValue, newValue) ->

        {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
            motorCyclenum = newValue;

        });
        chkMotorCycle.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnMotorCycle.setVisibility(View.GONE);

                txtmotorcycle.setText(motorCyclenum + " MotorCycle(s) selected");
            } else {
                btnMotorCycle.setVisibility(View.VISIBLE);
                txtmotorcycle.setText("Motor Cycle");

            }
        });
        ///End MotorCycle--------------------

        ////Others---------------------
        chkOther = findViewById(R.id.chkOther);

        btnOther = findViewById(R.id.btnOther);
        btnOther.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnOther.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                othernum = newValue;

            }
        });
        chkOther.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnOther.setVisibility(View.GONE);

                txtother.setText(othernum + " Other(s) selected");
            } else {
                btnOther.setVisibility(View.VISIBLE);
                txtother.setText("Other");

            }



            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.otherprompt, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editextotherprice);

            final EditText name = (EditText) promptsView
                    .findViewById(R.id.editextothername);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    app.setOtherprice(userInput.getText().toString());
                                    app.setOthername(name.getText().toString());

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        });
        ///End Other--------------------


        ////SaloonCar---------------------
        chkSaloonCar = findViewById(R.id.chkSalooncar);

        btnSaloonCar = findViewById(R.id.btnSalooncar);
        btnSaloonCar.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSaloonCar.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                saloonCarnum = newValue;

            }
        });
        chkSaloonCar.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSaloonCar.setVisibility(View.GONE);

                txtsalooncar.setText(saloonCarnum + " Saloon car(s) selected");
            } else {
                btnSaloonCar.setVisibility(View.VISIBLE);
                txtsalooncar.setText("Saloon car");

            }
        });
        ///End Saloon Car--------------------


        ////Small Animal---------------------

        chkSmallAnimal = findViewById(R.id.chkSmallAnimal);

        btnSmallAnimal = findViewById(R.id.btnSmallAnimal);
        btnSmallAnimal.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSmallAnimal.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                smallAnimalnum = newValue;

            }
        });
        chkSmallAnimal.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSmallAnimal.setVisibility(View.GONE);

                txtsmallaminal.setText(smallAnimalnum + "Animal(s) selected");
            } else {
                btnSmallAnimal.setVisibility(View.VISIBLE);
                txtsmallaminal.setText("Small Animal");

            }
        });
        ///End Small Animal--------------------

        ////Small Truck---------------------
        chkSmallTruck = findViewById(R.id.chksmalltruck);

        btnSmallTruck = findViewById(R.id.btnsmalltruck);
        btnSmallTruck.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSmallTruck.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                smallTrucknum = newValue;

            }
        });
        chkSmallTruck.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSmallTruck.setVisibility(View.GONE);

                txtsmalltruck.setText(smallTrucknum + " Truck(s) selected");
            } else {
                btnSmallTruck.setVisibility(View.VISIBLE);
                txtsmalltruck.setText("Small Truck");

            }
        });
        ///End Small Truck--------------------

        ////Station Wagon---------------------
        chkStationWagon = findViewById(R.id.chkstaionwagon);
        btnStationWagon = findViewById(R.id.btnstaionwagon);
        btnStationWagon.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnStationWagon.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                stationWagonnum = newValue;

            }
        });
        chkStationWagon.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnStationWagon.setVisibility(View.GONE);

                txtstationwagon.setText(stationWagonnum + "Station wagon(s) selected");
            } else {
                btnStationWagon.setVisibility(View.VISIBLE);
                txtstationwagon.setText("Station Wagon");

            }
        });

        ///End Station Wagon--------------------
        ////TukTuk---------------------
        chkTuktuk =

                findViewById(R.id.chktuktuk);
        chkTuktuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
//
//                    tukTuk = new TukTuk("Tuk Tuk", tuktuknum, (tuktuknum * 500));
//                    TukTukTicket = new Ticket("Tuk Tuk", tuktuknum, 500, date, ref_no);


                } else {
//                    tukTuk.setPrice(0);
                }
            }
        });
        btnTuktuk = findViewById(R.id.btntuktuk);
        btnTuktuk.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnTuktuk.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
                tuktuknum = newValue;

            }
        });
        chkTuktuk.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnTuktuk.setVisibility(View.GONE);

                txttuktuk.setText(tuktuknum + " Tuktuk(s) selected");
            } else {
                btnTuktuk.setVisibility(View.VISIBLE);
                txttuktuk.setText("Tuk Tuk");

            }
        });
        ///End Tuk Tuk--------------------


    }




    private void processAndSave() {

        total = (adultnum * 150) + (biganimalnum * 300)+(bigtrucknum*2320)+(childnum*50)
        +(luggagenum*60)+(motorCyclenum*250)+(saloonCarnum*930)+(smallAnimalnum*200)+(smallTrucknum*1740)
        +(stationWagonnum*1160)+(tuktuknum*500)+(othernum*Integer.valueOf(app.getOtherprice()));



        ActiveAndroid.beginTransaction();
        try {

            if (chkAdult.isChecked()) {

                for (int x = 0; x < adultnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Adult";
                    ticket.cost = 150;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();

                    //Displaying a toast message for successfull insertion
                    Log.d("Saved", String.valueOf(x));

                }
            }

            if (chkBigAnumal.isChecked()) {


                for (int x = 0; x < biganimalnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Big Animal";
                    ticket.cost = 300;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }

            if (chkBigTruck.isChecked()) {


                for (int x = 0; x < bigtrucknum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Big Truck";
                    ticket.cost = 2320;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            if (chkChild.isChecked()) {


                for (int x = 0; x < childnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Child";
                    ticket.cost = 50;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            if (chkLuggage.isChecked()) {


                for (int x = 0; x < luggagenum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Luggage";
                    ticket.cost = 60;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            if (chkMotorCycle.isChecked()) {


                for (int x = 0; x < motorCyclenum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Motor Cycle";
                    ticket.cost = 250;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }

            if (chkOther.isChecked()) {


                for (int x = 0; x < othernum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Other";
                    ticket.cost = Integer.valueOf(app.getOtherprice());
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }

            if (chkSaloonCar.isChecked()) {


                for (int x = 0; x < saloonCarnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Saloon Car";
                    ticket.cost = 930;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }

            if (chkSmallAnimal.isChecked()) {


                for (int x = 0; x < smallAnimalnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Small Animal";
                    ticket.cost = 200;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            if (chkSmallTruck.isChecked()) {


                for (int x = 0; x < smallTrucknum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Small Truck";
                    ticket.cost = 1740;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }

            if (chkStationWagon.isChecked()) {


                for (int x = 0; x < stationWagonnum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Station Wagon";
                    ticket.cost = 1160;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            if (chkTuktuk.isChecked()) {


                for (int x = 0; x < tuktuknum; x++) {

                    ticket = new Ticket();
                    ticket.ticket_type = "Tuk Tuk";
                    ticket.cost = 500;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();
                }
            }


            ActiveAndroid.setTransactionSuccessful();
            Toast.makeText(getApplicationContext(), "Tickets saved successfully", Toast.LENGTH_SHORT).show();


        } finally {
            ActiveAndroid.endTransaction();

            new Delete().from(RefferenceNumber.class).where("name = ?", ref).execute();


        }

    }

    private void print() {
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (Build.MODEL.equals("MobiPrint")) {


            Toast.makeText(getApplicationContext(), "Mobiwire Printing Ticket", Toast.LENGTH_LONG).show();
            Printer print = Printer.getInstance();
            print.printFormattedText();
            print.printText("------Mbita Ferry Services-----");
            print.printText("..........Mbita,KENYA..........");
            print.printText(".......Passenger Details........");

            print.printText("Total: " + total + " ksh");

            print.printText("Ticket Ref: " + ref);

            print.printText("Item      Quantity    Cost\n");


            if (chkAdult.isChecked()) {
                print.printText("Adult" + "           " + adultnum + "    " + adultnum * 150);

            }


            if (chkBigAnumal.isChecked()) {
                print.printText("Big Animal" + "      " + biganimalnum + "    " + biganimalnum * 300);

            }

            if (chkBigTruck.isChecked()) {
                print.printText("Big Truck" + "       " + bigtrucknum + "    " + bigtrucknum * 2320);

            }


            if (chkChild.isChecked()) {
                print.printText("Child " + "          " + childnum + "    " + childnum * 50);

            }


            if (chkLuggage.isChecked()) {
                print.printText("Luggage " + "        " + luggagenum + "    " + luggagenum * 60);

            }


            if (chkMotorCycle.isChecked()) {
                print.printText("Motor Cycle " + "    " + motorCyclenum + "    " + motorCyclenum * 250);

            }


            if (chkOther.isChecked()) {
                print.printText(app.getOthername()+ "     " + othernum + "     " + othernum *  (Integer.valueOf(app.getOtherprice())));

            }


            if (chkSaloonCar.isChecked()) {
                print.printText("Salon Car " + "      " + saloonCarnum + "    " + saloonCarnum * 930);

            }


            if (chkSmallAnimal.isChecked()) {
                print.printText("Small Animal " + "   " + smallAnimalnum + "    " + smallAnimalnum * 200);

            }


            if (chkSmallTruck.isChecked()) {
                print.printText("Small Truck " + "    " + smallTrucknum + "    " + smallTrucknum * 1740);

            }


            if (chkStationWagon.isChecked()) {
                print.printText("Station Wagon " + "  " + stationWagonnum + "   " + stationWagonnum * 1160);

            }


            if (chkTuktuk.isChecked()) {
                print.printText("Tuk Tuk " + "        " + tuktuknum + "    " + tuktuknum * 500);

            }
            print.printText("Issued On :" + currentDateandTime);
            print.printBitmap(getResources().openRawResource(R.raw.payment_methods_old));
            print.printBitmap(getResources().openRawResource(R.raw.powered_by_mobiticket));
            print.printEndLine();
        } else {


        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.search_tickets_id) {
            startActivity(new Intent(getApplicationContext(), SearchTicketActivity.class));
        } else if (id == R.id.manifest_id) {
            startActivity(new Intent(getApplicationContext(), ManifestActivity.class));
        } else if (id == R.id.payments_id) {
//            startActivity(new Intent(getApplicationContext(), PaymentsActivity.class));
        } else if (id == R.id.search_payments_id) {
//            startActivity(new Intent(getApplicationContext(), SearchPaymentsActivity.class));
        } else if (id == R.id.viewlocaldata) {
//            startActivity(new Intent(getApplicationContext(), LocalDataActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public static class ConnectivityHelper {
        public  static boolean isConnectedToNetwork(Context context) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean isConnected = false;
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
            }

            return isConnected;
        }
    }

    private void showRefsList() {

        List<RefferenceNumber> items = new Select().from(RefferenceNumber.class).orderBy("name ASC").limit(100).execute();
        for (int i = 0; i < items.size(); i++) {
            String refs = items.get(i).name;
            Log.e("Refs", refs);
        }

    }





    private void batch_reserve() {




        final List<Ticket> list = new Select().from(Ticket.class).orderBy("date ASC").execute();

        for (int i=0; i < list.size(); i++) {

            refno=(String.valueOf(list.get(i).ref_no));

            Log.e("Type", String.valueOf(list.get(i).ticket_type));

            }



        final List<Ticket> list2 = new Select().from(Ticket.class).where("reference= ?" ,refno).orderBy("Ticket_type ASC").execute();

        Log.e("List",list2.toString());

        JSONObject obj;
        JSONArray ticket_items = new JSONArray();

        for (int i=0; i < list2.size(); i++) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(list2.get(i).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", "31947982");
                obj.put("from_city", app.getFrom());
                obj.put("to_city", app.getTo());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", "3");
                obj.put("seater", "491");
                obj.put("selected_seat", String.valueOf(i));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", "1");
                obj.put("email_address", "brianoroni6@gmail.com");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(list2.get(i).cost));
                obj.put("reference_number", refno);
                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }


        RequestQueue batchreserve = Volley.newRequestQueue(MainActivity.this);


        JSONObject postparams = new JSONObject();

        try {
            postparams.put("username", app.getUsername());
            postparams.put("api_key",  app.getApi_key());
            postparams.put("action", "BatchReserveSeats");
            postparams.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
            postparams.put("ticket_items", ticket_items);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,urls.apiUrl, postparams,
                response -> {
                    try {

                        Log.d("Response: ", response.toString(4));


                        if (response.getInt("response_code") == 0) {
                            JSONArray message = response.getJSONArray("ticket_message");

                            new Delete().from(Ticket.class).where("ref_no = ?", refno).execute();



                            for (int i = 0; i < message.length(); i++) {
                                JSONObject jsonObject1 = message.getJSONObject(i);


                            }

                            JSONArray jsonArray = response.getJSONArray("ticket");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            }

                        } else {
                            Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Exception", e.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Volley Error:", error.toString());


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
                return "application/json; charset=utf-8";
            }

        };

        try {
            Log.e("Request Body", postparams.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        batchreserve.add(req);




    }


}
