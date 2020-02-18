package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import Adapter.AccountPageListAdapter;
import Adapter.Search_adapter;
import Model.AccountPageListModel;
import Model.ClickListener;
import Model.RecyclerTouchListener;
import Model.Searchmodel;

public class AccountPage extends AppCompatActivity {
    RecyclerView recyclerView;
    AccountPageListAdapter accountPageListAdapter;
    ArrayList<AccountPageListModel> ac_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        recyclerView=(RecyclerView)findViewById(R.id.account_list_view);
        ac_List=new ArrayList<>();
        ac_List.add(new AccountPageListModel(R.drawable.icon_order,"Order"));
        ac_List.add(new AccountPageListModel(R.drawable.subscription,"Subscription"));
        ac_List.add(new AccountPageListModel(R.drawable.prescription,"Prescription"));
        ac_List.add(new AccountPageListModel(R.drawable.wallet,"Wallet"));
        ac_List.add(new AccountPageListModel(R.drawable.patient,"Mange Patient"));
        ac_List.add(new AccountPageListModel(R.drawable.address,"Manage Address"));
        ac_List.add(new AccountPageListModel(R.drawable.help,"Need Help"));
        ac_List.add(new AccountPageListModel(R.drawable.legal,"Legal"));
        ac_List.add(new AccountPageListModel(R.drawable.logout,"Logout"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountPageListAdapter=new AccountPageListAdapter(ac_List,this);
        recyclerView.setAdapter(accountPageListAdapter);
        accountPageListAdapter.notifyDataSetChanged();
         recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
             @Override
             public void onClick(View view, int position) {
                 if(position==8){
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
             }

             @Override
             public void onLongClick(View view, int position) {

             }
         }));

    }
}
