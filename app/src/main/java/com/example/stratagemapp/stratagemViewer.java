package com.example.stratagemapp;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class stratagemViewer extends AppCompatActivity implements Serializable {

    String spinnerUnit = "UNIVERSAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stratagem_viewer);

        Search search = (Search)getIntent().getSerializableExtra("Search");

        TextView phase = (TextView) findViewById(R.id.phase);
        switch (search.getPhase()){
            case "0":
                phase.setText("Deployment");
                break;
            case "1":
                phase.setText("Command");
                break;
            case "2":
                phase.setText("Movement");
                break;
            case "3":
                phase.setText("Psychic");
                break;
            case "4":
                phase.setText("Shooting");
                break;
            case "5":
                phase.setText("Assault");
                break;
            case "6":
                phase.setText("Morale");
                break;

        }


        Toast toast = Toast.makeText(stratagemViewer.this, search.getArmy(), Toast.LENGTH_LONG);
        toast.show();

        DatabaseHelper myDbHelper = null;
        myDbHelper = new DatabaseHelper(this);
        myDbHelper.truncateData();

        loadXML(search.getArmy(), search, myDbHelper);


    }

    private void displayDb( Search search, XmlResourceParser parser, DatabaseHelper myDbHelper, String importSource){
        //XmlResourceParser parser = loadXML(search.getArmy());  //=getResources().getXml(R.xml._core_stratagems);
        //DatabaseHelper myDbHelper = null;


        try {
           // myDbHelper = new DatabaseHelper(this);
            //myDbHelper.truncateData();
            //myDbHelper.open();
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("entry")) {
                    String stratName = null, desc = null, phase = null, unit = null, cost = null;
                    while (parser.next() != XmlPullParser.END_TAG) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        name = parser.getName();
                        if (name.equals("name")) {
                            stratName = readText(parser);
                        } else if (name.equals("desc")) {
                            desc = readText(parser);
                        } else if (name.equals("phase")) {
                            phase = readText(parser);
                        } else if (name.equals("unit")) {
                            unit = readText(parser);
                        } else if (name.equals("cost")) {
                            cost = readText(parser);
                        }

                    }

                    myDbHelper.insertData(stratName,desc,phase,unit,cost,importSource);
                }
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (myDbHelper != null) {
                myDbHelper.close();
            }
        }


///////////////////////// Look up for ListView /////////////////////////

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "stratagem_table",   // The table to query
                null,             // The array of columns to return (pass null to get all)
                ("phase ="+search.getPhase()+ " OR phase = 7"),              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        ArrayList<String> unitList = new ArrayList<>();
        while (cursor.moveToNext()){
            if(!unitList.contains(cursor.getString(cursor.getColumnIndex("unit")).toUpperCase())){
                unitList.add(cursor.getString(cursor.getColumnIndex("unit")).toUpperCase());
            }


        }
        //Spinner

        Spinner spinner = (Spinner) findViewById(R.id.unitSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);


                spinnerUnit = unitList.get(position);
//                Toast toast = Toast.makeText(MainActivity.this, Army+" Selected", Toast.LENGTH_LONG);
//                toast.show();
                SQLiteDatabase db2 = myDbHelper.getReadableDatabase();
                Cursor cursor2 = db.query(
                        "stratagem_table",   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        ("(phase ="+search.getPhase()+ " OR phase = 7) AND unit = '" +spinnerUnit+"' "), // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );

                ArrayList<String> stratagemList = new ArrayList<>();
                while (cursor2.moveToNext()){
                    stratagemList.add(cursor2.getString(cursor.getColumnIndex("name")));

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(stratagemViewer.this, android.R.layout.simple_list_item_1, stratagemList);
                ListView listView = (ListView) findViewById(R.id.list_view);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        cursor2.moveToPosition(position);
                        String name = cursor2.getString(cursor2.getColumnIndex("name"));
                        String desc = cursor2.getString(cursor2.getColumnIndex("desc"));
                        int cost = Integer.parseInt(cursor2.getString(cursor2.getColumnIndex("cost")));
                        infoDetail InfoDetail = new infoDetail(name,desc,cost);

                        Intent intent = new Intent(stratagemViewer.this, detailView.class);
                        intent.putExtra("InfoDetail", InfoDetail);
                        intent.putExtra("Search", search);
                        startActivity(intent);

                    }
                });
                listView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, unitList);
        // Specify the layout to use when the list of choices appears
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(unitAdapter);




    }

    private void loadXML(String searchArmy, Search search,  DatabaseHelper myDbHelper){
        XmlResourceParser parser;
        if (searchArmy.equals("Space Wolves")){
            parser = getResources().getXml(R.xml._space_wolves_stratagems);
            displayDb(search, parser, myDbHelper, "_space_wolves_stratagems");
            parser = getResources().getXml(R.xml._space_marine_stratagems);
            displayDb(search, parser,myDbHelper, "_space_marine_stratagems");
            parser = getResources().getXml(R.xml._core_stratagems);
            displayDb(search, parser, myDbHelper, "_core_stratagems");

        }
        else if (searchArmy.equals("Space Marines")){
            parser = getResources().getXml(R.xml._space_marine_stratagems);
            displayDb(search, parser,myDbHelper, "_space_marine_stratagems");
            parser = getResources().getXml(R.xml._core_stratagems);
            displayDb(search, parser, myDbHelper, "_core_stratagems");

        }
        else if (searchArmy.equals("Death Guard")){
            parser = getResources().getXml(R.xml._deathguard_stratagems);
            displayDb(search, parser,myDbHelper, "_deathguard_stratagems");
            parser = getResources().getXml(R.xml._core_stratagems);
            displayDb(search, parser, myDbHelper, "_core_stratagems");

        }
        else if (searchArmy.equals("Death Guard W/ VULGAR")){
            parser = getResources().getXml(R.xml._deathguard_stratagems);
            displayDb(search, parser,myDbHelper, "_deathguard_stratagems");
            parser = getResources().getXml(R.xml._deathguard_stratagems_gellerpox);
            displayDb(search, parser,myDbHelper, "_deathguard_stratagems_gellerpox");
            parser = getResources().getXml(R.xml._core_stratagems);
            displayDb(search, parser, myDbHelper, "_core_stratagems");

        }
        else{
            parser = getResources().getXml(R.xml._core_stratagems);
            displayDb(search, parser, myDbHelper, "_core_stratagems");
        }
        //return parser;
    }



    private String readText(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;

    }




}
