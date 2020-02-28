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

import Model.LegalModel;

public class LegalAdapter extends RecyclerView.Adapter<LegalAdapter.MyViewHolder> {
    ArrayList<LegalModel> legalModels;
    Context context;
    private Animation animationUp;
    private Animation animationDown;
    public LegalAdapter(ArrayList<LegalModel> legalModels, Context context) {
        this.legalModels = legalModels;
        this.context = context;
    }

    @NonNull
    @Override
    public LegalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_legal, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LegalAdapter.MyViewHolder holder, int position) {
        LegalModel legalModel=legalModels.get(position);
        animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        holder.legal_head.setText(legalModel.getLegal_head());
        holder.legal_content.setText(legalModel.getLegal_content());
        holder.legal_content.setVisibility(View.GONE);
        holder.legal_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.legal_content.isShown()){
                    holder.legal_content.setVisibility(View.GONE);
                    holder.legal_content.startAnimation(animationUp);
                }
                else {
                    holder.legal_content.setVisibility(View.VISIBLE);
                    holder.legal_content.startAnimation(animationDown);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return legalModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView legal_head,legal_content;
        CardView legal_card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            legal_card=(CardView)itemView.findViewById(R.id.legal_card);
            legal_head=(TextView)itemView.findViewById(R.id.legal_head);
            legal_content=(TextView)itemView.findViewById(R.id.legal_content);

        }
    }
}
