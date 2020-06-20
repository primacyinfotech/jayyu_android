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
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.NormalWalletAdapter;
import Model.NormalWalletModel;

public class Wallet extends AppCompatActivity {
  RecyclerView wallet_history;
    NormalWalletAdapter normalWalletAdapter;
    private ImageView back_button;
    ArrayList<NormalWalletModel> normalWalletModels;
    private String wallet_history_url= BaseUrl.BaseUrlNew+"normal_wallet_display";
    SharedPreferences prefs_register;
    String u_id;
     TextView balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        wallet_history=(RecyclerView)findViewById(R.id.wallet_history);
        balance=(TextView)findViewById(R.id.balance);
        getWalletHistory();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(Wallet.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    private void  getWalletHistory(){
        RequestQueue requestQueue = Volley.newRequestQueue(Wallet.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,wallet_history_url,
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
                                String balance_og=person.getString("balance");
                                try {
                                    if (Double.parseDouble(balance_og) < 0) {
                                        balance_og = "0";
                                    }
                                } catch (Exception e) {
                                    balance_og = "0";
                                }
                                balance.setText("\u20B9"+balance_og);
                                normalWalletModels=new ArrayList<>();
                                JSONArray jsonArray=person.getJSONArray("history");
                                for(int i=0;i<jsonArray.length();i++){
                                    NormalWalletModel walletModel=new NormalWalletModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    walletModel.setDebit(object.getString("debit"));
                                    walletModel.setCredit(object.getString("credit"));
                                    walletModel.setRemark(object.getString("remark"));
                                    String fetch_date=object.getString("created_at");
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        Date testDate = sdf.parse(fetch_date);
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM,yyyy");
                                        fetch_date = formatter.format(testDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    walletModel.setDate_val(fetch_date);
                                    normalWalletModels.add(walletModel);

                                }
                                Collections.reverse(normalWalletModels);
                                normalWalletAdapter=new NormalWalletAdapter(normalWalletModels,Wallet.this);
                                wallet_history.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                wallet_history.setLayoutManager(layoutManager);

                                wallet_history.setAdapter(normalWalletAdapter);
                                normalWalletAdapter.notifyDataSetChanged();

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

                params.put("user_id" ,u_id);
               /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(Wallet.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
