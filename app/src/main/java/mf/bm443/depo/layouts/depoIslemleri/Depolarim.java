package mf.bm443.depo.layouts.depoIslemleri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.DepoAdapter;
import mf.bm443.depo.models.DepolarimModel;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle;
    private FirebaseFirestore db;
    private TextView adTV,adresTV;
    ArrayList<DepolarimModel> depolarimList;
    DepoAdapter depoadapter;
    FirebaseAuth mAuth;
    private FirebaseUser mUser;
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



        //Methods
        DepolarEventChangeListener();
        initComponents();
        yeniDepoEkle();

       //RecyclerView loading.
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Depolar yükleniyor...");
        progressDialog.show();



    }


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



    private void yeniDepoEkle() {
        btnYeniDepoEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Depolarim.this, DepoEkle.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        adTV=findViewById(R.id.txtDepoAdi);
        adresTV =findViewById(R.id.txtDepoAdresi);
        btnYeniDepoEkle = findViewById(R.id.btnYeniDepoEkle);
    }


}
