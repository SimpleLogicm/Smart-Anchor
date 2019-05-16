package com.anchor.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.anchor.local.dao.ComplaintDao;
import com.anchor.model.Complaint;

@Database(entities = {Complaint.class},version = 1)
public abstract class ComplaintDatabase extends RoomDatabase {

    public abstract ComplaintDao complaintDao();
    public static ComplaintDatabase mInstance;
    public static final String COMPLAINT_DATABASE_NAME = "complaint_database";

    public static ComplaintDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context,ComplaintDatabase.class,COMPLAINT_DATABASE_NAME)
                    .build();
        }

        return mInstance;
    }
}
