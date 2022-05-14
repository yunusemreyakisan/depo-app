package mf.bm443.depo.layouts.depoIslemleri;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.DepoAdapter;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.models.DepolarimModel;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle;
    private FirebaseFirestore db;
    ArrayList<DepolarimModel> depolarimList;
    DepoAdapter depoadapter;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolarim);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = findViewById(R.id.depolarimRecyclerView);

        //Realtime DB
        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Kullanıcılar")
                .child(user_id)
                .child("Depolarım")
                .child("");


        db = FirebaseFirestore.getInstance();
        depolarimList = new ArrayList<DepolarimModel>();
        depoadapter = new DepoAdapter(Depolarim.this, depolarimList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(depoadapter);

        //Methods
        //DepolarEventChangeListener();

        initComponents();
        yeniDepoEkle();
        DepoEventChangeListener();

    }

    //Geri tuşuna basıldığında çalışacak method.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Depolarim.this, HomePage.class);
        startActivity(intent);
    }

    private void DepoEventChangeListener() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DepolarimModel model = dataSnapshot.getValue(DepolarimModel.class);
                    depolarimList.add(model);

                }
                depoadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veritabanı hatası!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void yeniDepoEkle() {
        btnYeniDepoEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Depolarim.this, DepoEkle.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        btnYeniDepoEkle = findViewById(R.id.btnYeniDepoEkle);
    }


}


//Firestore
/*
    private void DepolarEventChangeListener() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        CollectionReference cRef = (CollectionReference) db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Depolarım");

        DocumentReference dRef = db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Depolarım")
                .document();


        cRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore hatası!", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                depolarimList.add(dc.getDocument().toObject(DepolarimModel.class));
                            }
                        }





                        depoadapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }

                });

    }

 */

