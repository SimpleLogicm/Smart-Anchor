package com.anchor.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.anchor.model.Complaint;

import java.util.List;

import io.reactivex.Maybe;


@Dao
public interface ComplaintDao {


    @Query("SELECT * FROM complaint_table ORDER BY time_stamp DESC")
    Maybe<List<Complaint>> getAllComplaints();

    @Insert
    void insertComplaint(Complaint Complaint);

    @Query("DELETE FROM complaint_table")
    void deleteAllComplaints();

}
