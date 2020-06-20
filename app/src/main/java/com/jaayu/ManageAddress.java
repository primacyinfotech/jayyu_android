package com.jaayu;

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
import com.jaayu.Model.BaseUrl;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManageAddress extends AppCompatActivity {
    private EditText first_name, last_name, mob_number, email_add, pin_number, address, lan_mark, city_mark, state_mark, other_mark;
    private ImageView back_button;
    MultiStateToggleButton mstb_multi_id;
    String work_pref, work_pref2, f_nm, l_nm, mob_num, pin_no, addr, l_mark, e_mail, count, state, city;
    private Button save;
    private String u_id;
    private TextView label_other;
    int address_id;
    SharedPreferences prefs_register;
    private String fetch_addressUrl = BaseUrl.BaseUrlNew + "order_address_single";
    private String update_addressUrl = BaseUrl.BaseUrlNew + "order_address_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        Intent fetchADDID = getIntent();
        address_id = fetchADDID.getIntExtra("ADDRESS_ID", 0);
        mstb_multi_id = (MultiStateToggleButton) findViewById(R.id.mstb_multi_id);
        first_name = (EditText) findViewById(R.id.first_name);
        /* last_name=(EditText)findViewById(R.id.last_name);*/
        mob_number = (EditText) findViewById(R.id.mob_number);
        pin_number = (EditText) findViewById(R.id.pin_number);
        address = (EditText) findViewById(R.id.address);
        lan_mark = (EditText) findViewById(R.id.lan_mark);
        // email_add=(EditText)findViewById(R.id.email_add);
        // country=(EditText)findViewById(R.id.country);
      /*  state_mark=(EditText)findViewById(R.id.state_mark);
        city_mark=(EditText)findViewById(R.id.city_mark);*/
        other_mark = (EditText) findViewById(R.id.other_mark);
        label_other = (TextView) findViewById(R.id.label_other);
        other_mark.setVisibility(View.GONE);
        label_other.setVisibility(View.GONE);
        save = (Button) findViewById(R.id.save);
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent gotoLocationAddress = new Intent(ManageAddress.this, ManagaAddressView.class);
                startActivity(gotoLocationAddress);
                finish();*/
                onBackPressed();
            }
        });
        mstb_multi_id.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                // Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
                if (value == 0) {
                    other_mark.setVisibility(View.GONE);
                    label_other.setVisibility(View.GONE);
                    work_pref = "Home";
                    //  Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                } else if (value == 1) {
                    other_mark.setVisibility(View.GONE);
                    label_other.setVisibility(View.GONE);
                    work_pref = "Work";
                    // Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                } else if (value == 2) {
                    other_mark.setVisibility(View.VISIBLE);
                    label_other.setVisibility(View.VISIBLE);

                    // work_pref="Other";
                    // Toast.makeText(getApplicationContext(),work_pref,Toast.LENGTH_LONG).show();
                }


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_nm = first_name.getText().toString().trim();
                //  l_nm=last_name.getText().toString();
                mob_num = mob_number.getText().toString().trim();
                pin_no = pin_number.getText().toString().trim();
                addr = address.getText().toString().trim();
                l_mark = lan_mark.getText().toString().trim();
                //e_mail=email_add.getText().toString();
                // count=country.getText().toString();
       /* state=state_mark.getText().toString();
        city=city_mark.getText().toString();*/
                work_pref2 = other_mark.getText().toString();
                if (mob_num.length() < 13) {
                    Toast.makeText(ManageAddress.this, "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
                } else if (pin_no.length() < 6) {
                    Toast.makeText(ManageAddress.this, "Invalid Pincode!", Toast.LENGTH_SHORT).show();
                } else if (l_mark.length() < 2)
                    Toast.makeText(ManageAddress.this, "Invalid Landmark!", Toast.LENGTH_SHORT).show();
             else if (addr.length() < 2)
                    Toast.makeText(ManageAddress.this, "Invalid Address!", Toast.LENGTH_SHORT).show();
                else {
                    mob_num = mob_num.substring(3,13);
                    UpdatedAddress();
                }
            }
        });
        SingleAddressShow();
    }

    private void SingleAddressShow() {
        RequestQueue requestQueue = Volley.newRequestQueue(ManageAddress.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, fetch_addressUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");
                            if (status.equals("1")) {
                                JSONObject object = person.getJSONObject("address");
                                int address_Id = object.getInt("id");
                                String firstname = object.getString("first_name");
                                /*String lastname=object.getString("last_name");*/
                                String phonenumber = object.getString("phone");
                                if (phonenumber.length() < 11)
                                    phonenumber = "+91" + phonenumber;
                                //String emailaddress=object.getString("email");
                                String addressNormal = object.getString("address");
                                String landmark = object.getString("landmark");
                               /* String stateof=object.getString("state");
                                String cityof=object.getString("city");*/
                                String zipcode = object.getString("zip_code");
                                //  int atype=object.getInt("atype");
                                first_name.setText(firstname);
                                //last_name.setText(lastname);
                                mob_number.setText(phonenumber);
                                // email_add.setText(emailaddress);
                                address.setText(addressNormal);
                                lan_mark.setText(landmark);
                                // state_mark.setText(stateof);
                                // city_mark.setText(cityof);
                                pin_number.setText(zipcode);


                            } else if (status.equals("0")) {
                                String message = person.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

                params.put("aId", String.valueOf(address_id));
                //params.put("aId", "179");

                return params;
            }
        };

        requestQueue.add(postRequest);
    }

    private void UpdatedAddress() {
        RequestQueue requestQueue = Volley.newRequestQueue(ManageAddress.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, update_addressUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");
                            if (status.equals("1")) {
                                Intent gotoLocationAddress = new Intent(ManageAddress.this, ManagaAddressView.class);
                                overridePendingTransition(0, 0);
                                startActivity(gotoLocationAddress);
                                finish();
                            } else if (status.equals("2")) {
                                String message = person.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            } else if (status.equals("0")) {
                                String message = person.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                params.put("aId", String.valueOf(address_id));
                params.put("user_id", u_id);
                params.put("first_name", f_nm);
                // params.put("last_name", l_nm);
                params.put("phone", mob_num);
                //params.put("email", e_mail);
                params.put("address", addr);
                params.put("landmark", l_mark);
                //params.put("state", state);
                // params.put("city", city);
                params.put("zip_code", pin_no);
                if (work_pref2.equals("")) {
                    params.put("atype", work_pref);
                } else {
                    params.put("atype", work_pref2);
                }


                return params;
            }
        };

        requestQueue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        /*Intent gotoLocationAddress = new Intent(ManageAddress.this, ManagaAddressView.class);
        startActivity(gotoLocationAddress);
        overridePendingTransition(0, 0);
        finish();*/
        super.onBackPressed();
        finish();
    }
}
