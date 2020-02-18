package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {
    ImageView back_button;
    private RadioButton paytm,amazon_pay,ola_money,paypal_pay,netbank,credit_debit_card,jaayu_details,cash_delivery;
    private RadioGroup radio_three;
    String user_id,user_add,day_time,duration,cod,net_bank,presc_img;
    private String Order_tiem_total_dataUrl="https://work.primacyinfotech.com/jaayu/api/addtocart/sum_value";
    private String Place_order_url="https://work.primacyinfotech.com/jaayu/api/addorder";
    private TextView open_item,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,
            customer_name,address_text,email_add;
    private Button submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        radio_three=(RadioGroup)findViewById(R.id.radio_three);
        cash_delivery=(RadioButton)findViewById(R.id.cash_delivery);
        netbank=(RadioButton)findViewById(R.id.netbank);
        paytm=(RadioButton)findViewById(R.id.paytm);
        amazon_pay=(RadioButton)findViewById(R.id.amazon_pay);
        ola_money=(RadioButton)findViewById(R.id.ola_money);
        paypal_pay=(RadioButton)findViewById(R.id.paypal_pay);
        credit_debit_card=(RadioButton)findViewById(R.id.credit_debit_card);
        jaayu_details=(RadioButton)findViewById(R.id.jaayu_wallet);
        submit_btn=(Button)findViewById(R.id.submit_btn) ;
        mrp_total=(TextView)findViewById(R.id.mrp_total);
        total_save_price=(TextView)findViewById(R.id.total_save_price);
        shipping_charge=(TextView)findViewById(R.id.shipping_charge);
        payable_amount=(TextView)findViewById(R.id.payable_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        discount_limit_amt=(TextView)findViewById(R.id.discount_limit_amt);
        main_pay=(TextView)findViewById(R.id.main_pay);
        Intent passDataFromDeliveryPage=getIntent();
        user_id=passDataFromDeliveryPage.getStringExtra("User_ID");
        user_add=passDataFromDeliveryPage.getStringExtra("User_add");
        day_time=passDataFromDeliveryPage.getStringExtra("DAY");
        duration=passDataFromDeliveryPage.getStringExtra("Duration");
        presc_img=passDataFromDeliveryPage.getStringExtra("presc_img");

        calcutate_section();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(Payment.this, SubscriptionDelivery.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!paytm.isSelected()){
                    cod="Paytm";
                    Toast.makeText(getApplicationContext(),cod,Toast.LENGTH_LONG).show();
                    paytm.setSelected(true);
                    paytm.setChecked(true);
                    amazon_pay.setEnabled(false);
                    ola_money.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);

                }
                else {
                    paytm.setSelected(false);
                    paytm.setChecked(false);
                    amazon_pay.setEnabled(true);
                    ola_money.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }
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
                    paytm.setEnabled(false);
                    ola_money.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);


                }
                else {
                    amazon_pay.setSelected(false);
                    amazon_pay.setChecked(false);
                    paytm.setEnabled(true);
                    ola_money.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }

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
                    paytm.setEnabled(false);
                    amazon_pay.setEnabled(false);
                    paypal_pay.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);

                }
                else {
                    ola_money.setSelected(false);
                    ola_money.setChecked(false);
                    paytm.setEnabled(true);
                    amazon_pay.setEnabled(true);
                    paypal_pay.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }
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
                    paytm.setEnabled(false);
                    amazon_pay.setEnabled(false);
                    ola_money.setEnabled(false);
                    netbank.setEnabled(false);
                    credit_debit_card.setEnabled(false);
                    cash_delivery.setEnabled(false);
                    jaayu_details.setEnabled(false);

                }
                else {
                    paypal_pay.setSelected(false);
                    paypal_pay.setChecked(false);
                    paytm.setEnabled(true);
                    amazon_pay.setEnabled(true);
                    ola_money.setEnabled(true);
                    netbank.setEnabled(true);
                    credit_debit_card.setEnabled(true);
                    cash_delivery.setEnabled(true);
                    jaayu_details.setEnabled(true);


                }
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
                     paytm.setEnabled(false);
                     amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     cash_delivery.setEnabled(false);
                     jaayu_details.setEnabled(false);

                 }
                 else {
                     netbank.setSelected(false);
                     netbank.setChecked(false);
                     paytm.setEnabled(true);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     cash_delivery.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }
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
                     amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     cash_delivery.setEnabled(false);
                     jaayu_details.setEnabled(false);

                 }
                 else {
                     credit_debit_card.setSelected(false);
                     credit_debit_card.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     cash_delivery.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }
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
                     amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     jaayu_details.setEnabled(false);

                 }
                 else {
                     cash_delivery.setSelected(false);
                     cash_delivery.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     jaayu_details.setEnabled(true);


                 }
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
                     amazon_pay.setEnabled(false);
                     ola_money.setEnabled(false);
                     paypal_pay.setEnabled(false);
                     netbank.setEnabled(false);
                     credit_debit_card.setEnabled(false);
                     cash_delivery.setEnabled(false);

                 }
                 else {
                     jaayu_details.setSelected(false);
                     jaayu_details.setChecked(false);
                     amazon_pay.setEnabled(true);
                     ola_money.setEnabled(true);
                     paypal_pay.setEnabled(true);
                     netbank.setEnabled(true);
                     credit_debit_card.setEnabled(true);
                     cash_delivery.setEnabled(true);


                 }
             }
         });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                requestQueue.add(postRequest);
            }
        });
    }
    private void  calcutate_section(){
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Order_tiem_total_dataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            Double total_mrp_value = person.getDouble("MRP");
                            DecimalFormat format = new DecimalFormat("##.##");
                            String tot_mrp=format.format(total_mrp_value);
                            mrp_total.setText(tot_mrp);
                            Double tot_save_amt=person.getDouble("saving");
                            String sav_amt_tot=format.format(tot_save_amt);
                            save_amount.setText(sav_amt_tot);
                            total_save_price.setText(sav_amt_tot);
                            Double ship_charge=person.getDouble("shipping");
                            String ship_crhg=format.format(ship_charge);
                            shipping_charge.setText(ship_crhg);
                            Double pay_amount=person.getDouble("Total");
                            String p_amt = format.format(pay_amount);
                            String main_pay_amt=format.format(Math.round(pay_amount));
                            payable_amount.setText(p_amt);
                            main_pay.setText("\u20B9"+main_pay_amt);
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


                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Payment.this, SubscriptionDelivery.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

}
