package mf.bm443.depo.layouts.urunIslemleri;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.local.LruGarbageCollector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import mf.bm443.depo.R;
import mf.bm443.depo.models.UrunlerimModel;


public class UrunEkle extends AppCompatActivity {

    private EditText urunAdi, urunDeposu, urunMiktari, yeniKategori;
    private Spinner spinnerUrunKategorisi;
    private Button btnUrunekle, btnKategoriEkle;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference spinnerRef;
    private DatabaseReference mDBRef;
    private ArrayList<String> spinnerList;
    private ArrayAdapter<String> spinnerAdapter;
    //private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);
        mAuth = FirebaseAuth.getInstance();
        //mFirestore = FirebaseFirestore.getInstance();


        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();

        //Ürün Kategorisi Spinner DB Referansı
        spinnerRef = FirebaseDatabase.getInstance().getReference()
                .child("Kullanıcılar")
                .child(user_id)
                .child("Ürünlerim")
                .child("Ürün Kategorileri");

        initComponents();
        btnUrunEkleIslevi();
        urunKategorisiEkle();

    }

    private void urunKategorisiEkle() {
        spinnerList = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<String>(UrunEkle.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);

        btnKategoriEkle.setOnClickListener(v -> {

            String value = yeniKategori.getText().toString();
            String key = spinnerRef.push().getKey(); //UID ile kayıt etme

            if(TextUtils.isEmpty(value)){
            Toast.makeText(getApplicationContext(), "Boş kategori kaydedilemez.", Toast.LENGTH_SHORT).show();
            }else{

                assert key != null;
                spinnerRef.child(key).setValue(value);
                yeniKategori.setText("");
                spinnerList.clear();
                spinnerAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), value + " kategorisi eklendi.", Toast.LENGTH_SHORT).show();
            }
        });

        spinnerUrunKategorisi.setAdapter(spinnerAdapter);
        showData();

    }


    //Spinner Veriyi Gösterme
    private void showData() {
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    spinnerList.add(Objects.requireNonNull(item.getValue()).toString());
                }
                spinnerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // never ignore errors.
            }
        });
    }


    private void btnUrunEkleIslevi() {
        mAuth = FirebaseAuth.getInstance();
        String value = yeniKategori.getText().toString();
        btnUrunekle.setOnClickListener(view -> {

            //Kategori Boşluk Kontrolü
            if(spinnerList.isEmpty() && value.isEmpty()){
                Toast.makeText(getApplicationContext(), "Lütfen kategori seçiniz", Toast.LENGTH_SHORT).show();
            }

            //Veritabanına Canlı Kayıt Etme (Realtime Database)
            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            mUser = mAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(user_id).child("Ürünlerim");


            //Alan Tanımları
            String urunadi = urunAdi.getText().toString();
            String urundeposu = urunDeposu.getText().toString();
            String urunMiktar = urunMiktari.getText().toString();
            if (TextUtils.isEmpty(urunadi) || TextUtils.isEmpty(urunMiktar) || TextUtils.isEmpty(urundeposu)) {
                Toast.makeText(UrunEkle.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            } else {

                UrunlerimModel urunlerimmodel = new UrunlerimModel(urunadi, urundeposu, urunMiktar);
                mDatabase.push().setValue(urunlerimmodel); //without push()
                Intent intent = new Intent(UrunEkle.this, Urunlerim.class);
                startActivity(intent);
                Toast.makeText(UrunEkle.this, "Ürün başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void initComponents() {
        urunAdi = findViewById(R.id.txtUrunAdi);
        spinnerUrunKategorisi = findViewById(R.id.txtUrunKategori);
        urunDeposu = findViewById(R.id.txtUrunDeposu);
        urunMiktari = findViewById(R.id.txtUrunMiktar);
        btnUrunekle = findViewById(R.id.btnUrunuEkle);
        btnKategoriEkle = findViewById(R.id.btnUrunKategoriEkle);
        yeniKategori = findViewById(R.id.txtYeniKategori);
    }


}
