package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SubscriptionDelivery extends AppCompatActivity {
    ImageView back_button;
private CheckBox one_time_order,thirty_day_order,fortyfive_day_order,sixty_day_order,single_day_order,selected_three_delivery,
    selected_six_delivery,single_delivery_order;
private EditText edt_single_day,edt_single_delivery;
private LinearLayout custom_part_delivery;
    private Button confirm_btn;
    String user_id,user_add,One_Day,thirty_Day,fortifive_day,sixty_day,three_days_deliveries,six_days_delivery,single_day,single_delivery,day_portion,duration,prescription_img;
    private String Place_order_url= BaseUrl.BaseUrlNew+"addorder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent getUserDetails=getIntent();
        user_add=getUserDetails.getStringExtra("Address");
        user_id=getUserDetails.getStringExtra("userID");
        prescription_img=getUserDetails.getStringExtra("prescription_img");

        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        one_time_order=(CheckBox)findViewById(R.id.one_time_order);
        thirty_day_order=(CheckBox)findViewById(R.id.thirty_day_order);
        fortyfive_day_order=(CheckBox)findViewById(R.id.fortyfive_day_order);
        sixty_day_order=(CheckBox)findViewById(R.id.sixty_day_order);
        single_day_order=(CheckBox)findViewById(R.id.single_day_order);
        selected_three_delivery=(CheckBox)findViewById(R.id.selected_three_delivery);
        selected_six_delivery=(CheckBox)findViewById(R.id.selected_six_delivery);
        single_delivery_order=(CheckBox)findViewById(R.id.single_delivery_order);
        edt_single_day=(EditText)findViewById(R.id.edt_single_day);
        edt_single_day.setEnabled(false);

        edt_single_delivery=(EditText)findViewById(R.id.edt_single_delivery);
        custom_part_delivery=(LinearLayout)findViewById(R.id.custom_part_delivery);
        confirm_btn=(Button)findViewById(R.id.confirm_btn);
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
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
     one_time_order.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(one_time_order.isChecked()){
                /* thirty_day_order.setChecked(false);
                 fortyfive_day_order.setChecked(false);
                 sixty_day_order.setChecked(false);
                 single_day_order.setChecked(false);
                 selected_three_delivery.setChecked(false);
                 selected_six_delivery.setChecked(false);
                 single_delivery_order.setChecked(false);*/
                One_Day="1";

                 thirty_day_order.setEnabled(false);
                 fortyfive_day_order.setEnabled(false);
                 sixty_day_order.setEnabled(false);
                 single_day_order.setEnabled(false);


                 custom_part_delivery.setVisibility(View.GONE);
                 edt_single_day.setEnabled(false);

             }
             else {
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
                  /*  single_day=edt_single_day.getText().toString();*/
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
                    /*thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*/

                }
                else {
                    one_time_order.setEnabled(false);
                    selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);
                 /*   thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*/
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
                 /*   one_time_order.setEnabled(false);
                    thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    single_delivery_order.setEnabled(false);
                    edt_single_delivery.setEnabled(false);*/
                }
                else {
                    one_time_order.setEnabled(false);
                    selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);
                    /*thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    single_delivery_order.setEnabled(true);
                    edt_single_delivery.setEnabled(true);*/
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
                   /* single_delivery=edt_single_delivery.getText().toString();*/
                    one_time_order.setEnabled(false);
                  /*  thirty_day_order.setEnabled(true);
                    fortyfive_day_order.setEnabled(true);
                    sixty_day_order.setEnabled(true);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(false);
                    selected_six_delivery.setEnabled(false);*/

                }
                else {
                    one_time_order.setEnabled(false);
                    selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    edt_single_delivery.setEnabled(false);

                  /*  thirty_day_order.setEnabled(false);
                    fortyfive_day_order.setEnabled(false);
                    sixty_day_order.setEnabled(false);
                    single_day_order.setEnabled(true);
                    edt_single_day.setEnabled(true);
                    selected_three_delivery.setEnabled(true);
                    selected_six_delivery.setEnabled(true);
                    edt_single_delivery.setEnabled(false);*/
                }
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single_day=edt_single_day.getText().toString();
                single_delivery=edt_single_delivery.getText().toString();
                if(One_Day!=null){
                    day_portion=One_Day;
                }
                else if(thirty_Day!=null){
                    day_portion=thirty_Day;

                }
                else if(fortifive_day!=null){
                    day_portion=fortifive_day;
                }
                else  if(sixty_day!=null){
                    day_portion=sixty_day;
                }
                else if(single_day!=null){
                    day_portion=single_day;
                }
                else {
                    day_portion="";
                }
                if(three_days_deliveries!=null){
                    duration=three_days_deliveries;
                }
                else if(six_days_delivery!=null){
                    duration=six_days_delivery;
                }
                else if(single_delivery!=null){
                    duration=single_delivery;
                }
                else {
                    duration="";
                }
           if(!one_time_order.isChecked()){

               Toast.makeText(getApplicationContext(),"Please,Checked One Time Order",Toast.LENGTH_LONG).show();
           }
           else {

               RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDelivery.this);
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
                                       Intent goToOrderView_method=new Intent(SubscriptionDelivery.this,OrderReview.class);
                                       goToOrderView_method.putExtra("DAY",day_portion);
                                       goToOrderView_method.putExtra("Duration",duration);
                                       goToOrderView_method.putExtra("User_add",user_add);
                                       goToOrderView_method.putExtra("User_ID",user_id);
                                       // goTopayment_method.putExtra("presc_img",prescription_img);
                                       startActivity(goToOrderView_method);
                                       overridePendingTransition(0,0);
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

               requestQueue.add(postRequest);

           }
              if(!thirty_day_order.isChecked()&&!fortyfive_day_order.isChecked()&&!sixty_day_order.isChecked()
              && single_day.matches("")||!selected_three_delivery.isChecked()&&!selected_six_delivery.isChecked()&&single_delivery.matches("")){
                  Toast.makeText(getApplicationContext(),"Please, Checked All Preference Of Delivery Order",Toast.LENGTH_LONG).show();
              }


              else {
                  if(single_day.equals("0")||single_day.equals("00")||single_delivery.equals("0")||single_delivery.equals("00")){

                  }
                  else {
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
                              params.put("days", day_portion);
                              params.put("duration", duration);
                              // params.put("spid", presc_img);
                              // params.put("payment_method", cod);
                              params.put("status", "1");
                              return params;
                          }
                      };

                      requestQueue.add(postRequest);
                  }

              }





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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SubscriptionDelivery.this, OrderSummery.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
