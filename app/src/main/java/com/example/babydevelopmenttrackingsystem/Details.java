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


    EditText firstname, lastname, birthdate, gender, birthweight, currentweight, currentheight,
            firstnameG, lastnameG, birthdateG, NIC, address, noofchildren;
    Button btnB, btnG, btnupdate;

    DatabaseH myDB;


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


        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        birthweight = findViewById(R.id.birthweight);
        currentweight = findViewById(R.id.currentweight);
        currentheight = findViewById(R.id.currentheight);

        firstnameG = findViewById(R.id.firstnameG);
        lastnameG = findViewById(R.id.lastnameG);
        birthdateG = findViewById(R.id.birthdateG);
        NIC = findViewById(R.id.NIC);
        address = findViewById(R.id.address);
        noofchildren = findViewById(R.id.noofchildren);

        btnB = findViewById(R.id.btnB);
        btnG = findViewById(R.id.btnG);
        btnupdate = findViewById(R.id.btnupdate);

        myDB = new DatabaseH(this);


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
                    gender.setText(res.getString(4));
                    currentweight.setText(res.getString(5));
                    currentheight.setText(res.getString(6));
                    birthweight.setText(res.getString(7));
                }

            }
        });

        // loading and display current baby's guardian details

        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.readLastBabyData();
                if (res.getCount()==0){
                    Toast.makeText(Details.this, "No entry exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                while (res.moveToNext()){
                    firstnameG.setText(res.getString(8));
                    lastnameG.setText(res.getString(9));
                    birthdateG.setText(res.getString(10));
                    NIC.setText(res.getString(11));
                    address.setText(res.getString(12));
                    noofchildren.setText(res.getString(13));

                }

            }
        });


        // updating details

        // to update all fields required
        btnupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (firstname.getText().toString().equals("") || lastname.getText().toString().equals("")
                        || birthdate.getText().toString().equals("") || currentweight.getText().toString().equals("")
                        || currentheight.getText().toString().equals("") || birthweight.getText().toString().equals("") ||
                        firstnameG.getText().toString().equals("") || lastnameG.getText().toString().equals("")
                        || birthdateG.getText().toString().equals("") || NIC.getText().toString().equals("")
                        || address.getText().toString().equals("") || noofchildren.getText().toString().equals("")) {

                    Toast.makeText(Details.this,"Empty fields not allowed!",
                            Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(Details.this,"Proceed..",
                            Toast.LENGTH_SHORT).show();

                    // baby
                    String firstnameTXT = firstname.getText().toString();
                    String lastnameTXT = lastname.getText().toString();
                    String birthdateTXT = birthdate.getText().toString();

                    String currentweightTXT = currentweight.getText().toString();
                    String currentheightTXT = currentheight.getText().toString();
                    String birthweightTXT = birthweight.getText().toString();

                    // guardian
                    String firstnameGTXT = firstnameG.getText().toString();
                    String lastnameGTXT = lastnameG.getText().toString();
                    String birthdateGTXT = birthdateG.getText().toString();
                    String nicTXT = NIC.getText().toString();
                    String addressTXT = address.getText().toString();

                    int noofchildrenTXT = Integer.parseInt(noofchildren.getText().toString());

                    Boolean checkupdatedata = myDB.updateuserdata(firstnameTXT, lastnameTXT, birthdateTXT, currentweightTXT, currentheightTXT, birthweightTXT,
                            firstnameGTXT, lastnameGTXT, birthdateGTXT, nicTXT, addressTXT, noofchildrenTXT);

                    if (checkupdatedata == true) {
                        Toast.makeText(Details.this, "data entry updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Details.this, "data entry not updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
    }

}