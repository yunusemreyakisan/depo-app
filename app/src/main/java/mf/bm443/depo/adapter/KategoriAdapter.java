package mf.bm443.depo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.models.UrunlerimModel;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriHolder> {

    Context context;
    ArrayList<UrunlerimModel> kategorilerimList;

    public KategoriAdapter(Context context, ArrayList<UrunlerimModel> kategorilerimList) {
        this.context = context;
        this.kategorilerimList = kategorilerimList;
    }


    @NonNull
    @Override
    public KategoriAdapter.KategoriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kategori_item, parent, false);
        return new KategoriAdapter.KategoriHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.KategoriHolder holder, int position) {
        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        UrunlerimModel model = kategorilerimList.get(position);
       // holder.kategoriAdi.setText(model.getKey());
        holder.urunAdeti.setText(model.getUrunMiktar());


        //Animation
        holder.itemView.startAnimation(anim);
        //CardView Dinleyicisi
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "İçeriğe tıklandı.", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return kategorilerimList.size();
    }


    //Holder
    public static class KategoriHolder extends RecyclerView.ViewHolder {
        TextView kategoriAdi, urunAdeti;

        public KategoriHolder(@NonNull View itemView) {
            super(itemView);

            kategoriAdi = itemView.findViewById(R.id.KategoriAdi);
            urunAdeti = itemView.findViewById(R.id.KategoriUrunAdeti);


        }
    }


}


