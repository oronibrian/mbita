package com.example.mibitaferrynew;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
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
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.Model.Adult;
import com.example.mibitaferrynew.TableModel.Price;
import com.example.mibitaferrynew.TableModel.RefferenceNumber;
import com.example.mibitaferrynew.TableModel.Seat;
import com.example.mibitaferrynew.TableModel.Ticket;
import com.example.mibitaferrynew.service.AlarmReceiver;
import com.google.android.material.navigation.NavigationView;
import com.mobiwire.CSAndroidGoLib.AndroidGoCSApi;
import com.mobiwire.CSAndroidGoLib.CsPrinter;
import com.nbbse.printapi.Printer;
import com.sagereal.printer.PrinterInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SNIMatcher;

import static com.activeandroid.Cache.getContext;
import static com.mobiwire.CSAndroidGoLib.CsPrinter.printEndLine;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Number";
    public static PrinterInterface printInterfaceService;
    String[] paymentmethods = {"Cash", "MPESA"};
    String[] PaymentNames = {"Cash", "mpesa", "wallet"};
    Handler handler = new Handler();
    String price;
    int payment_id;
    Spinner paymentmethod;
    CardView paymentcard;
    Runnable runnable;
    EditText walletpassword, wallet_username, mpesanumber;
    String source_name, source_account_number, trasaction_id, amount;
    Ticket ticket;
    Adult adult;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
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
    TextView headertext;
    String ref;
    MyApplication app;
    Printer print;
    CsPrinter mp4Printer;
    String refno;
    int total, all_tickets;
    Price Adult_item, Big_item, Station_item, Tuk_item,
            Big_Truck_item, Child_item, Luggage_item, Motor_item, Other_item, Saloon_item, Small_item, Small_Truck_item;

    TextView txtseats;
    ConnectivityChangeReceiver broadcastObserver = new ConnectivityChangeReceiver();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            android.util.Log.d("TestApp ", "aidl connect fail");
            printInterfaceService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            android.util.Log.d("TestApp", "aidl connect success");
            printInterfaceService = PrinterInterface.Stub.asInterface(service);
        }
    };
    private PendingIntent pendingIntent;
    private ProgressDialog confirmtransProgress, mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.MODEL.equals("MP4") || Build.MODEL.equals("MP3_Plus")) {
            new AndroidGoCSApi(getApplicationContext());
            mp4Printer = new CsPrinter();

        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        app = (MyApplication) getApplication();

        adult = new Adult("Adult", "", 0);

        app.setOtherprice("0");
        paymentmethod = findViewById(R.id.paymentmethod);
        paymentcard = findViewById(R.id.paymentcard);
        print = Printer.getInstance();


        confirmtransProgress = new ProgressDialog(this);
        confirmtransProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        confirmtransProgress.setTitle("Payment processing");
        confirmtransProgress.setMessage("processing...");
        confirmtransProgress.setCancelable(false);
        confirmtransProgress.setIndeterminate(true);


        mProgress = new ProgressDialog(this);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setTitle("processing");
        mProgress.setMessage("wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        ArrayAdapter ab = new ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentmethods);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentmethod.setAdapter(ab);
        paymentmethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (paymentmethod.getSelectedItem().toString().equalsIgnoreCase("Cash")) {

                    Toast.makeText(getApplicationContext(), PaymentNames[0], Toast.LENGTH_SHORT).show();
                    payment_id = 6;

                } else {

                    Toast.makeText(getApplicationContext(), PaymentNames[1], Toast.LENGTH_SHORT).show();
                    payment_id = 3;


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {

            @Override
            public void run() {

                From available_tickets = new Select()
                        .from(Seat.class)
                        .orderBy("name ASC");

                all_tickets = available_tickets.count();

                txtseats.setText(String.format("%s Tickets Remaining", String.valueOf(all_tickets)));


            }
        }, 1000); //1 seconds


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);

        headertext = header.findViewById(R.id.menutxt);
        headertext.setText(String.format("%s\n%s\n%s", app.getLogged_user(), app.getPhone_num(), app.getRoute()));

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
        txtseats = findViewById(R.id.txtseats);

        chkTuktuk = findViewById(R.id.chktuktuk);
        chkStationWagon = findViewById(R.id.chkstaionwagon);
        chkSmallTruck = findViewById(R.id.chksmalltruck);

        chkSmallAnimal = findViewById(R.id.chkSmallAnimal);
        chkSaloonCar = findViewById(R.id.chkSalooncar);

        chkOther = findViewById(R.id.chkOther);
        chkMotorCycle = findViewById(R.id.chkMotor_cycle);

        chkLuggage = findViewById(R.id.chkLuggage);
        chkChild = findViewById(R.id.chkChild);
        chkBigTruck = findViewById(R.id.chkBigTruck);
        chkAdult = findViewById(R.id.chkAdult);

        btnBigAnumal = findViewById(R.id.btnBigAnumal);
        chkBigAnumal = findViewById(R.id.chkBigAnumal);


        Log.e("phone", app.getPhone_num());
        Log.e("To city", app.getTo_id());
        Log.e("From City", app.getFrom_id());


        List<RefferenceNumber> items = new Select().from(RefferenceNumber.class).orderBy("name ASC").limit(1).execute();
        for (int i = 0; i < items.size(); i++) {
            ref = items.get(i).name;
            Log.e("Ref Number", ref);
        }


        new Delete().from(Price.class).where("name = ?", "Tuk Tuk - Standard - 250.00").execute();
        List<Price> price_item = new Select().from(Price.class).execute();

        Adult_item = new Select().from(Price.class).where("name = ?", "Adult - Standard - 150.00").executeSingle();
        Big_item = new Select().from(Price.class).where("name = ?", "Big Animal - Standard - 300.00").executeSingle();

        Station_item = new Select().from(Price.class).where("name = ?", "Station Wagon - Standard - 1160.00").executeSingle();
        Tuk_item = new Select().from(Price.class).where("name = ?", "Tuk Tuk - Standard - 500.00").executeSingle();

        Big_Truck_item = new Select().from(Price.class).where("name = ?", "Big Truck - Standard - 2320.00").executeSingle();

        Child_item = new Select().from(Price.class).where("name = ?", "Child - Standard - 50.00").executeSingle();
        Luggage_item = new Select().from(Price.class).where("name = ?", "Luggage - Standard - 60.00").executeSingle();
        Motor_item = new Select().from(Price.class).where("name = ?", "Motor Cycle - Standard - 250.00").executeSingle();
        Other_item = new Select().from(Price.class).where("name = ?", "Other - Standard - 0.00").executeSingle();
        Saloon_item = new Select().from(Price.class).where("name = ?", "Saloon Car - Standard - 930.00").executeSingle();
        Small_item = new Select().from(Price.class).where("name = ?", "Small Animal - Standard - 200.00").executeSingle();
        Small_Truck_item = new Select().from(Price.class).where("name = ?", "Small Truck - Standard - 1740.00").executeSingle();

        Log.e("Name", Adult_item.name);
        Log.e("price", String.valueOf(Adult_item.cost));


        final List<Ticket> check = new Select().from(Ticket.class).orderBy("date ASC").execute();

//        startResearvationService();

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                //call the method which is schedule to call after 20 sec

                if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

                    if (check.size() > 0) {


                        addNotification();
                        batch_reserve();

                    } else {

                        Toast.makeText(getApplicationContext(), "No Tickets Locally", Toast.LENGTH_SHORT).show();

                        NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notifManager.cancelAll();
                    }
                } else {
                    //Show disconnected screen
                }
