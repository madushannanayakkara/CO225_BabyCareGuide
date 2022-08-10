package com.example.babydevelopmenttrackingsystem;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegisterForm extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterForm";
    private EditText txtFirstName, txtLastName, txtBirthdate, txtCurrentWeight, txtCurrentHeight;
    private TextView txtViewNotify;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnRegister;
    private RelativeLayout parent;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonRegister:

                if (validateData()){
                    showSnackBar();
                }
                break;
            case R.id.editTextFirstName:
                Toast.makeText(this, "Type first name of your baby here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editTextLastName:
                Toast.makeText(this, "Type last name of your baby here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editTextBirthDate:
                Toast.makeText(this, "Please enter in this format ex: 2000/02/02", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editTextCurrentWeight:
                Toast.makeText(this, "Please enter only the VALUE in kg(KILOGRAM)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editTextCurrentHeight:
                Toast.makeText(this, "Please enter only the VALUE in m(METERS)", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        initViews();

        txtFirstName.setOnClickListener(this);
        txtLastName.setOnClickListener(this); txtBirthdate.setOnClickListener(this);
        txtCurrentWeight.setOnClickListener(this); txtCurrentHeight.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                switch (checkedId){
                    case R.id.rbMale:
                        Toast.makeText(RegisterForm.this, "Male", Toast.LENGTH_SHORT).show();
                    case R.id.rbFemale:
                        Toast.makeText(RegisterForm.this, "Female", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        })
        ;



        btnRegister.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (validateData()) {
                    showSnackBar();

                    int selectedGender = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton)findViewById(selectedGender);

                    DatabaseH myDB = new DatabaseH(RegisterForm.this);
                    myDB.addBaby(toTitleCase(txtFirstName.getText().toString()),
                            toTitleCase(txtLastName.getText().toString()),
                            txtBirthdate.getText().toString().trim(),
                            radioButton.getText().toString().trim(),
                            txtCurrentWeight.getText().toString().trim(),
                            txtCurrentHeight.getText().toString().trim());

                }
            }
        });

    }

    private static String toTitleCase(String str) {

        if(str == null || str.isEmpty())
            return "";

        if(str.length() == 1)
            return str.toUpperCase();

        String[] parts = str.split(" ");

        StringBuilder sb = new StringBuilder( str.length() );

        for(String part : parts){

            if(part.length() > 1 )
                sb.append( part.substring(0, 1).toUpperCase() )
                        .append( part.substring(1).toLowerCase() );
            else
                sb.append(part.toUpperCase());

            sb.append(" ");
        }

        return sb.toString().trim();
    }

    private boolean validateData(){
        Log.d(TAG, "validateData: started");
        if (txtFirstName.getText().toString().equals("") || txtLastName.getText().toString().equals("")
                || txtBirthdate.getText().toString().equals("") || txtCurrentWeight.getText().toString().equals("")
                || txtCurrentHeight.getText().toString().equals("") ){
            txtViewNotify.setVisibility(View.VISIBLE);
            txtViewNotify.setText("Required all fields !");
            return false;
        }
        return true;
    }

    private  void showSnackBar(){
        Log.d(TAG, "showSnackBar: Started");
        txtViewNotify.setVisibility(View.GONE);
        Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();

        Snackbar.make(parent, "User Registered", Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtFirstName.setText("");   txtBirthdate.setText("");
                        txtLastName.setText("");    txtCurrentHeight.setText("");
                        txtCurrentWeight.setText("");
                    }
                }).show();
    }

    public void checkButton (View view){
        int selectedGender = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)findViewById(selectedGender);
    }

    private void initViews(){
        Log.d(TAG, "initViews: Started");
        txtFirstName     = findViewById(R.id.editTextFirstName);
        txtLastName = findViewById(R.id.editTextLastName);
        txtBirthdate = findViewById(R.id.editTextBirthDate);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);


        btnRegister = findViewById(R.id.buttonRegister);
        txtCurrentWeight = findViewById(R.id.editTextCurrentWeight);
        txtCurrentHeight = findViewById(R.id.editTextCurrentHeight);
        txtViewNotify = findViewById(R.id.textViewNotify);
        parent = findViewById(R.id.parent);
    }
}