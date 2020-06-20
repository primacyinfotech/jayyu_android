package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.SubscriptionBenefit;

public class SubscriptionFtureAdapter extends RecyclerView.Adapter<SubscriptionFtureAdapter.MyViewHolder> {
    ArrayList<SubscriptionBenefit> fetureModels;
    Context context;

    public SubscriptionFtureAdapter(ArrayList<SubscriptionBenefit> fetureModels, Context context) {
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
        Picasso.with(context).load(BaseUrl.imageUrlSub + fetureModels.get(position).getPhoto()).into(holder.feture_subs_img);
        holder.feture_head.setText(fetureModels.get(position).getTitle());
        holder.feture_content.setText(fetureModels.get(position).getDescription());

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
