package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AccountPrescription extends AppCompatActivity {
    private ImageView back_button;
    RecyclerView patient_assign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_prescription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        patient_assign=(RecyclerView)findViewById(R.id.patient_assign);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(AccountPrescription.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(AccountPrescription.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
