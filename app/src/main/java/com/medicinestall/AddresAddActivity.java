package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.medicinestall.Model.BaseUrl;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddresAddActivity extends AppCompatActivity {
    private ImageView back_button;
    MultiStateToggleButton mstb_multi_id;
    String work_pref,work_pref2,f_nm,l_nm,mob_num,pin_no,addr,l_mark,e_mail,count,state,city;
    private EditText first_name,last_name,mob_number,pin_number,address,lan_mark,email_add,country,state_mark,city_mark,other_mark;
    private TextView label_other;
    private Button save;
    private String u_id;
    SharedPreferences prefs_register;
    private  String add_addressUrl= BaseUrl.BaseUrlNew+"order_address_add";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addres_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        mstb_multi_id=(MultiStateToggleButton)findViewById(R.id.mstb_multi_id);
        first_name=(EditText)findViewById(R.id.first_name);
        last_name=(EditText)findViewById(R.id.last_name);
        mob_number=(EditText)findViewById(R.id.mob_number);

        pin_number=(EditText)findViewById(R.id.pin_number);
        address=(EditText)findViewById(R.id.address);
        lan_mark=(EditText)findViewById(R.id.lan_mark);
        email_add=(EditText)findViewById(R.id.email_add);
       // country=(EditText)findViewById(R.id.country);
        state_mark=(EditText)findViewById(R.id.state_mark);
        city_mark=(EditText)findViewById(R.id.city_mark);
        other_mark=(EditText)findViewById(R.id.other_mark);
        label_other=(TextView)findViewById(R.id.label_other);
        other_mark.setVisibility(View.GONE);
        label_other.setVisibility(View.GONE);
        save=(Button)findViewById(R.id.save);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLocationAddress=new Intent(AddresAddActivity.this,LocationAddress.class);
                startActivity(gotoLocationAddress);
                finish();
            }
        });
        mstb_multi_id.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
               // Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
                if(value==0){
                    other_mark.setVisibility(View.GONE);
                    label_other.setVisibility(View.GONE);
                    work_pref="Home";
                    //Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                }
                else if(value==1){
                    other_mark.setVisibility(View.GONE);
                    label_other.setVisibility(View.GONE);
                    work_pref="work";
                   // Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                }
                else if(value==2){
                    other_mark.setVisibility(View.VISIBLE);
                    label_other.setVisibility(View.VISIBLE);
                    //work_pref="Other";
                  //  Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_nm=first_name.getText().toString();
                l_nm=last_name.getText().toString();
                mob_num=mob_number.getText().toString();
                pin_no=pin_number.getText().toString();
                addr=address.getText().toString();
                l_mark=lan_mark.getText().toString();
                e_mail=email_add.getText().toString();
               // count=country.getText().toString();
                state=state_mark.getText().toString();
                city=city_mark.getText().toString();
                work_pref2=other_mark.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(AddresAddActivity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,add_addressUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    //Do it with this it will work
                                    JSONObject person = new JSONObject(response);
                                    String status=person.getString("status");
                                    if(status.equals("1")){
                                        Intent gotoLocationAddress=new Intent(AddresAddActivity.this,LocationAddress.class);
                                        startActivity(gotoLocationAddress);
                                    }
                                    else if(status.equals("2")) {
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }
                                    else if(status.equals("0")){
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }



                                } catch (JSONException e) {
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

                        params.put("user_id", u_id);
                        params.put("first_name", f_nm);
                        params.put("last_name", l_nm);
                        params.put("phone", mob_num);
                        params.put("email", e_mail);
                        params.put("address", addr);
                        params.put("landmark", l_mark);
                        params.put("state", state);
                        params.put("city", city);
                        params.put("zip_code", pin_no);
                        if(work_pref2.equals("")){
                            params.put("atype", work_pref);
                        }
                        else {
                            params.put("atype", work_pref2);
                        }

                        return params;
                    }
                };

                requestQueue.add(postRequest);
            }
        });
    }
}
