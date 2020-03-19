package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        PatientWisePrescriptionModel patientWisePrescriptionModel=patientWisePrescriptionModels.get(position);
        Picasso.with(context).load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + patientWisePrescriptionModel.getPatient_img()).into(holder.patient_pres_img);


    }

    @Override
    public int getItemCount() {
        return patientWisePrescriptionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView patient_pres_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_pres_img=(ImageView)itemView.findViewById(R.id.patient_pres_img);
        }
    }
}
