package mf.bm443.depo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle;
    private FirebaseFirestore db;
    ArrayList<DepolarimModel> depolarimList;
    DepoAdapter depoadapter;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolarim);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = findViewById(R.id.depolarimRecyclerView);

        db = FirebaseFirestore.getInstance();
        depolarimList = new ArrayList<DepolarimModel>();
        depoadapter = new DepoAdapter(Depolarim.this, depolarimList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(depoadapter);

        EventChangeListener();

        //Methods
        initComponents();
        yeniDepoEkle();

        //RecyclerView loading.
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Depolar yükleniyor...");
        progressDialog.show();


    }

    private void EventChangeListener() {
       /* mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        db.collectionGroup("Depolarım").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                depolarimList.add(dc.getDocument().toObject(DepolarimModel.class));
                            }
                        }


                        depoadapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                });

        */


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        db.collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Depolarım")
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