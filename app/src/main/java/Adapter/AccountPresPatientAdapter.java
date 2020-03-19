package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinestall.PatientwisePrescriptionView;
import com.medicinestall.R;

import java.util.ArrayList;

import Model.AccountPresPatientModel;

public class AccountPresPatientAdapter extends RecyclerView.Adapter<AccountPresPatientAdapter.MyViewHolder> {
    ArrayList<AccountPresPatientModel> accountPresPatientModels;
    Context context;

    public AccountPresPatientAdapter(ArrayList<AccountPresPatientModel> accountPresPatientModels, Context context) {
        this.accountPresPatientModels = accountPresPatientModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountPresPatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_pres_patient_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountPresPatientAdapter.MyViewHolder holder, int position) {
        final AccountPresPatientModel mList = accountPresPatientModels.get(position);
        holder.patient_name.setText(mList.getPatient_name());
        holder.pres_count.setText("("+""+mList.getPrescription_count()+")");
        holder.patient_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendTo=new Intent(context, PatientwisePrescriptionView.class);
                sendTo.putExtra("Patient_id",mList.getPatient_id());
                ((Activity) context).overridePendingTransition(0,0);
                context.startActivity(sendTo);
                ((Activity) context).finish();

            }
        });


    }

    @Override
    public int getItemCount() {
        return accountPresPatientModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView patient_name,pres_count;
        LinearLayout patient_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_name=(TextView)itemView.findViewById(R.id.patient_name);
            pres_count=(TextView)itemView.findViewById(R.id.pres_count);
            patient_card=(LinearLayout)itemView.findViewById(R.id.patient_card);
        }
    }
}
