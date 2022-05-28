package mf.bm443.depo.adapter;

import android.content.Context;
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
import mf.bm443.depo.models.DepolarimModel;

public class SonIslemlerDepoAdapter extends RecyclerView.Adapter<SonIslemlerDepoAdapter.SonIslemlerDepoHolder>{

    Context context;
    ArrayList<DepolarimModel> sonIslemlerDepolarimList;

    public SonIslemlerDepoAdapter(Context context, ArrayList<DepolarimModel> sonIslemlerDepolarimList) {
        this.context = context;
        this.sonIslemlerDepolarimList = sonIslemlerDepolarimList;
    }

    @NonNull
    @Override
    public SonIslemlerDepoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.islemler_item, parent, false);
        return new SonIslemlerDepoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SonIslemlerDepoHolder holder, int position) {

        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        DepolarimModel dModel = sonIslemlerDepolarimList.get(position);
        holder.depoeklenmeTarihi.setText(dModel.getEklenmeTarihi());
        holder.eklenenDepoItemAdi.setText(dModel.getDepoAdi());


        //Animation
        holder.itemView.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return sonIslemlerDepolarimList.size();
    }

    public static class SonIslemlerDepoHolder extends RecyclerView.ViewHolder{

        TextView  depoeklenmeTarihi, eklenenDepoItemAdi;

        public SonIslemlerDepoHolder(@NonNull View itemView) {
            super(itemView);

            eklenenDepoItemAdi = itemView.findViewById(R.id.eklenenItemAdi);
            depoeklenmeTarihi = itemView.findViewById(R.id.eklenmeTarihi);
        }
    }

}
