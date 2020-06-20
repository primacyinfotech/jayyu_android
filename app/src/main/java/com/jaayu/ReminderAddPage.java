package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.ReminderDataFromServerDatabase;
import com.jaayu.Model.TimerDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.OldPrescriptionAdapter;
import Adapter.ReminderTimeAdapter;
import Model.ReminderTimeModel;

public class ReminderAddPage extends AppCompatActivity {
 private Spinner edit_medicine,days_count,month_date,week_count,month_count;
 RadioButton daily_time,until_stop,weekly_time,monthly_time,until_stop_week,until_stop_month;
 LinearLayout section_daily_time,section_of_week,section_of_month,new_time;
 private CheckBox sunday,monday,tuesday,wednessday,thirstday,friday,saturday;
 String s_day,m_day,t_day,w_day,th_day,f_day,sat_day,daytime,weektime,monthtime,SendWeek,time_view_list,daySpin,monthSpin,
    weekcountSpinn,monthcountSpinner,repeat,duration,medSelected;
 RecyclerView timer_list;
 private Button btn_save;
 ArrayList<String> daycountList;
    ArrayList<String> mothList;
    ArrayList<String> medicinList;
    ArrayList<String> weekList;
    ArrayList<String> weekListcount;
    ArrayList<String> monthListcount;
    ArrayList<String>allWeekList=new ArrayList<>();
    ArrayList<String>timegetList;
    List<String> All_Plist_two=new ArrayList<>();
    List<String>[] old_Plist_two;
    List<String>l;
    SharedPreferences prefs_register;
    private String u_id;
    ReminderDataFromServerDatabase reminderDataFromServerDatabase;

