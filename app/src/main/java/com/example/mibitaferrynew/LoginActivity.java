package com.example.mibitaferrynew;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
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
import com.example.mibitaferrynew.Model.Routes;
import com.example.mibitaferrynew.TableModel.Price;
import com.example.mibitaferrynew.TableModel.RefferenceNumber;
import com.example.mibitaferrynew.TableModel.Seat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    final Context context = this;
    RefferenceNumber reff_number_object;
    Price price_Object;

    Button login;
    String seats;
    EditText username, password;
    Dialog dialog;
    ProgressDialog progressDialog, progressDialog2;
    MyApplication app;
    ArrayList<String> list_of_seats;
    private FerryRouteCardArrayAdapter cardArrayAdapter;
    private ListView listView;

    private int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_login);


        app = (MyApplication) getApplication();

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_top);
//        setSupportActionBar(toolbar);

        String currentDate = new SimpleDateFormat("dd-MM-yyy").format(new Date());


        app.setApi_key("c8e254c0adbe4b2623ff85567027d78d4cc066357627e284d4b4a01b159d97a7");
        app.setUsername("emuswailit");
        app.setHash_key("1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");
        app.setTravel_date(currentDate);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Login");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.setCancelable(false);


        progressDialog2 = new ProgressDialog(LoginActivity.this);
        progressDialog2.setMessage("Loading...");
        progressDialog2.setTitle("Loading Routes");
        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog2.setCancelable(false);

        listView = findViewById(R.id.card_listView);

        listView.addFooterView(new View(this));


        cardArrayAdapter = new FerryRouteCardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        loadFerryRoutesandSeats();

        new Delete().from(Seat.class).execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = String.valueOf(listView.indexOfChild(view));

                TextView textView = view.findViewById(R.id.line1);


                TextView textView2 = view.findViewById(R.id.from_id);
                textView2.setVisibility(View.GONE);


                TextView textView3 = view.findViewById(R.id.to_id);
                textView3.setVisibility(View.GONE);

                String route = textView.getText().toString();
                String from_id = textView2.getText().toString();
                String to_id = textView3.getText().toString();

                TextView textView4 = view.findViewById(R.id.ferry_id);
                String ferry_id = textView4.getText().toString();


                List<RefferenceNumber> items = new Select().from(RefferenceNumber.class).orderBy("name ASC").limit(1).execute();
               if( items.size()<=10){
                   saveRefferences();

               }


                app.setRoute(route);

                String Mbita = "Mbita";
                String mfangano = "Mfangano";

                String luanda_kotiono = "Luanda Kotieno";


                if (selected.equals("0")) {
                    app.setTo(mfangano);
                    app.setFrom(Mbita);

                    app.setTo_id(to_id);
                    app.setFrom_id(from_id);
                    app.setFerry_id(ferry_id);

                    Log.e("Route", route);
                    Log.e("to_id", to_id);
                    Log.e("from_id", from_id);
                    Log.e("ferry_id", ferry_id);


                } else if (selected.equals("1")) {
                    app.setTo(Mbita);
                    app.setFrom(mfangano);
                    app.setFerry_id(ferry_id);
                    app.setTo_id(to_id);
                    app.setFrom_id(from_id);

                    Log.e("Route", route);
                    Log.e("to_id", to_id);
                    Log.e("from_id", from_id);
                    Log.e("ferry_id", ferry_id);

                } else if (selected.equals("2")) {
                    app.setTo(luanda_kotiono);
                    app.setFrom(Mbita);
                    app.setFerry_id(ferry_id);
                    app.setTo_id(to_id);
                    app.setFrom_id(from_id);

                    Log.e("Route", route);
                    Log.e("to_id", to_id);
                    Log.e("from_id", from_id);
                    Log.e("ferry_id", ferry_id);


                } else if (selected.equals("3")) {
                    app.setTo(Mbita);
                    app.setFrom(luanda_kotiono);
                    app.setFerry_id(ferry_id);
                    app.setTo_id(to_id);
                    app.setFrom_id(from_id);

                    Log.e("Route", route);
                    Log.e("to_id", to_id);
                    Log.e("from_id", from_id);
                    Log.e("ferry_id", ferry_id);


                }

                Toast.makeText(getApplicationContext(), app.getFrom() + "  " + app.getTo(), Toast.LENGTH_SHORT).show();


                dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                dialog.setContentView(R.layout.login_dialog);
                dialog.setTitle("      Account Login");

                TextView selected_route = dialog.findViewById(R.id.selected_route);

                selected_route.setText(route);


                username = dialog.findViewById(R.id.editTextusername);
                password = dialog.findViewById(R.id.editTextpassword);

                Button dialogButton = dialog.findViewById(R.id.btnlogin);


                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        login();

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
            params.put("developer_username", "rWIv7GWzSp");
            params.put("developer_api_key", "831b238c5cd73308520e38bbc6c1774f470a89e96d07a5bb6bcac3b86456f889");
            params.put("action", "userlogin");
            params.put("username", email);
            params.put("password", pass);


            JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, urls.login, new JSONObject(params),
                    response -> {
                        try {

                            if (response.getInt("response_code") == 0) {
                                String first_name = response.getString("first_name");
                                String last_name = response.getString("last_name");

                                String phone_num = response.getString("phone_number");
                                String company_name = response.getString("company_name");
                                String api_key = response.getString("api_key");
                                String company_apis_url = response.getString("company_apis_url");

                                String id_number = response.getString("id_number");


                                app.setLogged_user(first_name + " " + last_name);
                                app.setPhone_num(phone_num);
                                app.setCompany_apis_url(company_apis_url);
                                app.setId_number(id_number);

                                progressDialog.dismiss();

                                loadTickets();


                                Log.d("log in ", first_name);


                                startActivity(new Intent(getApplicationContext(), MainActivity.class));


                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("response_message"), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

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
        params.put("limit", "300");


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
                                Toast.makeText(getApplicationContext(), "References Loaded successfully", Toast.LENGTH_SHORT).show();

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
        progressDialog2.show();


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

                                progressDialog2.dismiss();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String ferry_route = jsonObject1.getString("route");
                                    String feery_id = jsonObject1.getString("id");

                                    String travel_from = jsonObject1.getString("from");
                                    String travel_to = jsonObject1.getString("to");

                                    String from_id = jsonObject1.getString("travel_from_id");
                                    String to_id = jsonObject1.getString("travel_to_id");


                                    Log.e("Ferry Routes: ", ferry_route);

                                    Routes card = new Routes(feery_id, ferry_route, travel_from, travel_to, ferry_route, from_id, to_id);

                                    cardArrayAdapter.add(card);


                                    JSONArray priceArray = jsonObject1.getJSONArray("price");
                                    for (int x = 0; x < priceArray.length(); x++) {

                                        JSONObject jsonObject2 = priceArray.getJSONObject(x);

                                        String cost = jsonObject2.getString("cost");
                                        String price_name = jsonObject2.getString("name");


                                        price_Object = new Price();
                                        price_Object.cost = Float.parseFloat(cost);
                                        price_Object.name = price_name;
                                        price_Object.save();
                                    }


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
        params.put("travel_from", app.getFrom_id());
        params.put("travel_to", app.getTo_id());
        params.put("travel_date", app.getTravel_date());
        params.put("hash", "1FBEAD9B-D9CD-400D-ADF3-F4D0E639CEE0");


        JsonObjectRequest req = new JsonObjectRequest(urls.apiUrl, new JSONObject(params),
                response -> {
                    try {

                        if (response.getInt("response_code") == 0) {


                            JSONArray jsonArray = response.getJSONArray("bus");

                            for (int cnt = 0; cnt < jsonArray.length(); cnt++) {
                                JSONObject jsonObj = null;
                                jsonObj = jsonArray.getJSONObject(cnt);


                                JSONArray jsonArrSubCat = jsonObj.getJSONArray("seats");
                                if (jsonArrSubCat != null && jsonArrSubCat.length() > 0) {

                                    for (int subCnt = 0; subCnt < jsonArrSubCat.length(); subCnt++) {
                                        JSONObject jsonObjSubCategory = jsonArrSubCat.getJSONObject(subCnt);

                                        Log.e("Seater", jsonObjSubCategory.getString("seater"));
                                        Log.e("Seat", jsonObjSubCategory.getString("name"));

                                        String string_seats = jsonObjSubCategory.getString("name").replaceAll("\\s+", "");

                                        list_of_seats = new ArrayList<String>(Arrays.asList(string_seats.split(",")));
                                        Log.e("Seat List", list_of_seats.toString());

                                    }
                                }
                            }


                            //Save first 500 tickets
                            for (int n = 0; n < 500; n++) {

                                Seat seat = new Seat();
                                seat.name = list_of_seats.get(n);
                                seat.save();

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
