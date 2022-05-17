package mf.bm443.depo.layouts.urunIslemleri;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;

import mf.bm443.depo.R;
import mf.bm443.depo.models.UrunlerimModel;


public class UrunEkle extends AppCompatActivity {

    private EditText urunAdi, urunDeposu, urunKategorisi, urunMiktari;
    private Button btnUrunekle;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    //private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);
        mAuth = FirebaseAuth.getInstance();
        //mFirestore = FirebaseFirestore.getInstance();

        initComponents();
        btnUrunEkleIslevi();
    }


        private void btnUrunEkleIslevi () {
            mAuth = FirebaseAuth.getInstance();
            btnUrunekle.setOnClickListener(view -> {

                //Veritabanına Canlı Kayıt Etme (Realtime Database)
                String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                mUser = mAuth.getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(user_id).child("Ürünlerim");


                //Alan Tanımları
                String urunadi = urunAdi.getText().toString();
                String urundeposu = urunDeposu.getText().toString();
                String urunKategori = urunKategorisi.getText().toString();
                String urunMiktar = urunMiktari.getText().toString();
                if (TextUtils.isEmpty(urunadi) || TextUtils.isEmpty(urunMiktar) || TextUtils.isEmpty(urunKategori) || TextUtils.isEmpty(urundeposu)) {
                    Toast.makeText(UrunEkle.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
                } else {

                    UrunlerimModel urunlerimmodel = new UrunlerimModel(urunadi, urundeposu, urunKategori, urunMiktar);
                    mDatabase.push().setValue(urunlerimmodel);
                    Intent intent = new Intent(UrunEkle.this, Urunlerim.class);
                    startActivity(intent);
                    Toast.makeText(UrunEkle.this, "Ürün başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                }

            });
        }


        private void initComponents () {
            urunAdi = findViewById(R.id.txtUrunAdi);
            urunKategorisi = findViewById(R.id.txtUrunKategori);
            urunDeposu = findViewById(R.id.txtUrunDeposu);
            urunMiktari = findViewById(R.id.txtUrunMiktar);
            btnUrunekle = findViewById(R.id.btnUrunuEkle);

        }


    }
