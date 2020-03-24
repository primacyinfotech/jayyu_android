package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

public class ReminderAddPage extends AppCompatActivity {
 private Spinner edit_medicine,days_count,month_date;
 RadioButton daily_time,until_stop,weekly_time,monthly_time;
 LinearLayout section_daily_time,section_of_week,section_of_month,new_time;
 private CheckBox sunday,monday,tuesday,wednessday,thirstday,friday,saturday;
 RecyclerView  timer_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_add_page);
    }
}
