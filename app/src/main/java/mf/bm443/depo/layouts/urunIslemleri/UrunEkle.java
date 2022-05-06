package mf.bm443.depo.layouts.urunIslemleri;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import mf.bm443.depo.R;
import mf.bm443.depo.layouts.depoIslemleri.DepoEkle;
import mf.bm443.depo.layouts.depoIslemleri.Depolarim;


public class UrunEkle extends AppCompatActivity {

    private EditText urunAdi, urunDeposu, urunKategorisi, urunMiktari;
    private Button btnUrunekle;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseFirestore mFirestore;
    private HashMap<String, Object> mCData;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        initComponents();
        btnUrunEkleIslevi();



    }

    private void btnUrunEkleIslevi() {
                mAuth = FirebaseAuth.getInstance();
                btnUrunekle.setOnClickListener(view -> {
                    //Alan Tanımları
                    String urunadi = urunAdi.getText().toString();
                    String urundeposu = urunDeposu.getText().toString();
                    String urunKategori = urunKategorisi.getText().toString();
                    String urunMiktar = urunMiktari.getText().toString();
                    if (TextUtils.isEmpty(urunadi) || TextUtils.isEmpty(urunMiktar) || TextUtils.isEmpty(urunKategori) || TextUtils.isEmpty(urundeposu)) {
                        Toast.makeText(UrunEkle.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                    } else {
                        //Veritabanına Canlı Kayıt Etme (Realtime Database)
                        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        mUser = mAuth.getCurrentUser();
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(user_id).child("Depolarım");

                        mCData = new HashMap<>();
                        //HashMap<String, String> mData = new HashMap<>();
                        mCData.put("urunAdi", urunadi);
                        mCData.put("urunDeposu", urundeposu);
                        mCData.put("urunKategorisi", urunKategori);
                        mCData.put("urunMiktari", urunMiktar);

                        mFirestore.collection("Kullanıcılar")
                                .document(mUser.getUid())
                                .collection("Ürünler")
                                .document()
                                .set(mCData)
                                .addOnSuccessListener(aVoid -> {
                                    Intent intent = new Intent(UrunEkle.this, Urunlerim.class);
                                    startActivity(intent);
                                    Toast.makeText(UrunEkle.this, "Ürün başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> Toast.makeText(UrunEkle.this, "Ürün eklenemedi, yeniden deneyin.", Toast.LENGTH_SHORT).show());
                    }
                });



            }





    private void initComponents() {
        urunAdi = findViewById(R.id.urunAdi);
        urunKategorisi = findViewById(R.id.urunKategori);
        urunDeposu = findViewById(R.id.urunDeposu);
        urunMiktari = findViewById(R.id.urunMiktar);
        btnUrunekle = findViewById(R.id.btnUrunuEkle);

    }


}
