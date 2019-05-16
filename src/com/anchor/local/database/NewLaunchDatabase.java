package com.anchor.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.anchor.local.dao.NewLaunchDao;
import com.anchor.model.New_Launches;

@Database(entities = {New_Launches.class},version = 1)

public abstract class NewLaunchDatabase extends RoomDatabase {

    public abstract NewLaunchDao newLaunchDao();
    public static NewLaunchDatabase mInstance;
    public static final String NewLaunch_Database_NAME = "newlauch_database";

    public static NewLaunchDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context, NewLaunchDatabase.class,NewLaunch_Database_NAME)
                    .build();
        }

        return mInstance;
    }
}
