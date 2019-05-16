package com.anchor.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.anchor.model.New_Launches;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface NewLaunchDao {

    @Query("SELECT * FROM new_launches ")
    Maybe<List<New_Launches>> getAllNewlaunch();

    @Insert
    void insertnewlaunch(New_Launches new_launches);

    @Query("DELETE FROM new_launches")
    void deleteAllnew_launches();

}
