package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import javax.annotation.Nonnull;

public class KayitOl extends AppCompatActivity {
    private Button KayitOl;
    FirebaseAuth mAuth;
    private TextInputEditText AdiSoyadi;
    private TextInputEditText sifre;
    private TextInputEditText eMail;
    private TextInputEditText sifreYeniden;
    private DatabaseReference mDatabase;
    private FirebaseFirestore mFirestore;
    private HashMap<String, Object> mData;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        initComponents();
        btnKayitOlIslevi();
    }

    private void btnKayitOlIslevi() {
        mAuth = FirebaseAuth.getInstance();
        KayitOl.setOnClickListener(view -> {
            String name = AdiSoyadi.getText().toString();
            String email = eMail.getText().toString();
            String password = sifre.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                Toast.makeText(KayitOl.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(KayitOl.this, "Şifre 6 karakterden daha uzun olmalı.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Veritabanına Canlı Kayıt Etme (Realtime Database)
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    mUser = mAuth.getCurrentUser();
                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                    mData = new HashMap<>();
                                   //HashMap<String, String> mData = new HashMap<>();
                                    mData.put("E-Mail", email);
                                    mData.put("Password", password);
                                    mData.put("Name", name);

                                    mFirestore.collection("Kullanıcılar").document(mUser.getUid())
                                            .set(mData)
                                            .addOnCompleteListener(KayitOl.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@Nonnull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent intent = new Intent(KayitOl.this, MainActivity.class);
                                                        startActivity(intent);
                                                        Toast.makeText(KayitOl.this, "Hesap başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(KayitOl.this, "Hesap oluşturulamadı, yeniden deneyin.", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

                                    //Realtime Database
                                 /*   mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(KayitOl.this, MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(KayitOl.this, "Hesap başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(KayitOl.this, "Hesap oluşturulamadı, yeniden deneyin.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });*/
                                }
                            }
                        });
            }
        });
    }


    private void initComponents() {
        KayitOl = (Button) findViewById(R.id.btnKayitOl);
        AdiSoyadi = findViewById(R.id.adSoyad);
        sifre = findViewById(R.id.sifre);
        eMail = findViewById(R.id.ePosta);
        sifreYeniden = findViewById(R.id.sifreAgain);
    }
}