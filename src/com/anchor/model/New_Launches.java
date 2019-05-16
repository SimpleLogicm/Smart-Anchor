package com.anchor.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "new_launches")
public class New_Launches implements Serializable{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "file_path")
    private String file_path;

    @ColumnInfo(name = "file_type")
    private String file_type;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "business_unit")
    private String business_unit;

    @ColumnInfo(name = "doc_type")
    private String doc_type;


    public New_Launches(String name, String file_path, String file_type, String date, String business_unit, String doc_type) {
        this.name = name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.date = date;
        this.business_unit = business_unit;
        this.doc_type = doc_type;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getBusiness_unit() {
        return business_unit;
    }

    public void setBusiness_unit(String business_unit) {
        this.business_unit = business_unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
