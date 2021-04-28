package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class phasePage extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase_page);


        Search search = (Search)getIntent().getSerializableExtra("Search");

        TextView commandPts = (TextView) findViewById(R.id.commandPts);
        commandPts.setText("Remaining CP: " + search.getRemainingCp());


        //Deployment Phase Button
        Button deploymentPhase = (Button) findViewById(R.id.deploymentPhase);
        deploymentPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("0");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Command Phase Button
        Button commandPhase = (Button) findViewById(R.id.commandPhase);
        commandPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("1");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Movement Phase Button
        Button movementPhase = (Button) findViewById(R.id.movementPhase);
        movementPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("2");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Psychic Phase Button
        Button psychicPhase = (Button) findViewById(R.id.psychicPhase);
        psychicPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("3");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Shooting Phase Button
        Button shootingPhase = (Button) findViewById(R.id.shootingPhase);
        shootingPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("4");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Assault Phase Button
        Button assaultPhase = (Button) findViewById(R.id.assaultPhase);
        assaultPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("5");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });

        //Morale Phase Button
        Button moralePhase = (Button) findViewById(R.id.moralePhase);
        moralePhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setPhase("6");

                Intent intent = new Intent(phasePage.this, stratagemViewer.class);
                intent.putExtra("Search", search);
                startActivity(intent);
            }
        });




    }
}