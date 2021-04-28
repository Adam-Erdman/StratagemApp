package com.example.stratagemapp;

import java.io.Serializable;

public class infoDetail implements Serializable {
    private  String name;
    private  String desc;
    private  int cost;



    infoDetail() {
        name = "None";
        desc = "None";
        cost = -1;

    }

    infoDetail(String name, String desc, int cost) {
        this.name = name;
        this.desc = desc;
        this.cost = cost;

    }

    //accessors
    public String getName(){return name;}
    public String getDesc(){return desc;}
    public int getCost(){return cost;}


    //mutators
    public void setName(String name) {this.name = name;}
    public void setDesc(String desc) {this.desc = desc;}
    public void setCost(int cost) {this.cost = cost;}

}
