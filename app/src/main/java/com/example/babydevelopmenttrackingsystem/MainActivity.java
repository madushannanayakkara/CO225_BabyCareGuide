package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView bottomNav;
    private Button btnDetails, btnVaccination, btnBMI, btnSettings;
    private TextView txtv_title;
    private RecyclerView recylerview;

    DatabaseH dh;
    ArrayList<String> fname, lname;
    CustomAdpter customAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh = new DatabaseH(MainActivity.this);
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

        txtv_title = (TextView) findViewById(R.id.txtv_title);
        txtv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                                R.layout.layout_bottom_sheet_acc_list,
                                (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

                bottomSheetView.findViewById(R.id.btnAddAccount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RegisterForm.class));
                        overridePendingTransition(0, 0);
                        bottomSheetDialog.dismiss();
                    }
                });

                recylerview = bottomSheetView.findViewById(R.id.recyclerview);
                fname = new ArrayList<>();
                lname = new ArrayList<>();

                storeAccDataArrays();

                customAdpter = new CustomAdpter(MainActivity.this, fname, lname, dh, bottomSheetDialog);
                recylerview.setAdapter(customAdpter);
                recylerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        txtv_title.setText(dh.readAccName(dh.readLastSavedID()));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
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

    void storeAccDataArrays(){
        Cursor cursor = dh.readAccounts();
        if(cursor.getCount() == 0){
            startActivity(new Intent(getApplicationContext(), RegisterForm.class));
            overridePendingTransition(0, 0);
            Toast.makeText(this, "Welcome to BabyCareGuide, Add a new Account!", Toast.LENGTH_LONG).show();
        } else {
            while(cursor.moveToNext()){
                fname.add(cursor.getString(0));
                lname.add(cursor.getString(1));
            }
        }
    }

}