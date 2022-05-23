package mf.bm443.depo.layouts.kategoriIslemleri;

import android.os.Bundle;
import android.widget.TextView;
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
import mf.bm443.depo.adapter.KategoriAdapter;
import mf.bm443.depo.models.DepolarimModel;
import mf.bm443.depo.models.UrunlerimModel;

public class Kategoriler extends AppCompatActivity {

    private RecyclerView kategorilerimRecyclerView;
    ArrayList<UrunlerimModel> kategorilerimList;
    KategoriAdapter kategoriAdapter;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategoriler);

        mAuth = FirebaseAuth.getInstance();
        RecyclerView kategorilerimRecyclerView = findViewById(R.id.kategorilerimRecyclerView);

        kategorilerimList = new ArrayList<UrunlerimModel>();
        kategoriAdapter = new KategoriAdapter(Kategoriler.this, kategorilerimList);

        kategorilerimRecyclerView.setHasFixedSize(true);
        kategorilerimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        kategorilerimRecyclerView.setAdapter(kategoriAdapter);

        //Realtime DB
        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Kullanıcılar")
                .child(user_id)
                .child("Ürünlerim");


        //Methods
        initComponents();
        KategorilerEventChangeListener();





    }

    private void KategorilerEventChangeListener() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UrunlerimModel model = dataSnapshot.getValue(UrunlerimModel.class);
                    kategorilerimList.add(model);
                }
                kategoriAdapter.notifyDataSetChanged();

                //snapshot.getRef().removeValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veritabanı hatası!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void initComponents() {
        kategorilerimRecyclerView = findViewById(R.id.kategorilerimRecyclerView);
    }
}
