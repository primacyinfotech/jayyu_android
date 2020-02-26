package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapter.AddressAdapter;
import Model.AddressModel;

public class ManagaAddressView extends AppCompatActivity {
    RecyclerView address_list;
    AddressAdapter mAdapter;
    private ImageView back_button;
    private ArrayList<AddressModel> modelList;
    private  String order_addressUrl="https://work.primacyinfotech.com/jaayu/api/order_address";
    SharedPreferences prefs_register;
    private String u_id;
    FloatingActionButton actionButton;
    SharedPreferences prefs_Address_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managa_address_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        actionButton=(FloatingActionButton)findViewById(R.id.add_new_address) ;
        address_list=(RecyclerView)findViewById(R.id.address_list);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        prefs_Address_pin = getSharedPreferences(
                "Address pin Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        fetchAddress();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddress=new Intent(ManagaAddressView.this,AddresAddActivity.class);
                startActivity(gotoAddress);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagaAddressView.this, OrderSummery.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    private void fetchAddress(){

    }
}
