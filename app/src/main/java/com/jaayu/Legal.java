package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.LegalAdapter;
import Adapter.NormalWalletAdapter;
import Model.LegalModel;
import Model.NormalWalletModel;

public class Legal extends AppCompatActivity {
  RecyclerView legal_list;
    private ImageView back_button;
    LegalAdapter legalAdapter;
    ArrayList<LegalModel> legalModels;
    private String Legal_Url= BaseUrl.BaseUrlNew+"jaayu_legal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        legal_list=(RecyclerView)findViewById(R.id.legal_list);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(Legal.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        GetLegal();
    }
    private void GetLegal() {
        RequestQueue requestQueue = Volley.newRequestQueue(Legal.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Legal_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");


                            if (status.equals("1")) {

                                JSONArray jsonArray = person.getJSONArray("legal");
                                legalModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    LegalModel legalModel = new LegalModel();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    legalModel.setLegal_head(object.getString("heads"));
                                    legalModel.setLegal_content(object.getString("conts"));
                                    legalModels.add(legalModel);

                                }
                                legalAdapter = new LegalAdapter(legalModels, Legal.this);
                                legal_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                legal_list.setLayoutManager(layoutManager);

                                legal_list.setAdapter(legalAdapter);
                                legalAdapter.notifyDataSetChanged();

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

                // params.put("user_id" ,u_id);
                /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
        @Override
        public void onBackPressed() {
            Intent GotOaccount=new Intent(Legal.this,AccountPage.class);
            startActivity(GotOaccount);
            overridePendingTransition(0,0);
            finish();
        }
    }

