package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.OrderAdapter;
import Adapter.OrderMainAdapter;
import Model.OrderModel;
import Model.OrderPressModel;

public class OrderPage extends AppCompatActivity {
    private ArrayList<OrderModel> modelList;
   private    ArrayList<Object> normalandPress;
    RecyclerView recyclerView;
    private ImageView back_button;
    OrderAdapter orderAdapter;
    OrderMainAdapter orderMainAdapter;
    private String order_track_url="https://work.primacyinfotech.com/jaayu/api/order_display_profile";
    private String Press_order_track_url="https://work.primacyinfotech.com/jaayu/api/profile_prescription_order";
    SharedPreferences prefs_register;
    String order_people_name,u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);

        u_id=prefs_register.getString("USER_ID","");
        modelList=new ArrayList<>();
        /*modelList.add(new OrderModel("PO025896456","Ashish Agarwal","12,Nov,2019,4:58PM"));
        recyclerView=(RecyclerView)findViewById(R.id.rv_order);
        orderAdapter=new OrderAdapter(modelList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();*/
        recyclerView=(RecyclerView)findViewById(R.id.rv_order);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( OrderPage.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getOrderTrack();
    }
  /*  private void getOrderTrack(){
        order_people_name= prefs_register.getString("USER_NAME", "");
        RequestQueue requestQueue = Volley.newRequestQueue(OrderPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,order_track_url,
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
                                JSONArray jsonArray=person.getJSONArray("Order");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    OrderModel orderModel=new OrderModel();
                                    orderModel.setTbl_order_id(object.getInt("id"));
                                    orderModel.setOrder_mame(order_people_name);
                                    orderModel.setOrder_id(object.getString("orderid"));
                                    orderModel.setOrder_date(object.getString("order_date"));
                                    orderModel.setShip_status(object.getString("shipping_status"));
                                    orderModel.setInstant(object.getString("instant"));
                                    orderModel.setPrescription_chk(object.getString("prescription"));
                                    modelList.add(orderModel);
                                }
                                orderAdapter=new OrderAdapter(modelList,OrderPage.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                                recyclerView.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();

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
                return params;
            }
        };

        requestQueue.add(postRequest);
    }*/
  private void getOrderTrack(){
      order_people_name= prefs_register.getString("USER_NAME", "");
      normalandPress=new ArrayList<>();
      RequestQueue requestQueue = Volley.newRequestQueue(OrderPage.this);
      StringRequest postRequest = new StringRequest(Request.Method.POST,order_track_url,
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
                              JSONArray jsonArray=person.getJSONArray("Order");
                              for(int i=0;i<jsonArray.length();i++){
                                  JSONObject object=jsonArray.getJSONObject(i);
                                  OrderModel orderModel=new OrderModel();
                                  orderModel.setTbl_order_id(object.getInt("id"));
                                  orderModel.setOrder_mame(order_people_name);
                                  orderModel.setOrder_id(object.getString("orderid"));
                                  orderModel.setOrder_date(object.getString("order_date"));
                                  orderModel.setShip_status(object.getString("shipping_status"));
                                  orderModel.setInstant(object.getString("instant"));
                                  orderModel.setPrescription_chk(object.getString("prescription"));
                                  normalandPress.add(orderModel);
                              }
                            /*  orderAdapter=new OrderAdapter(modelList,OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderAdapter);
                              orderAdapter.notifyDataSetChanged();*/
                              orderMainAdapter=new OrderMainAdapter(OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderMainAdapter);
                              orderMainAdapter.setNormalandPress(normalandPress);
                              orderMainAdapter.notifyDataSetChanged();

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
              return params;
          }
      };

      requestQueue.add(postRequest);
      RequestQueue requestQueue2 = Volley.newRequestQueue(OrderPage.this);
      StringRequest postRequest2 = new StringRequest(Request.Method.POST,Press_order_track_url,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      // response
                      Log.d("Response", response);
                      try {
                          //Do it with this it will work
                          JSONObject person2 = new JSONObject(response);
                          String status=person2.getString("status");
                          if(status.equals("1")){
                              JSONArray jsonArray2=person2.getJSONArray("order");
                              for(int i=0;i<jsonArray2.length();i++){
                                  JSONObject object2=jsonArray2.getJSONObject(i);
                                  OrderPressModel orderModel=new OrderPressModel();
                                  orderModel.setTbl_order_id(object2.getInt("id"));
                                  orderModel.setOrder_mame(order_people_name);
                                  orderModel.setOrder_id(object2.getString("orderid"));
                                  orderModel.setOrder_date(object2.getString("created_at"));
                                  orderModel.setShip_status(object2.getString("shipping_status"));
                                  orderModel.setInstant(object2.getString("instant"));
                                  orderModel.setPrescription_chk(object2.getString("prescription"));
                                  normalandPress.add(orderModel);
                              }
                            /*  orderAdapter=new OrderAdapter(modelList,OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderAdapter);
                              orderAdapter.notifyDataSetChanged();*/
                              orderMainAdapter=new OrderMainAdapter(OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderMainAdapter);
                              orderMainAdapter.setNormalandPress(normalandPress);
                              orderMainAdapter.notifyDataSetChanged();


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
              return params;
          }
      };

      requestQueue2.add(postRequest2);


  }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( OrderPage.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
