package com.example.mibitaferrynew.TableModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Price")
public class Price extends Model {
    @Column(name = "name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String name;

    @Column(name = "cost")
    public float cost;

    public Price() {
        super();
    }

}
