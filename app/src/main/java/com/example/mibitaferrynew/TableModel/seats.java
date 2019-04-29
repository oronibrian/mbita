package com.example.mibitaferrynew.TableModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Seats")

public class seats extends Model {

    @Column(name = "seater")
    public String seater;

    @Column(name = "name")
    public String name;

    public seats() {
        super();
    }
}
