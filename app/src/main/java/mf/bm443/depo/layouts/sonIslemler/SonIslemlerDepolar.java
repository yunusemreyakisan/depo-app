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
import java.util.Objects;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.SonIslemlerAdapter;
import mf.bm443.depo.adapter.SonIslemlerDepoAdapter;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.models.DepolarimModel;
import mf.bm443.depo.models.UrunlerimModel;

public class SonIslemlerDepolar extends AppCompatActivity {

    private RecyclerView rVSonIslemlerDepo;
    ArrayList<DepolarimModel> sonIslemlerDepolarimList;
    SonIslemlerDepoAdapter sonIslemlerDepoAdapter;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_islemler_depolar);


        //Son İşlemler (Depolar) Recyclerview
        RecyclerView rvSonIslemlerDepo = findViewById(R.id.rvSonIslemlerDepo);
        sonIslemlerDepolarimList = new ArrayList<DepolarimModel>();
        sonIslemlerDepoAdapter = new SonIslemlerDepoAdapter(SonIslemlerDepolar.this, sonIslemlerDepolarimList);

        rvSonIslemlerDepo.setHasFixedSize(true);
        rvSonIslemlerDepo.setLayoutManager(new LinearLayoutManager(this));
        rvSonIslemlerDepo.setAdapter(sonIslemlerDepoAdapter);

        //Reference
        mAuth = FirebaseAuth.getInstance();
        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(user_id).child("Depolarım");


        //Methods
        initComponents();
        sonIslemlerDepoEventChangeListener();

    }

    private void sonIslemlerDepoEventChangeListener() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        DepolarimModel model = dataSnapshot.getValue(DepolarimModel.class);
                        if (model != null && model.getDepoAdi() != null)
                            sonIslemlerDepolarimList.add(model);
                    }
                }
                sonIslemlerDepoAdapter.notifyDataSetChanged();

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
        Intent intent = new Intent(SonIslemlerDepolar.this, HomePage.class);
        startActivity(intent);
    }



    private void initComponents() {
        rVSonIslemlerDepo = findViewById(R.id.rvSonIslemlerDepo);

    }
}
