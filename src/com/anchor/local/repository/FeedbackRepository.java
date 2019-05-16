package com.anchor.local.repository;

import com.anchor.local.dao.FeedbackDao;
import com.anchor.local.database.FeedbackDatabase;
import com.anchor.model.Feedback;

import java.util.List;

import io.reactivex.Maybe;

public class FeedbackRepository implements IFeedback{
    //here r for Repository naming convention
    private FeedbackDatabase rFeedbackDatabase;
    private FeedbackDao rFeedbackDao;

    public FeedbackRepository(FeedbackDatabase rFeedbackDatabase) {
        this.rFeedbackDatabase = rFeedbackDatabase;
        this.rFeedbackDao = rFeedbackDatabase.feedbackDao();
    }


    @Override
    public Maybe<List<Feedback>> getAllFeedbacks() {
        return rFeedbackDao.getAllFeedbacks();
    }

    @Override
    public void insertFeedback(Feedback feedBack) {
        rFeedbackDao.insertFeedback(feedBack);
    }

    @Override
    public void deleteAllFeedbacks() { rFeedbackDao.deleteAllFeedbacks(); }
/*
    @Override
    public void deleteAllFeedbacks() { rFeedbackDao.deleteAllFeedbacks(); };*/
}
