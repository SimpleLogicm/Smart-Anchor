package com.anchor.local.repository;

import com.anchor.local.dao.ComplaintDao;
import com.anchor.local.database.ComplaintDatabase;
import com.anchor.model.Complaint;

import java.util.List;

import io.reactivex.Maybe;

public class ComplaintRepository implements IComplaint{
    //here r for Repository naming convention
    private ComplaintDatabase rComplaintDatabase;
    private ComplaintDao rComplaintDao;

    public ComplaintRepository(ComplaintDatabase rComplaintDatabase) {
        this.rComplaintDatabase = rComplaintDatabase;
        this.rComplaintDao = rComplaintDatabase.complaintDao();
    }


    @Override
    public Maybe<List<Complaint>> getAllComplaints() {
        return rComplaintDao.getAllComplaints();
    }

    @Override
    public void insertComplaint(Complaint complaint) {
        rComplaintDao.insertComplaint(complaint);
    }

    @Override
    public void deleteAllComplaints() { rComplaintDao.deleteAllComplaints(); }

}
