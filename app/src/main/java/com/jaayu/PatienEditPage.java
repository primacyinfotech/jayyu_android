package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class PatienEditPage extends AppCompatActivity {
    private ImageView back_button;
    private EditText patient_name, patient_relation, dob, patient_gender, patient_blood, height, weight, medical_condition, reaction, medication;
    private Button view_prescrip;
    private TextView add_con_one, add_con_two;
    DatePickerDialog datePickerDialog;
    String[] value = new String[]{
            "Not Set",
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-",
            "O+",
            "O-",
            "Other"
    };
    String[] gender=new String[]{
            "Male",
            "Female",
            "Others"
    };
    String[] relation=new String[]{
            "Myself",
            "Father",
            "Mother",
            "Husband",
            "wife",
            "Brother",
            "Sister",
            "Grandfather",
            "Grandmother",
            "Other"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patien_edit_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        patient_name = (EditText) findViewById(R.id.patient_name);
        patient_relation = (EditText) findViewById(R.id.patient_relation);
        patient_relation.setInputType(InputType.TYPE_NULL);
        dob = (EditText) findViewById(R.id.dob);
        patient_blood = (EditText) findViewById(R.id.patient_blood);
        patient_blood.setInputType(InputType.TYPE_NULL);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        medical_condition = (EditText) findViewById(R.id.medical_condition);
        reaction = (EditText) findViewById(R.id.reaction);
        medication = (EditText) findViewById(R.id.medication);
        patient_gender = (EditText) findViewById(R.id.patient_gender);
        patient_gender.setInputType(InputType.TYPE_NULL);
        view_prescrip = (Button) findViewById(R.id.view_prescrip);
        add_con_one = (TextView) findViewById(R.id.add_con_one);
        add_con_two = (TextView) findViewById(R.id.add_con_two);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        patient_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(PatienEditPage.this);
                alertdialogbuilder.setTitle("Select A BloodType ");
                alertdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(value).get(which);
                        patient_blood.setText(selectedText);
                    }
                });
                AlertDialog dialog = alertdialogbuilder.create();

                dialog.show();
            }
        });
        patient_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertgender = new AlertDialog.Builder(PatienEditPage.this);
                alertgender.setTitle("Select Gender");
                alertgender.setItems(gender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(gender).get(which);
                        patient_gender.setText(selectedText);
                    }
                });
                AlertDialog dialoggen = alertgender.create();

                dialoggen.show();
            }
        });
        patient_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertrelation = new AlertDialog.Builder(PatienEditPage.this);
                alertrelation.setTitle("Choose Relation");
                alertrelation.setItems(relation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedText = Arrays.asList(relation).get(which);
                        patient_relation.setText(selectedText);
                    }
                });
                AlertDialog dialogrelation = alertrelation.create();

                dialogrelation.show();
            }
        });
    }
    
}
