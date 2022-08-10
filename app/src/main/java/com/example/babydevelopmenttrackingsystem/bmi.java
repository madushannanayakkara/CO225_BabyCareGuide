package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bmi extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private EditText etxtHeight, etxtWeight;
    private TextView txtBMIValueDisplay, txt_normal_weight, txt_obese, txt_overweight, txt_underweight, txtBMIstate;
    private Button btnBMIcalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        etxtHeight = findViewById(R.id.etxtHeight);
        etxtWeight = findViewById(R.id.etxtWeight);
        txtBMIstate = findViewById(R.id.txtBMIstate);
        txtBMIValueDisplay = findViewById(R.id.txtBMIValueDisplay);
        txt_normal_weight = findViewById(R.id.txt_normal_weight);
        txt_obese = findViewById(R.id.txt_obese);
        txt_overweight = findViewById(R.id.txt_overweight);
        txt_underweight = findViewById(R.id.txt_underweight);
        btnBMIcalc = findViewById(R.id.btnBMIcalc);

        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setSelectedItemId(R.id.nav_bmi);

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
                        startActivity(new Intent(getApplicationContext(), Vaccination.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_bmi:
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        btnBMIcalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String height = String.valueOf(etxtHeight.getText());
                String weight = String.valueOf(etxtWeight.getText());

                if(height.equals("") || weight.equals("")){
                    Toast.makeText(bmi.this, "Please enter values first!", Toast.LENGTH_SHORT).show();
                } else {
                    double bmi = calcBMI(Double.parseDouble(height) / 100.0, Double.parseDouble(weight));
                    txtBMIValueDisplay.setText(String.valueOf(Math.round (bmi * 100.0) / 100.0));

                    if(bmi < 18.5){
                        txt_underweight.setVisibility(View.VISIBLE);
                        txt_normal_weight.setVisibility(View.INVISIBLE);
                        txt_overweight.setVisibility(View.INVISIBLE);
                        txt_obese.setVisibility(View.INVISIBLE);
                        txtBMIstate.setText("Underweight");
                    } else if (bmi >= 18.5 && bmi < 25){
                        txt_underweight.setVisibility(View.INVISIBLE);
                        txt_normal_weight.setVisibility(View.VISIBLE);
                        txt_overweight.setVisibility(View.INVISIBLE);
                        txt_obese.setVisibility(View.INVISIBLE);
                        txtBMIstate.setText("Normal");
                    } else if (bmi >= 25 && bmi < 30){
                        txt_underweight.setVisibility(View.INVISIBLE);
                        txt_normal_weight.setVisibility(View.INVISIBLE);
                        txt_overweight.setVisibility(View.VISIBLE);
                        txt_obese.setVisibility(View.INVISIBLE);
                        txtBMIstate.setText("Overweight");
                    } else {
                        txt_underweight.setVisibility(View.INVISIBLE);
                        txt_normal_weight.setVisibility(View.INVISIBLE);
                        txt_overweight.setVisibility(View.INVISIBLE);
                        txt_obese.setVisibility(View.VISIBLE);
                        txtBMIstate.setText("Obese");
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

    double calcBMI(double height, double weight){
        return weight/(height * height);
    }

}