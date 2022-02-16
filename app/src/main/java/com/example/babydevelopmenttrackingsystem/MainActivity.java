package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView bottomNav;
    private Button btnDetails, btnVaccination, btnBMI, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDetails = findViewById(R.id.btnDetails);
        btnVaccination = findViewById(R.id.btnVaccination);
        btnBMI = findViewById(R.id.btnBmi);
        btnSettings = findViewById(R.id.btnSettings);

        btnDetails.setOnClickListener(this);
        btnVaccination.setOnClickListener(this);
        btnBMI.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDetails:
                startActivity(new Intent(getApplicationContext(), Details.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.btnVaccination:
                startActivity(new Intent(getApplicationContext(), Vaccination.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.btnBmi:
                startActivity(new Intent(getApplicationContext(), bmi.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.btnSettings:
                startActivity(new Intent(getApplicationContext(), settings.class));
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }
}