package com.example.mibitaferrynew.TableModel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "reference")
public class RefferenceNumber extends Model {
    //The table consist only one field name

    @Column(name = "guid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String guid;
    @Column(name = "name")
    public String name;


    public RefferenceNumber() {
        super();
    }
    //find items by guid
    public static RefferenceNumber findByGUID(String guid){
        return new Select().from(RefferenceNumber.class).where("guid = ?", guid).executeSingle();
    }
    //retrieve all items
    public static List<RefferenceNumber> getAllFeedItems() {
        return new Select().from(RefferenceNumber.class).orderBy("guid DESC").execute();
    }

}

