package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.AssignPatientModel;

public class AssignPatientAdapter extends RecyclerView.Adapter<AssignPatientAdapter.MyViewHolder> {
    ArrayList<AssignPatientModel> assignPatientModels;
    Context context;
    private int selectedPosition = -1;

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
            Toast.makeText(context, "Selected Item : " + assignPatientModels.get(selectedPosition).getAssign_patient_id(), Toast.LENGTH_SHORT).show();
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