//                batch_reserve();

            }
        }, 200000);  //the time is in miliseconds
//
        if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

            if (check.size() > 0) {


                addNotification();
                batch_reserve();

            } else {

                Toast.makeText(getApplicationContext(), "No Tickets Locally", Toast.LENGTH_SHORT).show();

                NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
            }
        } else {
            //Show disconnected screen
        }


        btnprocess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (chkAdult.isChecked() ||
                        chkBigAnumal.isChecked()
                        || chkBigTruck.isChecked() ||
                        chkLuggage.isChecked() ||
                        chkMotorCycle.isChecked()
                        || chkChild.isChecked() ||
                        chkOther.isChecked() ||
                        chkSaloonCar.isChecked()
                        || chkSmallAnimal.isChecked() ||
                        chkStationWagon.isChecked() || chkSmallTruck.isChecked() || chkTuktuk.isChecked()) {
                    processAndSave();


                    if (payment_id == 6) {
                        Toast.makeText(getApplicationContext(), "Cash", Toast.LENGTH_SHORT).show();
                        app.setPayment_method("6");
                        //print();

                    } else {
                        Toast.makeText(getApplicationContext(), "Mpesa", Toast.LENGTH_SHORT).show();
                        app.setPayment_method("3");
//                        MpesaDialog(ref);

                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Can't process an empty ticket", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //Adult ********************************************


        if (adultnum <= 0) {
            chkAdult.setEnabled(false);

        }


        btnadult = findViewById(R.id.btnadult);
        btnadult.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            String adultnum = btnadult.getNumber();
        });

        btnadult.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d("", String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            adultnum = newValue;
            chkAdult.setEnabled(true);

            if (newValue <= 0) {
                chkAdult.setEnabled(false);

            }


        });


        chkAdult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnadult.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtadlt.setText(String.format("%d Adult(s) selected", adultnum));
            } else {
                btnadult.setVisibility(View.VISIBLE);
                txtadlt.setText("Adult");

            }
        });


        //Big Animal ************************************************************************************************************


        if (biganimalnum <= 0) {
            chkBigAnumal.setEnabled(false);

        }


        btnBigAnumal.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnBigAnumal.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            biganimalnum = newValue;
            chkBigAnumal.setEnabled(true);
            if (newValue <= 0) {
                chkBigAnumal.setEnabled(false);

            }

        });

        chkBigAnumal.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnBigAnumal.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtbiganiamal.setText(biganimalnum + "Animal(s) selected");
            } else {
                btnBigAnumal.setVisibility(View.VISIBLE);
                txtbiganiamal.setText("Big Animal");

            }
        });


        //End Big Animal *************************************************************************************************


        /// Big Truck------------------------------------------------------------

        if (bigtrucknum <= 0) {
            chkBigTruck.setEnabled(false);

        }

        btnBigTruck = findViewById(R.id.btnBigTruck);
        btnBigTruck.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnBigTruck.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            bigtrucknum = newValue;
            chkBigTruck.setEnabled(true);
            if (newValue <= 0) {
                chkBigTruck.setEnabled(false);

            }

        });
        chkBigTruck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnBigTruck.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtbigtruck.setText(bigtrucknum + " Truck(s) selected");
            } else {
                btnBigTruck.setVisibility(View.VISIBLE);
                txtbigtruck.setText("Big Truck");

            }
        });

        /// End Big Truck------------------------------------------------------------

/// Child-----------------------------------------------------------


        if (childnum <= 0) {
            chkChild.setEnabled(false);

        }

        btnchild = findViewById(R.id.btnchild);
        btnchild.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnchild.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            childnum = newValue;
            chkChild.setEnabled(true);
            if (newValue <= 0) {
                chkChild.setEnabled(false);

            }

        });
        chkChild.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnchild.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtchild.setText(childnum + " Child(ren) selected");
            } else {
                btnchild.setVisibility(View.VISIBLE);
                txtchild.setText("Child");

            }
        });


        /// End Child------------------------------------------------------------


