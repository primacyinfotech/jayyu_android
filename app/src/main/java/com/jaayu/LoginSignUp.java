package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class LoginSignUp extends AppCompatActivity {
private Button goTo_submit;
    int GET_MY_PERMISSION = 1;
    private String Login_Otp_url= BaseUrl.BaseUrlNew+"login_otp";
    private EditText input_mobile;
    private  String u_phone;
    private TextView term_condition;
    SharedPreferences sp;
    SharedPreferences prefs_register;
    ProgressDialog progressDialog;
    String Otp,sp_otp,sp_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        sp=getSharedPreferences("login",MODE_PRIVATE);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        input_mobile=(EditText)findViewById(R.id.input_mobile);
        term_condition=(TextView)findViewById(R.id.term_condition) ;
        term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Intent goToManageAddress=new Intent(LoginSignUp.this,Legal.class);

                startActivity(goToManageAddress);
                overridePendingTransition(0,0);
                finish();*/
            }
        });
        requestSmsPermission();
        if(ContextCompat.checkSelfPermission(LoginSignUp.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginSignUp.this,
                    Manifest.permission.READ_SMS)) {
                /* do nothing*/
            } else {

                ActivityCompat.requestPermissions(LoginSignUp.this,
                        new String[]{Manifest.permission.READ_SMS}, GET_MY_PERMISSION);
            }
        }

        goTo_submit=(Button)findViewById(R.id.goTo_submit);
        goTo_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginSignUp.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setMessage("loading....");
                progressDialog.setCancelable(false);
                u_phone=input_mobile.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(LoginSignUp.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, Login_Otp_url,
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
                                          progressDialog.dismiss();
                                        Intent goViewOtp=new Intent(LoginSignUp.this,ViewOtp.class);
                                         goViewOtp.putExtra("PHONE",u_phone);
                                        startActivity(goViewOtp);
                                        finish();
                                    }
                                    if(status.equals("0")) {
                                        progressDialog.dismiss();
                                        Intent goViewOtp=new Intent(LoginSignUp.this,Registration.class);
                                        startActivity(goViewOtp);
                                        finish();
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }

                                    Otp=person.getString("Otp");
                                    SharedPreferences.Editor e=sp.edit();
                                    e.putString("OTP",Otp);
                                    e.putString("PHON",Otp);
                                    e.commit();

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
                        params.put("phone", u_phone);
                        return params;
                    }
                };
                queue.add(postRequest);
               /* Intent goRegis=new Intent(LoginSignUp.this,Registration.class);
                startActivity(goRegis);*/
            }
        });

        /*sp_otp=sp.getString("OTP","");
        sp_phone=sp.getString("PHON","");
        if (sp_otp.equals(Otp)||sp_phone.equals(u_phone)) {
           Intent goToHome=new Intent(LoginSignUp.this,MainActivity.class);
           startActivity(goToHome);
           finish();
        }*/
    }
    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

        System.exit(1);
    }
}
