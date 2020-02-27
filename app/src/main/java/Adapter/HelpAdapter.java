package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.HelpModel;
import Model.Item_model;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {
    ArrayList<HelpModel> helpModels;
    Context context;
    

    public HelpAdapter(ArrayList<HelpModel> helpModels, Context context) {
        this.helpModels = helpModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HelpAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_help, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HelpAdapter.MyViewHolder holder, int position) {
        HelpModel mList = helpModels.get(position);


    }

    @Override
    public int getItemCount() {
        return helpModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView help_item,help_item_content;
        private CardView help_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            help_card=(CardView)itemView.findViewById(R.id.help_card);
            help_item=(TextView)itemView.findViewById(R.id.help_item);

        }
    }
}
