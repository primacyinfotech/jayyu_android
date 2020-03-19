package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinestall.R;

import java.util.ArrayList;

import Model.Item_model;

public class Item_view  extends RecyclerView.Adapter<Item_view.MyViewHolder>{
    private ArrayList<Item_model> modelList;
    private Context context;
    ArrayList<String> Categories2;;


    public Item_view(ArrayList<Item_model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;

    }

    @NonNull
    @Override
    public Item_view.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Item_view.MyViewHolder holder, int position) {
        Item_model mList = modelList.get(position);
        holder.mrp.setText(mList.getMrp());
        holder.save_percernt.setText(mList.getSave_percent());
        holder.save_amt.setText(mList.getSave_amt());
        holder.qty_per.setText(mList.getUnit());
        holder.refund_nonrefund.setText(mList.getRefund_nonrefund());
        Categories2=new ArrayList<>();
        Categories2.add("1");
        Categories2.add("2");
        Categories2.add("3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Categories2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.country_select.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();




    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mrp,save_percernt,save_amt,qty_per,refund_nonrefund;
        Spinner country_select;
        Button add_cart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mrp=(TextView)itemView.findViewById(R.id.mrp);
            save_percernt=(TextView)itemView.findViewById(R.id.save_percernt);
            save_amt=(TextView)itemView.findViewById(R.id.save_amt);
            qty_per=(TextView)itemView.findViewById(R.id.qty_per);
            refund_nonrefund=(TextView)itemView.findViewById(R.id.refund_nonrefund);
            country_select=(Spinner)itemView.findViewById(R.id.country_select);
            add_cart=(Button)itemView.findViewById(R.id.add_cart);

        }
    }
}
