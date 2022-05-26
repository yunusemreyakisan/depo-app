package mf.bm443.depo.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.layouts.kategoriIslemleri.Kategoriler;
import mf.bm443.depo.models.DepolarimModel;


public class DepoAdapter extends RecyclerView.Adapter<DepoAdapter.DepoViewHolder> {

    Context context;
    ArrayList<DepolarimModel> depolarimList;

    public DepoAdapter(Context context, ArrayList<DepolarimModel> depolarimList) {
        this.context = context;
        this.depolarimList = depolarimList;
    }

    @NonNull
    @Override
    public DepoAdapter.DepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.depo_item, parent, false);
        return new DepoAdapter.DepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepoAdapter.DepoViewHolder holder, int position) {


        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        DepolarimModel model = depolarimList.get(position);
        holder.depoAdi.setText(model.getDepoAdi());
        holder.depoAdresi.setText(model.getDepoAdresi());
        holder.depoBuyuklugu.setText(model.getDepoBuyuklugu());

        //Animation
        holder.itemView.startAnimation(anim);

        //CardView Dinleyicisi
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Kategoriler.class);
                context.startActivity(intent);




                //Toast.makeText(context, "İçeriğe tıklandı.", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return depolarimList.size();
    }


    public static class DepoViewHolder extends RecyclerView.ViewHolder {

        TextView depoAdi, depoAdresi, depoBuyuklugu;


        public DepoViewHolder(@NonNull View itemView) {
            super(itemView);

            depoAdi = itemView.findViewById(R.id.txtDepoAdi);
            depoAdresi = itemView.findViewById(R.id.txtDepoAdresi);
            depoBuyuklugu = itemView.findViewById(R.id.txtDepoBuyuklugu);


        }
    }


}



















/*
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

 */


