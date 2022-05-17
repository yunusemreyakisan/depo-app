package mf.bm443.depo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mf.bm443.depo.R;
import mf.bm443.depo.models.UrunlerimModel;


public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.UrunHolder> {
    Context context;
    ArrayList<UrunlerimModel> urunlerimList;
    FirebaseAuth mAuth;
    private FirebaseUser mUser;



    public UrunAdapter(Context context, ArrayList<UrunlerimModel> urunlerimList) {
        this.context = context;
        this.urunlerimList = urunlerimList;
    }

    @NonNull
    @Override
    public UrunAdapter.UrunHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.urun_item, parent, false);
        return new UrunAdapter.UrunHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunAdapter.UrunHolder holder, int position) {

        //Animation
        Animation anim = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);


        UrunlerimModel urunlerimmodel = urunlerimList.get(position);
        holder.urunAdi.setText(urunlerimmodel.getUrunAdi());
        holder.urunDeposu.setText(urunlerimmodel.getUrunDeposu());
        holder.urunKategori.setText(urunlerimmodel.getUrunKategori());
        holder.urunMiktar.setText(urunlerimmodel.getUrunMiktar());



        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

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
        return urunlerimList.size();
    }



    public static class UrunHolder extends RecyclerView.ViewHolder {

        TextView urunAdi, urunDeposu, urunKategori, urunMiktar;
        ImageButton btnEdit;

        public UrunHolder(@NonNull View itemView) {
            super(itemView);
            urunAdi = itemView.findViewById(R.id.UrunAdi);
            urunDeposu = itemView.findViewById(R.id.UrunDeposu);
            urunKategori = itemView.findViewById(R.id.UrunKategorisi);
            urunMiktar = itemView.findViewById(R.id.UrunMiktari);
            btnEdit = itemView.findViewById(R.id.btnUrunEdit);


        }
    }
}
