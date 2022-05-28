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
import mf.bm443.depo.models.UrunlerimModel;

public class SonIslemlerAdapter extends RecyclerView.Adapter<SonIslemlerAdapter.SonIslemlerHolder> {

    Context context;
    ArrayList<UrunlerimModel> urunlerimSonIslemlerList;

    public SonIslemlerAdapter(Context context, ArrayList<UrunlerimModel> urunlerimSonIslemlerList) {
        this.context = context;
        this.urunlerimSonIslemlerList = urunlerimSonIslemlerList;
    }



    @NonNull
    @Override
    public SonIslemlerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.islemler_item, parent, false);
        return new SonIslemlerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SonIslemlerHolder holder, int position) {
        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        UrunlerimModel uModel = urunlerimSonIslemlerList.get(position);
        holder.eklenenItemAdi.setText(uModel.getUrunAdi());
        holder.eklenmeTarihi.setText(uModel.getEklenmeTarihi());


        //Animation
        holder.itemView.startAnimation(anim);


    }

    @Override
    public int getItemCount() {
        return urunlerimSonIslemlerList.size();
    }

    public static class SonIslemlerHolder extends RecyclerView.ViewHolder {

        TextView eklenenItemAdi, eklenmeTarihi;

        public SonIslemlerHolder(@NonNull View itemView) {
            super(itemView);

            eklenenItemAdi = itemView.findViewById(R.id.eklenenItemAdi);
            eklenmeTarihi = itemView.findViewById(R.id.eklenmeTarihi);


        }
    }
}
