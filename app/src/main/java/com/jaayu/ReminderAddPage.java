package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jaayu.Model.TimerDatabase;

import java.util.ArrayList;

import Adapter.ReminderTimeAdapter;
import Model.ReminderTimeModel;

public class ReminderAddPage extends AppCompatActivity {
 private Spinner edit_medicine,days_count,month_date;
 RadioButton daily_time,until_stop,weekly_time,monthly_time;
 LinearLayout section_daily_time,section_of_week,section_of_month,new_time;
 private CheckBox sunday,monday,tuesday,wednessday,thirstday,friday,saturday;
 RecyclerView  timer_list;
 int clickCount=0;
    int hour, mhour, minute;
    String format;
    TimerDatabase timerDatabase;
    ReminderTimeAdapter reminderTimeAdapter;
    ArrayList<ReminderTimeModel> reminderTimeModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_add_page);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_time_intent"));

        timerDatabase=new TimerDatabase(this);
         reminderTimeModels=new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edit_medicine=(Spinner)findViewById(R.id.edit_medicine);
        days_count=(Spinner)findViewById(R.id.days_count);
        month_date=(Spinner)findViewById(R.id.month_date);
        daily_time=(RadioButton)findViewById(R.id.daily_time);
        until_stop=(RadioButton)findViewById(R.id.until_stop);
        weekly_time=(RadioButton)findViewById(R.id.weekly_time);
        monthly_time=(RadioButton)findViewById(R.id.monthly_time);
        section_daily_time=(LinearLayout)findViewById(R.id.section_daily_time);
        section_of_week=(LinearLayout)findViewById(R.id.section_of_week);
        section_of_month=(LinearLayout)findViewById(R.id.section_of_month);
        new_time=(LinearLayout)findViewById(R.id.new_time);
        sunday=(CheckBox)findViewById(R.id.sunday);
        monday=(CheckBox)findViewById(R.id.monday);
        tuesday=(CheckBox)findViewById(R.id.tuesday);
        wednessday=(CheckBox)findViewById(R.id.wednessday);
        thirstday=(CheckBox)findViewById(R.id.thirstday);
        friday=(CheckBox)findViewById(R.id.friday);
        saturday=(CheckBox)findViewById(R.id.saturday);
        timer_list=(RecyclerView)findViewById(R.id.timer_list);
        section_daily_time.setVisibility(View.GONE);
        section_of_week.setVisibility(View.GONE);
        section_of_month.setVisibility(View.GONE);
        new_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount=clickCount+1;
                if(clickCount<=3){

                    TimePickerDialog timePickerDialog=new TimePickerDialog(ReminderAddPage.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            hour = selectedTimeFormat(hour);
                            String time_input=hour+":"+minute+format;
                            boolean isUpdate=timerDatabase.insertData(time_input);
                            if(isUpdate){
                                Intent intent= new Intent("message_time_intent");

                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                LocalBroadcastManager.getInstance(ReminderAddPage.this).sendBroadcast(intent);
                                overridePendingTransition(0,0);
                            }
                            else {

                            }

                        }
                    },hour,minute,true);
                    timePickerDialog.show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Stop",Toast.LENGTH_LONG).show();
                    new_time.setEnabled(true);
                }
            }
        });

        getTime();

    }
    public int selectedTimeFormat(int hour){

        if(hour == 0){
            hour += 12;
            format = "AM";
        } else if(hour == 12){
            format = "PM";
        } else if(hour > 12){
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        return hour;

    }
    private void getTime(){
    reminderTimeModels.clear();
        Cursor c=timerDatabase.getAllData();
        while (c.moveToNext()){
            ReminderTimeModel reminderTimeModel=new ReminderTimeModel();
            reminderTimeModel.setTime_scheduled(c.getString(1));
            reminderTimeModels.add(reminderTimeModel);

        }
        reminderTimeAdapter=new ReminderTimeAdapter(reminderTimeModels,ReminderAddPage.this);
        timer_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
        timer_list.setLayoutManager(layoutManager);

        timer_list.setAdapter(reminderTimeAdapter);
        reminderTimeAdapter.notifyDataSetChanged();


    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTime();
        }
    };

}
