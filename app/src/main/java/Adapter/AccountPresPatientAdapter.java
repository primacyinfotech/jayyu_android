package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.AccountPresPatientModel;
import Model.HelpModel;

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



    }

    @Override
    public int getItemCount() {
        return accountPresPatientModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView patient_name,pres_count;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patient_name=(TextView)itemView.findViewById(R.id.patient_name);
            pres_count=(TextView)itemView.findViewById(R.id.pres_count);
        }
    }
}
