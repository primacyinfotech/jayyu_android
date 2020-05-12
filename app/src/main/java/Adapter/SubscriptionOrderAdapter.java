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

import Model.HelpModel;
import Model.SubscriptionOrderModel;

public class SubscriptionOrderAdapter extends RecyclerView.Adapter<SubscriptionOrderAdapter.MyViewHolder> {
    ArrayList<SubscriptionOrderModel> subscriptionOrderModels;
    Context context;

    public SubscriptionOrderAdapter(ArrayList<SubscriptionOrderModel> subscriptionOrderModels, Context context) {
        this.subscriptionOrderModels = subscriptionOrderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_ord_suscription, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionOrderAdapter.MyViewHolder holder, int position) {
    holder.ord_id.setText(subscriptionOrderModels.get(position).getSubscription_order());
    holder.ord_date.setText(subscriptionOrderModels.get(position).getOrd_date());
    }

    @Override
    public int getItemCount() {
        return subscriptionOrderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ord_id,ord_date;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ord_date=(TextView)itemView.findViewById(R.id.ord_date);
            ord_id=(TextView)itemView.findViewById(R.id.ord_id);
        }
    }
}
