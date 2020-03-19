package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.OrderSummeryModel;

public class OrderSummeryAdapter extends RecyclerView.Adapter<OrderSummeryAdapter.MyViewHolder> {
    private ArrayList<OrderSummeryModel> modelList;
    private Context context;

    public OrderSummeryAdapter(ArrayList<OrderSummeryModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderSummeryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_summery_items, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummeryAdapter.MyViewHolder holder, int position) {
        OrderSummeryModel mList = modelList.get(position);
        holder.item_cart.setText(mList.getCart_item());
       // holder.item_total.setText(mList.getTotal());
        holder.unit.setText(mList.getUnit());
        holder.percentage.setText(mList.getSaveings_percentage());
        holder.percentage_amt.setText("\u20B9"+mList.getSave_amount());
        holder.price_amt.setText("\u20B9"+mList.getPrice_amt());
        holder.company_nam.setText(mList.getCompany_name());
        holder.quantity.setText("Qty :"+mList.getQuantity());
        if (holder.item_total != null) {
          /*  DecimalFormat format_tot = new DecimalFormat("##.##");
            String formatted_sum = format_tot.format(mList.getTotal());*/

            holder.item_total.setText(""+mList.getTotal());
            // holder.item_total.setText(formatted_sum);
            holder.item_total.setPaintFlags(holder.item_total.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_cart,item_total,unit,percentage,percentage_amt,price_amt,company_nam,quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart=(TextView)itemView.findViewById(R.id.item_cart);
            item_total=(TextView)itemView.findViewById(R.id.item_total);
            unit=(TextView)itemView.findViewById(R.id.unit);
            percentage=(TextView)itemView.findViewById(R.id.percentage);
            percentage_amt=(TextView)itemView.findViewById(R.id.percentage_amt);
            price_amt=(TextView)itemView.findViewById(R.id.price_amt);
            company_nam=(TextView)itemView.findViewById(R.id.company_nam);
            quantity=(TextView)itemView.findViewById(R.id.quantity);
        }
    }
}
