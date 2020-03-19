package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PatientAddPage extends AppCompatActivity {
    SharedPreferences prefs_register;
    private ImageView back_button;
    private Button add_btn;
    private EditText patient_name, patient_relation, dob, patient_gender, patient_blood, height,weight,  medical_condition, reaction, medication,
            ft,inch;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button view_prescrip;
    private TextView add_con_one, add_con_two;
    DatePickerDialog datePickerDialog;
    String u_id, p_name,p_relation,date_of_birth,p_gen,blood_group,height_of_patient,weight_of_patient,m_condition,reaction_patient,
    medication_of_patient,con1,con2;
    private static  int RQS_PICK_CONTACT=1;
    private static  int RQS_PICK_CONTACT_SECOND=2;
    private String patient_add_url= BaseUrl.BaseUrlNew+"patient_add";
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
        setContentView(R.layout.activity_patient_add_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        patient_name = (EditText) findViewById(R.id.patient_name);
        patient_relation = (EditText) findViewById(R.id.patient_relation);
        patient_relation.setInputType(InputType.TYPE_NULL);
        dob = (EditText) findViewById(R.id.dob);
        dob.setInputType(InputType.TYPE_NULL);
        patient_blood = (EditText) findViewById(R.id.patient_blood);
        patient_blood.setInputType(InputType.TYPE_NULL);
       // height = (EditText) findViewById(R.id.height);
        ft=(EditText) findViewById(R.id.ft);
        inch=(EditText) findViewById(R.id.inch);
        weight = (EditText) findViewById(R.id.weight);
        medical_condition = (EditText) findViewById(R.id.medical_condition);
        reaction = (EditText) findViewById(R.id.reaction);
        medication = (EditText) findViewById(R.id.medication);
        patient_gender = (EditText) findViewById(R.id.patient_gender);
        patient_gender.setInputType(InputType.TYPE_NULL);
       // view_prescrip = (Button) findViewById(R.id.view_prescrip);
        add_con_one = (TextView) findViewById(R.id.add_con_one);
        add_con_two = (TextView) findViewById(R.id.add_con_two);
        add_btn=(Button)findViewById(R.id.add_btn);
      /* view_prescrip.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent goTtoReport=new Intent(PatientAddPage.this,MedicalPrescriptionReport.class);
               startActivity(goTtoReport);
               overridePendingTransition(0,0);
               finish();
           }
       });*/
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent goTtoBack=new Intent(PatientAddPage.this,PatientListView.class);
             startActivity(goTtoBack);
             overridePendingTransition(0,0);
             finish();
            }
        });
        patient_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(PatientAddPage.this);
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
                AlertDialog.Builder alertgender = new AlertDialog.Builder(PatientAddPage.this);
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
                AlertDialog.Builder alertrelation = new AlertDialog.Builder(PatientAddPage.this);
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
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(PatientAddPage.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        add_con_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, RQS_PICK_CONTACT);
            }
        });
        add_con_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent, RQS_PICK_CONTACT_SECOND);
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            p_name=patient_name.getText().toString();
            p_relation=patient_relation.getText().toString();
            date_of_birth=dob.getText().toString();
            p_gen=patient_gender.getText().toString();
            blood_group=patient_blood.getText().toString();
            height_of_patient=ft.getText().toString()+"ft"+inch.getText().toString()+"inch";
            weight_of_patient=weight.getText().toString()+"kg";
            m_condition=medical_condition.getText().toString();
            reaction_patient=reaction.getText().toString();
            medication_of_patient=medication.getText().toString();
            con1=add_con_one.getText().toString();
            con2=add_con_two.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(PatientAddPage.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, patient_add_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    //Do it with this it will work
                                    JSONObject person = new JSONObject(response);
                                    String status=person.getString("status");


                                    if(status.equals("1")){
                                    Toast.makeText(getApplicationContext(),"Add Patient Data",Toast.LENGTH_LONG).show();
                                        patient_name.getText().clear();
                                        patient_relation.getText().clear();
                                        dob.getText().clear();
                                        patient_gender.getText().clear();
                                        patient_blood.getText().clear();
                                        ft.getText().clear();
                                        inch.getText().clear();
                                        weight.getText().clear();
                                        medical_condition.getText().clear();
                                        reaction.getText().clear();
                                        reaction.getText().clear();
                                        medication.getText().clear();
                                        Intent refreshPage=new Intent(getApplicationContext(),PatientListView.class);
                                        overridePendingTransition(0,0);
                                        startActivity(refreshPage);
                                        finish();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("user_id", u_id);
                        params.put("name", p_name);
                        params.put("rel", p_relation);
                        params.put("dob", date_of_birth);
                        params.put("sex", p_gen);
                        params.put("blood", blood_group);
                        params.put("height", height_of_patient);
                        params.put("weight", weight_of_patient);
                        params.put("mcon", m_condition);
                        params.put("alerg", reaction_patient);
                        params.put("med_cation", medication_of_patient);
                        params.put("emgconct1", con1);
                        params.put("emgconct2", con2);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RQS_PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                //contactName.setText(name);
                add_con_one.setText(number);
                //contactEmail.setText(email);
            }
        }
            if(requestCode==RQS_PICK_CONTACT_SECOND){
                if(resultCode == RESULT_OK){
                    Uri contactData2 = data.getData();
                    Cursor cursor2 =  managedQuery(contactData2, null, null, null, null);
                    cursor2.moveToFirst();

                    String number2 =       cursor2.getString(cursor2.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    //contactName.setText(name);
                    add_con_two.setText(number2);
                    //contactEmail.setText(email);
                }
            }
        }
    @Override
    public void onBackPressed() {
        Intent goTtoBack=new Intent(PatientAddPage.this,PatientListView.class);
        startActivity(goTtoBack);
        overridePendingTransition(0,0);
        finish();
    }
    }

