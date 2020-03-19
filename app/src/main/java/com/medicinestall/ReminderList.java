package com.medicinestall;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.RemniderAdapter;
import Model.ReminderModel;

public class ReminderList extends AppCompatActivity {
    RecyclerView recyclerView;
    RemniderAdapter remniderAdapter;
    private ArrayList<ReminderModel> reminderModels;
    private ImageView back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.rv_reminder);
        back_button=(ImageView)findViewById(R.id.back_button);
        reminderModels=new ArrayList<>();
        reminderModels.add(new ReminderModel("Calpol","8.00AM","1 tablet"));
        reminderModels.add(new ReminderModel("Raxin","10.00AM","1 tablet"));
        reminderModels.add(new ReminderModel("Omege","12.00AM","1 tablet"));
        reminderModels.add(new ReminderModel("Zyloric","13.00PM","1 tablet"));
        remniderAdapter=new RemniderAdapter(reminderModels,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(remniderAdapter);
        remniderAdapter.notifyDataSetChanged();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGotoMainActivity=new Intent(ReminderList.this,MainActivity.class);
                startActivity(intentGotoMainActivity);
            }
        });
    }


}
