package mf.bm443.depo.layouts.depoIslemleri;

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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import mf.bm443.depo.R;
import mf.bm443.depo.models.DepolarimModel;

public class DepoEkle extends AppCompatActivity {

    private Button depolarimaEkle;
    private EditText depoAd, depoAdres, depoBuyuklugu;
    //Depo Kaydetme
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseFirestore mFirestore;
    private HashMap<String, Object> mCData;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_ekle);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        initComponents();
        depoOzellikleriKaydet();


    }

    private void initComponents() {

        depolarimaEkle = findViewById(R.id.btnDepolarimaEkle);
        depoAd = findViewById(R.id.depoAd);
        depoAdres = findViewById(R.id.depoAdres);
        depoBuyuklugu = findViewById(R.id.depoBuyuklugu);
    }


    //Özellikleri döküman olarak içeriye ekleme
    private void depoOzellikleriKaydet() {
        mAuth = FirebaseAuth.getInstance();
        depolarimaEkle.setOnClickListener(view -> {

            //Veritabanına Canlı Kayıt Etme (Realtime Database)
            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            mUser = mAuth.getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(user_id).child("Depolarım");

            //Alan Tanımları
            String depoadi = depoAd.getText().toString();
            String depoAdresi = depoAdres.getText().toString();
            String depoBuyukluk = depoBuyuklugu.getText().toString();
            Calendar calendar = Calendar.getInstance();
            String eklenmeTarihi = DateFormat.getDateInstance().format(calendar.getTime());
            if (TextUtils.isEmpty(depoadi) || TextUtils.isEmpty(depoBuyukluk)|| TextUtils.isEmpty(depoAdresi)) {
                Toast.makeText(DepoEkle.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            } else {
                DepolarimModel model = new DepolarimModel(depoadi, depoAdresi, depoBuyukluk, eklenmeTarihi);
                mDatabase.push().setValue(model);
                Intent sayfa = new Intent(DepoEkle.this, Depolarim.class);
                startActivity(sayfa);
                Toast.makeText(DepoEkle.this, "Deponuz başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
            }


        });
    }

}


