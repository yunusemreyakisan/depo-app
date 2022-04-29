package mf.bm443.depo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private CheckBox remember;
    private Button girisYap, kaydol;
    private TextInputEditText girisEmail;
    private TextInputEditText sifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        btnKaydolIslevi();
        beniHatirlaMethodu();
        btnGirisYapIslevi();
        // verileriGetir(mAuth.getUid());

    }


    //Shared Preferences (Beni Hatırla)
    private void beniHatirlaMethodu() {
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("Remember", "");

        if (checkbox.equals("true")) {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        }


        remember.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("Remember", "true");
                editor.apply();
                Toast.makeText(getApplicationContext(), "Beni hatırla açık!", Toast.LENGTH_SHORT).show();
            } else if (!compoundButton.isChecked()) {
                SharedPreferences preferences1 = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("Remember", "false");
                editor.apply();
                Toast.makeText(getApplicationContext(), "Beni hatırla kapalı!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void btnKaydolIslevi() {
        kaydol.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, KayitOl.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        girisYap = findViewById(R.id.btnGirisYap);
        kaydol = findViewById(R.id.btnKaydol);
        girisEmail = findViewById(R.id.txtGrsEmail);
        sifre = findViewById(R.id.txtGrsSifre);
        remember = findViewById(R.id.beniHatirlaCheckbox);

    }


    //E-Mail ve Şifre ile Giriş İşlemi
    private void btnGirisYapIslevi() {
        //Kaydolunan şifre ve ad soyad şartlanacak.
        mAuth = FirebaseAuth.getInstance();
        girisYap.setOnClickListener(v -> kayitliKullaniciGirisi());
    }

    private void kayitliKullaniciGirisi() {
        // Firebase üzerinden email ve şifre alınması
        String email, password;
        email = Objects.requireNonNull(girisEmail.getText()).toString();
        password = Objects.requireNonNull(sifre.getText()).toString();

        // Email ve Şifre Giriş Kontrolü (Dolu-Boş)
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Lütfen email giriniz.",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Lütfen şifrenizi giriniz.",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Kayıtlı Kullanıcı Girişi
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Giriş Başarılı!",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // Eğer giriş bilgileri doğruysa:
                                // Anasayfaya geç.
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                startActivity(intent);


                            } else {

                                // Giriş hatalı ise:
                                Toast.makeText(getApplicationContext(),
                                        "E-Mail veya Şifre Hatalı!",
                                        Toast.LENGTH_LONG)
                                        .show();

                            }
                        });
    }


}








/*
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:id="@+id/recyclerView"
    app:cardCornerRadius="8dp"
    app:cardElevation="8dp">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginVertical="10dp"
        android:layout_marginStart="10dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/txtDepoAdi"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=""
            android:textColor="@color/colorPrimary"
            android:textSize="20sp" />

        <!-- Depo Adresi -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/txtDepoAdresiWrapper"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Depo adresi : "
                android:textColor="@color/colorPrimary"
                android:textSize="15sp"
                android:textStyle="normal" />

            <TextView
                android:id="@+id/txtDepoAdresi"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="TextView" />
        </LinearLayout>

        <!-- Depo Büyüklüğü -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/txtDepoBuyuklukWrapper"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Depo büyüklüğü : "
                android:textColor="@color/colorPrimary"
                android:textSize="15sp"
                android:textStyle="normal" />

            <TextView
                android:id="@+id/txtDepoBuyukluk"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="TextView" />
        </LinearLayout>

        <!-- Depo Kategorisi -->
        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/txtDepoKategorisiWrapper"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Depo kategorisi : "
                android:textColor="@color/colorPrimary"
                android:textSize="15sp"
                android:textStyle="normal" />

            <TextView
                android:id="@+id/txtDepoKategorisi"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="TextView" />
        </LinearLayout>


    </LinearLayout>


</androidx.cardview.widget.CardView>
 */