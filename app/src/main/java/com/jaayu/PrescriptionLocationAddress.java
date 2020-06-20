package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.AddressPresAdapter;
import Model.AddressModel;

public class PrescriptionLocationAddress extends AppCompatActivity {
    RecyclerView address_list;
    AddressPresAdapter mAdapter;
    private ImageView back_button;
    private ArrayList<AddressModel> modelList;
    private String order_addressUrl = BaseUrl.BaseUrlNew+"order_address";
    SharedPreferences prefs_register;
    private String u_id;
    FloatingActionButton actionButton;
    SharedPreferences prefs_Address_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_location_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        actionButton = (FloatingActionButton) findViewById(R.id.add_new_address);
        address_list = (RecyclerView) findViewById(R.id.address_list);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        prefs_Address_pin = getSharedPreferences(
                "Address pin Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        fetchAddress();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddress = new Intent(PrescriptionLocationAddress.this, AddresAddActivity.class);
                gotoAddress.putExtra("FIELD_REQ","2");
                startActivityForResult(gotoAddress,007);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescriptionLocationAddress.this, PrescriptionOrderSummery.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAddress();
    }

    private void fetchAddress() {

        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionLocationAddress.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, order_addressUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = person.getJSONArray("address");
                                modelList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    AddressModel addressModel = new AddressModel();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String first_name = object.getString("first_name");
                                    //String last_name=object.getString("last_name");
                                    String fullname = first_name;

                                    String phone = object.getString("phone");
                                    //String email=object.getString("email");
                                    String address = object.getString("address");
                                    // String country=object.getString("country");
                                    // String state=object.getString("state");
                                    // String city=object.getString("city");
                                    String landMark=object.getString("landmark");
                                    String zip_code = object.getString("zip_code");
                                    SharedPreferences.Editor editor = prefs_Address_pin.edit();
                                    editor.putString("SELECTED_PIN", zip_code);
                                    editor.commit();
                                   // String all_address = address + "\n" + "Pin Code-" + zip_code + "," + "\n" + "phone:" + phone;
                                    String all_address = address;
                                    addressModel.setAdd_id(object.getInt("id"));
                                    addressModel.setAddress_pref(object.getString("atype"));
                                    addressModel.setName(fullname);
                                    addressModel.setAddress(all_address);
                                    addressModel.setZip_code(zip_code);
                                    addressModel.setLanmark(landMark);
                                    addressModel.setPhone(phone);

                                    modelList.add(addressModel);


                                }
                                mAdapter = new  AddressPresAdapter(modelList, PrescriptionLocationAddress.this);
                                address_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                address_list.setLayoutManager(layoutManager);

                                address_list.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();

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


                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
       /* super.onBackPressed();
        finish();*/
        Intent intent = new Intent(PrescriptionLocationAddress.this, PrescriptionOrderSummery.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}