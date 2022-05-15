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
    private DatabaseReference dbReferenceForImage;
    //private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);
        mAuth = FirebaseAuth.getInstance();
        //mFirestore = FirebaseFirestore.getInstance();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        initComponents();
        btnUrunEkleIslevi();
    }
/*

        urunPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotografSec();
            }
        });


    }

    //Ürün için fotoğraf seçimi
    private void fotografSec() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    //Fotograf yükleme isteği
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            urunPhoto.setImageURI(imageUri);
            uploadPicture();
        }
    }

    /*
    //Fotograf Yüklenmesi
    private void uploadPicture() {
        //Fotoğrafları kayıt ederken kullanıcı adına kayıt ediyoruz.
        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();

        final ProgressDialog pD = new ProgressDialog(this);
        pD.setTitle("Fotograf yükleniyor...");
        pD.show();

        //Fotograf için rastgele isim oluşturulması
        final String randomKey = UUID.randomUUID().toString();
        //Geçerli kullanıcı için fotoğrafların UID ile kayıt alınması
        StorageReference ref = storageReference.child(user_id + "/images/" + randomKey);

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot task) {
                        pD.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Fotoğraf yüklendi", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@Nonnull Exception e) {
                        pD.dismiss();
                        Toast.makeText(getApplicationContext(), "Hata oluştu, yeniden deneyin.", Toast.LENGTH_SHORT).show();
                    }
                });

    }*/


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
