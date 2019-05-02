package com.example.mibitaferrynew;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MyApplication extends Application {



    String adult_price;
    String to, from;
    String username;
    String pasword;
    String api_key;
    String Hash_key;

    String Travel_date;


    private String name;
    private String Phone_num,car_name,remaining_seats;
    int no_passenges;
    private String agency_phone;
    private String logged_user;
    String city_id;

    String otherprice;
    String Othername;
    String Route;

    String to_id,from_id;


    @Override
    public void onCreate() {
        super.onCreate();

        //Initializing Active Android
        ActiveAndroid.initialize(this);
    }


    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getOthername() {
        return Othername;
    }


    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public void setOthername(String othername) {
        Othername = othername;
    }

    public String getOtherprice() {
        return otherprice;
    }

    public void setOtherprice(String otherprice) {
        this.otherprice = otherprice;
    }



    public String getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(String adult_price) {
        this.adult_price = adult_price;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getHash_key() {
        return Hash_key;
    }

    public void setHash_key(String hash_key) {
        Hash_key = hash_key;
    }

    public String getTravel_date() {
        return Travel_date;
    }

    public void setTravel_date(String travel_date) {
        Travel_date = travel_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getRemaining_seats() {
        return remaining_seats;
    }

    public void setRemaining_seats(String remaining_seats) {
        this.remaining_seats = remaining_seats;
    }


    public int getNo_passenges() {
        return no_passenges;
    }

    public void setNo_passenges(int no_passenges) {
        this.no_passenges = no_passenges;
    }


    public String getAgency_phone() {
        return agency_phone;
    }

    public void setAgency_phone(String agency_phone) {
        this.agency_phone = agency_phone;
    }

    public String getLogged_user() {
        return logged_user;
    }

    public void setLogged_user(String logged_user) {
        this.logged_user = logged_user;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getPhone_num() {
        return Phone_num;
    }

    public void setPhone_num(String phone_num) {
        Phone_num = phone_num;
    }
}
