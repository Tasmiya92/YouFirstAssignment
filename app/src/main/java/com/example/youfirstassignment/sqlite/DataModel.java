package com.example.youfirstassignment.sqlite;

public class DataModel {
    private int id;
    private String date;
    private String pointsEarned;
    private String pointsReedemed;

    public DataModel(int id, String date, String pointsEarned, String pointsReedemed) {
        this.id = id;
        this.date = date;
        this.pointsEarned = pointsEarned;
        this.pointsReedemed = pointsReedemed;
    }

    public DataModel(String date, String pointsEarned, String pointsReedemed) {
        this.date = date;
        this.pointsEarned = pointsEarned;
        this.pointsReedemed = pointsReedemed;
    }

    public DataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(String pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public String getPointsReedemed() {
        return pointsReedemed;
    }

    public void setPointsReedemed(String pointsReedemed) {
        this.pointsReedemed = pointsReedemed;
    }
}
