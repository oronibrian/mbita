package com.example.mibitaferrynew.Model;

public class Routes {

    String name,ferry_id;
    String ferry_route;
    String to, from;
    String to_id, from_id;


    public Routes(String ferry_id,String name, String ferry_route, String to, String from, String from_id, String to_id) {
        this.ferry_id=ferry_id;
        this.name = name;
        this.ferry_route = ferry_route;
        this.to = to;
        this.from = from;
        this.from_id = from_id;
        this.to_id = to_id;

    }

    public String getFerry_id() {
        return ferry_id;
    }

    public void setFerry_id(String ferry_id) {
        this.ferry_id = ferry_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFerry_route() {
        return ferry_route;
    }

    public void setFerry_route(String ferry_route) {
        this.ferry_route = ferry_route;
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
}
