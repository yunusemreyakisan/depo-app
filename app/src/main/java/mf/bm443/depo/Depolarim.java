package mf.bm443.depo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private FirebaseUser mUser;


    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolarim);



        //Methods
        initComponents();
        yeniDepoEkle();



        //RecyclerView loading.
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Depolar yükleniyor...");
        progressDialog.show();


    }






    private void yeniDepoEkle() {
        btnYeniDepoEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Depolarim.this, DepoEkle.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        recyclerView = findViewById(R.id.depolarimRecyclerView);
        btnYeniDepoEkle = findViewById(R.id.btnYeniDepoEkle);
    }


}