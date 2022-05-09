package mf.bm443.depo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mf.bm443.depo.models.DepolarimModel;
import mf.bm443.depo.R;


public class DepoAdapter extends RecyclerView.Adapter<DepoAdapter.DepoHolder> {

    Context context;
    ArrayList<DepolarimModel> depolarimList;


    public DepoAdapter(Context context, ArrayList<DepolarimModel> depolarimList) {
        this.context = context;
        this.depolarimList = depolarimList;
    }


    @NonNull
    @Override
    public DepoAdapter.DepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.depo_item, parent, false);
        return new DepoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DepoAdapter.DepoHolder holder, int position) {

        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        DepolarimModel depolarimmodel = depolarimList.get(position);
        holder.depoAdresi.setText(depolarimmodel.getDepoAdresi());
        //Animation
        holder.itemView.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return depolarimList.size();
    }





    static class DepoHolder extends RecyclerView.ViewHolder {

        TextView depoAdresi;

        public DepoHolder(@NonNull View itemView) {
            super(itemView);
            depoAdresi = itemView.findViewById(R.id.txtDepoAdresi);


        }

    }
}


