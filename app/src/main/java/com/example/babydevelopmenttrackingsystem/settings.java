package com.example.babydevelopmenttrackingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private BottomNavigationView bottomNav;
    private Button selectTimeBtn;
    private Button selectedDate;
    private Button selectedTime;
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
        selectTimeBtn = findViewById(R.id.selectTimeBtn);
        selectedDate = findViewById(R.id.selectedDate);
        selectedTime = findViewById(R.id.selectedTime);
        Calendar calendar = Calendar.getInstance();


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



        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month + 1);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String theDate = calendar.get(Calendar.YEAR)+ "/" +calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH)  ;

                        selectedDate.setText(theDate);
                    }


                };

                new DatePickerDialog(settings.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
         selectedTime.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                         calendar.set(Calendar.MINUTE, minute);
                         calendar.set(Calendar.SECOND, 0);
                         calendar.set(Calendar.MILLISECOND, 0);

                         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");


                         String theTime = calendar.get(Calendar.HOUR_OF_DAY)+ ":" +calendar.get(Calendar.MINUTE);


                         selectedTime.setText(theTime);
                     }
                 };
                 new TimePickerDialog(settings.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
             }
         });


        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(settings.this, "Notification set", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(settings.this, ReminderBroadcast.class);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(settings.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

//        selectTimeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calendar = Calendar.getInstance();
//
//                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(Calendar.YEAR,year);
//                        calendar.set(Calendar.MONTH,month);
//                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//
//                        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
//                            @Override
//                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                                calendar.set(Calendar.MINUTE, minute);
//                                calendar.set(Calendar.SECOND, 0);
//                                calendar.set(Calendar.MILLISECOND, 0);
//
//                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
//
//                                //date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
//
//
//                                Toast.makeText(settings.this, "Reminder set", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(settings.this, ReminderBroadcast.class);
//                                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(settings.this, 0, intent, 0);
//
//                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//                                //              long timeAtButtonOnClick = System.currentTimeMillis();
//                                //
//                                //              long tenSecondsInMillis = 1000 * 10;
//
//                                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//
//                            }
//                        };
//
//                        new TimePickerDialog(settings.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
//                    }
//                };
//
//                new DatePickerDialog(settings.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//
//
//            }
//        });
//
//
//
//
//
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

