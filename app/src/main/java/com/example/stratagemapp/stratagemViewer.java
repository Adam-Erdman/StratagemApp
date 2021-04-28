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
import android.widget.Toast;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class stratagemViewer extends AppCompatActivity implements Serializable {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stratagem_viewer);

        Search search = (Search)getIntent().getSerializableExtra("Search");



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

        ArrayList<String> stratagemList = new ArrayList<>();
        while (cursor.moveToNext()){
            stratagemList.add(cursor.getString(cursor.getColumnIndex("name")));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stratagemList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                int cost = Integer.parseInt(cursor.getString(cursor.getColumnIndex("cost")));
                infoDetail InfoDetail = new infoDetail(name,desc,cost);

                Intent intent = new Intent(stratagemViewer.this, detailView.class);
                intent.putExtra("InfoDetail", InfoDetail);
                startActivity(intent);

            }
        });
        listView.setAdapter(adapter);


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
