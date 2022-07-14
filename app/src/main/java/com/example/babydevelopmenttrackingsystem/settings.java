package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private BottomNavigationView bottomNav;
    private Button saveChanges;
    private Spinner spinner;
    private String text;
    private DatabaseH databaseH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        createNotificationChannel();

        bottomNav = findViewById(R.id.bottom_navigator);
        bottomNav.setSelectedItemId(R.id.nav_settings);

        databaseH = new DatabaseH(settings.this);

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
                        startActivity(new Intent(getApplicationContext(), bmi.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_settings:
                        return true;
                }

                return false;
            }
        });

        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.timePeriods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        saveChanges = findViewById(R.id.saveChanges);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(settings.this, "Reminder set", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(settings.this, ReminderBroadcast.class);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(settings.this,0,intent,0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonOnClick = System.currentTimeMillis();

                long tenSecondsInMillis = 1000 * 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonOnClick+tenSecondsInMillis, pendingIntent);

                int id = databaseH.readLastSavedID();
                text = spinner.getSelectedItem().toString();
                databaseH.addNotifyData(text,id);

            }

        });





    }


    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "VaccinReminderChannel";
            String description = "Channel for vaccine Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyBabyVaccine",name
            ,importance);
            channel.setDescription((description));

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);
    }

}

