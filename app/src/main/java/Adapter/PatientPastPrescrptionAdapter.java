package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.jaayu.MedicalPrescriptionReport;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.PatientPastPrescriptionModel;

public class PatientPastPrescrptionAdapter extends RecyclerView.Adapter<PatientPastPrescrptionAdapter.MyViewHolder> {
      ArrayList<PatientPastPrescriptionModel> patientPastPrescriptionModels;
      Context context;
    private String prescription_delete_url= BaseUrl.BaseUrlNew+"patient_prescription_delete";

    public PatientPastPrescrptionAdapter(ArrayList<PatientPastPrescriptionModel> patientPastPrescriptionModels, Context context) {
        this.patientPastPrescriptionModels = patientPastPrescriptionModels;
        this.context = context;
    }

    @NonNull
      @Override
      public PatientPastPrescrptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_pastprescription, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
      }

      @Override
      public void onBindViewHolder(@NonNull PatientPastPrescrptionAdapter.MyViewHolder holder, final int position) {
        final PatientPastPrescriptionModel patientPastPrescriptionModel=patientPastPrescriptionModels.get(position);
          Picasso.with(context).load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + patientPastPrescriptionModel.getPast_pres_img()).into(holder.pres_display);
          holder.pres_display.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final Dialog settingsDialog = new Dialog(context,R.style.AppBaseTheme);
                  settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                  settingsDialog.getWindow().setBackgroundDrawable(null);
                  settingsDialog.setContentView(R.layout.full_screen_image);
                  ImageView  imageView=(ImageView)settingsDialog.findViewById(R.id.full_screen);
                  ImageView  imageView2=(ImageView)settingsDialog.findViewById(R.id.close_full_img);
                  Picasso.with(context)
                          .load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + patientPastPrescriptionModel.getPast_pres_img())
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
          holder.doc_head.setText(patientPastPrescriptionModel.getPast_pres_head());
          holder.doc_date.setText(patientPastPrescriptionModel.getPast_pres_date());
          holder.pres_del.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  RequestQueue queue = Volley.newRequestQueue(context);
                  StringRequest postRequest = new StringRequest(Request.Method.POST, prescription_delete_url,
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
                                          Intent intent = new Intent(context, MedicalPrescriptionReport.class);
                                          // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                          context.startActivity(intent);
                                          ((Activity) context).overridePendingTransition(0, 0);
                                          ((Activity) context).finish();

                                          patientPastPrescriptionModels.remove(position);
                                          notifyItemRemoved(position);
                                          notifyItemRangeChanged(position,patientPastPrescriptionModels.size());
                                          Toast.makeText(context, status + " " + "Removed", Toast.LENGTH_LONG).show();


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
                          params.put("pid", String.valueOf(patientPastPrescriptionModel.getPatient_pres_id()));


                          return params;
                      }
                  };
                  queue.add(postRequest);
              }
          });


      }

      @Override
      public int getItemCount() {
          return patientPastPrescriptionModels.size();
      }

      public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doc_head,doc_date;
        ImageView pres_display;
        Button pres_del;
          public MyViewHolder(@NonNull View itemView) {
              super(itemView);
              doc_head=(TextView)itemView.findViewById(R.id.doc_head);
              doc_date=(TextView)itemView.findViewById(R.id.doc_date);
              pres_display=(ImageView)itemView.findViewById(R.id.pres_display);
              pres_del=(Button)itemView.findViewById(R.id.pres_del);



          }
      }
  }
