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

import Model.SubscriptionOrderDetailsModel;
import Model.SubscriptionOrderModel;

public class SubscriptionOrderDetailsAdapter extends RecyclerView.Adapter<SubscriptionOrderDetailsAdapter.MyViewHolder> {
    private ArrayList<SubscriptionOrderDetailsModel> subscriptionOrderDetailsModels;
    Context context;

    public SubscriptionOrderDetailsAdapter(ArrayList<SubscriptionOrderDetailsModel> subscriptionOrderDetailsModels, Context context) {
        this.subscriptionOrderDetailsModels = subscriptionOrderDetailsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionOrderDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscription_order_item_detail_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionOrderDetailsAdapter.MyViewHolder holder, int position) {
     holder.item_name.setText(subscriptionOrderDetailsModels.get(position).getItem_name());
     holder.unit.setText(subscriptionOrderDetailsModels.get(position).getItem_unit());
     holder.qyt.setText(subscriptionOrderDetailsModels.get(position).getItem_qty());
    }

    @Override
    public int getItemCount() {
        return subscriptionOrderDetailsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,unit,qyt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=(TextView)itemView.findViewById(R.id.item_name);
            unit=(TextView)itemView.findViewById(R.id.unit);
            qyt=(TextView)itemView.findViewById(R.id.qyt);
        }
    }
}
