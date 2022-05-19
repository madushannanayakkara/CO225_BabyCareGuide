package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class Details extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    //======================================================================
    EditText firstname, lastname, birthdate, birthweight, currentweight, currentheight;
    Button btnB, btnG, btnupdate;

    DatabaseH myDB;
    ArrayList<String> baby_id, first_name, last_name, birth_date, current_weight, current_height;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setSelectedItemId(R.id.nav_details);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_details:
                        return true;
                    case R.id.nav_vaccination:
                        startActivity(new Intent(getApplicationContext(), Vaccination.class));
                        overridePendingTransition(0, 0);
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


        // ==================================================================================================
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        birthdate = findViewById(R.id.birthdate);
        birthweight = findViewById(R.id.birthweight);
        currentweight = findViewById(R.id.currentweight);
        currentheight = findViewById(R.id.currentheight);

        btnB = findViewById(R.id.btnB);
        btnG = findViewById(R.id.btnG);
        btnupdate = findViewById(R.id.btnupdate);

        myDB = new DatabaseH(this);

        // Array Lists
        baby_id = new ArrayList<>();
        first_name = new ArrayList<>();
        last_name = new ArrayList<>();
        birth_date = new ArrayList<>();
        current_weight = new ArrayList<>();
        current_height = new ArrayList<>();

        storeDataInArrays();

        // loading and display current baby details

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.readLastBabyData();
                if (res.getCount()==0){
                    Toast.makeText(Details.this, "No entry exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                while (res.moveToNext()){
                    firstname.setText(res.getString(1));
                    lastname.setText(res.getString(2));
                    birthdate.setText(res.getString(3));
//                    birthweight.setText(res.getString(4));
                    currentweight.setText(res.getString(4));
                    currentheight.setText(res.getString(5));

                }

                // for checking
//                int IDwant =  myDB.readLastSavedID();
//                firstname.setText(String.valueOf(IDwant));
            }
        });

        // updating details

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnameTXT = firstname.getText().toString();
                String lastnameTXT = lastname.getText().toString();
                String birthdateTXT = birthdate.getText().toString();

                int currentweightTXT = Integer.parseInt(currentweight.getText().toString());
                int currentheightTXT = Integer.parseInt(currentheight.getText().toString());

                Boolean checkupdatedata = myDB.updateuserdata(firstnameTXT, lastnameTXT, birthdateTXT, currentweightTXT, currentheightTXT);

                if (checkupdatedata == true){
                    Toast.makeText(Details.this, "data entry updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Details.this, "data entry not updated", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    // to store in array  ------------------------>>>> later
    void storeDataInArrays(){
        Cursor cursor = myDB.readBabyData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else{
            // taking each column
            while (cursor.moveToNext()){
                baby_id.add(cursor.getString(0));
                first_name.add(cursor.getString(1));
                last_name.add(cursor.getString(2));
                birth_date.add(cursor.getString(3));
                current_weight.add(cursor.getString(4));
                current_height.add(cursor.getString(5));
            }
        }
    }

    

    //
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
    }

}