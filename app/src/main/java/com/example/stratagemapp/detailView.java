package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class detailView extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        infoDetail InfoDetail = (infoDetail) getIntent().getSerializableExtra("InfoDetail");

//        Toast toast = Toast.makeText(detailView.this, InfoDetail.getName()+InfoDetail.getDesc()+InfoDetail.getCost(), Toast.LENGTH_LONG);
//        toast.show();

        TextView detailViewName = (TextView) findViewById(R.id.detailViewName);
        detailViewName.setText(InfoDetail.getName());
        TextView detailViewDesc = (TextView) findViewById(R.id.detailViewDesc);
        detailViewDesc.setText(InfoDetail.getDesc());
        detailViewDesc.setMovementMethod(new ScrollingMovementMethod());
        TextView detailViewCost = (TextView) findViewById(R.id.detailViewCost);
        detailViewCost.setText("Command Point Cost: " +String.valueOf(InfoDetail.getCost()));


    }
}