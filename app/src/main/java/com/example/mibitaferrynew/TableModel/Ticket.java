package com.example.mibitaferrynew.TableModel;

import android.graphics.ColorSpace;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Ticket")
public class Ticket extends Model {

    @Column(name = "Ticket_type")
    public String ticket_type;

    @Column(name = "date")
    public String date;

    @Column(name = "cost")
    public int  cost;

    @Column(name = "reference")
    public String ref_no;

    @Column(name = "seat_no")
    public String seat_no;

    public Ticket() {
        super();
    }


}
