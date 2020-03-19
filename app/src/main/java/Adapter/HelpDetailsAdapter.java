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

import com.medicinestall.R;

import java.util.ArrayList;

import Model.HelpDetailsModel;

public class HelpDetailsAdapter extends RecyclerView.Adapter<HelpDetailsAdapter.MyViewHolder> {
    ArrayList<HelpDetailsModel> helpDetailsModels;
    Context context;
    private Animation animationUp;
    private Animation animationDown;
    public HelpDetailsAdapter(ArrayList<HelpDetailsModel> helpDetailsModels, Context context) {
        this.helpDetailsModels = helpDetailsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HelpDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_help_details, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HelpDetailsAdapter.MyViewHolder holder, int position) {
        HelpDetailsModel helpDetailsModel=helpDetailsModels.get(position);
        animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        holder.help_head.setText(helpDetailsModel.getHelp_head());
        holder.help_content.setText(helpDetailsModel.getHelp_content());
        holder.help_content.setVisibility(View.GONE);
        holder.help_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.help_content.isShown()){
                    holder.help_content.setVisibility(View.GONE);
                    holder.help_content.startAnimation(animationUp);
                }
                else {
                    holder.help_content.setVisibility(View.VISIBLE);
                    holder.help_content.startAnimation(animationDown);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpDetailsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView help_head,help_content;
        CardView help_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            help_card=(CardView)itemView.findViewById(R.id.help_card);
            help_head=(TextView)itemView.findViewById(R.id.help_head);
            help_content=(TextView)itemView.findViewById(R.id.help_content);
        }
    }
}
