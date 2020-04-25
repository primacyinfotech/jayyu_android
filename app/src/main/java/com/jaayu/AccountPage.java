package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

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
    TextView edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        myDb = new SaveCoupon(this);
        recyclerView=(RecyclerView)findViewById(R.id.account_list_view);
        edit_profile=(TextView)findViewById(R.id.edit_profile);
        ac_List=new ArrayList<>();
        ac_List.add(new AccountPageListModel(R.drawable.order,"My Orders"));
        ac_List.add(new AccountPageListModel(R.drawable.subscriptions,"Subscription"));
        ac_List.add(new AccountPageListModel(R.drawable.addressd,"Manage Address"));
        ac_List.add(new AccountPageListModel(R.drawable.prescripstion,"Prescription"));
        ac_List.add(new AccountPageListModel(R.drawable.manage_patient,"Manage Patient"));
        ac_List.add(new AccountPageListModel(R.drawable.wallet,"Wallet"));
        ac_List.add(new AccountPageListModel(R.drawable.wallet,"Jaayu Wallet"));
        ac_List.add(new AccountPageListModel(R.drawable.help,"Help"));
        ac_List.add(new AccountPageListModel(R.drawable.legal,"Legal"));
        ac_List.add(new AccountPageListModel(R.drawable.mpi,"About Us"));
        ac_List.add(new AccountPageListModel(R.drawable.logoutp,"Logout"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountPageListAdapter=new AccountPageListAdapter(ac_List,this);
        recyclerView.setAdapter(accountPageListAdapter);
        accountPageListAdapter.notifyDataSetChanged();
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GoToProfile=new Intent(AccountPage.this, ProfileActivity.class);
                startActivity(GoToProfile);
                overridePendingTransition(0,0);
                finish();
            }
        });
         recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
             @Override
             public void onClick(View view, int position) {
                 if(position==10){
                     myDb.deleteData();
                     SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
                     SharedPreferences.Editor e=sp.edit();
                     e.clear();
                     e.commit();
                     SharedPreferences prefs_register=getSharedPreferences(
                             "Register Details", Context.MODE_PRIVATE);
                     SharedPreferences.Editor edit=prefs_register.edit();
                     edit.clear();
                     edit.commit();
                     Intent goTOLogin=new Intent(AccountPage.this,LoginSignUp.class);
                     startActivity(goTOLogin);
                     finish();
                 }
                 if(position==2){
                     Intent goToManageAddress=new Intent(AccountPage.this,ManagaAddressView.class);
                     overridePendingTransition(0,0);
                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==0){
                     Intent goToManageAddress=new Intent(AccountPage.this,OrderPage.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==8){
                     Intent goToManageAddress=new Intent(AccountPage.this,Legal.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==5){
                     Intent goToManageAddress=new Intent(AccountPage.this,Wallet.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==7){
                     Intent goToManageAddress=new Intent(AccountPage.this,Help.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==6){
                     Intent goToManageAddress=new Intent(AccountPage.this,JaayuWallet.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==4){
                     Intent goToManageAddress=new Intent(AccountPage.this,PatientListView.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
                     finish();
                 }
                 if(position==3){
                     Intent goToManageAddress=new Intent(AccountPage.this,AccountPrescription.class);

                 startActivity(goToManageAddress);
                 overridePendingTransition(0,0);
                 finish();
             }
                 if ((position == 9)) {

                     Intent goToManageAddress=new Intent(AccountPage.this,AboutUS.class);

                     startActivity(goToManageAddress);
                     overridePendingTransition(0,0);
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountPage.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
