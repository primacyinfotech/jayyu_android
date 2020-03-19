package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.jaayu.AccountPrescription;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.AssignPatientModel;

public class AssignPatientAdapter extends RecyclerView.Adapter<AssignPatientAdapter.MyViewHolder> {
    ArrayList<AssignPatientModel> assignPatientModels;
    Context context;
    private int selectedPosition = -1;
    SharedPreferences assign_press_id;
    String ass_p_id;
    private String assign_patient_with_presscription_url= BaseUrl.BaseUrlNew+"patient_add_pres";

    public AssignPatientAdapter(ArrayList<AssignPatientModel> assignPatientModels, Context context) {
        this.assignPatientModels = assignPatientModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignPatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_assign_patient_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignPatientAdapter.MyViewHolder holder, int position) {
        holder.name_assign.setText(assignPatientModels.get(position).getAssign_patient_name());
        holder.selected_patient.setChecked(position==selectedPosition);
        holder.selected_patient.setTag(position);
       holder.selected_patient.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               itemCheckChanged(v);
           }
       });

    }
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }
    public String getSelectedItem() {
        if (selectedPosition != -1) {
            assign_press_id = context.getSharedPreferences(
                    "ASSIGN_PRESCRIPTION", Context.MODE_PRIVATE);

            int ass_p=assign_press_id.getInt("Press_id",0);
            final String asign_pres_id= String.valueOf(ass_p);
            final String assign_u_id=assignPatientModels.get(selectedPosition).getUser_id();
            final String asign_p_id= String.valueOf(assignPatientModels.get(selectedPosition).getAssign_patient_id());
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest postRequest = new StringRequest(Request.Method.POST,assign_patient_with_presscription_url,
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

                                    Intent GoTo_PatientList=new Intent(context, AccountPrescription.class);
                                    GoTo_PatientList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    ((Activity) context).overridePendingTransition(0,0);
                                    context.startActivity(GoTo_PatientList);
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

                    params.put("user_id" ,assign_u_id);
                    params.put("pres_id" ,asign_pres_id);
                    params.put("patient_id" ,asign_p_id);
                    /* params.put("user_id" ,"35");*/

                    return params;
                }

            };
            requestQueue.add(postRequest);

            Toast.makeText(context, assign_u_id+asign_pres_id+asign_p_id, Toast.LENGTH_SHORT).show();

            return String.valueOf(assignPatientModels.get(selectedPosition));
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return (null != assignPatientModels ? assignPatientModels.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_assign;
        RadioButton selected_patient;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_assign=(TextView)itemView.findViewById(R.id.name_assign);
            selected_patient=(RadioButton)itemView.findViewById(R.id.selected_patient);
        }
    }
}
