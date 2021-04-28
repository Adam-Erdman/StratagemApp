package com.example.stratagemapp;

import android.content.res.XmlResourceParser;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class parserSetup extends AppCompatActivity {

    public static ArrayList<String> gatherArmyList(String army){
        //enter

        ArrayList<String> arrayLists = new ArrayList<>();
        if (army == "Space Wolves"){
            arrayLists.add("R.xml._space_wolves_stratagems");
            arrayLists.add("R.xml._core_stratagems");
            arrayLists.add("R.xml._space_marine_stratagems");

     }
        if (army == "R.xml.Space Marines"){
            arrayLists.add("R.xml._core_stratagems");
            arrayLists.add("R.xml._space_marine_stratagems");
        }
        return arrayLists;
    }
}
