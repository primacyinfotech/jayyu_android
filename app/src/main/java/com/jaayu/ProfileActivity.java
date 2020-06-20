package com.jaayu;

import Adapter.InputFilterMinMax;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.jaayu.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.github.gafurcseku.internetconnectivitychecker.NetworkUtil;

public class  ProfileActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();

    Button updatebtn;
    ImageView backbtn;
    String user_id;
    SharedPreferences prefs_register;
    FrameLayout pgsframe;
    ProgressBar pgsbar;
    EditText heightinft, heightininch;
    Spinner genderspinner,bloodgroupspinner,maritalstatusspinner;
    String  profilename, profileemail,profilephone,profiledob,profgender,
            profilebloodgroup, profileheightinft, profilehtininch, profileweight,
            profilemaritalstatus, profileemergencycontact,profileemergencyname;
    TextView phonenumet;
    TextInputEditText nameet, dobet,emailet, weightet, emergencycontactnoet, emergencycontactname ;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialisation();
        valuesinit();

        showuserProfileDetailsApiCall();

        initlistner();

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
    private void initlistner()
    {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(), AccountPage.class);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nametoupdate = nameet.getText().toString().trim();
                final String mobilenum = phonenumet.getText().toString().trim();
                String memail = "mull";
                String mdateofbirth = "null";
                String mgender = "null";
                String mblgrp = "null";
                String gweight= "null";
                String htft = "null";
                String htin = "null";
                String mstat = "null";
                String emgnum = "null";
                String emgname = "null";
                if (!emailet.getText().toString().trim().isEmpty()) {
                    memail = emailet.getText().toString().trim();
                }

                if ( !dobet.getText().toString().trim().isEmpty())
                {
                    mdateofbirth = dobet.getText().toString().trim();
                }
                if ( !emergencycontactnoet.getText().toString().trim().isEmpty())
                {
                    emgnum = emergencycontactnoet.getText().toString().trim();
                }
                if ( !emergencycontactname.getText().toString().trim().isEmpty())
                {
                    emgname = emergencycontactname.getText().toString().trim();
                }
                if ( !weightet.getText().toString().trim().isEmpty())
                {
                    gweight = weightet.getText().toString().trim();
                }
                if ( !heightinft.getText().toString().trim().isEmpty())
                {
                    htft = heightinft.getText().toString().trim();
                }
                if ( !heightininch.getText().toString().trim().isEmpty())
                {
                    htin = heightininch.getText().toString().trim();
                }
                if (TextUtils.isEmpty(nametoupdate))
                {
                    nameet.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(mobilenum))
                {
                    phonenumet.setText("This field is required");
                    return;
                }
                /*if (genderspinner.getSelectedItemPosition() != 0)
                {*/
                    mgender = genderspinner.getSelectedItem().toString();
               // }
               /* if (bloodgroupspinner.getSelectedItemPosition() != 0)
                {*/
                    mblgrp = bloodgroupspinner.getSelectedItem().toString();
                //}
               /* if (maritalstatusspinner.getSelectedItemPosition() != 0)
                {*/
                    mstat = maritalstatusspinner.getSelectedItem().toString();
                //}
                if (!emergencycontactnoet.getText().toString().trim().isEmpty() && emergencycontactnoet.getText().toString().trim().length() != 10)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Phone number",Toast.LENGTH_SHORT).show();
                }
                else if (!memail.matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                }
               /* else if (ConnectivityStatus.getInstance(getApplicationContext()).isOnline())
                {*/

                else if (NetworkUtil.isConnected(getApplicationContext()))
                {
                    pgsbar.setVisibility(View.VISIBLE);
                    updatebtn.setText("");
                    RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                    final String finalMemail = memail;
                    final String finalMdateofbirth = mdateofbirth;
                    final String finalMgender = mgender;
                    final String finalMblgrp = mblgrp;
                    final String finalHtft = htft;
                    final String finalHtin = htin;
                    final String finalGweight = gweight;
                    final String finalMstat = mstat;
                    final String finalEmgnum = emgnum;
                    final String finalEmgname = emgname;
                    Log.i("tag","dob et"+finalMdateofbirth);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"profile_data_update",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject jsonres = new JSONObject(response);
                                        String status=jsonres.getString("status");
                                        if(status.contentEquals("1")) {
                                            pgsbar.setVisibility(View.GONE);
                                            updatebtn.setText("UPDATE");
                                            Toast.makeText(ProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                                            Intent i = new Intent(getApplicationContext(), AccountPage.class);
                                            startActivity(i);
                                        }

                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }


                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error

                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("name", nametoupdate);
                            params.put("user_id", user_id);
                            params.put("email", finalMemail);
                            params.put("phone", mobilenum);
                            params.put("dob", finalMdateofbirth);
                            params.put("gender", finalMgender);
                            params.put("blood", finalMblgrp);
                            params.put("ht_ft", finalHtft);
                            params.put("ht_inch", finalHtin);
                            params.put("weight", finalGweight);
                            params.put("mstatus", finalMstat);
                            params.put("emgno", finalEmgnum);
                            params.put("emgname", finalEmgname);
                            return params;
                        }
                    };

                    requestQueue.add(postRequest);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check Internet Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialisation()
    {
        nameet = findViewById(R.id.nameet);
        phonenumet = findViewById(R.id.mobilenumet);
        pgsframe = findViewById(R.id.progressframe);
        dobet = findViewById(R.id.dobet);
        emailet = findViewById(R.id.emailet);
        genderspinner = findViewById(R.id.selectgenderspinner);
        bloodgroupspinner = findViewById(R.id.bloodgroupspinner);
        maritalstatusspinner = findViewById(R.id.maritalstatusspinner);
        heightinft = findViewById(R.id.ftht);
        heightininch = findViewById(R.id.ftin);
        emergencycontactnoet = findViewById(R.id.emergencynum);
        emergencycontactname = findViewById(R.id.emergencyname);
        weightet = findViewById(R.id.weightet);
        updatebtn = findViewById(R.id.updatebtn);
        pgsbar = findViewById(R.id.pgsbar);
        pgsbar.setVisibility(View.GONE);
        backbtn = findViewById(R.id.back_button);
        scrollView = findViewById(R.id.scrollView);

        heightininch.setFilters(new InputFilter[]{new InputFilterMinMax("1", "12")});


    }
    private void valuesinit()
    {
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        user_id = prefs_register.getString("USER_ID", null);
        // user_id = new SharedPref(getApplicationContext()).getUserID();
        if (user_id == null)
        {
            SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
            SharedPreferences.Editor e=sp.edit();
            e.clear();
            e.commit();
            SharedPreferences prefs_register=getSharedPreferences(
                    "Register Details", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit=prefs_register.edit();
            edit.clear();
            edit.commit();
            Intent goTOLogin=new Intent(ProfileActivity.this,LoginSignUp.class);
            startActivity(goTOLogin);
            finish();
        }


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                //fethcing current date
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String todayString = formatter.format(todayDate);

                dobet.setText(sdf.format(myCalendar.getTime()));
                //}
            }


        };
        dobet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }

    private void showuserProfileDetailsApiCall()
    {
        pgsframe.setVisibility(View.VISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"profile_data_display",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject jsonres = new JSONObject(response);
                            String status=jsonres.getString("status");
                            if(status.contentEquals("1")) {
                                //JSONArray jsonArray= jsonres.getJSONArray("data");
                                //// for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject=jsonres.getJSONObject("data");
                                Log.i("tag","json object"+jsonObject);
                                profilename = jsonObject.getString("name");
                                profileemail = jsonObject.getString("email");
                                profilephone = jsonObject.getString("phone");
                                profiledob = jsonObject.getString("dob");
                                profgender = jsonObject.getString("gender");
                                profilebloodgroup = jsonObject.getString("blood");
                                profileheightinft = jsonObject.getString("ht_ft");
                                profileweight = jsonObject.getString("weight");
                                profilehtininch = jsonObject.getString("ht_inch");
                                profilemaritalstatus = jsonObject.getString("mstatus");
                                profileemergencycontact = jsonObject.getString("emgno");
                                profileemergencyname = jsonObject.getString("emgname");
                                pgsframe.setVisibility(View.GONE);
                                if (profilename != null) {
                                    nameet.setText(profilename);
                                }
                                Log.i("tag","profile dob"+profiledob +"json data"+jsonObject.getString("dob"));
                                if (profilephone != null) {
                                    phonenumet.setText(profilephone);
                                }

                                if (profiledob == null && !profileemail.contains("null") && profileemail.equals(null))
                                {

                                }
                                else
                                {
                                    if (!profiledob.contains("null")){

                                        try {
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                            Date testDate = sdf.parse(profiledob);
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                            profiledob = formatter.format(testDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        dobet.setText(profiledob);

                                    }

                                }

                                if (profgender == null && !profgender.toString().contains("null") && profgender.equals(null))
                                {
                                    genderspinner.setSelection(0);
                                }
                                else
                                {
                                    genderspinner.setSelection(getIndex(genderspinner, profgender));
                                }

                                if (profilebloodgroup == null && !profilebloodgroup.toString().contains("null") && profilebloodgroup.equals(null)) {

                                    bloodgroupspinner.setSelection(0);
                                }
                                else
                                {
                                    bloodgroupspinner.setSelection(getIndex(bloodgroupspinner, profilebloodgroup));
                                }
                                if (profilemaritalstatus == null && !profilemaritalstatus.toString().contains("null") && profilemaritalstatus.equals(null)) {
                                    maritalstatusspinner.setSelection(0);
                                }
                                else
                                {
                                    maritalstatusspinner.setSelection(getIndex(maritalstatusspinner, profilemaritalstatus));
                                }

                                if (profileheightinft == null && profileheightinft.toString().contains("null") && profileheightinft.equals(null)) {
                                }
                                else
                                {
                                    if (!profileheightinft.toString().contains("null"))
                                        heightinft.setText(profileheightinft);
                                }

                                if (profilehtininch == null && profilehtininch.toString().contains("null") && profilehtininch.equals(null)) {
                                }
                                else
                                {
                                    if (!profilehtininch.toString().contains("null"))
                                        heightininch.setText(profilehtininch);
                                }

                                if (profileemergencyname == null && profileemergencyname.toString().contains("null") && profileemergencyname.equals(null)) {

                                }
                                else
                                {
                                    if (!profileemergencyname.toString().contains("null"))
                                        emergencycontactname.setText(profileemergencyname);
                                }

                                if (profileemergencycontact == null && profileemergencycontact.toString().contains("null") && profileemergencycontact.equals(null)) {

                                }
                                else
                                {
                                    if (!profileemergencycontact.toString().contains("null"))
                                        emergencycontactnoet.setText(profileemergencycontact);
                                }
                                if (profileweight == null && profileweight.toString().contains("null") && profileweight.equals(null)) {

                                }
                                else
                                {
                                    if (!profileweight.toString().contains("null"))
                                        weightet.setText(profileweight);
                                }

//                                     else
//                                     {
//                                         if (profiledob.toString().contains("null"))
//                                         {
//
//                                         }
//                                         else {
//
//                                         }
//                                     }
                                if (profileemail != null
                                        && !profileemail.contentEquals("null")  && !profileemail.equals(null))
                                {
                                    emailet.setText(profileemail);
                                }
                                //   }

                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", user_id);

                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent i =new Intent(getApplicationContext(), AccountPage.class);
        startActivity(i);
        overridePendingTransition(0,0);
        finish();
    }
}
