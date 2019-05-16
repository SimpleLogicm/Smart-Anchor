package com.anchor.local.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.anchor.model.Claim;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface ClaimDao {

    @Query("SELECT * FROM claims_table ORDER BY time_stamp DESC")
    Maybe<List<Claim>> getAllClaims();

    @Insert
    void insertClaim(Claim claim);

    @Query("DELETE FROM claims_table")
    void deleteAllClaims();

}
