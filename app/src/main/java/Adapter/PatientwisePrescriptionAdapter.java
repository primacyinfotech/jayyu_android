package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoView;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.PatientWisePrescriptionModel;

public class PatientwisePrescriptionAdapter extends RecyclerView.Adapter<PatientwisePrescriptionAdapter.MyViewHolder> {
    ArrayList<PatientWisePrescriptionModel> patientWisePrescriptionModels;
    Context context;

    public PatientwisePrescriptionAdapter(ArrayList<PatientWisePrescriptionModel> patientWisePrescriptionModels, Context context) {
        this.patientWisePrescriptionModels = patientWisePrescriptionModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientwisePrescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_prescription_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientwisePrescriptionAdapter.MyViewHolder holder, int position) {
        final PatientWisePrescriptionModel patientWisePrescriptionModel=patientWisePrescriptionModels.get(position);
        Picasso.with(context).load(BaseUrl.imageUrl + patientWisePrescriptionModel.getPatient_img()).into(holder.patient_pres_img);
        holder.patient_pres_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(context,R.style.AppBaseTheme);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(null);
                settingsDialog.setContentView(R.layout.full_screen_image);
                PhotoView imageView=(PhotoView)settingsDialog.findViewById(R.id.full_screen);
                ImageView  imageView2=(ImageView)settingsDialog.findViewById(R.id.close_full_img);
                Picasso.with(context)

                        .load(BaseUrl.imageUrl +patientWisePrescriptionModel.getPatient_img())
                        .into(imageView);
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.dismiss();
                    }
                });
                settingsDialog.show();
            }
        });

        holder.btnUnassign.setOnClickListener(v -> {
            unAssignPrescription(patientWisePrescriptionModel, position);
        });

    }

    private void unAssignPrescription(PatientWisePrescriptionModel prescriptionModel, int position) {
        String k = "{\"status\":\"0\",\"message\":\"No prescription found\"}";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.unAssignPrescription,response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getString("status").equals("1")){
                    patientWisePrescriptionModels.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, patientWisePrescriptionModels.size());
                }

                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id",prescriptionModel.getUser_id());
                hashMap.put("opid", prescriptionModel.getId());
               // hashMap.put("normal", prescriptionModel.getNormal());
                return hashMap;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return patientWisePrescriptionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView patient_pres_img;
        Button btnUnassign;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_pres_img=(ImageView)itemView.findViewById(R.id.patient_pres_img);
            btnUnassign = itemView.findViewById(R.id.btn_unassign);
        }
    }
}