///Luggage ------------------------------------


        if (luggagenum <= 0) {
            chkLuggage.setEnabled(false);

        }
        btnLuggage = findViewById(R.id.btnluggage);
        btnLuggage.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {

        });

        btnLuggage.setOnValueChangeListener((view, oldValue, newValue) -> {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));

            luggagenum = newValue;
            chkLuggage.setEnabled(true);
            if (newValue <= 0) {
                chkLuggage.setEnabled(false);

            }

        });
        chkLuggage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                btnLuggage.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtluggage.setText(luggagenum + " Luggage selected");
            } else {
                btnLuggage.setVisibility(View.VISIBLE);
                txtluggage.setText("Luggage");

            }
        });
        ////End Luggage---------------------------------

        ////MotorCycle---------------------


        if (motorCyclenum <= 0) {
            chkMotorCycle.setEnabled(false);

        }

        btnMotorCycle = findViewById(R.id.btnmotor_cycle);
        btnMotorCycle.setOnClickListener((ElegantNumberButton.OnClickListener) view ->

        {

        });

        btnMotorCycle.setOnValueChangeListener((view, oldValue, newValue) ->

        {
            Log.d(TAG, String.format("oldValue: %d   newValue: %d", oldValue, newValue));
            motorCyclenum = newValue;
            chkMotorCycle.setEnabled(true);

            if (newValue <= 0) {
                chkMotorCycle.setEnabled(false);

            }

        });
        chkMotorCycle.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnMotorCycle.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtmotorcycle.setText(motorCyclenum + " MotorCycle(s) selected");
            } else {
                btnMotorCycle.setVisibility(View.VISIBLE);
                txtmotorcycle.setText("Motor Cycle");

            }
        });
        ///End MotorCycle--------------------

        ////Others---------------------


        if (othernum <= 0) {
            chkOther.setEnabled(false);

        }

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
                chkOther.setEnabled(true);
                if (newValue <= 0) {
                    chkOther.setEnabled(false);

                }

            }
        });
        chkOther.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnOther.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

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

            final EditText userInput = promptsView
                    .findViewById(R.id.editextotherprice);

            final EditText name = promptsView
                    .findViewById(R.id.editextothername);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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


        if (saloonCarnum <= 0) {
            chkSaloonCar.setEnabled(false);

        }

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
                chkSaloonCar.setEnabled(true);
                if (newValue <= 0) {
                    chkSaloonCar.setEnabled(false);

                }

            }
        });
        chkSaloonCar.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSaloonCar.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtsalooncar.setText(saloonCarnum + " Saloon car(s) selected");
            } else {
                btnSaloonCar.setVisibility(View.VISIBLE);
                txtsalooncar.setText("Saloon car");

            }
        });
        ///End Saloon Car--------------------


        ////Small Animal---------------------


        if (smallAnimalnum <= 0) {
            chkSmallAnimal.setEnabled(false);

        }

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
                chkSmallAnimal.setEnabled(true);
                if (newValue <= 0) {
                    chkSmallAnimal.setEnabled(false);

                }

            }
        });
        chkSmallAnimal.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSmallAnimal.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtsmallaminal.setText(smallAnimalnum + "Animal(s) selected");
            } else {
                btnSmallAnimal.setVisibility(View.VISIBLE);
                txtsmallaminal.setText("Small Animal");

            }
        });
        ///End Small Animal--------------------

        ////Small Truck---------------------


        if (smallTrucknum <= 0) {
            chkSmallTruck.setEnabled(false);

        }

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
                chkSmallTruck.setEnabled(true);
                if (newValue <= 0) {
                    chkSmallTruck.setEnabled(false);

                }

            }
        });
        chkSmallTruck.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnSmallTruck.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtsmalltruck.setText(smallTrucknum + " Truck(s) selected");
            } else {
                btnSmallTruck.setVisibility(View.VISIBLE);
                txtsmalltruck.setText("Small Truck");

            }
        });
        ///End Small Truck--------------------

        ////Station Wagon---------------------


        if (stationWagonnum <= 0) {
            chkStationWagon.setEnabled(false);

        }
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
                chkStationWagon.setEnabled(true);
                if (newValue <= 0) {
                    chkStationWagon.setEnabled(false);

                }

            }
        });
        chkStationWagon.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnStationWagon.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txtstationwagon.setText(stationWagonnum + "Station wagon(s) selected");
            } else {
                btnStationWagon.setVisibility(View.VISIBLE);
                txtstationwagon.setText("Station Wagon");

            }
        });

        ///End Station Wagon--------------------
        ////TukTuk---------------------


        if (tuktuknum <= 0) {
            chkTuktuk.setEnabled(false);

        }
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
                chkTuktuk.setEnabled(true);
                if (newValue <= 0) {
                    chkTuktuk.setEnabled(false);

                }

            }
        });
        chkTuktuk.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                btnTuktuk.setVisibility(View.GONE);
                paymentcard.setVisibility(View.VISIBLE);

                txttuktuk.setText(tuktuknum + " Tuktuk(s) selected");
            } else {
                btnTuktuk.setVisibility(View.VISIBLE);
                txttuktuk.setText("Tuk Tuk");

            }
        });
        ///End Tuk Tuk--------------------


    }

    public void MpesaDialog(String refnumber) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mpesa_layout, null);
        dialogBuilder.setView(dialogView);

        mpesanumber = dialogView.findViewById(R.id.mpesanumber);
        EditText localfare = dialogView.findViewById(R.id.editextprice);

        Button btncomplete = dialogView.findViewById(R.id.btncon);


        final android.app.AlertDialog dialog = dialogBuilder.create();
        localfare.setText(price);

        dialog.setCanceledOnTouchOutside(false);

        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mpesanumber.getText().toString().isEmpty()) {
                    mpesanumber.setError("Number is required");
                } else {

                    app.setYournamberyournamber(mpesanumber.getText().toString());

                    mpesapay_reserve(mpesanumber.getText().toString());
                    dialog.cancel();
                }


            }
        });

        dialog.show();

    }


    public void startResearvationService() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;
        final List<Ticket> check = new Select().from(Ticket.class).orderBy("date ASC").execute();

        if (ConnectivityHelper.isConnectedToNetwork(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

            if (check.size() > 0) {

                addNotification();
                batch_reserve();

            } else {

                Toast.makeText(getApplicationContext(), "No Tickets Locally", Toast.LENGTH_SHORT).show();

                NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.cancelAll();
            }
        } else {
            //Show disconnected screen
        }
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Sync service started", Toast.LENGTH_SHORT).show();
    }


    private void mpesapay_reserve(String mpesanumber) {


        final List<Ticket> list = new Select().from(Ticket.class).orderBy("date ASC").execute();

        for (int i = 0; i < list.size(); i++) {

            refno = (String.valueOf(list.get(i).ref_no));

            Log.e("Type", String.valueOf(list.get(i).ticket_type));

        }


        final List<Ticket> list2 = new Select().from(Ticket.class).where("reference= ?", refno).orderBy("Ticket_type ASC").execute();


        Log.e("List", list2.toString());

        JSONObject obj;
        JSONArray ticket_items = new JSONArray();


        // Querying Items from db and create object;


        //----------------------------------------Adult--------------------------
        String adult_keyword = "Adult";

        List<Ticket> adults = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, adult_keyword).orderBy("date ASC").execute();

        int count = adults.size();
        int adult_cost = (int) (count * (Adult_item.cost));

