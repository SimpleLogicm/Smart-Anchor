package com.anchor.local.repository;

import com.anchor.local.dao.NewLaunchDao;
import com.anchor.local.database.NewLaunchDatabase;
import com.anchor.model.New_Launches;

import java.util.List;
import io.reactivex.Maybe;

public class NewLaunchRepository  {
    private NewLaunchDatabase newLaunchDatabase;
    private NewLaunchDao newLaunchDao;

    public NewLaunchRepository(NewLaunchDatabase newLaunchDatabase) {
        this.newLaunchDatabase = newLaunchDatabase;
        this.newLaunchDao = newLaunchDatabase.newLaunchDao();
    }


    public Maybe<List<New_Launches>> getAllNewlaunch() {
        return newLaunchDao.getAllNewlaunch();
    }


    public void insertNewLaunch(New_Launches new_launches) {
        newLaunchDao.insertnewlaunch(new_launches);
    }


    public void deleteAllNewLaunches() {
        newLaunchDao.deleteAllnew_launches();
    }
}