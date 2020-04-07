package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.jaayu.ManagaAddressView;
import com.jaayu.ManageAddress;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.AddressModel;

public class ManageAdderssAdapter extends RecyclerView.Adapter<ManageAdderssAdapter.MyViewHolder> {
    private ArrayList<AddressModel> modelList;
    private Context context;
    private  String address_delete_url= BaseUrl.BaseUrlNew+"order_address_del";
    SharedPreferences prefs_register;

    public ManageAdderssAdapter(ArrayList<AddressModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ManageAdderssAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_manage, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageAdderssAdapter.MyViewHolder holder, final int position) {
        final AddressModel addressModel=modelList.get(position);
        holder.pref_add.setText(addressModel.getAddress_pref());
        holder.name.setText(addressModel.getName());
        holder.address.setText(addressModel.getAddress());
        holder.address_land.setText(addressModel.getLanmark());
        holder.address_zipt.setText(addressModel.getZip_code());
        holder.address_phone.setText(addressModel.getPhone());
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
                                        Intent intent= new Intent(context, ManagaAddressView.class);
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
        holder.edit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent goToUpdate=new Intent(context, ManageAddress.class);
                goToUpdate.putExtra("ADDRESS_ID",addressModel.getAdd_id());
                ((Activity) context).overridePendingTransition(0,0);
                context.startActivity(goToUpdate);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pref_add,name,address,edit_add,address_land,address_zipt,address_phone;
        LinearLayout delete_address;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pref_add=(TextView)itemView.findViewById(R.id.pref_add);
            name=(TextView)itemView.findViewById(R.id.name);
            address=(TextView)itemView.findViewById(R.id.address);
            edit_add=(TextView)itemView.findViewById(R.id.edit_add);
            address_land=(TextView)itemView.findViewById(R.id.address_land);
            address_zipt=(TextView)itemView.findViewById(R.id.address_zipt);
            address_phone=(TextView)itemView.findViewById(R.id.address_phone);
            delete_address=(LinearLayout)itemView.findViewById(R.id.delete_address);
        }
    }
}
