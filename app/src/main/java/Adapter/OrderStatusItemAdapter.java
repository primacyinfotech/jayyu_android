package Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.jaayu.OrderStatusConfirm;
import com.jaayu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.OrderStatusItemModel;

public class OrderStatusItemAdapter extends RecyclerView.Adapter<OrderStatusItemAdapter.MyViewHolder>{
    public  static ArrayList<OrderStatusItemModel> modelList;
    private Context context;
    SharedPreferences prefs_register;
    SharedPreferences ord_id_instant;
    private String u_id,spin_no;
    private String Order_quantity_update_url= BaseUrl.BaseUrlNew+"order_quantity_edit";
    private String Order_Item_Delete=BaseUrl.BaseUrlNew+"order_del_con";
    ProgressDialog progressDialog;

    public OrderStatusItemAdapter(ArrayList<OrderStatusItemModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderStatusItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_order_change, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderStatusItemAdapter.MyViewHolder holder, final int position) {
        final OrderStatusItemModel mList = modelList.get(position);
        prefs_register = context.getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        holder.item_name.setText(mList.getTitle());
        holder.amount.setText("Price: "+"\u20B9"+mList.getPrice_normal());
        holder.unit.setText(mList.getStrip());
        if (holder.mrp != null) {
          /*  DecimalFormat format_tot = new DecimalFormat("##.##");
            String formatted_sum = format_tot.format(mList.getTotal());*/

            holder.mrp.setText("\u20B9"+mList.getMrp_price());
            // holder.item_total.setText(formatted_sum);
            holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        //holder.qyt.setText(mList.getQuantity()+"x");
       // holder.cart_product_quantity_tv.setText(""+mList.getQuantity());
        holder.cart_product_quantity_tv.setText(""+modelList.get(position).getQuantity());
        holder.cart_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("loading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);*/
                int count=Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));

               /* if(count<=29)*/
                int act_qy=modelList.get(position).getQuantity();
                int sub_qty=act_qy-1;
                if(count<act_qy){
                    count++;
                    final int cou=count++;
                    holder.cart_product_quantity_tv.setText("" + cou);
                    modelList.get(position).setEditTextValue(cou);
                   /* RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Order_quantity_update_url,
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
                                        if(status.equals("1")){
                                            String msg=person.getString("Message");
                                      Intent intent= new Intent(context,OrderStatusConfirm.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                      context.startActivity(intent);
                                      ((Activity) context).overridePendingTransition(0,0);

                                          *//*  Intent intent= new Intent("message_order_intent");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0,0);
                                            progressDialog.dismiss();*//*


                                             //Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
                                        }





                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            params.put("id", String.valueOf(mList.getItem_id()));
                            params.put("quantity", String.valueOf(cou));




                            return params;
                        }
                    };
                    queue.add(postRequest);*/
                }
            }
        });
        holder.cart_minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("loading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);*/
                int count= Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));
                if (count == 1){
                    holder.cart_product_quantity_tv.setText("1");

                    final int cou2=count;
                    modelList.get(position).setEditTextValue(cou2);
                   /* RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Order_quantity_update_url,
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
                                        if(status.equals("1")){
                                            Intent intent= new Intent(context,OrderStatusConfirm.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            context.startActivity(intent);
                                            ((Activity) context).overridePendingTransition(0,0);
                                            *//*Intent intent= new Intent("message_order_intent");
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0,0);
                                            progressDialog.dismiss();*//*
                                            //  Toast.makeText(context,status,Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
                                        }





                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            params.put("id", String.valueOf(mList.getItem_id()));
                            params.put("quantity", String.valueOf(cou2));


                            return params;
                        }
                    };
                    queue.add(postRequest);*/
                }
                else {
                    count -= 1;
                    holder.cart_product_quantity_tv.setText("" + count);
                    final int cou3=count;
                    modelList.get(position).setEditTextValue(cou3);
                 /*   RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Order_quantity_update_url,
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
                                        if(status.equals("1")){
                                            Intent intent= new Intent(context,OrderStatusConfirm.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            context.startActivity(intent);
                                            ((Activity) context).overridePendingTransition(0,0);
                                         *//*   Intent intent= new Intent("message_order_intent");

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0,0);
                                            progressDialog.dismiss();*//*
                                            // Toast.makeText(context,status+" "+"Removed",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            // Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
                                        }





                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            params.put("id", String.valueOf(mList.getItem_id()));
                            params.put("quantity", String.valueOf(cou3));
                            return params;
                        }
                    };
                    queue.add(postRequest);*/
                }

            }
        });
        holder.delete_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, Order_Item_Delete,
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
                                        String msg=person.getString("Message");
                                        modelList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, modelList.size());
                                        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                                        ord_id_instant = context.getSharedPreferences(
                                                "Order_id Details", Context.MODE_PRIVATE);
                                        int od_id=ord_id_instant.getInt("OrderID",0);
                                        String od_instant=ord_id_instant.getString("INSTANT","");
                                        Intent intent= new Intent(context, OrderStatusConfirm.class);
                                       /* intent.putExtra("Order_id",od_id);
                                        intent.putExtra("Instant",od_instant);*/
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        ((Activity) context).overridePendingTransition(0,0);
                                        context.startActivity(intent);
                                       /* Intent intent= new Intent("message_delete_intent");

                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                        ((Activity) context).overridePendingTransition(0,0);*/

                                    }





                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                        params.put("id", String.valueOf(modelList.get(position).getItem_id()));



                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        holder.item_select_check.setChecked(modelList.get(position).getSelected());
        holder.item_select_check.setTag(position);
        holder.item_select_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos=(Integer) holder.item_select_check.getTag();
                if(modelList.get(pos).getSelected()){
                    modelList.get(pos).setSelected(false);
                }
                else {
                    modelList.get(pos).setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_minus_img,cart_plus_img;
        TextView cart_product_quantity_tv,delete_item_button,item_name,qyt,unit,mrp,amount;
        CheckBox item_select_check;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_plus_img=(ImageView)itemView.findViewById(R.id.cart_plus_img);
            cart_minus_img=(ImageView)itemView.findViewById(R.id.cart_minus_img_);
            delete_item_button=(TextView)itemView.findViewById(R.id.delete_item_button);
            item_name=(TextView)itemView.findViewById(R.id.item_name);
           // qyt=(TextView)itemView.findViewById(R.id.qyt);
            unit=(TextView)itemView.findViewById(R.id.unit);
            mrp=(TextView)itemView.findViewById(R.id.mrp);
            amount=(TextView)itemView.findViewById(R.id.amount);
            cart_product_quantity_tv=(TextView)itemView.findViewById(R.id.cart_product_quantity_tv);
            item_select_check=(CheckBox)itemView.findViewById(R.id.item_select_check);
        }
    }
}
