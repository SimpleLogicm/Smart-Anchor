package com.anchor.local.repository;

import com.anchor.model.Feedback;

import java.util.List;

import io.reactivex.Maybe;

public interface IFeedback {

    Maybe<List<Feedback>> getAllFeedbacks();

    void insertFeedback(Feedback feedBack);

    void deleteAllFeedbacks();
}
