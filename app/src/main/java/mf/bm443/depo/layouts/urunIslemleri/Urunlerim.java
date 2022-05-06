package mf.bm443.depo.layouts.urunIslemleri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.DepoAdapter;
import mf.bm443.depo.adapter.UrunAdapter;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.layouts.depoIslemleri.Depolarim;
import mf.bm443.depo.models.DepolarimModel;
import mf.bm443.depo.models.UrunlerimModel;

public class Urunlerim extends AppCompatActivity {

    private RecyclerView urunlerRecyclerView;
    private ImageView depoLogoUrunlerim;
    private Button btnUrunEkle, btnBackUrunEkle;
    private FirebaseFirestore db;
    ArrayList<UrunlerimModel> urunlerimList;
    UrunAdapter urunadapter;
    FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunlerim);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView urunlerRecyclerView = findViewById(R.id.urunlerimRecyclerView);

        db = FirebaseFirestore.getInstance();
        urunlerimList = new ArrayList<UrunlerimModel>();
        urunadapter = new UrunAdapter(Urunlerim.this, urunlerimList);

        urunlerRecyclerView.setHasFixedSize(true);
        urunlerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        urunlerRecyclerView.setAdapter(urunadapter);







        //Methods
        UrunlerEventChangeListener();
        initComponents();
        urunEkleyeGit();
        urunlerimeGit();



        //RecyclerView loading.
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Ürünler yükleniyor...");
        progressDialog.show();



    }

    private void UrunlerEventChangeListener() {

        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        CollectionReference cRef = (CollectionReference) db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Ürünler");

        DocumentReference dRef = db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Ürünler")
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
                                urunlerimList.add(dc.getDocument().toObject(UrunlerimModel.class));
                            }
                        }


                        urunadapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();


                    }
                });



    }


    private void urunlerimeGit() {
        btnBackUrunEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Urunlerim.this, HomePage.class);
            startActivity(intent);
        });
    }




    private void urunEkleyeGit() {
        btnUrunEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Urunlerim.this, UrunEkle.class);
            startActivity(intent);
        });
    }

    private void initComponents() {
        btnBackUrunEkle = findViewById(R.id.btnBackUrunlerim);
        urunlerRecyclerView = findViewById(R.id.urunlerimRecyclerView);
        btnUrunEkle = (Button) findViewById(R.id.btnYeniUrunEkle);
        depoLogoUrunlerim = findViewById(R.id.depoLogoUrunlerim);
    }


}
