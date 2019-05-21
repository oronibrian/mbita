package com.example.mibitaferrynew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.activeandroid.query.From;
import com.activeandroid.query.Select;
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
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.Adapters.MyTripsArrayAdapter;
import com.example.mibitaferrynew.Model.MytripsDetails;
import com.example.mibitaferrynew.TableModel.Ticket;
import com.google.android.material.button.MaterialButton;
import com.nbbse.printapi.Printer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchTicketActivity extends AppCompatActivity {

    MyApplication app;
    ArrayList<MytripsDetails> mytripsDetails;
    ListView mytripslistView;
    EditText editTextphone;
    Button btnsearch;
    String phoneno;
    MaterialButton btnondevice, btnonline;
    EditText editTextsearch;
    String searchtext;
    private ProgressDialog mProgress;
    private Context mcontext;



    private ListView Details;
    private ArrayList<String> employeeItems;
    private ArrayAdapter employeeItemsAdapter;

    Toolbar Toolbar;

    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ticket);

        app=(MyApplication) getApplication();

        Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setTitle("Search Ticket");

        mytripslistView = findViewById(R.id.mytriplistview_id);

        mytripsDetails = new ArrayList<MytripsDetails>();

        editTextsearch = findViewById(R.id.search_tickets_id);


        searchtext=editTextsearch.getText().toString();

        mProgress = new ProgressDialog(this);

        mProgress.setTitle("Searching Ticket...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);
        mcontext = getApplicationContext();

        btnondevice = findViewById(R.id.btnSearchDevice);
        btnonline = findViewById(R.id.btnSearchCloud);


        btnonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTickets();
            }
        });


        btnondevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_local();
            }
        });


        mytripslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Selected_payment_type = String.valueOf(mytripslistView.getSelectedItemPosition());

//


                TextView tootxt = (TextView) view.findViewById(R.id.type_list_item);
                String too = tootxt.getText().toString();
                tootxt.setVisibility(View.GONE);


                TextView fromtxt = (TextView) view.findViewById(R.id.amount_list_item);
                String from = fromtxt.getText().toString();
                fromtxt.setVisibility(View.GONE);


                TextView phonetxt = (TextView) view.findViewById(R.id.number_list_item);
                String phonenum = phonetxt.getText().toString();

               /* TextView issuedtxt = (TextView) view.findViewById(R.id.issuedon);
                String issuedon = issuedtxt.getText().toString();

                TextView seattxt = (TextView) view.findViewById(R.id.seat);
                String seat = seattxt.getText().toString();*/


                if (Build.MODEL.equals("MobiPrint")) {
                    Printer print = Printer.getInstance();
                    print.printFormattedText();
                    print.printText("------Mbita Ferry Services------");
                    print.printText("..........Mbita,KENYA..........");
                    print.printText("......Passenger Details.........");

                    print.printText("Name: " + too);
                    print.printText("Ref No:" + from);
//                    print.printText("Seat:" + seat);
                    print.printText("Fare: Ksh." + phonenum);

                    print.printText("................................");
                    print.printText("Route:" + app.getRoute());
//                    print.printText("Travel Date: " + issuedon);
                    print.printText("................................");
                    print.printText("Issued On :" +formattedDate);
                    print.printText("Issued by :" + app.getLogged_user());

                    print.printBitmap(getResources().openRawResource(R.raw.payment_methods_old));
                    print.printBitmap(getResources().openRawResource(R.raw.powered_by_mobiticket));
                    print.printEndLine();
                }
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MainActivity.class);
        startActivity(backMainTest);
        finish();
    }


    private void getTickets() {

        mProgress.show();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", app.getUsername());
        params.put("api_key", app.getApi_key());
        params.put("action", "SearchTicket");
        params.put("identifier", editTextsearch.getText().toString());


        RequestQueue batchreserve = Volley.newRequestQueue(SearchTicketActivity.this);

        JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("response_code") == 0) {
                                JSONArray jsonArray = response.getJSONArray("tickets");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    Log.d("Ticket details ", jsonObject1.toString());

                                    String car_name = jsonObject1.getString("vehicle_name");
                                    String travel_from = jsonObject1.getString("travel_from");
                                    String travel_to = jsonObject1.getString("travel_to");
                                    String travel_date = jsonObject1.getString("travel_date");
                                    String reference_number = jsonObject1.getString("reference_number");
                                    String amount = jsonObject1.getString("amount");
                                    String seat = jsonObject1.getString("seat");

                                    String name = jsonObject1.getString("name");
                                    String phone = jsonObject1.getString("msisdn");
                                    String date_issued = jsonObject1.getString("payment_date");

                                    mytripsDetails.add(new MytripsDetails(car_name, travel_from, travel_to, travel_date, reference_number));
                                    mProgress.dismiss();

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();

                            }

                            MyTripsArrayAdapter tripsArrayAdapter = new MyTripsArrayAdapter(SearchTicketActivity.this, mytripsDetails);

                            mytripslistView.setAdapter(tripsArrayAdapter);
                            tripsArrayAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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



    public void search_local(){


        From from_Ticket = new Select()
                .from(Ticket.class)
                .where(" reference= ?", editTextsearch.getText().toString())
                .orderBy("Ticket_type ASC");

       int count = from_Ticket.count();

        Log.e("Count", String.valueOf(count));


        Log.e("Loacal",from_Ticket.toString());


        final List<Ticket> list = from_Ticket.execute();
        for (int i = 0; i < list.size(); i++) {
            Log.e("Ref", String.valueOf(list.get(i).ref_no));
            Log.e("type", String.valueOf(list.get(i).ticket_type));
            Log.e("cost", String.valueOf(list.get(i).cost));
            Log.e("date", String.valueOf(list.get(i).date ));

            mytripsDetails.add(new MytripsDetails(String.valueOf(list.get(i).ticket_type),
                    String.valueOf(list.get(i).ref_no),
                    String.valueOf(list.get(i).cost),
                    String.valueOf(list.get(i).ticket_type),
                    String.valueOf(list.get(i).ticket_type)));


        }


        MyTripsArrayAdapter tripsArrayAdapter = new MyTripsArrayAdapter(SearchTicketActivity.this, mytripsDetails);

        mytripslistView.setAdapter(tripsArrayAdapter);




    }

}

