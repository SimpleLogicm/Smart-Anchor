package com.anchor.local.repository;

import com.anchor.model.Complaint;

import java.util.List;

import io.reactivex.Maybe;

public interface IComplaint {

    Maybe<List<Complaint>> getAllComplaints();

    void insertComplaint(Complaint complaint);

    void deleteAllComplaints();
}
