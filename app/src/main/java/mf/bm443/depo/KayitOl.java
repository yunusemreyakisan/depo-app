package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitOl extends AppCompatActivity {
    private Button KayitOl;
    FirebaseAuth mAuth;
    private TextInputEditText AdiSoyadi;
    private TextInputEditText sifre;
    private TextInputEditText eMail;
    private TextInputEditText sifreYeniden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        mAuth = FirebaseAuth.getInstance();
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
            }else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(KayitOl.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(KayitOl.this, "Hesap başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(KayitOl.this, "Hesap oluşturulamadı, yeniden deneyin.", Toast.LENGTH_SHORT).show();
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