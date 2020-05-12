package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.HelpAdapter;
import Adapter.SubscriptionFtureAdapter;
import Model.HelpModel;
import Model.SuscriptionFetureNodel;

public class SuscriptionFront extends AppCompatActivity {
    private ImageView back_button;
    private RecyclerView feture_subscription;
    ArrayList<SuscriptionFetureNodel> fetureNodels;
    SubscriptionFtureAdapter subscriptionFtureAdapter;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription_front);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView) toolbar.findViewById(R.id.back_button);
        fetureNodels=new ArrayList<>();
        feture_subscription=(RecyclerView)findViewById(R.id.feture_subscription);
        submit_btn=(Button)findViewById(R.id.submit_btn) ;
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GotOaccount=new Intent(SuscriptionFront.this,SubscriptionOrder.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(SuscriptionFront.this,MainActivity.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getFeture();
    }
    private void getFeture(){
        fetureNodels.add(new SuscriptionFetureNodel(R.drawable.jts1,"Auto Order","We create order medicines er for you before your gets over."));
        fetureNodels.add(new SuscriptionFetureNodel(R.drawable.jys2,"Easy Cancellation","You can cancel the order anytime if you don’t want the medicines."));
        fetureNodels.add(new SuscriptionFetureNodel(R.drawable.jys3,"Auto Reminder","We remind you well in advance before your current supply gets over."));
        fetureNodels.add(new SuscriptionFetureNodel(R.drawable.jys4,"Customize Subscriptions","Customized subscription tailored according to your needs."));
        subscriptionFtureAdapter=new SubscriptionFtureAdapter(fetureNodels,SuscriptionFront.this);
        feture_subscription.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
        feture_subscription.setLayoutManager(layoutManager);

        feture_subscription.setAdapter(subscriptionFtureAdapter);
        subscriptionFtureAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(SuscriptionFront.this,MainActivity.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
