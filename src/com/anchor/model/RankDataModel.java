package com.anchor.model;

public class RankDataModel {

    String Date;
    String Score;
    String Rank;


    public RankDataModel(String Date, String Score, String Rank) {
        this.Date = Date;
        this.Score = Score;
        this.Rank = Rank;

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }
}
