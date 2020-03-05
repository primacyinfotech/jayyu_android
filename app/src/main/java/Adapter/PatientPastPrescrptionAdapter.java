package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.PatientPastPrescriptionModel;

public class PatientPastPrescrptionAdapter extends RecyclerView.Adapter<PatientPastPrescrptionAdapter.MyViewHolder> {
      ArrayList<PatientPastPrescriptionModel> patientPastPrescriptionModels;
      Context context;

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
      public void onBindViewHolder(@NonNull PatientPastPrescrptionAdapter.MyViewHolder holder, int position) {
        PatientPastPrescriptionModel patientPastPrescriptionModel=patientPastPrescriptionModels.get(position);


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
