package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class detailView extends AppCompatActivity implements Serializable {
    int remainingCP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        infoDetail InfoDetail = (infoDetail) getIntent().getSerializableExtra("InfoDetail");
        Search search = (Search)getIntent().getSerializableExtra("Search");


        TextView detailViewName = (TextView) findViewById(R.id.detailViewName);
        detailViewName.setText(InfoDetail.getName());
        TextView detailViewDesc = (TextView) findViewById(R.id.detailViewDesc);
        detailViewDesc.setText(InfoDetail.getDesc());
        detailViewDesc.setMovementMethod(new ScrollingMovementMethod());
        TextView detailViewCost = (TextView) findViewById(R.id.detailViewCost);
        detailViewCost.setText("Command Point Cost: " +String.valueOf(InfoDetail.getCost()));

        //Submit Button
        Button use = (Button) findViewById(R.id.spendCP);
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    remainingCP = search.getRemainingCp();
                    if (remainingCP - InfoDetail.getCost() >= 0){
                    remainingCP -= InfoDetail.getCost();
                    search.setRemainingCP(remainingCP);


                    Intent intent = new Intent(detailView.this, phasePage.class);
                    intent.putExtra("Search", search);
                    startActivity(intent);}
                    else{
                        Toast toast = Toast.makeText(detailView.this, "Not Enough Command Points", Toast.LENGTH_LONG);
                        toast.show();
                    }




            }
        });


    }
}