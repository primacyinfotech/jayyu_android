package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OfferPage extends AppCompatActivity {
    private ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(OfferPage.this,MainActivity.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(OfferPage.this,MainActivity.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
