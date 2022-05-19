package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.text.DateFormat;
import java.util.Date;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Vaccination extends AppCompatActivity  {

    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    private Button calcButton;
    private TextView text, vac;
    private DatabaseH databaseH;
    private int[] count = {0,0,0,0,0,0,0,0,0};
    private static int vaccined = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setSelectedItemId(R.id.nav_vaccination);

        //context = this;
        databaseH = new DatabaseH(Vaccination.this);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_details:
                        startActivity(new Intent(getApplicationContext(), Details.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_vaccination:
                        return true;
                    case R.id.nav_bmi:
                        startActivity(new Intent(getApplicationContext(), bmi.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        //Variables used to calculate the next vaccination date
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        cb8 = findViewById(R.id.cb8);
        cb9 = findViewById(R.id.cb9);
        calcButton = findViewById(R.id.calcButton);
        text = findViewById(R.id.date);

        /*int id = databaseH.readLastSavedID();
        String d1 = databaseH.getDate(id);*/

        //Variables to add data to the database


        String d1 = "2017-01-29";


        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cb9.isChecked()){

                    vac = findViewById(R.id.r102);

                    String strDate = setDate(d1, 2, 60);
                    text.setText(strDate);

                    vaccined = count[0];
                    count[0]++;
                }
                else if(cb8.isChecked()){

                    vac = findViewById(R.id.r92);

                    String strDate = setDate(d1, 2, 36);
                    text.setText(strDate);

                    vaccined = count[1];
                    count[1]++;
                }
                else if(cb7.isChecked()){

                    vac = findViewById(R.id.r82);

                    String strDate = setDate(d1, 2, 18);
                    text.setText(strDate);

                    vaccined = count[2];
                    count[2]++;
                }
                else if(cb6.isChecked()){

                    vac = findViewById(R.id.r72);

                    String strDate = setDate(d1, 2, 12);
                    text.setText(strDate);

                    vaccined = count[3];
                    count[3]++;
                }
                else if(cb5.isChecked()){

                    vac = findViewById(R.id.r62);

                    String strDate = setDate(d1, 2, 9);
                    text.setText(strDate);

                    vaccined = count[4];
                    count[4]++;
                }
                else if(cb4.isChecked()){

                    vac = findViewById(R.id.r52);

                    String strDate = setDate(d1, 2, 6);
                    text.setText(strDate);

                    vaccined = count[5];
                    count[5]++;
                }
                else if(cb3.isChecked()){

                    vac = findViewById(R.id.r42);

                    String strDate = setDate(d1, 2, 4);
                    text.setText(strDate);

                    vaccined = count[6];
                    count[6]++;
                }
                else if(cb2.isChecked()){

                    vac = findViewById(R.id.r32);

                    String strDate = setDate(d1, 2, 2);
                    text.setText(strDate);

                    vaccined = count[7];
                    count[7]++;
                }
                else if(cb1.isChecked()){

                    vac = findViewById(R.id.r22);

                    String strDate = setDate(d1, 1, 14);
                    text.setText(strDate);

                    vaccined = count[8];
                    count[8]++;

                }

                databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined);

            }
        });


    }

    public String setDate(String date, int i, int noToAdd){

        String updated = "";
        String oldDate;
        oldDate = date;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try{
            //Setting the date to the given date
            c.setTime(Objects.requireNonNull(sdf.parse(oldDate)));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        if(i==1){
            c.add(Calendar.DAY_OF_MONTH, noToAdd);
            updated = sdf.format(c.getTime());
        }
        else {
            if(i==2){
                c.add(Calendar.MONTH, noToAdd);
            }
            else{
                updated = "";
            }
            updated = sdf.format(c.getTime());
        }

        //Date after adding the days to the given date
        return updated;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
    }

}

