package com.example.mibitaferrynew.Model;

public class Manifests {


    String types;
    String number;
    String amount;

    public Manifests() {
    }

    public Manifests(String types, String number, String amount) {
        this.types = types;
        this.number = number;
        this.amount = amount;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
