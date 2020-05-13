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
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.AccountPresPatientAdapter;
import Model.AccountPresPatientModel;

public class AccountPrescription extends AppCompatActivity {
    private ImageView back_button;
    RecyclerView patient_assign;
    LinearLayout all_prescription;
    AccountPresPatientAdapter accountPresPatientAdapter;
    ArrayList<AccountPresPatientModel> accountPresPatientModels;
    SharedPreferences prefs_register;
    private String u_id;
    //private String patient_assign_list_url= BaseUrl.BaseUrlNew+"patient_assign_list";
    private String patient_assign_list_url= BaseUrl.BaseUrlNew+"patient_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_prescription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        patient_assign=(RecyclerView)findViewById(R.id.patient_assign);
        all_prescription=(LinearLayout)findViewById(R.id.all_prescription);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(AccountPrescription.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        all_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAssignPrescription=new Intent(AccountPrescription.this,AccounPrescriptionView.class);
                startActivity(gotoAssignPrescription);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getUpdateAssignPatient();

    }
    private void getUpdateAssignPatient(){
        accountPresPatientModels=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(AccountPrescription.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,patient_assign_list_url,
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
                                JSONArray jsonArray=person.getJSONArray("patient_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    AccountPresPatientModel assignPrescriptionModel=new AccountPresPatientModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    assignPrescriptionModel.setPatient_id(object.getInt("id"));
                                    assignPrescriptionModel.setPatient_name(object.getString("name"));

                                    assignPrescriptionModel.setPrescription_count(object.getInt("pres_count"));
                                    accountPresPatientModels.add(assignPrescriptionModel);

                                }
                                accountPresPatientAdapter=new AccountPresPatientAdapter(accountPresPatientModels,AccountPrescription.this);
                                patient_assign.setHasFixedSize(true);
                                patient_assign.setLayoutManager(new LinearLayoutManager(AccountPrescription.this));
                                patient_assign.setAdapter(accountPresPatientAdapter);
                                accountPresPatientAdapter.notifyDataSetChanged();

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
        )
        {
            @Override
            protected Map<String, String> getParams ()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(AccountPrescription.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
