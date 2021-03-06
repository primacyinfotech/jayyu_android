package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.PrescriptionCouponListAdapter;
import Model.CouponListModel;
import Model.SaveCoupon;

public class OrderPrescriptionInfo extends AppCompatActivity {

    CheckBox order_by_press,for_days,during_doctor,specify_medicin,call_me;
    EditText edt_days,edit_specify_medicin;
    LinearLayout first_child;
    String odrevery,duration,sp,days_to,specify,mob,val,u_id,phone;
    ImageView back_button;
    private Button btn_continue;
    TextView disclaimer;
    SharedPreferences prefs_register;
    String data,val1,val2,val3,val4,val_sub_1,val_sub_2,val_sub3,val_sub4,result,show_coupon,note;
    SharedPreferences prefs_Pass_Value1,prefs_Pass_Value2,prefs_Pass_Value3,prefs_Pass_Value4;
    private String Choose_first_url= BaseUrl.BaseUrlNew+"prescription_order_subscription_first";
    private String Choose_second_url=BaseUrl.BaseUrlNew+"prescription_order_subscription_second";
    private String Choose_third_url=BaseUrl.BaseUrlNew+"prescription_order_subscription_third";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    private LinearLayout apply_coupon_btn;
    TextView coupon_off_on,place_apply_coupon,additional_note;
    SaveCoupon myDb;
    PrescriptionCouponListAdapter couponListAdapter;
    private ArrayList<CouponListModel> couponListModelArrayList;
    Dialog dialog;
    private String coupon_list_url= BaseUrl.BaseUrlNew+"coupon_listing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_prescription_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new SaveCoupon(this);
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
        coupon_off_on=(TextView) findViewById(R.id.coupon_off_on);
        additional_note=(TextView) findViewById(R.id.additional_note);
        apply_coupon_btn=(LinearLayout)findViewById(R.id.apply_coupon_btn);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        Cursor res=myDb.getAllData();
        while (res.moveToNext()){
            show_coupon=res.getString(2);
        }
        if(show_coupon!=null){
            place_apply_coupon.setText(show_coupon);
            coupon_off_on.setVisibility(View.VISIBLE);
        }
        else {
            place_apply_coupon.setText("Apply Coupon");
            coupon_off_on.setVisibility(View.GONE);
        }
        coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                place_apply_coupon.setText("Apply Coupon");
                coupon_off_on.setVisibility(View.GONE);
            }
        });
        apply_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponListModelArrayList=new ArrayList<>();
                dialog = new Dialog(OrderPrescriptionInfo.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.coupon_dialog_layout);

                ImageView close_btn=(ImageView)dialog.findViewById(R.id.close_btn);
                final RecyclerView coupon_list=(RecyclerView)dialog.findViewById(R.id.coupon_list);
             /* couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"GOFERCE","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListAdapter=new CouponListAdapter(couponListModelArrayList,CartActivity.this);
              coupon_list.setHasFixedSize(true);
              coupon_list.setLayoutManager(new LinearLayoutManager(CartActivity.this));
              coupon_list.setAdapter(couponListAdapter);
              couponListAdapter.notifyDataSetChanged();*/
                RequestQueue requestQueue = Volley.newRequestQueue(OrderPrescriptionInfo.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,coupon_list_url,
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


                                        JSONArray jsonArray=person.getJSONArray("list");
                                        for(int i=0;i<jsonArray.length();i++){
                                            CouponListModel couponListModel=new CouponListModel();
                                            JSONObject object=jsonArray.getJSONObject(i);
                                            couponListModel.setCoupon_id(object.getInt("id"));
                                            couponListModel.setCoupon_code(object.getString("coupon_code"));
                                            couponListModel.setCoupon_img(object.getString("image"));
                                            couponListModel.setCoupn_code_details(object.getString("sdescr"));
                                            couponListModel.setCoupon_code_des(object.getString("heading"));
                                            /*String cDes=object.getString("descr");
                                            Spanned htmlAsSpanned = Html.fromHtml(cDes);
                                            couponListModel.setCoupon_code_des(String.valueOf(htmlAsSpanned));*/

                                            String date_view=object.getString("validtill");
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Date testDate=sdf.parse(date_view);
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy");
                                            String newFormat = formatter.format(testDate);
                                            couponListModel.setCoupon_time(newFormat);
                                            // couponListModel.setCoupon_code_des(object.getString("descr"));

                                            couponListModelArrayList.add(couponListModel);

                                        }
                                        couponListAdapter=new PrescriptionCouponListAdapter(couponListModelArrayList,OrderPrescriptionInfo.this);
                                        coupon_list.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                        coupon_list.setLayoutManager(layoutManager);

                                        coupon_list.setAdapter(couponListAdapter);
                                        couponListAdapter.notifyDataSetChanged();

                                    }





                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                } catch (ParseException e) {
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

                        /* params.put("user_id" ,u_id);*/
                        /* params.put("user_id" ,"35");*/

                        return params;
                    }

                };
                requestQueue.add(postRequest);
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*    place_apply_coupon.setText("Apply Coupon");
            coupon_off_on.setImageResource(R.drawable.rigth_arrow);
        }*/
                myDb.deleteData();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                place_apply_coupon.setText("Apply Coupon");
                coupon_off_on.setVisibility(View.GONE);


            }
        });
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
            note=additional_note.getText().toString();
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
                       if(note!=null){
                           params.put("note", note);
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
                        if(note!=null){
                            params.put("note", note);
                        }


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
                   if(note!=null){
                       params.put("note", note);
                   }


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
                   if(note!=null){
                       params.put("note", note);
                   }

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
    @Override
    public void onBackPressed() {
        Intent goToBack=new Intent(OrderPrescriptionInfo.this,OnlyUploadPrescription.class);
        startActivity(goToBack);
        overridePendingTransition(0,0);
        finish();
    }
}
