package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.AccountPageListAdapter;
import Model.AccountPageListModel;
import Model.ClickListener;
import Model.RecyclerTouchListener;
import Model.SaveCoupon;

public class AccountPage extends AppCompatActivity {
    SaveCoupon myDb;
    RecyclerView recyclerView;
    AccountPageListAdapter accountPageListAdapter;
    ArrayList<AccountPageListModel> ac_List;
    TextView edit_profile, customer_name, customer_phone, customer_email;
    private String login_usr__url = BaseUrl.BaseUrlNew + "login_user_details";
    SharedPreferences prefs_register;
    private String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        myDb = new SaveCoupon(this);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        recyclerView = (RecyclerView) findViewById(R.id.account_list_view);
        edit_profile = (TextView) findViewById(R.id.edit_profile);
        customer_name = (TextView) findViewById(R.id.customer_name);
        customer_phone = (TextView) findViewById(R.id.customer_phone);
        customer_email = (TextView) findViewById(R.id.customer_email);
        getProfileDetails();
        ac_List = new ArrayList<>();
        ac_List.add(new AccountPageListModel(R.drawable.order, "My Order"));
        ac_List.add(new AccountPageListModel(R.drawable.subscriptions, "Subscription"));
        ac_List.add(new AccountPageListModel(R.drawable.addressd, "Manage Address"));
        ac_List.add(new AccountPageListModel(R.drawable.prescripstion, "Prescription"));
        ac_List.add(new AccountPageListModel(R.drawable.manage_patient, "Manage Patient"));
        ac_List.add(new AccountPageListModel(R.drawable.wallet, "Wallet"));
        ac_List.add(new AccountPageListModel(R.drawable.wallet, "Jaayu Wallet"));
        ac_List.add(new AccountPageListModel(R.drawable.help, "Help"));
        ac_List.add(new AccountPageListModel(R.drawable.legal, "Legal"));
        ac_List.add(new AccountPageListModel(R.drawable.mpi, "About Us"));
        ac_List.add(new AccountPageListModel(R.drawable.logoutp, "Logout"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountPageListAdapter = new AccountPageListAdapter(ac_List, this);
        recyclerView.setAdapter(accountPageListAdapter);
        accountPageListAdapter.notifyDataSetChanged();
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GoToProfile = new Intent(AccountPage.this, ProfileActivity.class);
                startActivity(GoToProfile);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 10) {
                    myDb.deleteData();
                    SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor e = sp.edit();
                    e.clear();
                    e.apply();
                    SharedPreferences prefs_register = getSharedPreferences(
                            "Register Details", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs_register.edit();
                    edit.clear();
                    edit.apply();
                    Intent goTOLogin = new Intent(AccountPage.this, LoginSignUp.class);
                    startActivity(goTOLogin);
                    finishAffinity();
                }
                if (position == 1) {
                    Intent goToSubscription = new Intent(AccountPage.this, SuscriptionFront.class);
                    startActivity(goToSubscription);
                }
                if (position == 2) {
                    Intent goToManageAddress = new Intent(AccountPage.this, ManagaAddressView.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                }
                if (position == 0) {
                    Intent goToOrder = new Intent(AccountPage.this, OrderPage.class);
                    startActivity(goToOrder);
                }
                if (position == 8) {
                    Intent goToManageAddress = new Intent(AccountPage.this, Legal.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }
                if (position == 5) {
                    Intent goToManageAddress = new Intent(AccountPage.this, Wallet.class);

                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }
                if (position == 7) {
                    Intent goToManageAddress = new Intent(AccountPage.this, Help.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    //finish();
                }
                if (position == 6) {
                    Intent goToManageAddress = new Intent(AccountPage.this, JaayuWallet.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }
                if (position == 4) {
                    Intent goToManageAddress = new Intent(AccountPage.this, PatientListView.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }
                if (position == 3) {
                    Intent goToManageAddress = new Intent(AccountPage.this, AccountPrescription.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }
                if ((position == 9)) {
                    Intent goToManageAddress = new Intent(AccountPage.this, AboutUS.class);
                    startActivity(goToManageAddress);
                    overridePendingTransition(0, 0);
                    finish();
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                 /*Intent goToManageAddress=new Intent(AccountPage.this,Wallet.class);
                 startActivity(goToManageAddress);
                 overridePendingTransition(0,0);
                 finish();*/
            }
        }));

    }

    private void getProfileDetails() {
        RequestQueue requestQueue = Volley.newRequestQueue(AccountPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, login_usr__url,
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
                                String name = person.getString("name");
                                String email = person.getString("email");
                                String phone = person.getString("phone");
                                customer_name.setText(name);
                                customer_email.setText(email);
                                customer_phone.setText(phone);
                            }
                            /*else {
                                card_view_istant.setVisibility(View.GONE);
                            }
*/


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
                params.put("user_id", u_id);
                return params;
            }

        };
        requestQueue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountPage.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
