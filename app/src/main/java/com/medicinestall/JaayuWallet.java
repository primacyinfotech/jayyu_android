package com.medicinestall;

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
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.JaayuWalletAdapter;
import Model.NormalWalletModel;

public class JaayuWallet extends AppCompatActivity {
    RecyclerView wallet_history;
    JaayuWalletAdapter jaayuWalletAdapter;
    ArrayList<NormalWalletModel> normalWalletModels;
    SharedPreferences prefs_register;
    String u_id;
    TextView balance;
    private String Jaayu_wallet_history_url= BaseUrl.BaseUrlNew+"jaayu_wallet_display";
    private ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jaayu_wallet);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        wallet_history=(RecyclerView)findViewById(R.id.wallet_history);
        balance=(TextView)findViewById(R.id.balance);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(JaayuWallet.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getJWalletHistory();
    }
    private void getJWalletHistory(){
        RequestQueue requestQueue = Volley.newRequestQueue(JaayuWallet.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Jaayu_wallet_history_url,
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
                                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    SimpleDateFormat output = new SimpleDateFormat("MMMM dd,yyyy");
                                    Date date=input.parse(fetch_date);
                                    String newFDate=output.format(date);

                                    walletModel.setDate_val(newFDate);
                                    normalWalletModels.add(walletModel);

                                }
                                jaayuWalletAdapter=new JaayuWalletAdapter(normalWalletModels,JaayuWallet.this);
                                wallet_history.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                wallet_history.setLayoutManager(layoutManager);

                                wallet_history.setAdapter(jaayuWalletAdapter);
                                jaayuWalletAdapter.notifyDataSetChanged();

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
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
               /*  params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(JaayuWallet.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
