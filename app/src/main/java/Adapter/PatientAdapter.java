package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.LegalModel;
import Model.PatientListModel;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {
    ArrayList<PatientListModel> patientListModels;
    Context context;

    public PatientAdapter(ArrayList<PatientListModel> patientListModels, Context context) {
        this.patientListModels = patientListModels;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_patient, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.MyViewHolder holder, int position) {
        PatientListModel patientListModel=patientListModels.get(position);

    }

    @Override
    public int getItemCount() {
        return patientListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView patient_head,patient_relation;
        CardView patient_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_head=(TextView)itemView.findViewById(R.id.patient_head);
            patient_relation=(TextView)itemView.findViewById(R.id.patient_relation);
            patient_card=(CardView)itemView.findViewById(R.id.patient_card);
        }
    }
}
