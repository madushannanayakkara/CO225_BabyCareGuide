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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.text.DateFormat;
import java.util.Date;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Vaccination extends AppCompatActivity  {

    private CheckBox[] cb_arr = new CheckBox[9];
//    private Button calcButton;
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

        cb_arr[0] = findViewById(R.id.cb1);
        cb_arr[1] = findViewById(R.id.cb2);
        cb_arr[2] = findViewById(R.id.cb3);
        cb_arr[3] = findViewById(R.id.cb4);
        cb_arr[4] = findViewById(R.id.cb5);
        cb_arr[5] = findViewById(R.id.cb6);
        cb_arr[6] = findViewById(R.id.cb7);
        cb_arr[7] = findViewById(R.id.cb8);
        cb_arr[8] = findViewById(R.id.cb9);

//        calcButton = findViewById(R.id.calcButton);
        text = findViewById(R.id.date);

//        int id = databaseH.readLastSavedID();
//        String d1 = databaseH.getDate(id);

        /*String d1 = "2017.01.02";*/

        int id = databaseH.readLastSavedID();
        if (id > 0){
            for (int j=0; j<databaseH.readLastVaccination(id); j++) {
                cb_arr[j].setChecked(true);
            }
        }

        for (int i=0; i<9; i++){
            cb_arr[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        setNextVaccDate();
                    } else {
                        text.setText("");
                        setNextVaccDate();
                    }
                }
            });
        }

        setNextVaccDate();

//        calcButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setNextVaccDate(id, d1);
//
//            }
//        });

    }

    private void setNextVaccDate(){

        int id = databaseH.readLastSavedID();
        String d1 = databaseH.getDate(id);

        if(cb_arr[8].isChecked()){

            vac = findViewById(R.id.r102);

            String strDate = setDate(d1, 2, 60);
            text.setText(strDate);

            vaccined = count[0];
            count[0]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,9);
        }
        else if(cb_arr[7].isChecked()){

            vac = findViewById(R.id.r92);

            String strDate = setDate(d1, 2, 36);
            text.setText(strDate);

            vaccined = count[1];
            count[1]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,8);
        }
        else if(cb_arr[6].isChecked()){

            vac = findViewById(R.id.r82);

            String strDate = setDate(d1, 2, 18);
            text.setText(strDate);

            vaccined = count[2];
            count[2]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,7);
        }
        else if(cb_arr[5].isChecked()){

            vac = findViewById(R.id.r72);

            String strDate = setDate(d1, 2, 12);
            text.setText(strDate);

            vaccined = count[3];
            count[3]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,6);
        }
        else if(cb_arr[4].isChecked()){

            vac = findViewById(R.id.r62);

            String strDate = setDate(d1, 2, 9);
            text.setText(strDate);

            vaccined = count[4];
            count[4]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,5);
        }
        else if(cb_arr[3].isChecked()){

            vac = findViewById(R.id.r52);

            String strDate = setDate(d1, 2, 6);
            text.setText(strDate);

            vaccined = count[5];
            count[5]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,4);
        }
        else if(cb_arr[2].isChecked()){

            vac = findViewById(R.id.r42);

            String strDate = setDate(d1, 2, 4);
            text.setText(strDate);

            vaccined = count[6];
            count[6]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,3);
        }
        else if(cb_arr[1].isChecked()){

            vac = findViewById(R.id.r32);

            String strDate = setDate(d1, 2, 2);
            text.setText(strDate);

            vaccined = count[7];
            count[7]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,2);
        }
        else if(cb_arr[0].isChecked()){

            vac = findViewById(R.id.r22);

            String strDate = setDate(d1, 1, 14);
            //calcButton.setText(strDate);
            text.setText(strDate);

            vaccined = count[8];
            count[8]++;

            databaseH.addVaccineData(vac.getText().toString().trim(),"done",vaccined,id,1);

        }
        else {
            Toast.makeText(Vaccination.this, "It seems that your baby is not yet vaccinated", Toast.LENGTH_LONG).show();
        }
    }

    /*public String setDate(String date, int i, int noToAdd){

        //To add months int i=2
        //TO add weeks int i=1
        String updated_date;

        String before_update = date;

        return updated_date;


    }*/

    public String setDate(String date, int i, int noToAdd){

        String updated = "";
        String oldDate;
        oldDate = date;

        /*String[] parts = date.split(".");
        String year = parts[0];
        String month = parts[1];
        String day = parts[2];

        LocalDate datecal = LocalDate.parse(date);*/

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

            c.add(Calendar.MONTH, noToAdd);
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

