package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.InputFilterIntRange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Model.MinMaxFilter;

public class SubscriptionDelivery extends AppCompatActivity {
    ImageView back_button;
    private CheckBox one_time_order, thirty_day_order, fortyfive_day_order, sixty_day_order, single_day_order, selected_three_delivery,
            selected_six_delivery, single_delivery_order;
    private EditText edt_single_day, edt_single_delivery;
    private LinearLayout custom_part_delivery;
    private Button confirm_btn;
    int min = 15, max = 99;
    String user_id, user_add, One_Day, thirty_Day, fortifive_day, sixty_day, three_days_deliveries, six_days_delivery, single_day, single_delivery, day_portion, day_portion2, duration, duration2, instant_deli;
    private String Place_order_url = BaseUrl.BaseUrlNew + "addorder";
    int s_int_day, add_table_id, s_int_day2;
    private int deliveryInterval = 0, noOfDelivery = 0;
    //int noOfDay = 0;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent getUserDetails = getIntent();
        user_add = getUserDetails.getStringExtra("Address");
        user_id = getUserDetails.getStringExtra("userID");
        add_table_id = getUserDetails.getIntExtra("address_id_table", 0);
        instant_deli = getUserDetails.getStringExtra("INSTANT");

        progressDialog = new ProgressDialog(SubscriptionDelivery.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        one_time_order = (CheckBox) findViewById(R.id.one_time_order);
        thirty_day_order = (CheckBox) findViewById(R.id.thirty_day_order);
        fortyfive_day_order = (CheckBox) findViewById(R.id.fortyfive_day_order);
        sixty_day_order = (CheckBox) findViewById(R.id.sixty_day_order);
        single_day_order = (CheckBox) findViewById(R.id.single_day_order);
        selected_three_delivery = (CheckBox) findViewById(R.id.selected_three_delivery);
        selected_six_delivery = (CheckBox) findViewById(R.id.selected_six_delivery);
        single_delivery_order = (CheckBox) findViewById(R.id.single_delivery_order);
     /*   one_time_order=(RadioButton)findViewById(R.id.one_time_order);
        thirty_day_order=(RadioButton)findViewById(R.id.thirty_day_order);
        fortyfive_day_order=(RadioButton)findViewById(R.id.fortyfive_day_order);
        sixty_day_order=(RadioButton)findViewById(R.id.sixty_day_order);
        single_day_order=(RadioButton)findViewById(R.id.single_day_order);
        selected_three_delivery=(RadioButton)findViewById(R.id.selected_three_delivery);
        selected_six_delivery=(RadioButton)findViewById(R.id.selected_six_delivery);
        single_delivery_order=(RadioButton)findViewById(R.id.single_delivery_order);*/
        edt_single_day = findViewById(R.id.edt_single_day);
        //edt_single_day.setFilters( new InputFilter[]{ new MinMaxFilter( "15" , "99" )}) ;
        InputFilterIntRange rangeFilter = new InputFilterIntRange(15, 99);
        edt_single_day.setFilters(new InputFilter[]{rangeFilter});
        //edt_single_day.setOnFocusChangeListener(rangeFilter);
        edt_single_day.setEnabled(false);
        edt_single_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                deliveryInterval = 2;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**/
        edt_single_delivery = (EditText) findViewById(R.id.edt_single_delivery);
        edt_single_delivery.setFilters(new InputFilter[]{new MinMaxFilter("1", "12")});
        edt_single_delivery.setEnabled(false);
        edt_single_delivery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noOfDelivery = 1;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        custom_part_delivery = (LinearLayout) findViewById(R.id.custom_part_delivery);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(SubscriptionDelivery.this, OrderSummery.class);
                intent.putExtra("ADDRESS_ID", add_table_id);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
   /*  one_time_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(one_time_order.isChecked()){
                *//* thirty_day_order.setChecked(false);
                 fortyfive_day_order.setChecked(false);
                 sixty_day_order.setChecked(false);
                 single_day_order.setChecked(false);
                 selected_three_delivery.setChecked(false);
                 selected_six_delivery.setChecked(false);
                 single_delivery_order.setChecked(false);*//*
                One_Day="1";


                 thirty_day_order.setEnabled(false);
                 fortyfive_day_order.setEnabled(false);
                 sixty_day_order.setEnabled(false);
                 single_day_order.setEnabled(false);
                 custom_part_delivery.setVisibility(View.GONE);
                 edt_single_day.setEnabled(false);



             }
             else {

                *//* thirty_day_order.setChecked(true);
                 fortyfive_day_order.setChecked(true);
                 sixty_day_order.setChecked(true);
                 single_day_order.setChecked(true);
                 selected_three_delivery.setChecked(true);
                 selected_six_delivery.setChecked(true);
                 single_delivery_order.setChecked(true);*//*
                 thirty_day_order.setEnabled(true);
                 fortyfive_day_order.setEnabled(true);
                 sixty_day_order.setEnabled(true);
                 single_day_order.setEnabled(true);

                 single_delivery_order.setEnabled(true);
             }
         }
     });
     thirty_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(thirty_day_order.isChecked()){
                 thirty_Day="30";


                 one_time_order.setEnabled(false);
                 fortyfive_day_order.setEnabled(false);
                 sixty_day_order.setEnabled(false);
                 single_day_order.setEnabled(false);
                 custom_part_delivery.setVisibility(View.VISIBLE);
                 edt_single_day.setEnabled(false);



             }
             else {
                // thirty_Day="";

                 one_time_order.setEnabled(true);
                 fortyfive_day_order.setEnabled(true);
                 sixty_day_order.setEnabled(true);
                 single_day_order.setEnabled(true);
                 custom_part_delivery.setVisibility(View.GONE);
                 single_delivery_order.setEnabled(true);

             }
         }
     });
        fortyfive_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(fortyfive_day_order.isChecked()){
                    fortifive_day="45";

                    one_time_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(false);
                    thirty_day_order.setEnabled(false);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    edt_single_day.setEnabled(false);


                }
                else {
                   // fortifive_day="";

                    one_time_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    thirty_day_order.setEnabled(true);
                    custom_part_delivery.setVisibility(View.GONE);
                    edt_single_day.setEnabled(true);
                    single_delivery_order.setEnabled(true);



                }
            }
        });

sixty_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(sixty_day_order.isChecked()){
            sixty_day="60";

            one_time_order.setEnabled(false);
            fortyfive_day_order.setEnabled(false);
            single_day_order.setEnabled(false);
            thirty_day_order.setEnabled(false);
            custom_part_delivery.setVisibility(View.VISIBLE);
            edt_single_day.setEnabled(false);


        }
        else {
         *//*   sixty_day="";*//*

            one_time_order.setEnabled(true);
            fortyfive_day_order.setEnabled(true);
            single_day_order.setEnabled(true);
            thirty_day_order.setEnabled(true);
            custom_part_delivery.setVisibility(View.GONE);
            edt_single_day.setEnabled(true);
            single_delivery_order.setEnabled(true);

        }
    }
});
        single_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(single_day_order.isChecked()){
                  *//*  single_day=edt_single_day.getText().toString();*//*
                    one_time_order.setEnabled(false);
                    thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                   edt_single_day.setEnabled(true);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    edt_single_delivery.setEnabled(false);


                }
                else {
                    one_time_order.setEnabled(true);
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    edt_single_day.setEnabled(false);
                    custom_part_delivery.setVisibility(View.GONE);
                    edt_single_delivery.setEnabled(false);
                }
            }
        });
        selected_three_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(selected_three_delivery.isChecked()){
                    three_days_deliveries="3";
                    one_time_order.setEnabled(false);
                    selected_six_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);
                    *//*thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*//*



                }
                else {
                  //  three_days_deliveries="";

                    one_time_order.setEnabled(false);
                    selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);
                 *//*   thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*//*
                }
            }
        });
        selected_six_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(selected_six_delivery.isChecked()){
                    six_days_delivery="6";
                    one_time_order.setEnabled(false);
                    selected_three_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);
                 *//*   one_time_order.setEnabled(false);
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*//*


                }
                else {
                  //  six_days_delivery="";

                    one_time_order.setEnabled(false);
                    selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);
                    *//*thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*//*
                }
            }
        });
        single_delivery_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(single_delivery_order.isChecked()){
                    edt_single_delivery.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    selected_six_delivery.setEnabled(false);
                   *//* single_delivery=edt_single_delivery.getText().toString();*//*
                    one_time_order.setEnabled(false);
                  *//*  thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    selected_six_delivery.setEnabled(false);*//*

                }
                else {
                    one_time_order.setEnabled(false);
                    selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    edt_single_delivery.setEnabled(false);

                  *//*  thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    edt_single_delivery.setEnabled(false);*//*
                }
            }
        });*/
        one_time_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (one_time_order.isChecked()) {
                /* thirty_day_order.setChecked(false);
                 fortyfive_day_order.setChecked(false);
                 sixty_day_order.setChecked(false);
                 single_day_order.setChecked(false);
                 selected_three_delivery.setChecked(false);
                 selected_six_delivery.setChecked(false);
                 single_delivery_order.setChecked(false);*/
                    One_Day = "1";

                    day_portion = One_Day;
                    thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(false);
                    custom_part_delivery.setVisibility(View.GONE);
                    edt_single_day.setEnabled(false);
                    edt_single_day.setText("");

                    deliveryInterval = 1;

                } else {
                    day_portion = "";
                /* thirty_day_order.setChecked(true);
                 fortyfive_day_order.setChecked(true);
                 sixty_day_order.setChecked(true);
                 single_day_order.setChecked(true);
                 selected_three_delivery.setChecked(true);
                 selected_six_delivery.setChecked(true);
                 single_delivery_order.setChecked(true);*/
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);

                    single_delivery_order.setEnabled(true);

                    deliveryInterval = 0;
                }
            }
        });
        thirty_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (thirty_day_order.isChecked()) {
                    thirty_Day = "30";
                    day_portion = thirty_Day;
                    s_int_day = Integer.parseInt(thirty_Day);

                    one_time_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(false);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    edt_single_day.setEnabled(false);
                    edt_single_day.setText("");

                    deliveryInterval = 30;
                } else {
                    // thirty_Day="";
                    day_portion = "";
                    s_int_day = 0;
                    one_time_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    custom_part_delivery.setVisibility(View.GONE);
                    single_delivery_order.setEnabled(true);

                    deliveryInterval = 0;
                }
            }
        });
        fortyfive_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fortyfive_day_order.isChecked()) {
                    fortifive_day = "45";
                    day_portion = fortifive_day;
                    s_int_day = Integer.parseInt(fortifive_day);
                    one_time_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(false);
                    thirty_day_order.setEnabled(false);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    edt_single_day.setEnabled(false);
                    edt_single_day.setText("");

                    deliveryInterval = 45;
                } else {
                    // fortifive_day="";
                    day_portion = "";
                    s_int_day = 0;
                    one_time_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    thirty_day_order.setEnabled(true);
                    custom_part_delivery.setVisibility(View.GONE);
                    single_delivery_order.setEnabled(true);

                    deliveryInterval = 0;
                }
            }
        });

        sixty_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sixty_day_order.isChecked()) {
                    sixty_day = "60";
                    day_portion = sixty_day;
                    s_int_day = Integer.parseInt(sixty_day);
                    one_time_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    single_day_order.setEnabled(false);
                    thirty_day_order.setEnabled(false);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    edt_single_day.setEnabled(false);
                    edt_single_day.setText("");

                    deliveryInterval = 60;
                } else {
                    /*   sixty_day="";*/
                    day_portion = "";
                    s_int_day = 0;
                    one_time_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    thirty_day_order.setEnabled(true);
                    custom_part_delivery.setVisibility(View.GONE);
                    single_delivery_order.setEnabled(true);

                    deliveryInterval = 0;
                }
            }
        });
        single_day_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (single_day_order.isChecked()) {
                    /*  single_day=edt_single_day.getText().toString();*/
                    one_time_order.setEnabled(false);
                    thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    edt_single_day.setEnabled(true);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    //edt_single_delivery.setEnabled(false);

                    deliveryInterval = 2;
                } else {
                    one_time_order.setEnabled(true);
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    edt_single_day.setEnabled(false);
                    edt_single_day.setText("");
                    custom_part_delivery.setVisibility(View.GONE);
                    //edt_single_delivery.setEnabled(false);

                    deliveryInterval = 0;
                }
            }
        });
        selected_three_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selected_three_delivery.isChecked()) {
                    three_days_deliveries = "3";
                    duration = three_days_deliveries;
                    one_time_order.setEnabled(false);
                  /*  selected_six_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);*/
                    selected_three_delivery.setChecked(true);
                    selected_six_delivery.setChecked(false);
                    single_delivery_order.setChecked(false);
                    edt_single_delivery.setEnabled(false);
                    edt_single_delivery.setText("");
                    /*thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*/

                    noOfDelivery = 3;
                } else {
                    //  three_days_deliveries="";
                    duration = "";
                    one_time_order.setEnabled(false);
                   /* selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);*/


                    // edt_single_delivery.setEnabled(true);
                 /*   thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*/

                    noOfDelivery = 0;
                }
            }
        });
        selected_six_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selected_six_delivery.isChecked()) {
                    six_days_delivery = "6";
                    duration = six_days_delivery;
                    one_time_order.setEnabled(false);
               /*     selected_three_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);*/
                    selected_six_delivery.setChecked(true);
                    selected_three_delivery.setChecked(false);
                    single_delivery_order.setChecked(false);
                    edt_single_delivery.setEnabled(false);
                    edt_single_delivery.setText("");
                 /*   one_time_order.setEnabled(false);
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*/
                    noOfDelivery = 6;
                } else {
                    //  six_days_delivery="";
                    duration = "";
                    one_time_order.setEnabled(false);
                   /* selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);*/

                    //edt_single_delivery.setEnabled(true);
                    /*thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*/

                    noOfDelivery = 0;
                }
            }
        });
        single_delivery_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (single_delivery_order.isChecked()) {
                    edt_single_delivery.setEnabled(true);
                  /*  selected_three_delivery.setEnabled(false);
                    selected_six_delivery.setEnabled(false);*/
                    single_delivery_order.setChecked(true);
                    selected_three_delivery.setChecked(false);
                    selected_six_delivery.setChecked(false);

                    /* single_delivery=edt_single_delivery.getText().toString();*/
                    one_time_order.setEnabled(false);
                  /*  thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    selected_six_delivery.setEnabled(false);*/

                    noOfDelivery = 1;
                } else {
                    duration2 = "";
                    one_time_order.setEnabled(false);
                   /* selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);*/

                    edt_single_delivery.setEnabled(false);
                    edt_single_delivery.setText("");

                  /*  thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    edt_single_delivery.setEnabled(false);*/

                    noOfDelivery = 0;
                }
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single_day = edt_single_day.getText().toString().trim();
                single_delivery = edt_single_delivery.getText().toString().trim();

                if (deliveryInterval == 0)
                    Toast.makeText(SubscriptionDelivery.this, "Please checked one delivery interval", Toast.LENGTH_SHORT).show();
                else if (deliveryInterval == 1) {
                    noOfDelivery = 0;
                    callDeliveryInterval();
                } else if (deliveryInterval > 2) {
                    checkingInterval();
                } else {
                    if (single_day.equals("") || Integer.parseInt(single_day) < 14)
                        Toast.makeText(SubscriptionDelivery.this, "Invalid Delivery Interval!", Toast.LENGTH_SHORT).show();
                    else {
                        deliveryInterval = Integer.parseInt(single_day);
                        checkingInterval();
                    }
                }

                                              /* single_day = edt_single_day.getText().toString();

                                               single_delivery = edt_single_delivery.getText().toString();
                                               if (!single_day.equals("")) {
                                                   day_portion2 = single_day;
                                                   s_int_day2 = Integer.parseInt(day_portion2);

                                               } else {
                                                   s_int_day2 = 0;
                                                   day_portion2 = "";
                                               }


                                               if (single_delivery != null) {
                                                   duration2 = single_delivery;
                                               } else {
                                                   duration2 = "";
                                               }

                                            *//*   if (One_Day!=null) {
                                                   day_portion = One_Day;

                                                   // s_int_day = Integer.parseInt(One_Day);
                                               }
                                               else if (thirty_Day!=null) {
                                                   day_portion = thirty_Day;
                                                //   s_int_day = Integer.parseInt(thirty_Day);

                                               } else if (fortifive_day!=null) {
                                                   day_portion = fortifive_day;
                                                  // s_int_day = Integer.parseInt(fortifive_day);


                                               } else if (sixty_day!=null) {
                                                   day_portion = sixty_day;
                                                  // s_int_day = Integer.parseInt(sixty_day);


                                               } else if (single_day!=null) {
                                                   day_portion = single_day;
                                                   if(!day_portion.equals(""))
                                                   {
                                                       s_int_day = Integer.parseInt(single_day);
                                                   }
                                                   else {
                                                       s_int_day = 0;
                                                   }

                                               } else {
                                                   day_portion = "";
                                                   //s_int_day = 0;
                                               }
                                               if (three_days_deliveries != null) {
                                                   duration = three_days_deliveries;
                                               } else if (six_days_delivery != null) {
                                                   duration = six_days_delivery;
                                               } else if (single_delivery != null) {
                                                   duration = single_delivery;
                                               } else {
                                                   duration = "";
                                               }*//*

                                               System.out.println("DAYp;" + day_portion + "DURATION:" + duration);
                                               if (!one_time_order.isChecked()) {

                                                   Toast.makeText(getApplicationContext(), "Please,Checked One Time Order", Toast.LENGTH_LONG).show();
                                               } else {

                                                   RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
                                                   StringRequest postRequest = new StringRequest(Request.Method.POST, Place_order_url,
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
                                                                           String message = person.getString("message");
                                                                           Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                                                           Intent goToOrderView_method = new Intent(SubscriptionDelivery.this, OrderReview.class);
                                                                           goToOrderView_method.putExtra("DAY", day_portion);
                                                                           goToOrderView_method.putExtra("Duration", duration);
                                                                           goToOrderView_method.putExtra("User_add", user_add);
                                                                           goToOrderView_method.putExtra("User_ID", user_id);
                                                                           // goTopayment_method.putExtra("presc_img",prescription_img);
                                                                           startActivity(goToOrderView_method);
                                                                           overridePendingTransition(0, 0);
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

                                                           params.put("user_id", user_id);
                                                           params.put("aId", user_add);
                                                           if (day_portion != null) {
                                                               params.put("days", day_portion);
                                                           }
                                                           if (duration != null) {
                                                               params.put("duration", duration);
                                                           }
                                                           if (instant_deli != null) {
                                                               params.put("instant", instant_deli);
                                                           } else {
                                                               params.put("instant", "0");
                                                           }

                                                           // params.put("payment_method", cod);
                                                           params.put("status", "1");
                                                           return params;
                                                       }
                                                   };

                                                   requestQueue.add(postRequest);

                                               }


                                              *//* if (!thirty_day_order.isChecked() && !fortyfive_day_order.isChecked() && !sixty_day_order.isChecked()
                                                       && day_portion.matches("") || !selected_three_delivery.isChecked() && !selected_six_delivery.isChecked() && single_delivery.matches("")) {
                                                   Toast.makeText(getApplicationContext(), "Please, Checked All Preference Of Delivery Order", Toast.LENGTH_LONG).show();
                                               } else {

                                                   if (day_portion.equals("0") || day_portion.equals("00") || single_delivery.equals("0") || single_delivery.equals("00")|| s_int_day < 15 ) {

                                                   }*//*
                                               if (s_int_day < 15) {


                                               }
                                              else {
                                                   if (!selected_three_delivery.isChecked() && !selected_six_delivery.isChecked() && !single_delivery_order.isChecked()) {
                                                       Toast.makeText(getApplicationContext(), "Please, Checked All Preference Of Delivery Order", Toast.LENGTH_LONG).show();
                                                   } else {
                                                       RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
                                                       StringRequest postRequest = new StringRequest(Request.Method.POST, Place_order_url,
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
                                                                               String message = person.getString("message");
                                                                               // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                                                               Intent goToOrderView_method = new Intent(SubscriptionDelivery.this, OrderReview.class);
                                                                               goToOrderView_method.putExtra("DAY", day_portion);

                                                                               goToOrderView_method.putExtra("Duration", duration);


                                                                               goToOrderView_method.putExtra("Duration", duration2);


                                                                               goToOrderView_method.putExtra("User_add", user_add);
                                                                               goToOrderView_method.putExtra("User_ID", user_id);
                                                                               // goTopayment_method.putExtra("presc_img",prescription_img);
                                                                               startActivity(goToOrderView_method);
                                                                               overridePendingTransition(0, 0);
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

                                                               params.put("user_id", user_id);
                                                               params.put("aId", user_add);
                                                               if (day_portion != null) {
                                                                   params.put("days", day_portion);
                                                               }


                                                               if (!duration.equals("")) {
                                                                   params.put("duration", duration);
                                                               } else {
                                                                   params.put("duration", duration2);
                                                               }
                                                               if (instant_deli != null) {
                                                                   params.put("instant", instant_deli);
                                                               } else {
                                                                   params.put("instant", "0");
                                                               }
                                                               // params.put("spid", presc_img);
                                                               // params.put("payment_method", cod);
                                                               params.put("status", "1");
                                                               return params;
                                                           }
                                                       };

                                                       requestQueue.add(postRequest);
                                                   }
                                               }


                                               if(s_int_day2 < 15 ) {

                                               }
                                               else {
                                                   if (!selected_three_delivery.isChecked() && !selected_six_delivery.isChecked() && !single_delivery_order.isChecked()) {
                                                       Toast.makeText(getApplicationContext(), "Please, Checked All Preference Of Delivery Order", Toast.LENGTH_LONG).show();
                                                   } else {
                                                       RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
                                                       StringRequest postRequest = new StringRequest(Request.Method.POST, Place_order_url,
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
                                                                               String message = person.getString("message");
                                                                               // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                                                               Intent goToOrderView_method = new Intent(SubscriptionDelivery.this, OrderReview.class);
                                                                               goToOrderView_method.putExtra("DAY", day_portion);

                                                                               goToOrderView_method.putExtra("Duration", duration);

                                                                               goToOrderView_method.putExtra("Duration", duration2);

                                                                               goToOrderView_method.putExtra("User_add", user_add);
                                                                               goToOrderView_method.putExtra("User_ID", user_id);
                                                                               // goTopayment_method.putExtra("presc_img",prescription_img);
                                                                               startActivity(goToOrderView_method);
                                                                               overridePendingTransition(0, 0);
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

                                                               params.put("user_id", user_id);
                                                               params.put("aId", user_add);
                                                               if (day_portion2 != null) {
                                                                   params.put("days", day_portion2);
                                                               }


                                                               if (!duration2.equals("")) {
                                                                   params.put("duration", duration2);
                                                               } else {
                                                                   params.put("duration", duration);
                                                               }
                                                               if (instant_deli != null) {
                                                                   params.put("instant", instant_deli);
                                                               } else {
                                                                   params.put("instant", "0");
                                                               }
                                                               // params.put("spid", presc_img);
                                                               // params.put("payment_method", cod);
                                                               params.put("status", "1");
                                                               return params;
                                                           }
                                                       };

                                                       requestQueue.add(postRequest);


                                                   }
                                               }*/







             /*   Intent goTopayment_method=new Intent(SubscriptionDelivery.this,Payment.class);
                goTopayment_method.putExtra("DAY",day_portion);
                goTopayment_method.putExtra("Duration",duration);
                goTopayment_method.putExtra("User_add",user_add);
                goTopayment_method.putExtra("User_ID",user_id);
                goTopayment_method.putExtra("presc_img",prescription_img);
                startActivity(goTopayment_method);*/

            }
        });
    }

    private void checkingInterval() {
        if (noOfDelivery > 1)
            callDeliveryInterval();
        else if (noOfDelivery == 1) {
            if (single_delivery.equals(""))
                Toast.makeText(SubscriptionDelivery.this, "Invalid Number Of Delivery!", Toast.LENGTH_SHORT).show();
            else {
                noOfDelivery = Integer.parseInt(single_delivery);
                callDeliveryInterval();
            }
        } else {
            Toast.makeText(SubscriptionDelivery.this, "Invalid Number Of Delivery!", Toast.LENGTH_SHORT).show();
        }
    }

    private void callDeliveryInterval() {
        //Toast.makeText(this, "Delivery : "+deliveryInterval + " No of delivery : "+noOfDelivery, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Place_order_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");
                            String message = person.getString("message");
                            if (status.equals("1")) {
                                Intent goToOrderView_method = new Intent(SubscriptionDelivery.this, OrderReview.class);
                                goToOrderView_method.putExtra("DAY", day_portion);
                                goToOrderView_method.putExtra("Duration", duration);
                                goToOrderView_method.putExtra("User_add", user_add);
                                goToOrderView_method.putExtra("User_ID", user_id);
                                goToOrderView_method.putExtra("order_id", person.getString("order_id"));
                                // goTopayment_method.putExtra("presc_img",prescription_img);
                                startActivity(goToOrderView_method);
                                overridePendingTransition(0, 0);
                            } else
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", user_id);
                params.put("aId", user_add);
                params.put("days", String.valueOf(deliveryInterval));
                params.put("duration", String.valueOf(noOfDelivery));
                if (instant_deli != null) {
                    params.put("instant", instant_deli);
                } else {
                    params.put("instant", "0");
                }
                params.put("status", "1");
                return params;
            }
        };

        requestQueue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SubscriptionDelivery.this, OrderSummery.class);
        intent.putExtra("ADDRESS_ID", add_table_id);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
