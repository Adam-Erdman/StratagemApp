package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Serializable {

    String Army;
    int CommandPoints = -1;
    Search search = new Search();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //System.out.println("Launched");

        //Spinner

        Spinner spinner = (Spinner) findViewById(R.id.armySpinner);
        spinner.setOnItemSelectedListener(this);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.armies_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //edit text
        EditText commandPointEntry = (EditText) findViewById(R.id.commandPointEntry);


        //Submit Button
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commandPointEntry.getText().toString().trim().length()>0){
                    CommandPoints = Integer.parseInt(commandPointEntry.getText().toString());
                    //Search search = new Search(Army,"none" ,-1,CommandPoints);
                    search.setArmy(Army);
                    search.setRemainingCP(CommandPoints);

//                    Toast toast = Toast.makeText(MainActivity.this, search.getArmy()+search.getPhase()+search.getRemainingCp(), Toast.LENGTH_LONG);
//                    toast.show();

                    Intent intent = new Intent(MainActivity.this, phasePage.class);
                    intent.putExtra("Search", search);
                    startActivity(intent);
                }

                else {
                    Toast toast = Toast.makeText(MainActivity.this, "Enter Command Points & Select Army", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);

        //Resources res = getResources();
        String[] armyList = getResources().getStringArray(R.array.armies_array);
        Army = armyList[pos];
        Toast toast = Toast.makeText(MainActivity.this, Army+" Selected", Toast.LENGTH_LONG);
        toast.show();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }




}

