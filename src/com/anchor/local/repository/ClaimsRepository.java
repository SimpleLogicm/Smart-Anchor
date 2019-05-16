package com.anchor.local.repository;

import com.anchor.local.dao.ClaimDao;
import com.anchor.local.database.ClaimDatabase;
import com.anchor.model.Claim;

import java.util.List;

import io.reactivex.Maybe;

public class ClaimsRepository implements IClaim {
    private ClaimDatabase rClaimDatabase;
    private ClaimDao rClaimsDao;

    public ClaimsRepository(ClaimDatabase rClaimDatabase) {
        this.rClaimDatabase = rClaimDatabase;
        this.rClaimsDao = rClaimDatabase.claimDao();
    }

    @Override
    public Maybe<List<Claim>> getAllClaims() {
        return rClaimsDao.getAllClaims();
    }

    @Override
    public void insertClaim(Claim claim) {
        rClaimsDao.insertClaim(claim);
    }

    @Override
    public void deleteAllClaims() {
        rClaimsDao.deleteAllClaims();
    }
}
