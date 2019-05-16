package com.anchor.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.anchor.model.Feedback;

import java.util.List;

import io.reactivex.Maybe;


@Dao
public interface FeedbackDao {


    @Query("SELECT * FROM feed_back_table ORDER BY time_stamp DESC")
    Maybe<List<Feedback>> getAllFeedbacks();

    @Insert
    void insertFeedback(Feedback feedBack);

    @Query("DELETE FROM feed_back_table")
    void deleteAllFeedbacks();

}
