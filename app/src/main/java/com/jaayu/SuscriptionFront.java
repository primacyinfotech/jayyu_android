package com.jaayu;

import Model.SubscriptionBenefit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaayu.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapter.SubscriptionFtureAdapter;

public class SuscriptionFront extends AppCompatActivity {
    private ImageView back_button;
    private RecyclerView feture_subscription;
    ArrayList<SubscriptionBenefit> fetureNodels;
    SubscriptionFtureAdapter subscriptionFtureAdapter;
    private Button submit_btn;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscription_front);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        from = getIntent().getStringExtra("from");
        fetureNodels = new ArrayList<>();

        back_button = toolbar.findViewById(R.id.back_button);
        feture_subscription = findViewById(R.id.feture_subscription);
        submit_btn = findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GotOaccount = new Intent(SuscriptionFront.this, SubscriptionOrder.class);
                startActivity(GotOaccount);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSubscriptionBenefit();
    }

    private void getSubscriptionBenefit() {
        StringRequest requestQueue = new StringRequest(Request.Method.GET, BaseUrl.subscriptionBenefit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<SubscriptionBenefit>>() {
                            }.getType();

                            ArrayList<SubscriptionBenefit> benefits = gson.fromJson(String.valueOf(jsonObject.getJSONArray("item")), listType);

                            subscriptionFtureAdapter = new SubscriptionFtureAdapter(benefits, SuscriptionFront.this);
                            feture_subscription.setHasFixedSize(true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                            //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                            feture_subscription.setLayoutManager(layoutManager);

                            feture_subscription.setAdapter(subscriptionFtureAdapter);
                            subscriptionFtureAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SuscriptionFront.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(SuscriptionFront.this).add(requestQueue);
    }

    /*private void getFeture(){
        fetureNodels.add(new SubscriptionBenefit(R.drawable.jts1,"Auto Order","We create order medicines er for you before your gets over."));
        fetureNodels.add(new SubscriptionBenefit(R.drawable.jys2,"Easy Cancellation","You can cancel the order anytime if you don’t want the medicines."));
        fetureNodels.add(new SubscriptionBenefit(R.drawable.jys3,"Auto Reminder","We remind you well in advance before your current supply gets over."));
        fetureNodels.add(new SubscriptionBenefit(R.drawable.jys4,"Customize Subscriptions","Customized subscription tailored according to your needs."));
        subscriptionFtureAdapter=new SubscriptionFtureAdapter(fetureNodels,SuscriptionFront.this);
        feture_subscription.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
        feture_subscription.setLayoutManager(layoutManager);

        feture_subscription.setAdapter(subscriptionFtureAdapter);
        subscriptionFtureAdapter.notifyDataSetChanged();

    }*/
    @Override
    public void onBackPressed() {
       /* Intent GotOaccount = null;
        if (from.equals("home"))
            GotOaccount = new Intent(SuscriptionFront.this, MainActivity.class);
        else
            GotOaccount = new Intent(SuscriptionFront.this, AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0, 0);*/
       super.onBackPressed();
        finish();
    }
}
