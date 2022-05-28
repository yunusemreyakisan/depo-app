package mf.bm443.depo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import mf.bm443.depo.R;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.layouts.urunIslemleri.Urunlerim;
import mf.bm443.depo.models.UrunlerimModel;


public class UrunAdapter extends RecyclerView.Adapter<UrunAdapter.UrunHolder>{
    Context context;
    ArrayList<UrunlerimModel> urunlerimList;
    private FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference reference;



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
        //holder.urunKategori.setText(urunlerimmodel.getUrunKategori());
        holder.urunMiktar.setText(urunlerimmodel.getUrunMiktar());



        //Animation
        holder.itemView.startAnimation(anim);
        //CardView Dinleyicisi
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "İçeriğe tıklandı.", Toast.LENGTH_SHORT).show();
            }

        });


        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String urunAdi = holder.urunAdi.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                reference = FirebaseDatabase.getInstance().getReference("Kullanıcılar")
                        .child(user_id)
                        .child("Ürünlerim");
                       // .child("");

/*
                Query queryRef = reference.orderByChild(Objects.requireNonNull(reference.getKey()));
                queryRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChild) {
                        snapshot.getRef().setValue(null);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


 */
               reference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(context.getApplicationContext(), "Ürünler Silindi",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), HomePage.class);
                        context.startActivity(intent);
                    }
                });

            }
        });







        holder.btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








    }

    @Override
    public int getItemCount() {
        return urunlerimList.size();
    }

    public static class UrunHolder extends RecyclerView.ViewHolder {

        TextView urunAdi, urunDeposu, urunMiktar;
        Button btnRemoveItem, btnUpdateItem;

        public UrunHolder(@NonNull View itemView) {
            super(itemView);
            urunAdi = itemView.findViewById(R.id.UrunAdi);
            urunDeposu = itemView.findViewById(R.id.UrunDeposu);
            urunMiktar = itemView.findViewById(R.id.UrunMiktari);
            btnRemoveItem = itemView.findViewById(R.id.btnRemoveItem);
            btnUpdateItem = itemView.findViewById(R.id.btnUpdateItem);


        }
    }
}
