package com.jaayu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.PrescriptionReqDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Adapter.OrderAdapter;
import Adapter.OrderMainAdapter;
import Model.OrderModel;
import Model.OrderPressModel;

public class OrderPage extends AppCompatActivity {
    private ArrayList<OrderModel> modelList;
    private ArrayList<Object> normalandPress = new ArrayList<>();
    ;
    private ArrayList<OrderModel> orderList = new ArrayList<>();
    ;
    RecyclerView recyclerView;
    private ImageView back_button;
    OrderAdapter orderAdapter;
    OrderMainAdapter orderMainAdapter;
    //private String order_track_url= BaseUrl.BaseUrlNew+"order_display_profile";
    //private String Press_order_track_url=BaseUrl.BaseUrlNew+"profile_prescription_order";
    private String reorder_url = BaseUrl.BaseUrlNew + "reorder";
    SharedPreferences prefs_register;
    private String order_people_name, u_id, from;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        from = getIntent().getStringExtra("from");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences("Register Details", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(OrderPage.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        u_id = prefs_register.getString("USER_ID", "");
        modelList = new ArrayList<>();
        /*modelList.add(new OrderModel("PO025896456","Ashish Agarwal","12,Nov,2019,4:58PM"));
        recyclerView=(RecyclerView)findViewById(R.id.rv_order);
        orderAdapter=new OrderAdapter(modelList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();*/
        recyclerView = (RecyclerView) findViewById(R.id.rv_order);
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(OrderPage.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();*/
                onBackPressed();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                //finish();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        getOrderList();
        //getOrderTrack();
    }

    private void getOrderList() {

        progressDialog.show();
        RequestQueue requestQueue2 = Volley.newRequestQueue(OrderPage.this);
        StringRequest postRequest2 = new StringRequest(Request.Method.POST, BaseUrl.orderList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person2 = new JSONObject(response);
                            String status = person2.getString("status");
                            if (status.equals("1")) {
                                JSONArray jsonArray2 = person2.getJSONArray("Order");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    JSONObject object2 = jsonArray2.getJSONObject(i);
                                    OrderModel orderModel = new OrderModel();
                                    orderModel.setTbl_order_id(object2.getInt("id"));
                                    orderModel.setOrder_id(object2.getString("orderid"));
                                    String date_view = object2.getString("order_date");
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                                        Date testDate = sdf.parse(date_view);
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy HH:mm");
                                        date_view = formatter.format(testDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    orderModel.setOrder_date(date_view);
                                    orderModel.setShip_status(object2.getString("shipping_status"));
                                    orderModel.setInstant(object2.getString("instant"));
                                    orderModel.setPrescription_chk(object2.getString("prescription"));
                                    orderModel.setType(object2.getString("type"));
                                    orderList.add(orderModel);
                                }

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    orderList = (ArrayList<OrderModel>) orderList.stream()
                                            .sorted((o1, o2) -> o1.getOrder_date().compareTo(o2.getOrder_date()))
                                            .collect(Collectors.toList());
                                    //.sorted(Comparator.comparing(OrderModel::getOrder_date))
                                    Collections.reverse(orderList);
                                }else {
                                    Collections.sort(orderList, (lhs, rhs) -> lhs.getOrder_date().compareTo(rhs.getOrder_date()));
                                    Collections.reverse(orderList);
                                }

                                orderMainAdapter = new OrderMainAdapter(OrderPage.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this, RecyclerView.VERTICAL, false));
                                recyclerView.setAdapter(orderMainAdapter);
                                orderMainAdapter.setNormalandPress(orderList);
                                orderMainAdapter.notifyDataSetChanged();
                            }
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
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
  /*private void getOrderTrack(){
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
                                 // orderModel.setOrder_mame(order_people_name);
                                  orderModel.setOrder_id(object.getString("orderid"));
                                  String date_view=object.getString("order_date");
                              *//*    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
                                  Date testDate=sdf.parse(date_view);
                                  SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                  String newFormat = formatter.format(testDate);*//*
                                  orderModel.setOrder_date(date_view);
                                 // orderModel.setOrder_date(object.getString("order_date"));
                                  orderModel.setShip_status(object.getString("shipping_status"));

                                  orderModel.setInstant(object.getString("instant"));
                                  orderModel.setPrescription_chk(object.getString("prescription"));
                                  normalandPress.add(orderModel);
                              }
                            *//*  orderAdapter=new OrderAdapter(modelList,OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderAdapter);
                              orderAdapter.notifyDataSetChanged();*//*
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
                                 // orderModel.setOrder_mame(order_people_name);
                                  orderModel.setOrder_id(object2.getString("orderid"));
                                  String date_view=object2.getString("created_at");
                                  //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
                                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                                  Date testDate=sdf.parse(date_view);
                                  //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                  SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy");
                                  String newFormat = formatter.format(testDate);
                                  orderModel.setOrder_date(newFormat);
                                  //orderModel.setOrder_date(object2.getString("created_at"));
                                  //orderModel.setOrder_date(object2.getString("created_at"));
                                  orderModel.setShip_status(object2.getString("shipping_status"));
                                  orderModel.setInstant(object2.getString("instant"));
                                  orderModel.setPrescription_chk(object2.getString("prescription"));
                                  normalandPress.add(orderModel);
                              }
                            *//*  orderAdapter=new OrderAdapter(modelList,OrderPage.this);
                              recyclerView.setHasFixedSize(true);
                              recyclerView.setLayoutManager(new LinearLayoutManager(OrderPage.this,RecyclerView.VERTICAL,false));
                              recyclerView.setAdapter(orderAdapter);
                              orderAdapter.notifyDataSetChanged();*//*

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

              params.put("user_id", u_id);
              return params;
          }
      };

      requestQueue2.add(postRequest2);


  }*/

    @Override
    public void onBackPressed() {
        if (from != null && from.equals("thankYou"))
            startActivity(new Intent(OrderPage.this, MainActivity.class));
        else
            super.onBackPressed();

        finish();
    }
}
