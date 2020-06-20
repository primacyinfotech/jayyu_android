package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Adapter.SubscriptionOrderAdapter;
import Model.SubscriptionOrderModel;

public class SubscriptionOrder extends AppCompatActivity {
  private RecyclerView list_subscription;
    private ImageView back_button;
    private String subscription_order_list_url= BaseUrl.BaseUrlNew+"subscription_order_list";
    SharedPreferences prefs_register;
    ArrayList<SubscriptionOrderModel>  subscriptionOrderModels;
   SubscriptionOrderAdapter subscriptionOrderAdapter;
   String u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
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
      /*  subscriptionOrderModels.add(new SubscriptionOrderModel("JY1234567","25 Mar 2020"));
        subscriptionOrderModels.add(new SubscriptionOrderModel("JY123999","7 Mar 2020"));
        subscriptionOrderModels.add(new SubscriptionOrderModel("JY1789","25 Mar 2020"));
        subscriptionOrderAdapter=new SubscriptionOrderAdapter(subscriptionOrderModels,SubscriptionOrder.this);
        list_subscription.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
        list_subscription.setLayoutManager(layoutManager);

        list_subscription.setAdapter(subscriptionOrderAdapter);
        subscriptionOrderAdapter.notifyDataSetChanged();*/

        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionOrder.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,subscription_order_list_url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        // Loop through the array elements
                        try {
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");
                            if(status.equals("1")) {
                                JSONArray jsonArray=person.getJSONArray("subs_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    SubscriptionOrderModel subscriptionOrderModel=new SubscriptionOrderModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    subscriptionOrderModel.setSubscription_tbl_id(object.getInt("id"));
                                    subscriptionOrderModel.setSubscription_order(object.getString("order_id"));
                                   String date = object.getString("date");
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
                                        Date testDate = sdf.parse(date);
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
                                        date = formatter.format(testDate);
                                    }catch (ParseException parseError){
                                    }
                                    subscriptionOrderModel.setOrd_date(date);
                                    subscriptionOrderModels.add(subscriptionOrderModel);

                                }
                                subscriptionOrderAdapter=new SubscriptionOrderAdapter(subscriptionOrderModels,SubscriptionOrder.this);
                                list_subscription.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                list_subscription.setLayoutManager(layoutManager);
                                list_subscription.setAdapter(subscriptionOrderAdapter);
                                subscriptionOrderAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id" , u_id);
                // params.put("user_id" ,"35");

                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(SubscriptionOrder.this,SuscriptionFront.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
