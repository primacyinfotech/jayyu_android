package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.SubscriptionFtureAdapter;
import Adapter.SubscriptionOrderAdapter;
import Model.SubscriptionOrderModel;
import Model.SuscriptionFetureNodel;

public class SubscriptionOrder extends AppCompatActivity {
  private RecyclerView list_subscription;
    private ImageView back_button;

    ArrayList<SubscriptionOrderModel>  subscriptionOrderModels;
   SubscriptionOrderAdapter subscriptionOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subscriptionOrderModels=new ArrayList<>();
        back_button=(ImageView) toolbar.findViewById(R.id.back_button);
        list_subscription=(RecyclerView)findViewById(R.id.list_subscription);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(SubscriptionOrder.this,SuscriptionFront.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getOrdSubscription();
    }
    private void getOrdSubscription(){
        subscriptionOrderModels.add(new SubscriptionOrderModel("JY1234567","25 Mar 2020"));
        subscriptionOrderModels.add(new SubscriptionOrderModel("JY123999","7 Mar 2020"));
        subscriptionOrderModels.add(new SubscriptionOrderModel("JY1789","25 Mar 2020"));
        subscriptionOrderAdapter=new SubscriptionOrderAdapter(subscriptionOrderModels,SubscriptionOrder.this);
        list_subscription.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
        list_subscription.setLayoutManager(layoutManager);

        list_subscription.setAdapter(subscriptionOrderAdapter);
        subscriptionOrderAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(SubscriptionOrder.this,SuscriptionFront.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
