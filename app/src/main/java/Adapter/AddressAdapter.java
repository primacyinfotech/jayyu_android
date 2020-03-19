package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.CartActivity;
import com.jaayu.LocationAddress;
import com.jaayu.Model.BaseUrl;
import com.jaayu.OrderSummery;
import com.jaayu.R;
import com.jaayu.UpdateAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.AccountPageListModel;
import Model.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private ArrayList<AddressModel> modelList;
    private Context context;
    private int selectedStarPosition = -1;
    private AdapterView.OnItemClickListener onItemClickListener;
    private  String address_delete_url= BaseUrl.BaseUrlNew+"order_address_del";
    private  String Instant_Uncheck_api=BaseUrl.BaseUrlNew+"instant_unchecke";
    SharedPreferences prefs_register;

   /* private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private boolean ischecked = false;*/

    public AddressAdapter(ArrayList<AddressModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_items, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.MyViewHolder holder, final int position) {
        final AddressModel addressModel=modelList.get(position);

        holder.pref_add.setText(addressModel.getAddress_pref());
        holder.name.setText(addressModel.getName());
        holder.address.setText(addressModel.getAddress());
        holder.delete_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, address_delete_url,
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
                                        modelList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,modelList.size());
                                        String message=person.getString("message");
                                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(context, LocationAddress.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        context.startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
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
                        params.put("aId", String.valueOf(addressModel.getAdd_id()));



                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        holder.chk_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedStarPosition= position;
                notifyDataSetChanged();
                int add_id=addressModel.getAdd_id();
                String nameValue=addressModel.getName();
                String addresss_pref=addressModel.getAddress_pref();
                String addressValue=addressModel.getAddress();
                String zip=addressModel.getZip_code();
                Toast.makeText(context,
                        "selected offer is " + add_id,
                        Toast.LENGTH_LONG).show();
                prefs_register = context.getSharedPreferences(
                        "Register Details", Context.MODE_PRIVATE);
                final String u_id=prefs_register.getString("USER_ID","");
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, Instant_Uncheck_api,
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
                        params.put("aId", String.valueOf(addressModel.getAdd_id()));
                        params.put("user_id", u_id);
                        return params;
                    }
                };
                queue.add(postRequest);
                Intent goToOrderSammary=new Intent(context, OrderSummery.class);
                goToOrderSammary.putExtra("ADDRESS_ID",add_id);
                goToOrderSammary.putExtra("NAME",nameValue);
                goToOrderSammary.putExtra("ADDRESS_PREF",addresss_pref);
                goToOrderSammary.putExtra("ADDRESS",addressValue);
                goToOrderSammary.putExtra("ADDRESS_zip",zip);
                context.startActivity(goToOrderSammary);

            }
        });
        holder.layout_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent goToUpdate=new Intent(context, UpdateAddress.class);
                goToUpdate.putExtra("ADDRESS_ID",addressModel.getAdd_id());
                context.startActivity(goToUpdate);*/
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pref_add,name,address;
        RadioButton chk_add;
        LinearLayout delete_address;
        LinearLayout layout_address;
        public MyViewHolder(View itemView) {
            super(itemView);

            pref_add=(TextView)itemView.findViewById(R.id.pref_add);
            name=(TextView)itemView.findViewById(R.id.name);
            address=(TextView)itemView.findViewById(R.id.address);
            chk_add=(RadioButton)itemView.findViewById(R.id.chk_add);
            delete_address=(LinearLayout)itemView.findViewById(R.id.delete_address);
            layout_address=(LinearLayout)itemView.findViewById(R.id.layout_address);


        }



    }


}
