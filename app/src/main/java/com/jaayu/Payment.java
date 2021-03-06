package com.jaayu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    ImageView back_button;
    private CheckBox paytm,amazon_pay,ola_money,paypal_pay,netbank,credit_debit_card,jaayu_details,cash_delivery,wallet;
    private RadioGroup radio_three;
    String user_id,user_add,day_time,duration,cod,nor_wallet,jy_wallet,net_bank,presc_img,order_id,instant,u_id,mrp_amt,save_amt,shippingcharge,tot_pay,
            balance_og,balance_jy,pay_status,u_email,u_phone,ord_jy_id;
    SharedPreferences prefs_register;
    private String Orderdetails_url= BaseUrl.BaseUrlNew+"order_details_profile";
    private String Place_order_url=BaseUrl.BaseUrlNew+"order_payment";
    private String Normal_wallet_url=BaseUrl.BaseUrlNew+"normal_wallet_display";
    private String Jaayu_wallet_url=BaseUrl.BaseUrlNew+"jaayu_wallet_display";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";

    private TextView open_item,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,
            customer_name,address_text,email_add,wallwt_amount,jy_wallwt_amount,wallet_amount_,jywallet_amount_,disclaimer;
    private Button submit_btn;
    int w_bal,Jay_wal;
    double reminder_bal,reminder_bal_jy,jy_wal_substract,persentage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Checkout.preload(getApplicationContext());
        //LocalBroadcastManager.getInstance(Payment.this).registerReceiver(mMessageReceiver, new IntentFilter("message_wallet_intent"));
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        u_email=prefs_register.getString("USER_EMAIL","");
        u_phone=prefs_register.getString("USER_PHONE","");
        Intent ftch_Order=getIntent();
        order_id=ftch_Order.getStringExtra("ORDER_ID");
        instant=ftch_Order.getStringExtra("INSTANT");
        ord_jy_id=ftch_Order.getStringExtra("ORDER_JY_ID");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        radio_three=(RadioGroup)findViewById(R.id.radio_three);
        cash_delivery=(CheckBox)findViewById(R.id.cash_delivery);
        netbank=(CheckBox)findViewById(R.id.netbank);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        //paytm=(CheckBox)findViewById(R.id.paytm);
       // amazon_pay=(CheckBox)findViewById(R.id.amazon_pay);
       // ola_money=(CheckBox)findViewById(R.id.ola_money);
       // paypal_pay=(CheckBox)findViewById(R.id.paypal_pay);
        credit_debit_card=(CheckBox)findViewById(R.id.credit_debit_card);
        jaayu_details=(CheckBox)findViewById(R.id.jaayu_wallet);
        wallet=(CheckBox)findViewById(R.id.wallet);
        submit_btn=(Button)findViewById(R.id.submit_btn) ;
        mrp_total=(TextView)findViewById(R.id.mrp_total);
        total_save_price=(TextView)findViewById(R.id.total_save_price);
        shipping_charge=(TextView)findViewById(R.id.shipping_charge);
        payable_amount=(TextView)findViewById(R.id.payable_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        discount_limit_amt=(TextView)findViewById(R.id.discount_limit_amt);
        main_pay=(TextView)findViewById(R.id.main_pay);
        wallwt_amount=(TextView)findViewById(R.id.wallwt_amount);
        jy_wallwt_amount=(TextView)findViewById(R.id.jy_wallwt_amount);
        wallet_amount_=(TextView)findViewById(R.id.wallet_amount_);
        jywallet_amount_=(TextView)findViewById(R.id.jywallet_amount_);
        calcutate_section();
        normal_wallet_amt_display();
        jaayu_wallet_amount_displat();
        getDisclimer();
      /*  Intent passDataFromDeliveryPage=getIntent();
        user_id=passDataFromDeliveryPage.getStringExtra("User_ID");
        user_add=passDataFromDeliveryPage.getStringExtra("User_add");
        day_time=passDataFromDeliveryPage.getStringExtra("DAY");
        duration=passDataFromDeliveryPage.getStringExtra("Duration");
        presc_img=passDataFromDeliveryPage.getStringExtra("presc_img");*/
      /*  progressDialog = new ProgressDialog(Payment.this);
        progressDialog.setMessage("Downloading..."); // Setting Message

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {

                    calcutate_section();
                    normal_wallet_amt_display();
                    jaayu_wallet_amount_displat();
                    Thread.sleep(6000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();*/
       /* calcutate_section();
        normal_wallet_amt_display();
        jaayu_wallet_amount_displat();*/
        progressDialog = new ProgressDialog(Payment.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(Payment.this, OrderDetails.class);
                intent.putExtra("Order_id",Integer.valueOf(order_id));
                intent.putExtra("Order_Vid",ord_jy_id);
                intent.putExtra("Instant",instant);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

       /* paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!paytm.isSelected()){
                    cod="Paytm";
                    Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                    paytm.setSelected(true);
                    paytm.setChecked(true);
                  *//*  amazon_pay.setEnabled(false);
                    ola_money.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);*//*
                    amazon_pay.setChecked(false);
                    ola_money.setChecked(false);
                    paypal_pay.setChecked(false);
                    netbank.setChecked(false);
                    credit_debit_card.setChecked(false);
                    cash_delivery.setChecked(false);
                    jaayu_details.setChecked(false);


                }
               *//* else {
                    paytm.setSelected(false);
                    paytm.setChecked(false);
                    amazon_pay.setEnabled(true);
                    ola_money.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }*//*
            }
        });
        amazon_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amazon_pay.isSelected()){
                    cod="amazon_pay";
                    Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                    amazon_pay.setSelected(true);
                    amazon_pay.setChecked(true);
                *//*    paytm.setEnabled(false);
                    ola_money.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);*//*
                    paytm.setChecked(false);
                    ola_money.setChecked(false);
                    paypal_pay.setChecked(false);
                    netbank.setChecked(false);
                    credit_debit_card.setChecked(false);
                    cash_delivery.setChecked(false);
                    jaayu_details.setChecked(false);


                }
               *//* else {
                    amazon_pay.setSelected(false);
                    amazon_pay.setChecked(false);
                    paytm.setEnabled(true);
                    ola_money.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }*//*

            }
        });
        ola_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ola_money.isSelected()){
                    cod="Ola_Money";
                    Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                    ola_money.setSelected(true);
                    ola_money.setChecked(true);
                   *//* paytm.setEnabled(false);
                    amazon_pay.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);*//*
                    paytm.setChecked(false);
                    amazon_pay.setChecked(false);
                    paypal_pay.setChecked(false);
                    netbank.setChecked(false);
                    credit_debit_card.setChecked(false);
                    cash_delivery.setChecked(false);
                    jaayu_details.setChecked(false);

                }
              *//*  else {
                    ola_money.setSelected(false);
                    ola_money.setChecked(false);
                    paytm.setEnabled(true);
                    amazon_pay.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }*//*
            }
        });
        paypal_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!paypal_pay.isSelected()){
                    cod="Paypal";
                    Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                    paypal_pay.setSelected(true);
                    paypal_pay.setChecked(true);
                  *//*  paytm.setEnabled(false);
                    amazon_pay.setEnabled(false);
                    ola_money.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);*//*
                    paytm.setChecked(false);
                    amazon_pay.setChecked(false);
                    ola_money.setChecked(false);
                    netbank.setChecked(false);
                    credit_debit_card.setChecked(false);
                    cash_delivery.setChecked(false);
                    jaayu_details.setChecked(false);

                }
              *//*  else {
                    paypal_pay.setSelected(false);
                    paypal_pay.setChecked(false);
                    paytm.setEnabled(true);
                    amazon_pay.setEnabled(true);
                    ola_money.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }*//*
            }
        });
         netbank.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!netbank.isSelected()){
                     cod="netbank";
                     Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                     netbank.setSelected(true);
                     netbank.setChecked(true);
                    *//* paytm.setEnabled(false);
                     amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     cash_delivery.setEnabled(false);
                     jaayu_details.setEnabled(false);*//*
                     paytm.setChecked(false);
                     amazon_pay.setChecked(false);
                     ola_money.setChecked(false);
                     paypal_pay.setChecked(false);
                     credit_debit_card.setChecked(false);
                     cash_delivery.setChecked(false);
                     jaayu_details.setChecked(false);

                 }
              *//*   else {
                     netbank.setSelected(false);
                     netbank.setChecked(false);
                     paytm.setEnabled(true);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     cash_delivery.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }*//*
             }
         });
         credit_debit_card.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!credit_debit_card.isSelected()){
                     cod="credit_debit_card";
                     Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                     credit_debit_card.setSelected(true);
                     credit_debit_card.setChecked(true);
                    *//* amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     cash_delivery.setEnabled(false);
                     jaayu_details.setEnabled(false);*//*
                     amazon_pay.setChecked(false);
                     ola_money.setChecked(false);
                     paypal_pay.setChecked(false);
                     netbank.setChecked(false);
                     cash_delivery.setChecked(false);
                     jaayu_details.setChecked(false);

                 }
              *//*   else {
                     credit_debit_card.setSelected(false);
                     credit_debit_card.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     cash_delivery.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }*//*
             }
         });
         cash_delivery.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!cash_delivery.isSelected()){
                     cod="cod";
                     Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                     cash_delivery.setSelected(true);
                     cash_delivery.setChecked(true);
                    *//* amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     jaayu_details.setEnabled(false);*//*
                     amazon_pay.setChecked(false);
                     ola_money.setChecked(false);
                     paypal_pay.setChecked(false);
                     netbank.setChecked(false);
                     credit_debit_card.setChecked(false);
                     jaayu_details.setChecked(false);

                 }

                *//* else {
                     cash_delivery.setSelected(false);
                     cash_delivery.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }*//*
             }
         });
         jaayu_details.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!jaayu_details.isSelected()){
                     cod="Jaayu_Wallet";
                     Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                     jaayu_details.setSelected(true);
                     jaayu_details.setChecked(true);
                   *//*  amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     cash_delivery.setEnabled(false);*//*
                     amazon_pay.setChecked(false);
                     ola_money.setChecked(false);
                     paypal_pay.setChecked(false);
                     netbank.setChecked(false);
                     credit_debit_card.setChecked(false);
                     cash_delivery.setChecked(false);

                 }

                *//* else {
                     jaayu_details.setSelected(false);
                     jaayu_details.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     cash_delivery.setEnabled(true);


                 }*//*
             }
         });*/
   /* paytm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(paytm.isChecked()){
                cod="Paytm";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
              //  paytm.setSelected(true);
              //  paytm.setChecked(true);
              //  amazon_pay.setChecked(false);
               // ola_money.setChecked(false);
               // paypal_pay.setChecked(false);
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setChecked(false);
            }
        }
    });
    amazon_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(amazon_pay.isChecked()){
                cod="amazon_pay";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                //amazon_pay.setSelected(true);
                //amazon_pay.setChecked(true);
               // paytm.setChecked(false);
               // ola_money.setChecked(false);
               // paypal_pay.setChecked(false);
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setChecked(false);
            }
        }
    });
    ola_money.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(ola_money.isChecked()){
                cod="Ola_Money";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                ola_money.setSelected(true);
                ola_money.setChecked(true);
                paytm.setChecked(false);
                amazon_pay.setChecked(false);
                paypal_pay.setChecked(false);
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setChecked(false);
            }
        }
    });
    paypal_pay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(paypal_pay.isChecked()){
                cod="Paypal";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                paypal_pay.setSelected(true);
                paypal_pay.setChecked(true);
                paytm.setChecked(false);
                amazon_pay.setChecked(false);
                ola_money.setChecked(false);
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setChecked(false);
            }
        }
    });*/
    netbank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(netbank.isChecked()){
                cod="netbank";
                pay_status="1";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                netbank.setSelected(true);
                netbank.setChecked(true);
                /*paytm.setChecked(false);
                amazon_pay.setChecked(false);
                ola_money.setChecked(false);
                paypal_pay.setChecked(false);*/
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setSelected(true);
                wallet.setSelected(true);
            }
        }
    });
    credit_debit_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(credit_debit_card.isChecked()){
                cod="credit_debit_card";
                pay_status="1";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                credit_debit_card.setSelected(true);
                credit_debit_card.setChecked(true);
               /* amazon_pay.setChecked(false);
                ola_money.setChecked(false);
                paypal_pay.setChecked(false);*/
                netbank.setChecked(false);
                cash_delivery.setChecked(false);
                jaayu_details.setSelected(true);
                wallet.setSelected(true);
            }
        }
    });
    cash_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(cash_delivery.isChecked()){
                cod="cod";
                pay_status="1";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                cash_delivery.setSelected(true);
                cash_delivery.setChecked(true);
                /*amazon_pay.setChecked(false);
                ola_money.setChecked(false);
                paypal_pay.setChecked(false);*/
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                jaayu_details.setSelected(true);
                wallet.setSelected(true);
            }
        }
    });
    jaayu_details.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(jaayu_details.isChecked()){
                jy_wallet="Jaayu_Wallet";
               // pay_status="1";
                Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                jaayu_details.setSelected(true);
                jaayu_details.setChecked(true);
               /* amazon_pay.setChecked(false);
                ola_money.setChecked(false);
                paypal_pay.setChecked(false);*/

              /*  persentage=Double.parseDouble(tot_pay)*20/100;
                reminder_bal_jy=Double.parseDouble(balance_jy)-persentage;
                jywallet_amount_.setText("-"+new DecimalFormat("##.##").format(reminder_bal_jy));
                //Double sum_val=reminder_bal-persentage;
                jy_wal_substract=reminder_bal-persentage;
                main_pay.setText("\u20B9"+new DecimalFormat("##.##").format(jy_wal_substract));*/
             /*   if(Double.parseDouble(balance_jy)>Double.parseDouble(tot_pay)){
                    jywallet_amount_.setText("-"+Double.parseDouble(tot_pay));
                }
                else {
                     persentage=Double.parseDouble(tot_pay)*20/100;
                reminder_bal_jy=Double.parseDouble(balance_jy)-persentage;
                jywallet_amount_.setText("-"+new DecimalFormat("##.##").format(reminder_bal_jy));
                //Double sum_val=reminder_bal-persentage;
                jy_wal_substract=reminder_bal-persentage;
                main_pay.setText("\u20B9"+new DecimalFormat("##.##").format(jy_wal_substract));

                }*/
                persentage=Double.parseDouble(tot_pay)*20/100;
             if(Double.parseDouble(balance_jy)>= persentage){
                 jywallet_amount_.setText(""+new DecimalFormat("##.##").format(persentage));
                 jy_wal_substract=Double.parseDouble(tot_pay)-reminder_bal-persentage;
                 if(wallet.isChecked()==true){
                     jy_wal_substract=reminder_bal-persentage;
                 }
                 else {
                     jy_wal_substract=Double.parseDouble(tot_pay)-persentage;
                 }

                // main_pay.setText(""+new DecimalFormat("##.##").format(jy_wal_substract));
                 main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(jy_wal_substract))));

             }
             else {

                 //reminder_bal_jy=Double.parseDouble(balance_jy)-persentage;
                 jywallet_amount_.setText(""+new DecimalFormat("##.##").format(Double.parseDouble(balance_jy)));
                 //Double sum_val=reminder_bal-persentage;
                 if(wallet.isChecked()==true){
                     jy_wal_substract=reminder_bal-Double.parseDouble(balance_jy);
                 }
                 else {
                     jy_wal_substract=Double.parseDouble(tot_pay)-Double.parseDouble(balance_jy);
                 }
                 //main_pay.setText(""+new DecimalFormat("##.##").format(jy_wal_substract));
                 main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(jy_wal_substract))));
             }
             if(jy_wal_substract==0.0){
                 pay_status="1";
                 netbank.setEnabled(false);
                 credit_debit_card.setEnabled(false);
                 cash_delivery.setEnabled(false);
                 wallet.setEnabled(false);
                 wallet.setChecked(false);
                 netbank.setChecked(false);
                 credit_debit_card.setChecked(false);
                 cash_delivery.setChecked(false);


                 //wallet.setEnabled(true);
             }
           /*  else {
                 netbank.setEnabled(true);
                 credit_debit_card.setEnabled(true);
                 cash_delivery.setEnabled(true);
                 wallet.setEnabled(true);
             }*/


            }
            else {


                    //reminder_bal_jy=Double.parseDouble(balance_jy)-persentage;
                   // jywallet_amount_.setText("-"+new DecimalFormat("##.##").format(Double.parseDouble(balance_jy)));
                    jywallet_amount_.setText("0");
                    //Double sum_val=reminder_bal-persentage;


               /*     main_pay.setText(""+new DecimalFormat("##.##").format(reminder_bal));*/
                main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(reminder_bal))));
                /*if(jy_wal_substract!=0.0){
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    wallet.setEnabled(true);
                    wallet.setChecked(true);
                    netbank.setChecked(true);
                    credit_debit_card.setChecked(true);
                    cash_delivery.setChecked(true);
                }
                else {
                    jaayu_details.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                }*/

            }
        }
    });
    wallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(wallet.isChecked()){
                nor_wallet="Wallet";
               // pay_status="1";
                wallet.setSelected(true);
                wallet.setChecked(true);
               /* jaayu_details.setChecked(false);
                netbank.setChecked(false);
                credit_debit_card.setChecked(false);
                cash_delivery.setChecked(false);*/

                     if(Double.parseDouble(balance_og)>=Double.parseDouble(tot_pay)){
                         wallet_amount_.setText(""+Double.parseDouble(tot_pay));
                         reminder_bal=Double.parseDouble(tot_pay)-Double.parseDouble(tot_pay);
                         main_pay.setText(""+new DecimalFormat("##.##").format(reminder_bal));
                        /* main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(reminder_bal))));*/

                     }
                     else {
                         reminder_bal=Double.parseDouble(tot_pay)-Double.parseDouble(balance_og);
                         wallet_amount_.setText(""+balance_og);
                         main_pay.setText(""+new DecimalFormat("##.##").format(reminder_bal));
                        /* main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(reminder_bal))));*/
                     }
                     if(reminder_bal==0.0){
                         pay_status="1";
                         jaayu_details.setEnabled(false);
                        // wallet.setChecked(true);
                         netbank.setEnabled(false);
                         credit_debit_card.setEnabled(false);
                         cash_delivery.setEnabled(false);
                         jaayu_details.setChecked(false);
                         netbank.setChecked(false);
                         credit_debit_card.setChecked(false);
                         cash_delivery.setChecked(false);


                     }
                     else {
                         jaayu_details.setEnabled(true);
                         netbank.setEnabled(true);
                         credit_debit_card.setEnabled(true);
                         cash_delivery.setEnabled(true);
                     }

              /*  reminder_bal=Double.parseDouble(balance_og)-Double.parseDouble(tot_pay);
                wallet_amount_.setText("-"+reminder_bal);*/
                //wallwt_amount.setText("("+"\u20B9"+""+reminder_bal+")");


            }
            else {
               // reminder_bal=Double.parseDouble(tot_pay)+Double.parseDouble(balance_og);
                wallet_amount_.setText("0");
                main_pay.setText(""+new DecimalFormat("##.##").format(Double.parseDouble(tot_pay)));
            /*    main_pay.setText(""+Math.round(Double.parseDouble(new DecimalFormat("##.##").format(tot_pay))));*/

                    jaayu_details.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                   /* jaayu_details.setChecked(true);
                    netbank.setChecked(true);
                    credit_debit_card.setChecked(true);
                    cash_delivery.setChecked(true);*/

                /*else {
                    jaayu_details.setEnabled(true);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                }*/
            }
        }
    });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,Place_order_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    //Do it with this it will work
                                    JSONObject person = new JSONObject(response);
                                    String status=person.getString("status");
                                    if(status.equals("1")){
                                        String message=person.getString("message");
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
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
                        params.put("days", day_time);
                        params.put("duration", duration);
                       // params.put("spid", presc_img);
                        params.put("payment_method", cod);
                        params.put("status", "1");
                        return params;
                    }
                };

                requestQueue.add(postRequest);*/
               if(!netbank.isChecked()&& !credit_debit_card.isChecked()){
                   RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
                   StringRequest postRequest = new StringRequest(Request.Method.POST,Place_order_url,
                           new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                   // response
                                   Log.d("Response", response);
                                   try {
                                       //Do it with this it will work
                                       JSONObject person = new JSONObject(response);
                                       String status=person.getString("status");
                                       if(status.equals("1")){
                                           String message=person.getString("Message");
                                           //Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                          /* Intent goToThankPage=new Intent(Payment.this,ThankYouPayment.class);
                                           startActivity(goToThankPage);
                                           overridePendingTransition(0,0);
                                           finish();*/
                                           ViewGroup viewGroup = findViewById(android.R.id.content);

                                           //then we will inflate the custom alert dialog xml that we created
                                           View dialogView = LayoutInflater.from(Payment.this).inflate(R.layout.payment_success_dialog, viewGroup, false);
                                           TextView masege=(TextView)dialogView.findViewById(R.id.return_msg) ;
                                           Button buttonOk=(Button)dialogView.findViewById(R.id.buttonOk);
                                           masege.setText(message);


                                           //Now we need an AlertDialog.Builder object
                                           AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);

                                           //setting the view of the builder to our custom view that we already inflated
                                           builder.setView(dialogView);

                                           //finally creating the alert dialog and displaying it
                                           final AlertDialog alertDialog = builder.create();
                                           alertDialog.show();
                                           buttonOk.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   alertDialog.dismiss();
                                                   Intent goJumpPage=new Intent(Payment.this,OrderPage.class);

                                                   startActivity(goJumpPage);
                                                   overridePendingTransition(0,0);
                                                   finish();
                                               }
                                           });
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

                           params.put("user_id", u_id);
                           params.put("order_id", order_id);
                           params.put("payment_status", pay_status);
                           params.put("payment_id", "1");
                           params.put("total_payment",tot_pay);
                           params.put("n_wallet", String.valueOf(Math.round(Double.parseDouble(wallet_amount_.getText().toString()))));
                           params.put("j_wallet",String.valueOf(Math.round(Double.parseDouble(jywallet_amount_.getText().toString()))));
                           params.put("cod_pay", String.valueOf(Math.round(Double.parseDouble(main_pay.getText().toString()))));
                           params.put("online_pay", "0");
                           // params.put("spid", presc_img);

                          // params.put("status", "1");
                           return params;
                       }
                   };

                   requestQueue.add(postRequest);
               }
               else {
                   startPayment();
               }

            }
        });
    }
    private void  calcutate_section(){
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Orderdetails_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            progressDialog.dismiss();
                            JSONObject person = new JSONObject(response);
                            mrp_amt=person.getString("MRP");
                            save_amt=person.getString("Saving");
                            shippingcharge=person.getString("Shipping charge");
                            tot_pay=person.getString("Total Pay");

                            mrp_total.setText(mrp_amt);

                            save_amount.setText(save_amt);
                            total_save_price.setText(save_amt);

                            shipping_charge.setText(shippingcharge);

                            payable_amount.setText(tot_pay);
                            main_pay.setText(tot_pay);

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
                        progressDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);
                params.put("oid", order_id);
                params.put("order_id", ord_jy_id);
                params.put("instant", instant);


                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void normal_wallet_amt_display(){
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Normal_wallet_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");


                            if(status.equals("1")){
                                progressDialog.dismiss();
                                 balance_og=person.getString("balance");
                                 wallwt_amount.setText(" "+"\u20B9"+balance_og+" ");


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
                        progressDialog.dismiss();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id" ,u_id);
                /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    private void jaayu_wallet_amount_displat(){
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Jaayu_wallet_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");


                            if(status.equals("1")){
                                progressDialog.dismiss();
                                        balance_jy=person.getString("balance");

                                jy_wallwt_amount.setText(" "+"\u20B9"+balance_jy+" ");


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
                        progressDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id" ,u_id);
                /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
 public void startPayment(){
     final Activity activity = this;

     final Checkout co = new Checkout();

     try {
         JSONObject options = new JSONObject();
         options.put("name","JAAYU");
         options.put("description", "Payment Charges");
         options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
         options.put("currency", "INR");
         double total = Math.round(Double.parseDouble(main_pay.getText().toString()));
         total = total * 100;
         options.put("amount", Math.round(total));
         JSONObject preFill = new JSONObject();
         preFill.put("email", u_email);
         preFill.put("contact", u_phone);
         options.put("prefill", preFill);
         co.open(activity, options);
     } catch (JSONException e) {
        // e.printStackTrace();
         Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
         e.printStackTrace();
     }
 }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Payment.this, OrderDetails.class);
        intent.putExtra("Order_id",Integer.valueOf(order_id));
        intent.putExtra("Order_Vid",ord_jy_id);
        intent.putExtra("Instant",instant);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onPaymentSuccess(final String razorpayPaymentID) {
        //Toast.makeText(this,  razorpayPaymentID, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Place_order_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");
                            if(status.equals("1")){
                                String message=person.getString("Message");
                               // Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                              /*  Intent goToThankPage=new Intent(Payment.this,ThankYouPayment.class);
                                startActivity(goToThankPage);
                                overridePendingTransition(0,0);
                                finish();*/
                                ViewGroup viewGroup = findViewById(android.R.id.content);

                                //then we will inflate the custom alert dialog xml that we created
                                View dialogView = LayoutInflater.from(Payment.this).inflate(R.layout.payment_success_dialog, viewGroup, false);
                                TextView masege=(TextView)dialogView.findViewById(R.id.return_msg) ;
                                Button buttonOk=(Button)dialogView.findViewById(R.id.buttonOk);
                                masege.setText(message);


                                //Now we need an AlertDialog.Builder object
                                AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);

                                //setting the view of the builder to our custom view that we already inflated
                                builder.setView(dialogView);

                                //finally creating the alert dialog and displaying it
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        alertDialog.dismiss();
                                        Intent goJumpPage=new Intent(Payment.this,OrderPage.class);

                                        startActivity(goJumpPage);
                                        overridePendingTransition(0,0);
                                        finish();
                                    }
                                });
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

                params.put("user_id", u_id);
                params.put("order_id", order_id);
                params.put("payment_status", pay_status);
                params.put("payment_id", razorpayPaymentID);
                params.put("total_payment",tot_pay);
                params.put("n_wallet", String.valueOf(Math.round(Double.parseDouble(wallet_amount_.getText().toString()))));
                params.put("j_wallet",String.valueOf(Math.round(Double.parseDouble(jywallet_amount_.getText().toString()))));
                params.put("cod_pay", "0");
                params.put("online_pay", String.valueOf(Math.round(Double.parseDouble(main_pay.getText().toString()))));
                // params.put("spid", presc_img);

                // params.put("status", "1");
                return params;
            }
        };

        requestQueue.add(postRequest);
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,disclaimer_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");
                            if(status.equals("1")){
                                JSONObject ins_con=person.getJSONObject("discm");
                                String content_ins=ins_con.getString("body");
                                Spanned htmlAsSpanned = Html.fromHtml(content_ins);
                                disclaimer.setText(String.valueOf(htmlAsSpanned));

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
                return params;
            }

        };
        requestQueue.add(postRequest);
    }
}
