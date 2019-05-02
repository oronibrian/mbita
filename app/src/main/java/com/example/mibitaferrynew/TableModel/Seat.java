package com.example.mibitaferrynew.TableModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Seats")

public class Seat extends Model {


    @Column(name = "name")
    public String name;

    public Seat() {
        super();
    }
}
