package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

public class OrderPrescriptionInfo extends AppCompatActivity {

    CheckBox order_by_press,for_days,during_doctor,specify_medicin,call_me;
    EditText edt_days,edit_specify_medicin;
    LinearLayout first_child;
    String odrevery,duration,sp,days_to,specify,mob,val,u_id,phone;
    ImageView back_button;
    private Button btn_continue;
    TextView disclaimer;
    SharedPreferences prefs_register;
    String data,val1,val2,val3,val4,val_sub_1,val_sub_2,val_sub3,val_sub4,result;
    SharedPreferences prefs_Pass_Value1,prefs_Pass_Value2,prefs_Pass_Value3,prefs_Pass_Value4;
    private String Choose_first_url= BaseUrl.BaseUrlNew+"prescription_order_subscription_first";
    private String Choose_second_url=BaseUrl.BaseUrlNew+"prescription_order_subscription_second";
    private String Choose_third_url=BaseUrl.BaseUrlNew+"prescription_order_subscription_third";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_prescription_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        phone=prefs_register.getString("USER_PHONE","");
        order_by_press=(CheckBox)findViewById(R.id.order_by_press);
        for_days=(CheckBox)findViewById(R.id.for_days);
        during_doctor=(CheckBox)findViewById(R.id.during_doctor);
        specify_medicin=(CheckBox)findViewById(R.id.specify_medicin);
        call_me=(CheckBox)findViewById(R.id.call_me);
        edit_specify_medicin=(EditText)findViewById(R.id.edit_specify_medicin);
        edt_days=(EditText)findViewById(R.id.edt_days);
        first_child=(LinearLayout)findViewById(R.id.first_child);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        getDisclimer();
       first_child.setVisibility(View.GONE);
        edt_days.setEnabled(false);
        edit_specify_medicin.setVisibility(View.GONE);
         order_by_press.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(order_by_press.isChecked()){
                    odrevery="1e";
                    first_child.setVisibility(View.VISIBLE);
                    specify_medicin.setChecked(false);
                    call_me.setChecked(false);
                    order_by_press.setChecked(true);
                }
                else {
                    first_child.setVisibility(View.GONE);
                    odrevery="";


                }
             }
         });
         for_days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(for_days.isChecked()){

                    edt_days.setEnabled(true);
                    during_doctor.setChecked(false);
                }
                else {
                    //during_doctor.setEnabled(true);
                    edt_days.setEnabled(false);

                }

             }
         });
         during_doctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(during_doctor.isChecked()){
                    duration="2d";
                    edt_days.setEnabled(false);
                    for_days.setChecked(false);
                }
                else {
                   // edt_days.setEnabled(true);
                    duration="";
                    //for_days.setEnabled(true);
                }

             }

         });
        specify_medicin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(specify_medicin.isChecked()){
                    sp="3s";

                    edit_specify_medicin.setVisibility(View.VISIBLE);
                    order_by_press.setChecked(false);
                    call_me.setChecked(false);
                    specify_medicin.setChecked(true);
                }
                else {
                    edit_specify_medicin.setVisibility(View.GONE);
                    sp="";

                }
            }
        });
    call_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (call_me.isChecked()) {
                mob="4m";
                specify_medicin.setChecked(false);
                order_by_press.setChecked(false);
                call_me.setChecked(true);
            }
            else {
                mob="";
            }
           /* else {
                specify_medicin.setChecked(true);
                order_by_press.setChecked(true);
                call_me.setChecked(false);
            }*/
        }
    });
    back_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goToBack=new Intent(OrderPrescriptionInfo.this,OnlyUploadPrescription.class);
            startActivity(goToBack);
            overridePendingTransition(0,0);
            finish();
        }
    });
    btn_continue.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            days_to=edt_days.getText().toString();
            specify=edit_specify_medicin.getText().toString();
          /*  Toast.makeText(getApplicationContext(),mob,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),sp,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),duration,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),odrevery,Toast.LENGTH_LONG).show();*/
       if(!order_by_press.isChecked()){
           Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_LONG).show();
       }
       else {
           if(!for_days.isChecked()){
               Toast.makeText(getApplicationContext(),"choose",Toast.LENGTH_LONG).show();
           }
           else {
               RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
               StringRequest postRequest = new StringRequest(Request.Method.POST,Choose_first_url,
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
                                       data=person.getString("Data");
                                       result=person.getString("result");
                                       prefs_Pass_Value1 = getSharedPreferences(
                                               "PASS_DATA_1", Context.MODE_PRIVATE);
                                       SharedPreferences.Editor editor1 = prefs_Pass_Value1.edit();
                                       editor1.putString("VALUE_ONE",data);
                                       editor1.putString("VALUE_sub_ONE",result);
                                       editor1.commit();
                                       val1=prefs_Pass_Value1.getString("VALUE_ONE","");
                                       val_sub_1=prefs_Pass_Value1.getString("VALUE_sub_ONE","");
/*
                                      Intent gotoPresOrderSummery=new Intent(OrderPrescriptionInfo.this,PrescriptionOrderSummery.class);
                                      gotoPresOrderSummery.putExtra("Value",val1);
                                      gotoPresOrderSummery.putExtra("Pref",val_sub_1);
                                      startActivity(gotoPresOrderSummery);
                                      overridePendingTransition(0,0);
                                      finish();*/
                                       Intent gotoPresOrderSummery=new Intent(OrderPrescriptionInfo.this,PrescriptionSubscriptionDelivery.class);
                                       gotoPresOrderSummery.putExtra("Value",val1);
                                       gotoPresOrderSummery.putExtra("Pref",val_sub_1);
                                       startActivity(gotoPresOrderSummery);
                                       overridePendingTransition(0,0);
                                       finish();

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
                       params.put("odrevery", odrevery);
                       if(days_to.equals("0")|| days_to.equals("00")){

                       }
                       else {
                           params.put("days", days_to);
                       }
                      // params.put("days", days_to);


                       return params;
                   }
               };

               requestQueue.add(postRequest);
           }
            if(!during_doctor.isChecked()){
               Toast.makeText(getApplicationContext(),"choose2",Toast.LENGTH_LONG).show();
           }
           else {
                RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,Choose_first_url,
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
                                      //  Toast.makeText(getApplicationContext(),"OK2",Toast.LENGTH_LONG).show();
                                        data=person.getString("Data");
                                        result=person.getString("result");
                                        prefs_Pass_Value2 = getSharedPreferences(
                                                "PASS_DATA_2", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor2 = prefs_Pass_Value2.edit();
                                        editor2.putString("VALUE_TWO",data);
                                        editor2.putString("VALUE_sub_TWO",result);
                                        editor2.commit();
                                        val2=prefs_Pass_Value2.getString("VALUE_TWO","");
                                        val_sub_2=prefs_Pass_Value2.getString("VALUE_sub_TWO","");
                                        Intent gotoPresOrderSummery=new Intent(OrderPrescriptionInfo.this,PrescriptionSubscriptionDelivery.class);
                                        gotoPresOrderSummery.putExtra("Value",val2);
                                        gotoPresOrderSummery.putExtra("Pref",val_sub_2);
                                        startActivity(gotoPresOrderSummery);
                                        overridePendingTransition(0,0);
                                        finish();

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
                        params.put("odrevery", odrevery);
                        params.put("duration", duration);


                        return params;
                    }
                };

                requestQueue.add(postRequest);
           }

       }
       if(!specify_medicin.isChecked()||specify.matches("")){
           Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_LONG).show();
       }
       else {
          // Toast.makeText(getApplicationContext(),"Ok",Toast.LENGTH_LONG).show();
           RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
           StringRequest postRequest = new StringRequest(Request.Method.POST,Choose_second_url,
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
                                //   Toast.makeText(getApplicationContext(),"OK3",Toast.LENGTH_LONG).show();
                                    data=person.getString("Data");
                                    result=person.getString("result");
                                   prefs_Pass_Value3 = getSharedPreferences(
                                           "PASS_DATA_3", Context.MODE_PRIVATE);
                                   SharedPreferences.Editor editor3 = prefs_Pass_Value3.edit();
                                   editor3.putString("VALUE_THREE",data);
                                   editor3.putString("VALUE_sub_THREE",result);
                                   editor3.commit();
                                   val3=prefs_Pass_Value3.getString("VALUE_THREE","");
                                   val_sub3=prefs_Pass_Value3.getString("VALUE_sub_THREE","");
                                   Intent gotoPresOrderSummery=new Intent(OrderPrescriptionInfo.this,PrescriptionSubscriptionDelivery.class);
                                   gotoPresOrderSummery.putExtra("Value",val3);
                                   gotoPresOrderSummery.putExtra("Pref",val_sub3);
                                   startActivity(gotoPresOrderSummery);
                                   overridePendingTransition(0,0);
                                   finish();

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
                   params.put("spe", sp);
                   params.put("specify", specify);


                   return params;
               }
           };

           requestQueue.add(postRequest);
       }
       if(!call_me.isChecked()){
           Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_LONG).show();
       }
       else {
           RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
           StringRequest postRequest = new StringRequest(Request.Method.POST,Choose_third_url,
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
                                   //Toast.makeText(getApplicationContext(),"OK4",Toast.LENGTH_LONG).show();
                                    data=person.getString("message");
                                   result=person.getString("result");
                                   prefs_Pass_Value4 = getSharedPreferences(
                                           "PASS_DATA_4", Context.MODE_PRIVATE);
                                   SharedPreferences.Editor editor4 = prefs_Pass_Value4.edit();
                                   editor4.putString("VALUE_FOUR",phone);
                                   editor4.putString("VALUE_sub_FOUR",result);
                                   editor4.commit();
                                   val4=prefs_Pass_Value4.getString("VALUE_FOUR","");
                                   val_sub4=prefs_Pass_Value4.getString("VALUE_sub_FOUR","");
                                   Intent gotoPresOrderSummery=new Intent(OrderPrescriptionInfo.this,PrescriptionSubscriptionDelivery.class);
                                   gotoPresOrderSummery.putExtra("Value",val4);
                                   gotoPresOrderSummery.putExtra("Pref",val_sub4);
                                   startActivity(gotoPresOrderSummery);
                                   overridePendingTransition(0,0);
                                   finish();

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
                   params.put("mob", mob);

                   return params;
               }
           };

           requestQueue.add(postRequest);
       }


         /*   RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
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
                    params.put("days", day_portion);
                    params.put("duration", duration);
                    // params.put("spid", presc_img);
                    // params.put("payment_method", cod);
                    params.put("status", "1");
                    return params;
                }
            };

            requestQueue.add(postRequest);*/



        }
    });

    }
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
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
                                disclaimer.setText(content_ins);
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
    @Override
    public void onBackPressed() {
        Intent goToBack=new Intent(OrderPrescriptionInfo.this,OnlyUploadPrescription.class);
        startActivity(goToBack);
        overridePendingTransition(0,0);
        finish();
    }
}
