package com.jaayu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;


public class ViewOtp extends AppCompatActivity {
    private SmsVerifyCatcher smsVerifyCatcher;
    private OtpTextView otpTextView;
    private Button goTo_submit;
   // private EditText view_otp;
    private TextView resend_btn;
    String code,OTP_Code,OTP_Code_Two,cell_number;
    private String OTP_URL= BaseUrl.BaseUrlNew+"verify_otp";
    private String resend_Otp_url=BaseUrl.BaseUrlNew+"resend_otp";
    SharedPreferences prefs ;
    SharedPreferences prefs_register;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_otp);

        otpTextView = findViewById(R.id.otp_view);
        goTo_submit=(Button)findViewById(R.id.goTo_submit);
        resend_btn=(TextView)findViewById(R.id.resend_btn);
       Intent cell_no=getIntent();
       cell_number=cell_no.getStringExtra("PHONE");
        prefs = getSharedPreferences("Mobile_Number", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("MOBILE",cell_number);
        editor.apply();
       // view_otp=(EditText)findViewById(R.id.view_otp);
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                 code = parseCode(message);//Parse verification code
               // etCode.setText(code);//set code in edit text
                //then you can send verification code to server
                otpTextView.setOTP(code);
              OTP_Code= otpTextView.getOTP();

              //  view_otp.setText(code);
           /*  otpTextView.setOtpListener(new OTPListener() {
                 @Override
                 public void onInteractionListener() {

                 }

                 @Override
                 public void onOTPComplete(String otp) {
                    OTP_Code=otp;
                 }
             });*/


            }
        });
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
               // Toast.makeText(ViewOtp.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
                OTP_Code=otp;

            }
        });
       goTo_submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog = new ProgressDialog(ViewOtp.this);
               progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               progressDialog.show();
               progressDialog.setMessage("loading....");
               progressDialog.setCancelable(false);
               RequestQueue queue = Volley.newRequestQueue(ViewOtp.this);
               StringRequest postRequest = new StringRequest(Request.Method.POST, OTP_URL,
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
                                       JSONObject object=person.getJSONObject("user");
                                       String user_id=object.getString("id");
                                       String user_name=object.getString("name");
                                       String user_email=object.getString("email");
                                       String user_phone=object.getString("phone");
                                       String Referral_code=object.getString("referral_code");
                                       prefs_register = getSharedPreferences(
                                               "Register Details", Context.MODE_PRIVATE);
                                       SharedPreferences.Editor editor = prefs_register.edit();
                                       editor.putString("USER_ID", user_id);
                                       editor.putString("USER_NAME", user_name);
                                       editor.putString("USER_EMAIL", user_email);
                                       editor.putString("USER_PHONE", user_phone);
                                       editor.putString("USER_REFERRAL", Referral_code);
                                       editor.apply();
                                       Intent goViewOtp=new Intent(ViewOtp.this,MainActivity.class);
                                       startActivity(goViewOtp);
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

                       params.put("otp",OTP_Code);
                       params.put("phone",cell_number);
                       return params;
                   }
               };
               queue.add(postRequest);
           }
       });
       resend_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressDialog = new ProgressDialog(ViewOtp.this);
               progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
               progressDialog.show();
               progressDialog.setMessage("loading....");
               progressDialog.setCancelable(false);
               final String mob_no=prefs.getString("MOBILE","");
               RequestQueue queue2 = Volley.newRequestQueue(ViewOtp.this);
               StringRequest postRequest2 = new StringRequest(Request.Method.POST, resend_Otp_url,
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
                                       String message=person.getString("message");
                                       Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                       params.put("phone", mob_no);
                       return params;
                   }
               };
               queue2.add(postRequest2);
           }
       });
    }
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
