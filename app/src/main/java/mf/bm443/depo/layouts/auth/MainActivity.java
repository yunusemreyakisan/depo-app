package mf.bm443.depo.layouts.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import mf.bm443.depo.R;
import mf.bm443.depo.layouts.HomePage;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private CheckBox remember;
    private Button girisYap, kaydol;
    private TextInputEditText girisEmail;
    private TextInputEditText sifre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        btnKaydolIslevi();
        beniHatirlaMethodu();
        btnGirisYapIslevi();

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





