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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.jaayu.CartActivity;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.PrescriptionReqDatabase;
import com.jaayu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.CartModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private ArrayList<CartModel> modelList;
    private Context context;
    ArrayList<String> qtyList;
    private String product_delete_url = BaseUrl.BaseUrlNew + "del_cart";
    private String quantity_update_url = BaseUrl.BaseUrlNew + "addtocart/quantity";
    SharedPreferences prefs_register;
    SharedPreferences prefs_Quantity;
    private String u_id, spin_no;
    ProgressDialog progressDialog;
    PrescriptionReqDatabase prescriptionReqDatabase;

    int c = 5;
    boolean isFirstLoad = true;

    public CartAdapter(ArrayList<CartModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder holder, final int position) {
        final CartModel mList = modelList.get(position);
        prescriptionReqDatabase = new PrescriptionReqDatabase(context);
        prefs_register = context.getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);

        prefs_Quantity = context.getSharedPreferences(
                "Quantity Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        holder.item_cart.setText(mList.getCart_item());
        holder.med_company.setText(mList.getCompany_name());
        holder.price_amt.setText(mList.getPrice_amt());
        if (holder.item_total != null) {
          /*  DecimalFormat format_tot = new DecimalFormat("##.##");
            String formatted_sum = format_tot.format(mList.getTotal());*/

            holder.item_total.setText("\u20B9" + mList.getTotal());
            // holder.item_total.setText(formatted_sum);
            holder.item_total.setPaintFlags(holder.item_total.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.unit.setText(mList.getUnit());
       /* DecimalFormat format_per = new DecimalFormat("##.##");
        String formatted = format_per.format(mList.getSaveings_percentage());*/
        holder.percentage.setText("" + mList.getSaveings_percentage() + " %");
        // holder.percentage.setText(formatted);
       /* DecimalFormat sav_amt = new DecimalFormat("##.##");
        String s_amt = sav_amt.format("\u20B9"+mList.getSave_amount());*/
        holder.percentage_amt.setText("\u20B9" + mList.getSave_amount());
        //holder.percentage_amt.setText(s_amt);
        holder.cart_product_quantity_tv.setText("" + mList.getQty());


        qtyList = new ArrayList<>();
        qtyList.add("1");
        qtyList.add("2");
        qtyList.add("3");
        qtyList.add("4");
        qtyList.add("5");
        qtyList.add("6");
        qtyList.add("7");
        qtyList.add("8");
        qtyList.add("9");
        qtyList.add("10");
        qtyList.add("11");
        qtyList.add("12");
        qtyList.add("13");
        qtyList.add("14");
        qtyList.add("15");
        qtyList.add("16");
        qtyList.add("17");
        qtyList.add("18");
        qtyList.add("19");
        qtyList.add("20");
        qtyList.add("21");
        qtyList.add("22");
        qtyList.add("23");
        qtyList.add("24");
        qtyList.add("25");
        qtyList.add("26");
        qtyList.add("27");
        qtyList.add("28");
        qtyList.add("29");
        qtyList.add("30");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, qtyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spn_qty.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();


            String comareValue = String.valueOf(mList.getQty());
            if (comareValue != null) {
                int spinnerPosition = dataAdapter.getPosition(comareValue);
                holder.spn_qty.setSelection(spinnerPosition);
                holder.spn_qty.setTag("true");
            }


        holder.spn_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.setCancelable(false);

                if(!holder.spn_qty.getTag().equals("true")) {

                    progressDialog.show(); // Display Progress Dialog
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject person = new JSONObject(response);
                                        String status = person.getString("status");
                                        if (status.equals("success")) {
                                   /*   Intent intent= new Intent(context,CartActivity.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                      context.startActivity(intent);
                                      ((Activity) context).overridePendingTransition(0,0);*/
                                            Intent intent = new Intent("message_subject_intent");

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0, 0);

                                        } else {
                                            //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
                                        }
                                        progressDialog.dismiss();

                                    } catch (JSONException e) {
                                        progressDialog.dismiss();
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            String qty = qtyList.get(position);
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("product_id", String.valueOf(mList.getCart_item_id()));
                            params.put("quantity", qty);
                            params.put("cart_id", String.valueOf(mList.getCart_id()));
                            params.put("user_id", u_id);
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
                    progressDialog.dismiss();

                holder.spn_qty.setTag("");


                  /* spin_no=holder.spn_qty.getSelectedItem().toString();
                   Intent intent= new Intent(context,CartActivity.class);
                   intent.putExtra("product_id", String.valueOf(mList.getCart_item_id()));
                   intent.putExtra("quantity", spin_no);
                   intent.putExtra("user_id", u_id);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   context.startActivity(intent);
                   ((Activity) context).overridePendingTransition(0,0);
                   SharedPreferences.Editor qty=prefs_Quantity.edit();
                   qty.putString("product_id",String.valueOf(mList.getCart_item_id()));
                   qty.putString("quantity", spin_no);
                   qty.putString("user_id", u_id);
                   qty.apply();
                   Intent intent= new Intent("message_subject_intent");
                   intent.putExtra("product_id", String.valueOf(mList.getCart_item_id()));
                   intent.putExtra("quantity", spin_no);
                   intent.putExtra("user_id", u_id);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                   ((Activity) context).overridePendingTransition(0,0);
                   ((Activity) context).finish();*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     /*   RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
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
                            if(status.equals("success")){
                                           Intent intent= new Intent(context,CartActivity.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                           context.startActivity(intent);
                                           ((Activity) context).overridePendingTransition(0,0);
                                ((Activity) context).finish();

                                // Toast.makeText(context,status+" "+"Removed",Toast.LENGTH_LONG).show();
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
                params.put("product_id", String.valueOf(mList.getCart_item_id()));
                params.put("quantity", spin_no);
                params.put("user_id", u_id);
                return params;
            }
        };
        queue.add(postRequest);*/


        holder.cart_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("loading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                int count = Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));
                if (count <= 29) {
                    count++;
                    final int cou = count++;
             /* holder.cart_product_quantity_tv.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                  }

                  @Override
                  public void onTextChanged(CharSequence s, int start, int before, int count) {

                  }

                  @Override
                  public void afterTextChanged(Editable s) {

                  }
              });*/
                    holder.cart_product_quantity_tv.setText("" + cou);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject person = new JSONObject(response);
                                        String status = person.getString("status");
                                        if (status.equals("success")) {
                                   /*   Intent intent= new Intent(context,CartActivity.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                      context.startActivity(intent);
                                      ((Activity) context).overridePendingTransition(0,0);*/
                                            Intent intent = new Intent("message_subject_intent");

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            progressDialog.dismiss();

                                            // Toast.makeText(context,status+" "+"Removed",Toast.LENGTH_LONG).show();
                                        } else {
                                            //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
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
                            params.put("product_id", String.valueOf(mList.getCart_item_id()));
                            params.put("quantity", String.valueOf(cou));
                            params.put("cart_id", String.valueOf(mList.getCart_id()));
                            params.put("user_id", u_id);
                            return params;
                        }
                    };
                    queue.add(postRequest);
                }

            }
        });
        holder.cart_minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("loading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
                int count = Integer.parseInt(String.valueOf(holder.cart_product_quantity_tv.getText()));

                if (count == 1) {

                    holder.cart_product_quantity_tv.setText("1");

                    final int cou2 = count;
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject person = new JSONObject(response);
                                        String status = person.getString("status");
                                        if (status.equals("success")) {
                                          /*Intent intent= new Intent(context, CartActivity.class);
                                          intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                          context.startActivity(intent);
                                          ((Activity) context).overridePendingTransition(0,0);*/
                                            Intent intent = new Intent("message_subject_intent");

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            progressDialog.dismiss();
                                            //  Toast.makeText(context,status,Toast.LENGTH_LONG).show();
                                        } else {
                                            //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
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
                            params.put("product_id", String.valueOf(mList.getCart_item_id()));
                            params.put("quantity", String.valueOf(cou2));
                            params.put("cart_id", String.valueOf(mList.getCart_id()));
                            params.put("user_id", u_id);

                            return params;
                        }
                    };
                    queue.add(postRequest);
                } else {
                    count -= 1;
                    holder.cart_product_quantity_tv.setText("" + count);
                    final int cou3 = count;
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject person = new JSONObject(response);
                                        String status = person.getString("status");
                                        if (status.equals("success")) {
                                         /* Intent intent= new Intent(context, CartActivity.class);
                                          intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                          context.startActivity(intent);
                                          ((Activity) context).overridePendingTransition(0,0);*/
                                            Intent intent = new Intent("message_subject_intent");

                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            progressDialog.dismiss();
                                            // Toast.makeText(context,status+" "+"Removed",Toast.LENGTH_LONG).show();
                                        } else {
                                            // Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
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
                            params.put("product_id", String.valueOf(mList.getCart_item_id()));
                            params.put("quantity", String.valueOf(cou3));
                            params.put("cart_id", String.valueOf(mList.getCart_id()));
                            params.put("user_id", u_id);

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
        holder.delete_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String prs_req = mList.getPres_required();

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, product_delete_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                try {
                                    //Do it with this it will work
                                    JSONObject person = new JSONObject(response);
                                    String status = person.getString("status");
                                    if (status.equals("success")) {
                                        modelList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, modelList.size());
                                   /*   SharedPreferences preferences = context.getSharedPreferences("PRESCRIPTION REQUIRED", Context.MODE_PRIVATE);
                                      int prescrip_req=preferences.getInt("Prescrip_requir",0);*/
                                        if (prs_req.equals("1")) {
                                         /* SharedPreferences.Editor e=preferences.edit();
                                          e.clear();
                                          e.commit();*/
                                            //prescriptionReqDatabase.deleteDataPres();
                                            prescriptionReqDatabase.removeSingleContact(position);
                                            Toast.makeText(context, status + " " + "Removed", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(context, CartActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            context.startActivity(intent);
                                        } else {

                                            Toast.makeText(context, status + " " + "Removed", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(context, CartActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            context.startActivity(intent);
                                        }
                                   /*   SharedPreferences.Editor e=preferences.edit();
                                      e.clear();
                                      e.commit();*/
                                        Toast.makeText(context, status + " " + "Removed", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, CartActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        ((Activity) context).overridePendingTransition(0, 0);
                                        context.startActivity(intent);

                                    } else {
                                        Toast.makeText(context, status + " " + "Not Removed", Toast.LENGTH_LONG).show();
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
                        params.put("cart_id", String.valueOf(mList.getCart_id()));


                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_cart, item_total, unit, percentage, percentage_amt, delete_item_button, cart_product_quantity_tv, price_amt, med_company;
        ImageView cart_minus_img, cart_plus_img;
        Spinner spn_qty;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart = (TextView) itemView.findViewById(R.id.item_cart);
            item_total = (TextView) itemView.findViewById(R.id.item_total);
            unit = (TextView) itemView.findViewById(R.id.unit);
            percentage = (TextView) itemView.findViewById(R.id.percentage);
            percentage_amt = (TextView) itemView.findViewById(R.id.percentage_amt);
            price_amt = (TextView) itemView.findViewById(R.id.price_amt);
            delete_item_button = (TextView) itemView.findViewById(R.id.delete_item_button);
            // items_select=(Spinner)itemView.findViewById(R.id.items_select_spn);
            cart_product_quantity_tv = (TextView) itemView.findViewById(R.id.cart_product_quantity_tv);
            med_company = (TextView) itemView.findViewById(R.id.med_company);
            cart_plus_img = (ImageView) itemView.findViewById(R.id.cart_plus_img);
            cart_minus_img = (ImageView) itemView.findViewById(R.id.cart_minus_img_);
            spn_qty = itemView.findViewById(R.id.spn_qty);
        }
    }
}
