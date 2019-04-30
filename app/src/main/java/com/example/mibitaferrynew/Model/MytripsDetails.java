package com.example.mibitaferrynew.Model;

public class MytripsDetails {

    String travel_from,
            travel_to,
            travel_date,
            reference_number,
            amount;


    public MytripsDetails(String travel_from, String travel_to, String travel_date, String reference_number, String amount) {
        this.travel_from = travel_from;
        this.travel_to = travel_to;
        this.travel_date = travel_date;
        this.reference_number = reference_number;
        this.amount = amount;
    }





    public String getTravel_from() {
        return travel_from;
    }

    public void setTravel_from(String travel_from) {
        this.travel_from = travel_from;
    }

    public String getTravel_to() {
        return travel_to;
    }

    public void setTravel_to(String travel_to) {
        this.travel_to = travel_to;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
