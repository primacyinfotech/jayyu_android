package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.AccountPageListModel;
import Model.CartModel;

public class AccountPageListAdapter extends RecyclerView.Adapter<AccountPageListAdapter.MyViewHolder> {
    private ArrayList<AccountPageListModel> modelList;
    private Context context;

    public AccountPageListAdapter(ArrayList<AccountPageListModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountPageListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_page_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountPageListAdapter.MyViewHolder holder, int position) {
        AccountPageListModel mList = modelList.get(position);
        holder.list_icon.setImageResource(mList.getIcon_view());
        holder.list_name.setText(mList.getAc_list());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView list_icon;
        TextView list_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_icon=(ImageView)itemView.findViewById(R.id.list_icon);
            list_name=(TextView)itemView.findViewById(R.id.list_name);
        }
    }
}
