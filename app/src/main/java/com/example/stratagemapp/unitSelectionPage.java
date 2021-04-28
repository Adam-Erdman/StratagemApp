package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

public class unitSelectionPage extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_selection_page);

        Search search = (Search)getIntent().getSerializableExtra("Search");
        Toast toast = Toast.makeText(unitSelectionPage.this, search.getArmy()+search.getPhase()+search.getRemainingCp(), Toast.LENGTH_LONG);
        toast.show();
    }
}