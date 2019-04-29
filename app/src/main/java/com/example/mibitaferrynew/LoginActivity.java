package com.example.mibitaferrynew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.android.volley.AuthFailureError;
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
import com.example.mibitaferrynew.API.urls;
import com.example.mibitaferrynew.Adapters.FerryRouteCardArrayAdapter;
import com.example.mibitaferrynew.ModelPojo.Routes;
import com.example.mibitaferrynew.TableModel.RefferenceNumber;
import com.example.mibitaferrynew.TableModel.seats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {


    final Context context = this;
    RefferenceNumber reff_number_object;
    Button login;
    String seats;
    EditText username, password;
    Dialog dialog;
    ProgressDialog progressDialog;
    MyApplication app;
    private FerryRouteCardArrayAdapter cardArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        app = (MyApplication) getApplication();

        String currentDate = new SimpleDateFormat("dd-MM-yyy").format(new Date());


        app.setApi_key("c8e254c0adbe4b2623ff85567027d78d4cc066357627e284d4b4a01b159d97a7");
        app.setUsername("emuswailit");
        app.setHash_key("1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
        app.setTravel_date(currentDate);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Login In");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);


        listView = findViewById(R.id.card_listView);

        listView.addFooterView(new View(this));


        cardArrayAdapter = new FerryRouteCardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        loadFerryRoutesandSeats();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = String.valueOf(listView.indexOfChild(view));

                String Mbita = "1";
                String mfangano = "2";

                String luanda_kotiono = "3";


                if (selected.equals("1")) {
                    app.setTo(mfangano);
                    app.setFrom(Mbita);
                } else if (selected.equals("2")) {
                    app.setTo(Mbita);
                    app.setFrom(mfangano);

                } else if (selected.equals("3")) {
                    app.setTo(luanda_kotiono);
                    app.setFrom(Mbita);

                } else if (selected.equals("4")) {
                    app.setTo(Mbita);
                    app.setFrom(luanda_kotiono);

                }


                Toast.makeText(getApplicationContext(), app.getFrom() + "  " + app.getTo(), Toast.LENGTH_SHORT).show();


                dialog = new Dialog(context);
                dialog.setContentView(R.layout.login_dialog);
                dialog.setTitle("      Account Login");


                username = dialog.findViewById(R.id.editTextusername);
                password = dialog.findViewById(R.id.editTextpassword);

                Button dialogButton = dialog.findViewById(R.id.btnlogin);


                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();

                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                    }


                });
                dialog.show();


            }
        });


    }


    private void login() {

        final String email = username.getText().toString();
        final String pass = password.getText().toString();


        //validating inputs
        if (TextUtils.isEmpty(email)) {
            username.setError("Please enter your email address or phone");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        } else {

            progressDialog.show();
            RequestQueue reserverequestQueue = Volley.newRequestQueue(LoginActivity.this);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("username", "rWIv7GWzSp");
            params.put("api_key", "831b238c5cd73308520e38bbc6c1774f470a89e96d07a5bb6bcac3b86456f889");
            params.put("action", "UserLogin");
            params.put("clerk_username", email);
            params.put("clerk_password", pass);


            JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                    response -> {
                        try {

                            if (response.getInt("response_code") == 0) {
                                String first_name = response.getString("first_name");
                                String last_name = response.getString("last_name");

                                String phone_num = response.getString("phone_number");


                                app.setLogged_user(first_name + " " + last_name);
                                app.setPhone_num(phone_num);


                                Log.d("log in ", first_name);
                                progressDialog.dismiss();


                                startActivity(new Intent(getApplicationContext(), MainActivity.class));


                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
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
//            reserverequestQueue.getCache().clear();

            reserverequestQueue.add(req);

        }
    }


    private void saveRefferences() {


        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("developer_username", "emuswailit");
        params.put("developer_api_key", "c8e254c0adbe4b2623ff85567027d78d4cc066357627e284d4b4a01b159d97a7");
        params.put("action", "batchgeneratereferencenumbers");
        params.put("limit", "5");


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urls.refGeneration, new JSONObject(params),
                response -> {
                    try {

                        if (response.getInt("response_code") == 0) {
                            JSONArray jsonArray = response.getJSONArray("reference_numbers");

                            ActiveAndroid.beginTransaction();
                            try {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String refs = jsonObject1.getString("name");
                                    String id = jsonObject1.getString("id");


                                    Log.e("Refs", String.valueOf(refs));
                                    reff_number_object = new RefferenceNumber();
                                    reff_number_object.name = refs;
                                    reff_number_object.guid = id;
                                    //Saving name to sqlite database
                                    reff_number_object.save();

                                    //Displaying a toast message for successfull insertion
                                    Log.d("Saved", String.valueOf(i));

                                }

                                ActiveAndroid.setTransactionSuccessful();
                                Toast.makeText(getApplicationContext(), "Data Inserted successfully", Toast.LENGTH_SHORT).show();

                            } finally {
                                ActiveAndroid.endTransaction();

                            }


                        } else {
                            Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }


        };

        requestQueue.add(req);

    }


    private void loadFerryRoutesandSeats() {


        RequestQueue ferryrequests = Volley.newRequestQueue(LoginActivity.this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", "emuswailit");
        params.put("api_key", "c8e254c0adbe4b2623ff85567027d78d4cc066357627e284d4b4a01b159d97a7");
        params.put("action", "Schedule");
        params.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
        params.put("travel_date", app.getTravel_date());



        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urls.allferyapiUrl, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt("response_code") == 0) {
                                JSONArray jsonArray = response.getJSONArray("bus");
                                Log.i("Response:", jsonArray.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String ferry_route = jsonObject1.getString("route");

                                    String name = ferry_route.substring(8);


                                    Log.d("Ferry Routes: ", ferry_route);

                                    Routes card = new Routes(name);

                                    cardArrayAdapter.add(card);

                                    loadTickets();

//
//                                    JSONArray products = jsonObject1.getJSONArray("seats");
//                                    for (int x = 0; x < products.length(); x++) {
//
//                                        JSONObject jsonObjectseats = jsonArray.getJSONObject(i);
//                                        String seatname = jsonObjectseats.getString("seater");
//
//                                        String seater = jsonObjectseats.getString("name");
//
//                                        com.example.mibitaferrynew.TableModel.seats seats = new seats();
//                                        seats.name = seatname;
//                                        seats.seater = seater;
//                                        seats.save();
//
//                                        Log.e("Seater",seatname);
//
//                                    }


                                }




                            } else {

                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                            }


                            listView.setAdapter(cardArrayAdapter);


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
                return "application/json; charset=utf-8";
            }


        };
        ferryrequests.add(req);

    }


    private void loadTickets() {


        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", "emuswailit");
        params.put("api_key", "c8e254c0adbe4b2623ff85567027d78d4cc066357627e284d4b4a01b159d97a7");
        params.put("action", "SearchSchedule");
        params.put("travel_from", app.getFrom());
        params.put("travel_to", app.getTo());
        params.put("travel_date", app.getTravel_date());
        params.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");


        JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                response -> {
                    try {

                        if (response.getInt("response_code") == 0) {

                            saveRefferences();

                            JSONArray jsonArray = response.getJSONArray("bus");
                            JSONArray jsonArrayPrice = response.getJSONObject("bus").getJSONArray("price");


                            String myString = response.getJSONObject("bus").getJSONObject("price").getString("name");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                seats = jsonObject1.getString("total_seats");


                                Log.i("Count", String.valueOf(seats));

                                // Inserting Contacts
                                Log.d("Insert: ", "Inserting ..");


                            }


                            Log.d("Price Data: ", myString);

                            try {
                                FileWriter file = new FileWriter(Environment.getExternalStorageState());
                                file.write(String.valueOf(jsonArrayPrice));
                                file.flush();
                                file.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
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

        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=utf-8";
            }


        };

        requestQueue.add(req);


    }


}
