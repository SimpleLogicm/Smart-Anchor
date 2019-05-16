package com.anchor.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.anchor.local.dao.FeedbackDao;
import com.anchor.model.Feedback;

@Database(entities = {Feedback.class},version = 1)
public abstract class FeedbackDatabase extends RoomDatabase {

    public abstract FeedbackDao feedbackDao();
    public static FeedbackDatabase mInstance;
    public static final String FEEDBACK_DATABASE_NAME = "feedback_database";

    public static FeedbackDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context,FeedbackDatabase.class,FEEDBACK_DATABASE_NAME)
                    .build();
        }

        return mInstance;
    }
}
