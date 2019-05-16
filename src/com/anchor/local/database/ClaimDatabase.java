package com.anchor.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.anchor.local.dao.ClaimDao;
import com.anchor.model.Claim;


@Database(entities = {Claim.class}, version = 1)
public abstract class ClaimDatabase extends RoomDatabase {

    public abstract ClaimDao claimDao();
    public static ClaimDatabase mInstance;
    public static final String  CLAIMS_DATABASE_NAME = "claims_database";

    public static ClaimDatabase getInstance(Context mContext){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(mContext,ClaimDatabase.class,CLAIMS_DATABASE_NAME)
                            .build();
        }
        return mInstance;
    }
}
