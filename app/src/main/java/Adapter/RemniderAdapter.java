package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinestall.R;

import java.util.ArrayList;

import Model.ReminderModel;

public class RemniderAdapter extends RecyclerView.Adapter<RemniderAdapter.MyViewHolder> {
    private ArrayList<ReminderModel> modelList;
    private Context context;

    public RemniderAdapter(ArrayList<ReminderModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RemniderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RemniderAdapter.MyViewHolder holder, int position) {
        ReminderModel mList = modelList.get(position);
        holder.med_name.setText(mList.getMedicin_name());
        holder.med_time.setText(mList.getTrack_time());
        holder.med_qtyl.setText(mList.getNumbeerOf_tablet());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView med_name,med_time,med_qtyl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            med_name=(TextView)itemView.findViewById(R.id.med_name);
            med_time=(TextView)itemView.findViewById(R.id.med_time);
            med_qtyl=(TextView)itemView.findViewById(R.id.med_qty);
        }
    }
}
