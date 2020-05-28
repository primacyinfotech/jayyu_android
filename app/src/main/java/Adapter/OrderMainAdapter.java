package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.CartActivity;
import com.jaayu.Model.BaseUrl;
import com.jaayu.OldPresOrderDetailsPage;
import com.jaayu.OrderDetails;
import com.jaayu.OrderStatusConfirm;
import com.jaayu.PrescriptionOrderDetails;
import com.jaayu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.OrderModel;
import Model.OrderPressModel;

public class OrderMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_NORMAL=1,TYPE_PRESS=2;
    ArrayList<Object> normalandPress=new ArrayList<>();
    Context context;
    SharedPreferences ord_id_instant;
    SharedPreferences prefs_register;
    private String  reorder_url= BaseUrl.BaseUrlNew+"reorder";
     String u_id;
    public OrderMainAdapter(Context context) {
        this.context = context;
    }

    public void setNormalandPress(ArrayList<Object> normalandPress) {
        this.normalandPress = normalandPress;
    }

    @Override
    public int getItemViewType(int position) {
        if(normalandPress.get(position)instanceof OrderModel){
            return TYPE_NORMAL;
        }
        else if(normalandPress.get(position)instanceof OrderPressModel){
            return TYPE_PRESS;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case TYPE_NORMAL:
                View normalorderitemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_item_view, parent, false);
                viewHolder=new NormalOrder(normalorderitemView);
                break;
            case TYPE_PRESS:
                View pressorderitemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.order_item_view, parent, false);
                viewHolder=new PressOrderView(pressorderitemView);
                break;
            default:
                viewHolder=null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType=holder.getItemViewType();
        switch (viewType){
            case TYPE_NORMAL:
                OrderModel orderModel=(OrderModel)normalandPress.get(position);
                ((NormalOrder)holder).showNormalOrderDetails(orderModel);
                break;
            case TYPE_PRESS:
                OrderPressModel orderPressModel=(OrderPressModel)normalandPress.get(position);
                ((PressOrderView)holder).showPressOrderDetails(orderPressModel);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return normalandPress.size();
    }
    public class  NormalOrder extends RecyclerView.ViewHolder{

        TextView order_name,order_id,order_date,reoder_btn,active_order,active_order_two,active_order_three,active_order_four,active_order_five,active_order_six,
                active_order_seven,active_order_eight,active_order_nine,active_orderten,active_order_eleven;
        ImageView order_status_icon;
        CardView card_order;
        public NormalOrder(@NonNull View itemView) {
            super(itemView);
            order_name=(TextView)itemView.findViewById(R.id.order_name);
            order_id=(TextView)itemView.findViewById(R.id.order_id);
            order_date=(TextView)itemView.findViewById(R.id.order_date);
            reoder_btn=(TextView)itemView.findViewById(R.id.reoder_btn);
            active_order=(TextView)itemView.findViewById(R.id.active_order);
            active_order_two=(TextView)itemView.findViewById(R.id.active_order_two);
            active_order_three=(TextView)itemView.findViewById(R.id.active_order_three);
            active_order_four=(TextView)itemView.findViewById(R.id.active_order_four);
            active_order_five=(TextView)itemView.findViewById(R.id.active_order_five);
            active_order_six=(TextView)itemView.findViewById(R.id.active_order_six);
            active_order_seven=(TextView)itemView.findViewById(R.id.active_order_seven);
            active_order_eight=(TextView)itemView.findViewById(R.id.active_order_eight);
            active_order_nine=(TextView)itemView.findViewById(R.id.active_order_nine);
            active_orderten=(TextView)itemView.findViewById(R.id.active_orderten);
            active_order_eleven=(TextView)itemView.findViewById(R.id.active_order_eleven);
            order_status_icon=(ImageView)itemView.findViewById(R.id.order_status_icon);
            card_order=(CardView)itemView.findViewById(R.id.card_order);
        }
        public void showNormalOrderDetails(final OrderModel normalorder){
          order_name.setText(normalorder.getOrder_mame());
            // holder.order_name.setText(order_people_name);
           order_id.setText(normalorder.getOrder_id());
            order_date.setText(normalorder.getOrder_date());
            prefs_register = context.getSharedPreferences(
                    "Register Details", Context.MODE_PRIVATE);

            u_id=prefs_register.getString("USER_ID","");
            ord_id_instant = context.getSharedPreferences(
                    "Order_id Details", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = ord_id_instant.edit();
            editor.putInt("OrderID",normalorder.getTbl_order_id());
            editor.putString("INSTANT",normalorder.getInstant());
            editor.commit();
            active_order_two.setVisibility(View.GONE);
           active_order_three.setVisibility(View.GONE);
           card_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 /*   Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                    intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                    intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                    context.startActivity(intentGotodetaails);
                    ((Activity) context).overridePendingTransition(0,0);
                    ((Activity) context).finish();*/
                    String press_chk=normalorder.getPrescription_chk();
                    if(press_chk.equals("0")){
                      /*  Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();*/
                      /*  Intent intentGotodetaails=new Intent(context, OrderStatusConfirm.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();*/
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();

                    }
                    if(press_chk.equals("1")){
                       /* Intent intentGotodetaails=new Intent(context, PrescriptionOrderDetails.class);
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();*/
                      /*  Intent intentGotodetaails=new Intent(context, OrderStatusConfirm.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();*/
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("0")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }

                  if(normalorder.getShip_status().equals("2")){
                      Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                      intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                      intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                      intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                      context.startActivity(intentGotodetaails);
                      ((Activity) context).overridePendingTransition(0,0);
                      ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("3")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("4")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("5")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("6")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("7")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("8")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("9")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }
                    if(normalorder.getShip_status().equals("10")){
                        Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                        intentGotodetaails.putExtra("Order_Vid",normalorder.getOrder_id());
                        intentGotodetaails.putExtra("Order_id",normalorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",normalorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();
                    }


                }
            });

            String status=normalorder.getShip_status();
            if(status.equals("0")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
               active_order.setVisibility(View.VISIBLE);
               active_order_two.setVisibility(View.GONE);
               active_order_three.setVisibility(View.GONE);
               active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
               reoder_btn.setVisibility(View.GONE);
            }
            if(status.equals("1")){
               order_status_icon.setImageResource(R.drawable.tick);
               active_order.setVisibility(View.GONE);
               active_order_two.setVisibility(View.VISIBLE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
               reoder_btn.setVisibility(View.GONE);
            }
            if(status.equals("2")){
                order_status_icon.setImageResource(R.drawable.tick);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.VISIBLE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.GONE);

            }
            if(status.equals("3")){
                order_status_icon.setImageResource(R.drawable.tick);
               active_order.setVisibility(View.GONE);
               active_order_two.setVisibility(View.GONE);
               active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.VISIBLE);
               active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("4")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
               active_order_three.setVisibility(View.GONE);
               active_order_four.setVisibility(View.GONE);
               active_order_five.setVisibility(View.VISIBLE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
               reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("5")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.VISIBLE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("6")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.VISIBLE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("7")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.VISIBLE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("8")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.VISIBLE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("9")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.VISIBLE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }
            if (status.equals("10")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.VISIBLE);
                reoder_btn.setVisibility(View.VISIBLE);
                reoder_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest postRequest = new StringRequest(Request.Method.POST,reorder_url,
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
                                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                                                Intent intent = new Intent(context, CartActivity.class);
                                                context.startActivity(intent);
                                                ((Activity) context).overridePendingTransition(0,0);
                                                ((Activity) context).finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                                params.put("oid", String.valueOf(normalorder.getTbl_order_id()));

                                return params;
                            }

                        };
                        requestQueue.add(postRequest);
                    }
                });
            }


        }



    }

    public class PressOrderView extends RecyclerView.ViewHolder {
        TextView order_name,order_id,order_date,reoder_btn,active_order,active_order_two,active_order_three,active_order_four,active_order_five,active_order_six,
                active_order_seven,active_order_eight,active_order_nine,active_orderten,active_order_eleven;
        ImageView order_status_icon;
        CardView card_order;
        public PressOrderView(View pressorderitemView) {
            super(pressorderitemView);
            order_name=(TextView)pressorderitemView.findViewById(R.id.order_name);
            order_id=(TextView)pressorderitemView.findViewById(R.id.order_id);
            order_date=(TextView)pressorderitemView.findViewById(R.id.order_date);
            reoder_btn=(TextView)pressorderitemView.findViewById(R.id.reoder_btn);
            active_order=(TextView)pressorderitemView.findViewById(R.id.active_order);
            active_order_two=(TextView)pressorderitemView.findViewById(R.id.active_order_two);
            active_order_three=(TextView)pressorderitemView.findViewById(R.id.active_order_three);
            active_order_four=(TextView)pressorderitemView.findViewById(R.id.active_order_four);
            active_order_five=(TextView)pressorderitemView.findViewById(R.id.active_order_five);
            active_order_six=(TextView)itemView.findViewById(R.id.active_order_six);
            active_order_seven=(TextView)itemView.findViewById(R.id.active_order_seven);
            active_order_eight=(TextView)itemView.findViewById(R.id.active_order_eight);
            active_order_nine=(TextView)itemView.findViewById(R.id.active_order_nine);
            active_orderten=(TextView)itemView.findViewById(R.id.active_orderten);
            active_order_eleven=(TextView)itemView.findViewById(R.id.active_order_eleven);
            order_status_icon=(ImageView)pressorderitemView.findViewById(R.id.order_status_icon);
            card_order=(CardView)pressorderitemView.findViewById(R.id.card_order);
        }
        public void showPressOrderDetails(final OrderPressModel pressorder){
            order_name.setText(pressorder.getOrder_mame());
            // holder.order_name.setText(order_people_name);
            order_id.setText(pressorder.getOrder_id());
            order_date.setText(pressorder.getOrder_date());
            active_order_two.setVisibility(View.GONE);
            active_order_three.setVisibility(View.GONE);
            card_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String press_chk=pressorder.getPrescription_chk();
                    if(press_chk.equals("0")){
                        Intent intentGotodetaails=new Intent(context, OldPresOrderDetailsPage.class);
                        intentGotodetaails.putExtra("Order_id",pressorder.getOrder_id());
                        intentGotodetaails.putExtra("table_id",pressorder.getTbl_order_id());
                        intentGotodetaails.putExtra("Instant",pressorder.getInstant());
                        context.startActivity(intentGotodetaails);
                        ((Activity) context).overridePendingTransition(0,0);
                        ((Activity) context).finish();

                    }

                }
            });

            String status=pressorder.getShip_status();
            if(status.equals("0")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.VISIBLE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.GONE);
            }
            if(status.equals("1")){
                order_status_icon.setImageResource(R.drawable.tick);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.VISIBLE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.GONE);
            }
            if(status.equals("2")){
                order_status_icon.setImageResource(R.drawable.tick);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.VISIBLE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.GONE);

            }
            if(status.equals("3")){
                order_status_icon.setImageResource(R.drawable.tick);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.VISIBLE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("4")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.VISIBLE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("5")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.VISIBLE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("6")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.VISIBLE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("7")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.VISIBLE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("8")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.VISIBLE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("9")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.VISIBLE);
                active_order_eleven.setVisibility(View.GONE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
            if (status.equals("10")){
                order_status_icon.setImageResource(R.drawable.tickyellow);
                active_order.setVisibility(View.GONE);
                active_order_two.setVisibility(View.GONE);
                active_order_three.setVisibility(View.GONE);
                active_order_four.setVisibility(View.GONE);
                active_order_five.setVisibility(View.GONE);
                active_order_six.setVisibility(View.GONE);
                active_order_seven.setVisibility(View.GONE);
                active_order_eight.setVisibility(View.GONE);
                active_order_nine.setVisibility(View.GONE);
                active_orderten.setVisibility(View.GONE);
                active_order_eleven.setVisibility(View.VISIBLE);
                reoder_btn.setVisibility(View.VISIBLE);
            }
        }
    }
}
