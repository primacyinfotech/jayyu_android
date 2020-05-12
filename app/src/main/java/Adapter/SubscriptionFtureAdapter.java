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

import Model.HelpModel;
import Model.SuscriptionFetureNodel;

public class SubscriptionFtureAdapter extends RecyclerView.Adapter<SubscriptionFtureAdapter.MyViewHolder> {
    ArrayList<SuscriptionFetureNodel> fetureModels;
    Context context;

    public SubscriptionFtureAdapter(ArrayList<SuscriptionFetureNodel> fetureModels, Context context) {
        this.fetureModels = fetureModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionFtureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_feture_suscription, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionFtureAdapter.MyViewHolder holder, int position) {
        holder.feture_subs_img.setImageResource(fetureModels.get(position).getFeture_img());
        holder.feture_head.setText(fetureModels.get(position).getFeture_titel());
        holder.feture_content.setText(fetureModels.get(position).getFeture_content());

    }

    @Override
    public int getItemCount() {
        return fetureModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView feture_subs_img;
        TextView feture_head,feture_content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feture_content=(TextView)itemView.findViewById(R.id.feture_content);
            feture_head=(TextView)itemView.findViewById(R.id.feture_head);
            feture_subs_img=(ImageView)itemView.findViewById(R.id.feture_subs_img);
        }
    }
}
