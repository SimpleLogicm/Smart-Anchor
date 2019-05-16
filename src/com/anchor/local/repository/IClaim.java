package com.anchor.local.repository;

import com.anchor.model.Claim;

import java.util.List;

import io.reactivex.Maybe;

public interface IClaim {

    Maybe<List<Claim>> getAllClaims();

    void insertClaim(Claim claim);

    void deleteAllClaims();
}
