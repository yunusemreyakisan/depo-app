package mf.bm443.depo.layouts.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

import mf.bm443.depo.R;

public class KayitOl extends AppCompatActivity {
    private Button KayitOl;
    FirebaseAuth mAuth;
    private TextInputEditText adiSoyadi;
    private TextInputEditText sifre;
    private TextInputEditText eMail;
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
            String name = adiSoyadi.getText().toString();
            String email = eMail.getText().toString();
            String password = sifre.getText().toString();

            //E-Mail Validation
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                Toast.makeText(KayitOl.this, "Boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(KayitOl.this, "Şifre 6 karakterden daha uzun olmalı.", Toast.LENGTH_SHORT).show();
            } else if (!email.matches(emailPattern)) {
                Log.i("E-Mail Valid", "Geçerli E-posta girildi.");
                Toast.makeText(getApplicationContext(), "Geçersiz E-Posta", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Veritabanına Canlı Kayıt Etme (Realtime Database)
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    mUser = mAuth.getCurrentUser();
                                    mDatabase = FirebaseDatabase.getInstance().getReference()
                                            .child("Kullanıcılar")
                                            .child(user_id)
                                            .child("Kullanıcı Bilgileri");

                                    HashMap<String, String> mData = new HashMap<>();
                                    mData.put("E-Mail", email);
                                    mData.put("Password", password);
                                    mData.put("Name", name);

                                        //Realtime Database
                                        mDatabase.setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(KayitOl.this, MainActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(KayitOl.this, "Hesap oluşturuldu.", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(KayitOl.this, "Hesap oluşturulamadı, yeniden deneyin.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
            }
        });
    }




    private void initComponents() {
        KayitOl = (Button) findViewById(R.id.btnKayitOl);
        adiSoyadi = findViewById(R.id.adSoyad);
        sifre = findViewById(R.id.sifre);
        eMail = findViewById(R.id.ePosta);
    }
}




/*
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

 */