 int clickCount=0;
    int hour, mhour, minute;
    String fetchTIME;
    String format;
    TimerDatabase timerDatabase;
    ReminderTimeAdapter reminderTimeAdapter;
    ArrayList<ReminderTimeModel> reminderTimeModels;
    private String medicineList_url= BaseUrl.BaseUrlNew+"jaayu_medicine_list";
    private String addreminder_url= BaseUrl.BaseUrlNew+"jaayu_reminder";
    private String fetchreminder_url= BaseUrl.BaseUrlNew+"jaayu_reminder_listing";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_add_page);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_time_intent"));

        timerDatabase=new TimerDatabase(this);
        reminderDataFromServerDatabase=new ReminderDataFromServerDatabase(this);
         reminderTimeModels=new ArrayList<>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edit_medicine=(Spinner)findViewById(R.id.edit_medicine);
        edit_medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medSelected=edit_medicine.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        days_count=(Spinner)findViewById(R.id.days_count);
        daycountList=new ArrayList<>();
        daycountList.add("1");
        daycountList.add("2");
        daycountList.add("3");
        daycountList.add("4");
        daycountList.add("5");
        daycountList.add("6");
        daycountList.add("7");
        daycountList.add("8");
        daycountList.add("9");
        daycountList.add("10");
        daycountList.add("11");
        daycountList.add("12");
        daycountList.add("13");
        daycountList.add("14");
        daycountList.add("15");
        daycountList.add("16");
        daycountList.add("17");
        daycountList.add("18");
        daycountList.add("19");
        daycountList.add("20");
        daycountList.add("21");
        daycountList.add("22");
        daycountList.add("23");
        daycountList.add("24");
        daycountList.add("26");
        daycountList.add("27");
        daycountList.add("28");
        daycountList.add("29");
        daycountList.add("30");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, daycountList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_count.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        days_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daySpin=days_count.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        month_date=(Spinner)findViewById(R.id.month_date);
        mothList=new ArrayList<>();
        mothList.add("1st");
        mothList.add("2nd");
        mothList.add("3rd");
        mothList.add("4th");
        mothList.add("5th");
        mothList.add("6th");
        mothList.add("7th");
        mothList.add("8th");
        mothList.add("9th");
        mothList.add("10th");
        mothList.add("11th");
        mothList.add("12th");
        mothList.add("13th");
        mothList.add("14th");
        mothList.add("15th");
        mothList.add("16th");
        mothList.add("17th");
        mothList.add("18th");
        mothList.add("19th");
        mothList.add("20th");
        mothList.add("21th");
        mothList.add("22th");
        mothList.add("23th");
        mothList.add("24th");
        mothList.add("26th");
        mothList.add("27th");
        mothList.add("28th");
        mothList.add("29th");
        mothList.add("30th");
        ArrayAdapter<String> dataAdaptermonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mothList);
        dataAdaptermonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_date.setAdapter(dataAdaptermonth);
        dataAdaptermonth.notifyDataSetChanged();
        month_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSpin=month_date.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        week_count=(Spinner)findViewById(R.id.week_count);
        weekListcount=new ArrayList<>();
        weekListcount.add("1");
        weekListcount.add("2");
        weekListcount.add("3");
        weekListcount.add("4");
        weekListcount.add("5");
        weekListcount.add("6");
        weekListcount.add("7");
        weekListcount.add("8");
        weekListcount.add("9");
        weekListcount.add("10");
        weekListcount.add("11");
        weekListcount.add("12");
        ArrayAdapter<String> dataAdapterweekcount = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weekListcount);
        dataAdapterweekcount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        week_count.setAdapter(dataAdapterweekcount);
        dataAdapterweekcount.notifyDataSetChanged();
        week_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weekcountSpinn=week_count.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        month_count=(Spinner)findViewById(R.id.month_count);
        monthListcount=new ArrayList<>();
        monthListcount.add("1");
        monthListcount.add("2");
        monthListcount.add("3");
        monthListcount.add("4");
        ArrayAdapter<String> dataAdaptermonthcount = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, monthListcount);
        dataAdaptermonthcount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_count.setAdapter(dataAdaptermonthcount);
        month_count.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthcountSpinner=month_count.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dataAdaptermonthcount.notifyDataSetChanged();
        daily_time=(RadioButton)findViewById(R.id.daily_time);
        until_stop=(RadioButton)findViewById(R.id.until_stop);
        until_stop_week=(RadioButton)findViewById(R.id.until_stop_week);
        until_stop_month=(RadioButton)findViewById(R.id.until_stop_month);
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
        btn_save=(Button)findViewById(R.id.btn_save);
        section_daily_time.setVisibility(View.GONE);
        section_of_week.setVisibility(View.GONE);
        section_of_month.setVisibility(View.GONE);
        new_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  clickCount=clickCount+1;
                if(clickCount<=3){*/

                    TimePickerDialog timePickerDialog = new TimePickerDialog(ReminderAddPage.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                            hour = selectedTimeFormat(hour);
                            String time_input = hour + ":" + minute + format;
                            int count = 0;
                            int countOf=3;
                            if (count<=countOf) {
                                boolean isUpdate = timerDatabase.insertData(time_input);
                                if (isUpdate) {
                                    Intent intent = new Intent("message_time_intent");

                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    LocalBroadcastManager.getInstance(ReminderAddPage.this).sendBroadcast(intent);
                                    overridePendingTransition(0, 0);
                                } else {

                                }
                                count++;
                            }

                        }

                    }, hour, minute, true);
                    timePickerDialog.show();


            }
        });

        until_stop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(until_stop.isChecked()){
                    duration="0";
                    until_stop.setSelected(true);
                    until_stop.setChecked(true);
                    until_stop_week.setChecked(false);
                    until_stop_month.setChecked(false);

                }

            }
        });
        until_stop_week.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(until_stop_week.isChecked()){
                    duration="0";
                    until_stop_week.setSelected(true);
                    until_stop_week.setChecked(true);
                    until_stop.setChecked(false);
                    until_stop_month.setChecked(false);

                }

            }
        });
        until_stop_month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(until_stop_month.isChecked()){
                    duration="0";
                    until_stop_month.setSelected(true);
                    until_stop_month.setChecked(true);
                    until_stop_week.setChecked(false);
                    until_stop.setChecked(false);

                }

            }
        });
        sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(sunday.isChecked()){
                    s_day="1";
                    sunday.setSelected(true);
                    sunday.setChecked(true);
                }

            }
        });
        monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(monday.isChecked()){
                    m_day="1";
                    monday.setSelected(true);
                    monday.setChecked(true);
                }

            }
        });
        tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(tuesday.isChecked()){
                    t_day="1";
                    tuesday.setSelected(true);
                    tuesday.setChecked(true);
                }
            }
        });
        wednessday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(wednessday.isChecked()){
                    w_day="1";
                    wednessday.setSelected(true);
                    wednessday.setChecked(true);
                }
            }
        });
        thirstday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(thirstday.isChecked()){
                    th_day="1";
                    thirstday.setSelected(true);
                    thirstday.setChecked(true);
                }
            }
        });
        friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(friday.isChecked()){
                    f_day="1";
                    friday.setSelected(true);
                    friday.setChecked(true);
                }
            }
        });
        saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(saturday.isChecked()){
                    sat_day="1";
                    saturday.setSelected(true);
                    saturday.setChecked(true);
                }
            }
        });
        daily_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(daily_time.isChecked()){
                    repeat="1";
                    section_daily_time.setVisibility(View.VISIBLE);
                    section_of_week.setVisibility(View.GONE);
                    section_of_month.setVisibility(View.GONE);
                    daily_time.setSelected(true);
                    daily_time.setChecked(true);
                    weekly_time.setChecked(false);
                    monthly_time.setChecked(false);
                }
            }
        });
        weekly_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(weekly_time.isChecked()){
                    repeat="2";
                    section_of_week.setVisibility(View.VISIBLE);
                    section_daily_time.setVisibility(View.GONE);
                    section_of_month.setVisibility(View.GONE);
                    weekly_time.setSelected(true);
                    weekly_time.setChecked(true);
                    daily_time.setChecked(false);
                    monthly_time.setChecked(false);
                }
            }
        });
        monthly_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(monthly_time.isChecked()){
                    repeat="3";
                    section_of_month.setVisibility(View.VISIBLE);
                    section_of_week.setVisibility(View.GONE);
                    section_daily_time.setVisibility(View.GONE);
                    monthly_time.setSelected(true);
                    monthly_time.setChecked(true);
                    weekly_time.setChecked(false);
                    daily_time.setChecked(false);
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // reminderTimeAdapter.getSelectedItem();
                progressDialog = new ProgressDialog(ReminderAddPage.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setMessage("Upload Data To Server....");
                progressDialog.setCancelable(false);
                for(int i = 0; i< ReminderTimeAdapter.reminderTimeModels.size(); i++)
                    if( ReminderTimeAdapter.reminderTimeModels.get(i).getSelected()){
                      String med_time=ReminderTimeAdapter.reminderTimeModels.get(i).getTime_scheduled();
                      timegetList=new ArrayList<>();
                      timegetList.add(med_time);
                      All_Plist_two.addAll(timegetList);
                        Toast.makeText(getApplicationContext(),med_time,Toast.LENGTH_LONG);

                    }

                old_Plist_two=new List[1];
                old_Plist_two[0]=All_Plist_two;
                for(int j=0;j<old_Plist_two.length;j++) {
                    l = old_Plist_two[j];
                    // System.out.println("OlD"+l);
                    Gson gson = new Gson();
                    time_view_list = gson.toJson(l);
                    System.out.println("OlD" + time_view_list);



              /*  weekList=new ArrayList<>();
                if(s_day!=null){
                   weekList.add(s_day);
                }
                else {
                    s_day="0";
                    weekList.add(s_day);

                }
                if(m_day!=null){
                    weekList.add(m_day);
                }
                else {
                    m_day="0";
                    weekList.add(m_day);
                }
                if(t_day!=null){
                    weekList.add(t_day);
                }
                else {
                    t_day="0";
                    weekList.add(t_day);
                }
                if (w_day!=null){
                    weekList.add(w_day);

                }
                if(th_day!=null){
                    weekList.add(th_day);

                }
                else {
                    th_day="0";
                    weekList.add(th_day);

                }
                if(f_day!=null){
                    weekList.add(f_day);
                }
                else {
                    f_day="0";
                    weekList.add(f_day);
                }
                if(sat_day!=null){
                    weekList.add(sat_day);
                }
                else {
                    sat_day="0";
                    weekList.add(sat_day);
                }
                allWeekList.addAll(weekList);
                Gson weekJson=new Gson();
                SendWeek=weekJson.toJson(allWeekList);
                Toast.makeText(getApplicationContext(),SendWeek,Toast.LENGTH_LONG);*/

                    RequestQueue queue = Volley.newRequestQueue(ReminderAddPage.this);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, addreminder_url,
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
                                            progressDialog.dismiss();
                                            l.clear();
                                            fetchReminderData();

                                        }


                                    } catch (JSONException e) {
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
                            params.put("user_id", u_id);
                            params.put("medicine_name", medSelected);
                            params.put("repeat", repeat);
                            if(s_day!=null){
                                params.put("sun", s_day);
                            }
                            else{
                                s_day="0";
                                params.put("sun", s_day);
                            }
                            if(m_day!=null){
                                params.put("mon", m_day);
                            }
                            else {
                                m_day="0";
                                params.put("mon", m_day);
                            }
                            if(t_day!=null){
                                params.put("tue", t_day);
                            }
                            else {
                                t_day="0";
                                params.put("tue", t_day);

                            }
                            if (w_day!=null){
                                params.put("wed", w_day);
                            }
                            else {
                                w_day="0";
                                params.put("wed", w_day);
                            }
                            if(th_day!=null){
                                params.put("thu", th_day);
                            }
                            else {
                                th_day="0";
                                params.put("thu", th_day);

                            }
                            if(f_day!=null){
                                params.put("fri", f_day);
                            }
                            else {
                                f_day="0";
                                params.put("fri", f_day);

                            }
                            if(sat_day!=null){
                                params.put("sat", sat_day);
                            }
                            else {
                                sat_day="0";
                                params.put("sat", sat_day);
                            }

                            if(monthSpin!=null){
                                params.put("month_date", monthSpin);
                            }

                            if (daySpin != null) {
                                params.put("duration", daySpin);
                            } else if (duration != null) {

                                params.put("duration", duration);
                            }
                            if (weekcountSpinn != null) {
                                params.put("duration", weekcountSpinn);
                            } else if (duration != null) {

                                params.put("duration", duration);
                            }
                            if (monthcountSpinner != null) {
                                params.put("duration", monthcountSpinner);
                            } else if (duration != null) {

                                params.put("duration", duration);
                            }


                            params.put("set_time", time_view_list);


                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });

        getTime();
        getmedList();

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
    private void  getmedList(){
        medicinList=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(ReminderAddPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, medicineList_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");
                            if(status.equals("1")) {
                                JSONArray jsonArray = person.getJSONArray("message");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject serch = jsonArray.getJSONObject(i);
                                    String med_list=serch.getString("medicine_name");
                                    medicinList.add(med_list);
                                }
                                ArrayAdapter<String> dataAdaptermedi = new ArrayAdapter<String>(ReminderAddPage.this, android.R.layout.simple_spinner_item,  medicinList);
                                dataAdaptermedi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                edit_medicine.setAdapter(dataAdaptermedi);
                                dataAdaptermedi.notifyDataSetChanged();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                return params;
            }
        };
        queue.add(postRequest);

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getTime();
        }
    };
  private void  fetchReminderData(){
      RequestQueue queue = Volley.newRequestQueue(ReminderAddPage.this);
      StringRequest postRequest = new StringRequest(Request.Method.POST, fetchreminder_url,
              new Response.Listener<String>()
              {
                  @Override
                  public void onResponse(String response) {
                      // response
                      Log.d("Response", response);
                      try {
                          //Do it with this it will work
                          JSONObject person = new JSONObject(response);
                          JSONObject reminder=person.getJSONObject("reminder");
                          String med_name=reminder.getString("medicine_name");
                          String us_id=reminder.getString("user_id");
                          String rep=reminder.getString("repeat_ed");
                          String sund=reminder.getString("sun");
                          String mond=reminder.getString("mon");
                          String tued=reminder.getString("tue");
                          String wedd=reminder.getString("wed");
                          String thud=reminder.getString("thu");
                          String frid=reminder.getString("fri");
                          String satd=reminder.getString("sat");
                          String mondate=reminder.getString("month_date");
                          String start_date=reminder.getString("start_date");
                          String end_date=reminder.getString("end_date");
                          String due=reminder.getString("duration");
                          boolean isUpdate = reminderDataFromServerDatabase.insertData(med_name,us_id,rep,sund,tued,wedd,thud,mond,frid,
                                  satd,mondate,start_date,end_date,due);
                          if (isUpdate) {

                          } else {

                          }
                          JSONArray jsonArray=person.getJSONArray("set_time");
                          for(int i=0;i<jsonArray.length();i++){
                              JSONObject object=jsonArray.getJSONObject(i);
                              String set_time=object.getString("set_time");
                              boolean isTime = reminderDataFromServerDatabase.insertTime(set_time);
                              if (isTime) {

                              } else {

                              }
                          }






                      } catch (JSONException e) {
                          e.printStackTrace();

                      }


                  }
              },
              new Response.ErrorListener()
              {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      // error

                  }
              }
      ) {
          @Override
          protected Map<String, String> getParams()
          {
              Map<String, String>  params = new HashMap<String, String>();
              params.put("user_id", u_id);
              return params;
          }
      };
      queue.add(postRequest);

  }
}
