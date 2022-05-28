package mf.bm443.depo.layouts.sonIslemler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.SonIslemlerAdapter;
import mf.bm443.depo.adapter.SonIslemlerDepoAdapter;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.models.DepolarimModel;
import mf.bm443.depo.models.UrunlerimModel;

public class SonIslemlerUrunler extends AppCompatActivity {


    ArrayList<UrunlerimModel> urunlerimSonIslemlerList;
    ArrayList<DepolarimModel> sonIslemlerDepolarimList;
    SonIslemlerDepoAdapter sonIslemlerDepoAdapter;
    SonIslemlerAdapter sonIslemlerAdapter;
    private RecyclerView rvSonIslemlerUrunler;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_islemler_urunler);

        //Son İşlemler (Urunler) Recyclerview
        RecyclerView rvSonIslemlerUrunler = findViewById(R.id.rvSonIslemler);
        urunlerimSonIslemlerList = new ArrayList<UrunlerimModel>();
        sonIslemlerAdapter = new SonIslemlerAdapter(SonIslemlerUrunler.this, urunlerimSonIslemlerList);

        rvSonIslemlerUrunler.setHasFixedSize(true);
        rvSonIslemlerUrunler.setLayoutManager(new LinearLayoutManager(this));
        rvSonIslemlerUrunler.setAdapter(sonIslemlerAdapter);



        //Realtime DB
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Kullanıcılar")
                .child(user_id)
                .child("Ürünlerim");



        initComponents();
        sonIslemlerEventChangeListener();

    }






    private void sonIslemlerEventChangeListener() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        UrunlerimModel model = dataSnapshot.getValue(UrunlerimModel.class);
                        if (model != null && model.getUrunAdi() != null)
                            urunlerimSonIslemlerList.add(model);
                    }
                }
                sonIslemlerAdapter.notifyDataSetChanged();

                //snapshot.getRef().removeValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veritabanı hatası!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    //Geri tuşuna basıldığında çalışacak method.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SonIslemlerUrunler.this, HomePage.class);
        startActivity(intent);
    }


    private void initComponents() {
        rvSonIslemlerUrunler = findViewById(R.id.rvSonIslemler);
    }
}