//        Log.e("Name",String.valueOf(adults.get(0).ticket_type));


        //----------------------------------------Big Animal--------------------------

        String big_animal_keyword = "Big Animal";

        List<Ticket> big_animal = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, big_animal_keyword).orderBy("date ASC").execute();

        int big_animal_count = big_animal.size();
        int big_animal_cost = (int) (big_animal_count * Big_item.cost);

        //----------------------------------------Station Wagon--------------------------

        String station_wagon_keyword = "Station Wagon";

        List<Ticket> station_wagon = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, station_wagon_keyword).orderBy("date ASC").execute();

        int station_wagon_count = station_wagon.size();
        int station_wagon_cost = (int) (station_wagon_count * Station_item.cost);

        //----------------------------------------Tuk Tuk--------------------------

        String tuk_tuk_keyword = "Tuk Tuk";

        List<Ticket> tuk_tuk = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, tuk_tuk_keyword).orderBy("date ASC").execute();

        int tuk_tuk_count = tuk_tuk.size();
        int tuk_tuk_cost = (int) (tuk_tuk_count * Tuk_item.cost);

        //----------------------------------------Big Truck--------------------------

        String big_truck_keyword = "Big Truck";

        List<Ticket> big_truck = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, big_truck_keyword).orderBy("date ASC").execute();

        int big_truck_count = big_truck.size();
        int big_truck_cost = (int) (big_truck_count * Big_Truck_item.cost);


        //----------------------------------------Child --------------------------

        String child_keyword = "Child";


        List<Ticket> child = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, child_keyword).orderBy("date ASC").execute();

        int child_count = child.size();
        int child_count_cost = (int) (child_count * Child_item.cost);


        //----------------------------------------Luggage --------------------------

        String luggage_keyword = "Luggage";

        List<Ticket> luggage = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, luggage_keyword).orderBy("date ASC").execute();

        int luggage_count = luggage.size();
        int luggage_count_cost = (int) (luggage_count * Luggage_item.cost);


        //----------------------------------------Motor Cycle --------------------------

        String motor_keyword = "Motor Cycle";

        List<Ticket> motor = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, motor_keyword).orderBy("date ASC").execute();

        int motor_count = motor.size();
        int motor_count_cost = (int) (motor_count * Motor_item.cost);


        //----------------------------------------Other --------------------------

        String other_keyword = "Other";

        List<Ticket> other = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, other_keyword).orderBy("date ASC").execute();

        int other_count = other.size();
        int other_count_cost = (int) (motor_count * Other_item.cost);


        //----------------------------------------Saloon car --------------------------

        String Saloon_keyword = "Saloon Car";

        List<Ticket> saloon_car = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Saloon_keyword).orderBy("date ASC").execute();

        int saloon_count = saloon_car.size();
        int saloon_count_count_cost = (int) (saloon_count * Saloon_item.cost);


        //----------------------------------------Small Animal --------------------------

        String Small_animal_keyword = "Small Animal";

        List<Ticket> small_animal = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Small_animal_keyword).orderBy("date ASC").execute();

        int small_anima_count = small_animal.size();
        int small_animal_count_cost = (int) (small_anima_count * Small_item.cost);


        //----------------------------------------Small Truck --------------------------

        String Small_truck_keyword = "Small Truck";

        List<Ticket> small_truck = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Small_truck_keyword).orderBy("date ASC").execute();

        int small_truck_count = small_truck.size();
        int small_truck_count_cost = (int) (small_truck_count * Small_Truck_item.cost);


        Log.e("Adults", String.valueOf(count));
        Log.e("adult_cost", String.valueOf(adult_cost));


        Log.e("big_animal_count", String.valueOf(big_animal_count));
        Log.e("big_animal_adult_cost", String.valueOf(big_animal_cost));

        if (adults.size() > 0) {

            for (int i = 0; i < 1; i++) {
                obj = new JSONObject();


                try {

                    obj.put("passenger_name", String.valueOf(list2.get(i).ticket_type));
                    obj.put("phone_number", mpesanumber);
                    obj.put("id_number", app.getId_number());
                    obj.put("from_city", app.getFrom_id());
                    obj.put("to_city", app.getTo_id());
                    obj.put("travel_date", app.getTravel_date());
                    obj.put("selected_vehicle", app.getFerry_id());
                    obj.put("seater", "3500");
                    obj.put("selected_seat", String.valueOf(list2.get(i).seat_no));
                    obj.put("selected_ticket_type", "8");
                    obj.put("payment_method", app.getPayment_method());
                    obj.put("email_address", "boroni@mobiticket.co.ke");
                    obj.put("insurance_charge", "");
                    obj.put("served_by", app.getLogged_user());
                    obj.put("amount_charged", String.valueOf(adult_cost));
                    obj.put("reference_number", refno);
                    obj.put("quantity", count);
                    obj.put("item_name", "Adult - Standard - 150.00");

                    ticket_items.put(obj);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                Log.e("Passengername", String.valueOf(adults.get(i).ticket_type));
                Log.e("Seat", String.valueOf(adults.get(i).seat_no));
            }


        }

        if (big_animal.size() > 0) {
            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(big_animal.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(big_animal.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(big_animal_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(big_animal_count));
                obj.put("item_name", "Big Animal - Standard - 300.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (big_truck.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(big_truck.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(big_truck.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(big_truck_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(big_truck_count));
                obj.put("item_name", "Big Truck - Standard - 2320.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (luggage.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(luggage.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(luggage.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(luggage_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(luggage_count));
                obj.put("item_name", "Luggage - Standard - 60.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (saloon_car.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(saloon_car.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(saloon_car.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(saloon_count_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(saloon_count));
                obj.put("item_name", "Saloon Car - Standard - 930.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (small_truck.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(small_truck.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(small_truck.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(small_truck_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(small_truck_count));
                obj.put("item_name", "Small Truck - Standard - 1740.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);


            }

        }
        if (tuk_tuk.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(tuk_tuk.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(tuk_tuk.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(tuk_tuk_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(tuk_tuk_count));
                obj.put("item_name", "Tuk Tuk - Standard - 500.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (child.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(child.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(child.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(child_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(child_count));
                obj.put("item_name", "Child - Standard - 50.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (motor.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(motor.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(motor.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(motor_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(motor_count));
                obj.put("item_name", "Motor Cycle - Standard - 250.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


        if (other.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(other.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(other.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(other_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(other_count));
                obj.put("item_name", "Other - Standard - 0.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (small_animal.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(small_animal.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(small_animal.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(small_animal_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(small_anima_count));
                obj.put("item_name", "Small Animal - Standard - 200.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (station_wagon.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(station_wagon.get(0).ticket_type));
                obj.put("phone_number", mpesanumber);
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(station_wagon.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(station_wagon_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(station_wagon_count));
                obj.put("item_name", "Station Wagon - Standard - 1160.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


// Start the queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);


        JSONObject postparams = new JSONObject();

        mProgress.show();

        try {
            postparams.put("username", app.getUsername());
            postparams.put("api_key", app.getApi_key());
            postparams.put("action", "BatchReserveSeats");
            postparams.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
            postparams.put("ticket_items", ticket_items);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, app.getCompany_apis_url(), postparams,
                response -> {
                    try {

                        Log.d("Response: ", response.toString(4));


                        if (response.getInt("response_code") == 0) {
                            JSONArray message = response.getJSONArray("ticket");

                            mProgress.dismiss();

                            mpesaPayment(refno);

//                            new Delete().from(Ticket.class).where("reference = ?", refno).execute();


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
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();


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


        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(req);


    }


//    private boolean mpesapay_reserve(String refnumber) {
//
////        mProgress.show();
//
//        JSONObject obj = new JSONObject();
//
//        JSONObject postparams = new JSONObject();
//
//        JSONArray ticket_items = new JSONArray();
//
//        RequestQueue reserverequestQueue = Volley.newRequestQueue(MainActivity.this);
//
////        Log.e("batch", list_of_batch_ticket.toString());
//        try {
//            obj.put("passenger_name", "Intercity Passenger");
//            obj.put("phone_number", app.getYournamberyournamber());
//            obj.put("id_number", "3100000");
//            obj.put("from_city", app.getFrom_id());
//            obj.put("to_city", app.getTo_id());
//            obj.put("travel_date", date);
//            obj.put("selected_vehicle", app.getFerry_id());
//            obj.put("seater", "3500");
//            obj.put("selected_seat", "1");
//            obj.put("selected_ticket_type", "8");
//            obj.put("payment_method", payment_id);
//            obj.put("email_address", "info@mobiticket.co.ke");
//            obj.put("insurance_charge", "");
//            obj.put("served_by", app.getLogged_user() + "-MPESA");
//            obj.put("amount_charged", localprice);
//            obj.put("reference_number", refnumber);
//            obj.put("quantity", "1");
//
//            ticket_items.put(obj);
//
//
//            postparams.put("username", app.getUsername());
//            postparams.put("api_key", app.getApi_key());
//            postparams.put("action", "BatchReserveSeats");
//            postparams.put("hash", app.getHash_key());
//            postparams.put("ticket_items", ticket_items);
//
//
//            Log.e("param", postparams.toString(4));
//
//            JsonObjectRequest req = new JsonObjectRequest(urls.allferyapiUrl, postparams,
//                    response -> {
//                        try {
//
//                            Log.d("Response: ", response.toString(4));
//
//                            if (response.getInt("response_code") == 0) {
//
//                                JSONArray jsonArray = response.getJSONArray("ticket");
//
//                                String message = response.getString("response_message");
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                    String reserve_ref = jsonObject1.getString("reference_number");
//
//                                    mpesaPayment(reserve_ref);
//
//
//                                }
//
//                                mProgress.dismiss();
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            mProgress.dismiss();
//
//                            Log.d("Exception", e.toString());
//                            Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Log.e("Volley Error:", error.toString());
//                    mProgress.dismiss();
//
//
//                }
//
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//            };
//            Log.d("Request body: ", postparams.toString());
//
//            req.setRetryPolicy(new DefaultRetryPolicy(30000, -1, 0));
//
//            reserverequestQueue.add(req);
//
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return true;
//
//    }


    public void MpesaVerificationDialog(String ref, String message) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mpesaverification, null);
        dialogBuilder.setView(dialogView);

        TextView messages = dialogView.findViewById(R.id.mpesasms);
        messages.setText(message);

        Button btncomplete = dialogView.findViewById(R.id.bt_close);

        final android.app.AlertDialog dialog = dialogBuilder.create();

        dialog.setCanceledOnTouchOutside(false);

        btncomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
                startActivity(new Intent(MainActivity.this, MainActivity.class));

            }
        });

        Button btnverify = dialogView.findViewById(R.id.btn_verify);

        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchMpesaTransaction(app.getRefno());

//                dialog.cancel();


            }
        });


        dialog.show();

    }


    private void mpesaPayment(String ref) {

        confirmtransProgress.show();


        app.setRefno(ref);

        Log.e("Phone", mpesanumber.getText().toString());
        String num = "";
        if (mpesanumber.getText().toString().startsWith("0")) {
            num = mpesanumber.getText().toString().replaceFirst("0", "\\254");
        } else {
            num = mpesanumber.getText().toString();
            app.setMpesanumber(num);

        }

        RequestQueue reserverequestQueue = Volley.newRequestQueue(MainActivity.this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", app.getUsername());
        params.put("api_key", app.getApi_key());
        params.put("action", "AuthorizePayment");
        params.put("payment_method", "3");
        params.put("reference_number", ref);
        params.put("mpesa_phone_number", num);

        JsonObjectRequest req = new JsonObjectRequest(urls.allferyapiUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("response_code") == 0) {

                                JSONArray jsonArray = response.getJSONArray("tickets");


                                String message = response.getString("response_message");

                                Log.d("MPESA Response", message);

                                confirmtransProgress.dismiss();


                                MpesaVerificationDialog(ref, message);
                                app.setRefno(ref);


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String reserve_confirmation = jsonObject1.getString("description");
                                    String reference_number = jsonObject1.getString("reference_number");

                                    Log.d("Reservation Status: ", reserve_confirmation);
                                    Log.d("Reserve:%n %s", jsonArray.toString(4));

                                    new Delete().from(Ticket.class).where("reference = ?", ref).execute();


                                }


                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
//                                confirmtransProgress.cancel();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Response Error: " + e);
                            confirmtransProgress.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mpesaalertDialog.dismiss();
                confirmtransProgress.dismiss();


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
        reserverequestQueue.add(req);

    }

    private void searchMpesaTransaction(String ref) {

        RequestQueue reserverequestQueue = Volley.newRequestQueue(MainActivity.this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("developer_username", app.getUsername());
        params.put("developer_api_key", app.getApi_key());
        params.put("action", "searchpayment");
        params.put("identifier", ref);


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urls.Pay_Confim_URL, new JSONObject(params),
                response -> {
                    try {
                        String code = response.getString("response_code");

                        if (code.equals("0")) {

                            confirmtransProgress.dismiss();
                            startActivity(new Intent(MainActivity.this, MainActivity.class));

                            String message = response.getString("response_message");

                            Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                            Log.e("MPESA Payment Response", message);


                            handler.removeCallbacks(runnable);


                            JSONArray jsonArray = response.getJSONArray("transactions");


                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(x);

                                source_name = jsonObject1.getString("source_name");
                                source_account_number = jsonObject1.getString("source_account_number");
                                trasaction_id = jsonObject1.getString("transaction_id");
                                amount = jsonObject1.getString("transfer_amount");

                            }

                            printMpesaTicket(source_name, source_account_number, trasaction_id, amount);


                        } else {

                            confirmtransProgress.dismiss();

                            Log.e("Mpesa Payment Response", response.getString("response_message"));

                            Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
                            confirmtransProgress.cancel();
                            handler.removeCallbacks(runnable);


                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                confirmtransProgress.dismiss();

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

        req.setRetryPolicy(new DefaultRetryPolicy(60000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        reserverequestQueue.add(req);


    }

    private void processAndSave() {


        ActiveAndroid.beginTransaction();
        try {


            if (chkAdult.isChecked()) {
                String seat_no = null;


                for (int x = 0; x < adultnum; x++) {
                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Adult";
                    ticket.cost = (int) Adult_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;
                    ticket.save();

                    //Displaying a toast message for successfull insertion
                    Log.d("Saved", String.valueOf(x));
                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();


                }
            }

            if (chkBigAnumal.isChecked()) {

                String seat_no = null;

                for (int x = 0; x < biganimalnum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Big Animal";
                    ticket.cost = (int) Big_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;
                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }

            if (chkBigTruck.isChecked()) {


                String seat_no = null;


                for (int x = 0; x < bigtrucknum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Big Truck";
                    ticket.cost = (int) Big_Truck_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;
                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            if (chkChild.isChecked()) {

                String seat_no = null;
                for (int x = 0; x < childnum; x++) {


                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Child";
                    ticket.cost = (int) Child_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;
                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            if (chkLuggage.isChecked()) {
                String seat_no = null;


                for (int x = 0; x < luggagenum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }


                    ticket = new Ticket();
                    ticket.ticket_type = "Luggage";
                    ticket.cost = (int) Luggage_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();
                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            if (chkMotorCycle.isChecked()) {

                String seat_no = null;
                for (int x = 0; x < motorCyclenum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }


                    ticket = new Ticket();
                    ticket.ticket_type = "Motor Cycle";
                    ticket.cost = (int) Motor_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }

            if (chkOther.isChecked()) {

                String seat_no = null;

                for (int x = 0; x < othernum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }


                    ticket = new Ticket();
                    ticket.ticket_type = "Other";
                    ticket.cost = Integer.valueOf(app.getOtherprice());
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }

            if (chkSaloonCar.isChecked()) {
                String seat_no = null;


                for (int x = 0; x < saloonCarnum; x++) {


                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }


                    ticket = new Ticket();
                    ticket.ticket_type = "Saloon Car";
                    ticket.cost = (int) Saloon_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }

            if (chkSmallAnimal.isChecked()) {

                String seat_no = null;

                for (int x = 0; x < smallAnimalnum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }
                    ticket = new Ticket();
                    ticket.ticket_type = "Small Animal";
                    ticket.cost = (int) Small_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();

                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            if (chkSmallTruck.isChecked()) {

                String seat_no = null;

                for (int x = 0; x < smallTrucknum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Small Truck";
                    ticket.cost = (int) Small_Truck_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();
                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }

            if (chkStationWagon.isChecked()) {

                String seat_no = null;
                for (int x = 0; x < stationWagonnum; x++) {

                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Station Wagon";
                    ticket.cost = (int) Station_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;

                    ticket.save();
                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            if (chkTuktuk.isChecked()) {

                String seat_no = null;

                for (int x = 0; x < tuktuknum; x++) {


                    List<Seat> items = new Select().from(Seat.class).orderBy("name ASC").limit(1).execute();
                    for (int i = 0; i < items.size(); i++) {
                        seat_no = items.get(i).name;
                        Log.e("Seat_no", seat_no);
                    }

                    ticket = new Ticket();
                    ticket.ticket_type = "Tuk Tuk";
                    ticket.cost = (int) Tuk_item.cost;
                    ticket.date = date;
                    ticket.ref_no = ref;
                    ticket.seat_no = seat_no;
                    ticket.save();
                    new Delete().from(Seat.class).where("name = ?", seat_no).execute();

                }
            }


            ActiveAndroid.setTransactionSuccessful();
            Toast.makeText(getApplicationContext(), "Tickets saved successfully", Toast.LENGTH_SHORT).show();

            if (payment_id == 3) {
                MpesaDialog(ref);

            } else {
                print();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }


        } finally {
            ActiveAndroid.endTransaction();

            new Delete().from(RefferenceNumber.class).where("name = ?", ref).execute();


        }

    }

    private void print() {
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


        total = (int) ((adultnum * Adult_item.cost) + (biganimalnum * Big_item.cost) + (bigtrucknum * Big_Truck_item.cost) + (childnum * Child_item.cost)
                        + (luggagenum * Luggage_item.cost) + (motorCyclenum * Motor_item.cost) + (saloonCarnum * Saloon_item.cost)
                + (smallAnimalnum * Small_item.cost) + (smallTrucknum * Small_Truck_item.cost)
                        + (stationWagonnum * Station_item.cost) + (tuktuknum * Tuk_item.cost) + (othernum * Integer.valueOf(app.getOtherprice())));


        if (Build.MODEL.equals("MobiPrint")) {


            Toast.makeText(getApplicationContext(), "Mobiwire Printing Ticket", Toast.LENGTH_LONG).show();
            Printer print = Printer.getInstance();
            print.printFormattedText();
            print.printBitmap(getResources().openRawResource(R.raw.ship));
            print.printText("------Mbita Ferry Services-----");
            print.printText("..........Mbita,KENYA..........");
            print.printText(".......Passenger Details........");

            print.printText("Total: " + total + " ksh");

            print.printText("Ticket Ref: " + ref);
            print.printText("Route: " + app.getFrom() + " " + app.getTo());

            print.printText("Item      Quantity    Cost\n");


            if (chkAdult.isChecked()) {
                print.printText("Adult" + "           " + adultnum + "    " + adultnum *  Big_item.cost);

            }


            if (chkBigAnumal.isChecked()) {
                print.printText("Big Animal" + "      " + biganimalnum + "    " + biganimalnum * Big_Truck_item.cost);

            }

            if (chkBigTruck.isChecked()) {
                print.printText("Big Truck" + "       " + bigtrucknum + "    " + bigtrucknum * Big_Truck_item.cost);

            }


            if (chkChild.isChecked()) {
                print.printText("Child " + "          " + childnum + "    " + childnum * Child_item.cost);

            }


            if (chkLuggage.isChecked()) {
                print.printText("Luggage " + "        " + luggagenum + "    " + luggagenum * Luggage_item.cost);

            }


            if (chkMotorCycle.isChecked()) {
                print.printText("Motor Cycle " + "    " + motorCyclenum + "    " + motorCyclenum * Motor_item.cost);

            }


            if (chkOther.isChecked()) {
                print.printText(app.getOthername() + "          " + othernum + "     " + othernum * (Integer.valueOf(app.getOtherprice())));

            }


            if (chkSaloonCar.isChecked()) {
                print.printText("Salon Car " + "      " + saloonCarnum + "    " + saloonCarnum * Saloon_item.cost);

            }


            if (chkSmallAnimal.isChecked()) {
                print.printText("Small Animal " + "   " + smallAnimalnum + "    " + smallAnimalnum * Small_item.cost);

            }


            if (chkSmallTruck.isChecked()) {
                print.printText("Small Truck " + "    " + smallTrucknum + "    " + smallTrucknum * Small_Truck_item.cost);

            }

            if (chkStationWagon.isChecked()) {
                print.printText("Station Wagon " + "  " + stationWagonnum + "    " + stationWagonnum * Station_item.cost);

            }

            if (chkTuktuk.isChecked()) {
                print.printText("Tuk Tuk " + "        " + tuktuknum + "    " + tuktuknum * Tuk_item.cost);

            }
            print.printText("Issued On :" + currentDateandTime);
            print.printText("Served By :" + app.getLogged_user());

            print.printBitmap(getResources().openRawResource(R.raw.payment_methods_old));
            print.printBitmap(getResources().openRawResource(R.raw.powered_by_mobiticket));
            print.printEndLine();
        } else if (Build.MODEL.equals("MP4") || Build.MODEL.equals("MP3_Plus")) {


            Toast.makeText(getContext(), "Printing", Toast.LENGTH_SHORT).show();
            total = (int) ((adultnum * Adult_item.cost) + (biganimalnum * Big_item.cost) + (bigtrucknum * Big_Truck_item.cost) + (childnum * Child_item.cost)
                    + (luggagenum * Luggage_item.cost) + (motorCyclenum * Motor_item.cost) + (saloonCarnum * Saloon_item.cost)
                    + (smallAnimalnum * Small_item.cost) + (smallTrucknum * Small_Truck_item.cost)
                    + (stationWagonnum * Station_item.cost) + (tuktuknum * Tuk_item.cost) + (othernum * Integer.valueOf(app.getOtherprice())));


            CsPrinter printer = new CsPrinter();
            printer.addTextToPrint("Mbita Ferry Services", null, 30, true, false, 1);
            printer.addTextToPrint("         Mbita,KENYA      ", null, 20, true, false, 1);
            printer.addTextToPrint("Date: " + app.getTravel_date(), null, 20, true, false, 1);

            printer.addTextToPrint("Amount: " + total, null, 30, true, false, 1);

            printer.addTextToPrint("Ticket Ref: " + ref,null, 30, true, false, 1);
            printer.addTextToPrint("Route: " + app.getFrom() + " " + app.getTo(),null, 30, true, false, 1);

            printer.addTextToPrint("Item      Quantity    Cost\n",null, 30, true, false, 1);


            CsPrinter csPrinter = printer;

            if (chkAdult.isChecked()) {
                csPrinter.addTextToPrint("Adult" + "             " + adultnum + "     " + adultnum * Adult_item.cost, null, 25, true, false, 0);

            }


            if (chkBigAnumal.isChecked()) {
                csPrinter.addTextToPrint("Big Animal" + "        " + biganimalnum + "    " + biganimalnum * Big_item.cost, null, 25, true, false, 0);

            }

            if (chkBigTruck.isChecked()) {
                csPrinter.addTextToPrint("Big Truck" + "       " + bigtrucknum + "    " + bigtrucknum * Big_Truck_item.cost, null, 25, true, false, 0);

            }


            if (chkChild.isChecked()) {
                csPrinter.addTextToPrint("Child " + "          " + childnum + "    " + childnum * Child_item.cost, null, 25, true, false, 0);

            }


            if (chkLuggage.isChecked()) {
                csPrinter.addTextToPrint("Luggage " + "        " + luggagenum + "    " + luggagenum * Luggage_item.cost, null, 25, true, false, 0);

            }


            if (chkMotorCycle.isChecked()) {
                csPrinter.addTextToPrint("Motor Cycle " + "    " + motorCyclenum + "    " + motorCyclenum * Motor_item.cost, null, 25, true, false, 0);

            }


            if (chkOther.isChecked()) {
                csPrinter.addTextToPrint(app.getOthername() + "          " + othernum + "     " + othernum * (Integer.valueOf(app.getOtherprice())), null, 25, true, false, 0);

            }


            if (chkSaloonCar.isChecked()) {
                csPrinter.addTextToPrint("Salon Car " + "      " + saloonCarnum + "    " + saloonCarnum * Saloon_item.cost, null, 25, true, false, 0);

            }


            if (chkSmallAnimal.isChecked()) {
                csPrinter.addTextToPrint("Small Animal " + "   " + smallAnimalnum + "    " + smallAnimalnum * Small_item.cost, null, 25, true, false, 0);

            }


            if (chkSmallTruck.isChecked()) {
                csPrinter.addTextToPrint("Small Truck " + "    " + smallTrucknum + "    " + smallTrucknum * Small_Truck_item.cost, null, 25, true, false, 0);

            }

            if (chkStationWagon.isChecked()) {
                csPrinter.addTextToPrint("Station Wagon " + "  " + stationWagonnum + "    " + stationWagonnum * Station_item.cost, null, 25, true, false, 0);

            }

            if (chkTuktuk.isChecked()) {
                csPrinter.addTextToPrint("Tuk Tuk " + "        " + tuktuknum + "    " + tuktuknum * Tuk_item.cost, null, 25, true, false, 0);

            }

            csPrinter.addTextToPrint("Issued On :" + currentDateandTime, null, 25, true, false, 0);
            csPrinter.addTextToPrint("Served By :" + app.getLogged_user(), null, 25, true, false, 0);

            csPrinter.addBitmapFromRawToPrint(getContext(), R.raw.powered_by_mobiticket);
            printEndLine();
            printer.print(getContext());


        }
    }

    private void printMpesaTicket(String name, String phone, String trasaction_id, String amount) {
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (Build.MODEL.equals("MobiPrint")) {


            Toast.makeText(getApplicationContext(), "Mobiwire Printing Ticket", Toast.LENGTH_LONG).show();
            print.printFormattedText();

            print.printText("    ------Mbita Ferry Services-----     ");

            print.printText("         Mbita,KENYA        ");
            print.printText("                            ");


            print.printText("Paid By: " + name);
            print.printText("Phone no: " + phone);
            print.printText("Transaction ID: " + trasaction_id);

            print.printFormattedTextPrepare();
            print.addString("Ref:" + app.getRefno(), 2, true);
            print.printFormattedText();


            print.printFormattedTextPrepare();
            print.addString("Fare: " + amount + "/=", 2, true);
            print.printFormattedText();


            print.printText("Issued On: " + currentDateandTime);
            print.printText("Served By: " + app.getLogged_user());


            print.printBitmap(getResources().openRawResource(R.raw.powered_by_mobiticket));
            print.printEndLine();

        } else if (Build.MODEL.equals("MP4") || Build.MODEL.equals("MP3_Plus")) {


            Toast.makeText(getApplicationContext(), "Printing", Toast.LENGTH_SHORT).show();


            CsPrinter printer = new CsPrinter();
            printer.addTextToPrint("  Mbita Ferry  Services  ", null, 40, true, false, 1);
            printer.addTextToPrint("         Mbita,KENYA      ", null, 20, true, false, 1);


            CsPrinter csPrinter = printer;

            csPrinter.addTextToPrint("Fare: " + amount + "/=", null, 40, true, false, 0);
            csPrinter.addTextToPrint("Paid By: " + name, null, 25, true, false, 0);
            csPrinter.addTextToPrint("Phone no: " + phone, null, 25, true, false, 0);
            csPrinter.addTextToPrint("Transaction ID: " + trasaction_id, null, 25, true, false, 0);

            csPrinter.addTextToPrint("Issued On: " + currentDateandTime, null, 25, true, false, 0);
            csPrinter.addTextToPrint("Served By: " + app.getLogged_user(), null, 25, true, false, 0);
            csPrinter.addBitmapFromRawToPrint(this, R.raw.powered_by_mobiticket);
            printEndLine();
            printer.print(this);

        } else {
            Toast.makeText(getApplicationContext(), "Printing Not supported", Toast.LENGTH_SHORT).show();


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
        } else if (id == R.id.id_logout) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
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
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showRefsList() {

        List<RefferenceNumber> items = new Select().from(RefferenceNumber.class).orderBy("name ASC").limit(100).execute();
        for (int i = 0; i < items.size(); i++) {
            String refs = items.get(i).name;
            Log.e("Refs", refs);
        }

    }


    private void researveTickets() {
        final List<Ticket> list = new Select().from(Ticket.class).orderBy("date ASC").execute();

        for (int i = 0; i < list.size(); i++) {

            refno = (String.valueOf(list.get(i).ref_no));

            Log.e("Type", String.valueOf(list.get(i).ticket_type));

        }

    }

    private void batch_reserve() {


        final List<Ticket> list = new Select().from(Ticket.class).orderBy("date ASC").execute();

        for (int i = 0; i < list.size(); i++) {

            refno = (String.valueOf(list.get(i).ref_no));

            Log.e("Type", String.valueOf(list.get(i).ticket_type));

        }


        final List<Ticket> list2 = new Select().from(Ticket.class).where("reference= ?", refno).orderBy("Ticket_type ASC").execute();

        Log.e("List", list2.toString());

        JSONObject obj;
        JSONArray ticket_items = new JSONArray();


        // Querying Items from db and create object;


        //----------------------------------------Adult--------------------------
        String adult_keyword = "Adult";

        List<Ticket> adults = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, adult_keyword).orderBy("date ASC").execute();

        int count = adults.size();
        int adult_cost = (int) (count * Adult_item.cost);

//        Log.e("Name",String.valueOf(adults.get(0).ticket_type));


        //----------------------------------------Big Animal--------------------------

        String big_animal_keyword = "Big Animal";

        List<Ticket> big_animal = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, big_animal_keyword).orderBy("date ASC").execute();

        int big_animal_count = big_animal.size();
        int big_animal_cost = (int) (big_animal_count * Big_item.cost);

        //----------------------------------------Station Wagon--------------------------

        String station_wagon_keyword = "Station Wagon";

        List<Ticket> station_wagon = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, station_wagon_keyword).orderBy("date ASC").execute();

        int station_wagon_count = station_wagon.size();
        int station_wagon_cost = (int) (station_wagon_count * Station_item.cost);

        //----------------------------------------Tuk Tuk--------------------------

        String tuk_tuk_keyword = "Tuk Tuk";

        List<Ticket> tuk_tuk = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, tuk_tuk_keyword).orderBy("date ASC").execute();

        int tuk_tuk_count = tuk_tuk.size();
        int tuk_tuk_cost = (int) (tuk_tuk_count * Tuk_item.cost);

        //----------------------------------------Big Truck--------------------------

        String big_truck_keyword = "Big Truck";

        List<Ticket> big_truck = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, big_truck_keyword).orderBy("date ASC").execute();

        int big_truck_count = big_truck.size();
        int big_truck_cost = (int) (big_truck_count * Big_Truck_item.cost);


        //----------------------------------------Child --------------------------

        String child_keyword = "Child";

        List<Ticket> child = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, child_keyword).orderBy("date ASC").execute();

        int child_count = child.size();
        int child_count_cost = (int) (child_count * Child_item.cost);


        //----------------------------------------Luggage --------------------------

        String luggage_keyword = "Luggage";

        List<Ticket> luggage = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, luggage_keyword).orderBy("date ASC").execute();

        int luggage_count = luggage.size();
        int luggage_count_cost = (int) (luggage_count * Luggage_item.cost);


        //----------------------------------------Motor Cycle --------------------------

        String motor_keyword = "Motor Cycle";

        List<Ticket> motor = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, motor_keyword).orderBy("date ASC").execute();

        int motor_count = motor.size();
        int motor_count_cost = (int) (motor_count * Motor_item.cost);


        //----------------------------------------Other --------------------------

        String other_keyword = "Other";

        List<Ticket> other = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, other_keyword).orderBy("date ASC").execute();

        int other_count = other.size();
        int other_count_cost = (int) (motor_count * Other_item.cost);


        //----------------------------------------Saloon car --------------------------

        String Saloon_keyword = "Saloon Car";

        List<Ticket> saloon_car = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Saloon_keyword).orderBy("date ASC").execute();

        int saloon_count = saloon_car.size();
        int saloon_count_count_cost = (int) (saloon_count * Saloon_item.cost);


        //----------------------------------------Small Animal --------------------------

        String Small_animal_keyword = "Small Animal";

        List<Ticket> small_animal = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Small_animal_keyword).orderBy("date ASC").execute();

        int small_anima_count = small_animal.size();
        int small_animal_count_cost = (int) (small_anima_count * Small_item.cost);


        //----------------------------------------Small Truck --------------------------

        String Small_truck_keyword = "Small Truck";

        List<Ticket> small_truck = new Select().from(Ticket.class).where("reference = ? AND Ticket_type = ?", refno, Small_truck_keyword).orderBy("date ASC").execute();

        int small_truck_count = small_truck.size();
        int small_truck_count_cost = (int) (small_truck_count * Small_Truck_item.cost);


        Log.e("Adults", String.valueOf(count));
        Log.e("adult_cost", String.valueOf(adult_cost));


        Log.e("big_animal_count", String.valueOf(big_animal_count));
        Log.e("big_animal_adult_cost", String.valueOf(big_animal_cost));

        if (adults.size() > 0) {

            for (int i = 0; i < 1; i++) {
                obj = new JSONObject();


                try {

                    obj.put("passenger_name", String.valueOf(list2.get(i).ticket_type));
                    obj.put("phone_number", app.getPhone_num());
                    obj.put("id_number", app.getId_number());
                    obj.put("from_city", app.getFrom_id());
                    obj.put("to_city", app.getTo_id());
                    obj.put("travel_date", app.getTravel_date());
                    obj.put("selected_vehicle", app.getFerry_id());
                    obj.put("seater", "3500");
                    obj.put("selected_seat", String.valueOf(list2.get(i).seat_no));
                    obj.put("selected_ticket_type", "8");
                    obj.put("payment_method", app.getPayment_method());
                    obj.put("email_address", "boroni@mobiticket.co.ke");
                    obj.put("insurance_charge", "");
                    obj.put("served_by", app.getLogged_user());
                    obj.put("amount_charged", String.valueOf(adult_cost));
                    obj.put("reference_number", refno);
                    obj.put("quantity", count);
                    obj.put("item_name", "Adult - Standard - 150.00");

                    ticket_items.put(obj);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                Log.e("Passengername", String.valueOf(adults.get(i).ticket_type));
                Log.e("Seat", String.valueOf(adults.get(i).seat_no));
            }


        }

        if (big_animal.size() > 0) {
            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(big_animal.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(big_animal.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(big_animal_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(big_animal_count));
                obj.put("item_name", "Big Animal - Standard - 300.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (big_truck.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(big_truck.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(big_truck.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(big_truck_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(big_truck_count));
                obj.put("item_name", "Big Truck - Standard - 2320.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (luggage.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(luggage.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(luggage.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(luggage_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(luggage_count));
                obj.put("item_name", "Luggage - Standard - 60.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (saloon_car.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(saloon_car.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(saloon_car.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(saloon_count_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(saloon_count));
                obj.put("item_name", "Saloon Car - Standard - 930.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }
        if (small_truck.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(small_truck.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(small_truck.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(small_truck_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(small_truck_count));
                obj.put("item_name", "Small Truck - Standard - 1740.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);


            }

        }
        if (tuk_tuk.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(tuk_tuk.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(tuk_tuk.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(tuk_tuk_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(tuk_tuk_count));
                obj.put("item_name", "Tuk Tuk - Standard - 500.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (child.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(child.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(child.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(child_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(child_count));
                obj.put("item_name", "Child - Standard - 50.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (motor.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(motor.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(motor.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(motor_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(motor_count));
                obj.put("item_name", "Motor Cycle - Standard - 250.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


        if (other.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(other.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(other.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(other_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(other_count));
                obj.put("item_name", "Other - Standard - 0.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (small_animal.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(small_animal.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(small_animal.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(small_animal_count_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(small_anima_count));
                obj.put("item_name", "Small Animal - Standard - 200.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }

        if (station_wagon.size() > 0) {

            obj = new JSONObject();

            try {
                obj.put("passenger_name", String.valueOf(station_wagon.get(0).ticket_type));
                obj.put("phone_number", app.getPhone_num());
                obj.put("id_number", app.getId_number());
                obj.put("from_city", app.getFrom_id());
                obj.put("to_city", app.getTo_id());
                obj.put("travel_date", app.getTravel_date());
                obj.put("selected_vehicle", app.getFerry_id());
                obj.put("seater", "3500");
                obj.put("selected_seat", String.valueOf(station_wagon.get(0).seat_no));
                obj.put("selected_ticket_type", "8");
                obj.put("payment_method", app.getPayment_method());
                obj.put("email_address", "boroni@mobiticket.co.ke");
                obj.put("insurance_charge", "");
                obj.put("served_by", app.getLogged_user());
                obj.put("amount_charged", String.valueOf(station_wagon_cost));
                obj.put("reference_number", refno);
                obj.put("quantity", String.valueOf(station_wagon_count));
                obj.put("item_name", "Station Wagon - Standard - 1160.00");

                ticket_items.put(obj);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        }


// Start the queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);


        JSONObject postparams = new JSONObject();

        try {
            postparams.put("username", app.getUsername());
            postparams.put("api_key", app.getApi_key());
            postparams.put("action", "BatchReserveSeats");
            postparams.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
            postparams.put("ticket_items", ticket_items);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, app.getCompany_apis_url(), postparams,
                response -> {
                    try {

                        Log.d("Response: ", response.toString(4));


                        if (response.getInt("response_code") == 0) {
                            JSONArray message = response.getJSONArray("ticket");

                            new Delete().from(Ticket.class).where("reference = ?", refno).execute();


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


        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        requestQueue.add(req);


    }


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.pp)
                        .setContentTitle("Local Ticket ")
                        .setContentText("Local Tickets being synced,don't turn of the Internet");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private Intent getPrintIntent2() {
        Intent aidlIntent = new Intent();
        aidlIntent
                .setAction("android.intent.action.START_PRINTER_SERVICE_AIDL");
        aidlIntent.setPackage("com.sagereal.printer");
        return aidlIntent;
    }

    @Override
    protected void onStop() {
        unbindService(serviceConnection);
        super.onStop();
    }

    @Override
    protected void onResume() {
        bindService(getPrintIntent2(), serviceConnection, Service.BIND_AUTO_CREATE);

        super.onResume();


    }


    public static class ConnectivityHelper {
        public static boolean isConnectedToNetwork(Context context) {
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

}
