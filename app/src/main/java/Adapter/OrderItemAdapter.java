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

import Model.OderItemModel;
import Model.OrderModel;


public class OrderItemAdapter  extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>  {
    private ArrayList<OderItemModel> orderdetails_list;
    private Context context;

    public OrderItemAdapter(ArrayList<OderItemModel> orderdetails_list, Context context) {
        this.orderdetails_list = orderdetails_list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_detail_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.MyViewHolder holder, int position) {
        OderItemModel mList = orderdetails_list.get(position);
        holder.item_name.setText(mList.getItem_Name());
        holder.amount.setText("Price: "+"\u20B9"+mList.getItem_amt());
        holder.unit.setText(mList.getItem_unit());

    }

    @Override
    public int getItemCount() {
        return orderdetails_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,amount,unit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=(TextView)itemView.findViewById(R.id.item_name);
            amount=(TextView)itemView.findViewById(R.id.amount);
            unit=(TextView)itemView.findViewById(R.id.unit);
        }
    }
}
