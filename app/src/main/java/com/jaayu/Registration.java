package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private EditText user_name,user_email,user_phone;
    private Button goTo_submit;
    String u_name,u_email,u_phone;
    int GET_MY_PERMISSION = 1;
    private String regUrl="https://work.primacyinfotech.com/jaayu/api/customer_reg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        requestSmsPermission();
        if(ContextCompat.checkSelfPermission(Registration.this,Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(Registration.this,
                    Manifest.permission.READ_SMS)){
                /* do nothing*/
            }
            else{

                ActivityCompat.requestPermissions(Registration.this,
                        new String[]{Manifest.permission.READ_SMS},GET_MY_PERMISSION);
            }
        }
        user_name=(EditText)findViewById(R.id.user_name);
        user_email=(EditText)findViewById(R.id.user_email);
        user_phone=(EditText)findViewById(R.id.user_phone);
        goTo_submit=(Button)findViewById(R.id.goTo_submit);
        goTo_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_name=user_name.getText().toString();
                u_email=user_email.getText().toString();
                u_phone=user_phone.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(Registration.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, regUrl,
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
                                        Intent goViewOtp=new Intent(Registration.this,ViewOtp.class);
                                        goViewOtp.putExtra("PHONE",u_phone);
                                        startActivity(goViewOtp);
                                        finish();
                                    }
                                    else {
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                        params.put("name", u_name);
                        params.put("email", u_email);
                        params.put("phone", u_phone);
                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });


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
        Intent goToBack=new Intent(Registration.this,LoginSignUp.class);
        startActivity(goToBack);
        overridePendingTransition(0,0);
        finish();
    }

}
