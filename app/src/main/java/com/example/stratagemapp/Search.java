package com.example.stratagemapp;

import java.io.Serializable;

public class Search implements Serializable {
    private  static String army;
    private  String unit;
    private  String phase;
    private  int remainingCP;

    Search() {
        army = "None";
        unit = "None";
        phase = "-1";
        remainingCP = 0;
    }

    Search(String army, String unit, String phase, int remainingCP) {
        this.army = army;
        this.unit = unit;
        this.phase = phase;
        this.remainingCP = remainingCP;
    }

    //accessors
    public String getArmy(){return army;}
    public String getUnit(){return unit;}
    public String getPhase(){return phase;}
    public int getRemainingCp(){return remainingCP;}

    //mutators
    public void setArmy(String army) {this.army = army;}
    public void setUnit(String unit) {this.unit = unit;}
    public void setPhase(String phase) {this.phase = phase;}
    public void setRemainingCP(int remainingCP) {this.remainingCP = remainingCP;}

}
