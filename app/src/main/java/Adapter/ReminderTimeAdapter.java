package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.ReminderTimeModel;

public class ReminderTimeAdapter extends RecyclerView.Adapter<ReminderTimeAdapter.MyViewHolder> {
    ArrayList<ReminderTimeModel> reminderTimeModels;
    Context context;
    private int selectedPosition = -1;
    @NonNull
    @Override
    public ReminderTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_time_schdule, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderTimeAdapter.MyViewHolder holder, int position) {
        holder.set_time.setText(reminderTimeModels.get(position).getTime_scheduled());
        holder.qty_items.setText(reminderTimeModels.get(position).getQty_medicine());
        holder.selected_time.setChecked(position==selectedPosition);
        holder.selected_time.setTag(position);
        holder.selected_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCheckChanged(view);
            }
        });

        holder.edit_qty_schdule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }
    public String getSelectedItem(){
        if(selectedPosition!=-1){
            return String.valueOf(reminderTimeModels.get(selectedPosition));
        }
       return "";
    }
    @Override
    public int getItemCount() {
         return (null != reminderTimeModels ? reminderTimeModels.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton selected_time;
        TextView set_time,qty_items;
        ImageView edit_qty_schdule;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selected_time=(RadioButton)itemView.findViewById(R.id.selected_time);
            set_time=(TextView)itemView.findViewById(R.id.set_time);
            qty_items=(TextView)itemView.findViewById(R.id.qty_items);
            edit_qty_schdule=(ImageView)itemView.findViewById(R.id.edit_qty_schdule);




        }
    }
}
