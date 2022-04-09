package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private Button girisYap, kaydol;
    private TextInputEditText girisEmail;
    private TextInputEditText sifre;
    private TextView sifremiUnuttum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




/*
        //Google Giriş Bağlantısı
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

 */

        initComponents();
        btnKaydolIslevi();
        sifremiUnuttum();
        btnGirisYapIslevi();
        // verileriGetir(mAuth.getUid());

    }


    private void btnKaydolIslevi() {
        kaydol.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, KayitOl.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        sifremiUnuttum = findViewById(R.id.txtSifremiUnuttum);
        girisYap = findViewById(R.id.btnGirisYap);
        kaydol = findViewById(R.id.btnKaydol);
        girisEmail = findViewById(R.id.txtGrsEmail);
        sifre = findViewById(R.id.txtGrsSifre);
    }


    public void sifremiUnuttum() {
        //Kullanıcıya Firebase üzerinden Şifremi unuttum emaili gönderilecek.
        sifremiUnuttum.setMovementMethod(LinkMovementMethod.getInstance());
    }
/*
    //Firestore Veri Çekme
    private void verileriGetir(String uid) {
        mFirestore = FirebaseFirestore.getInstance();
        mDocReference = mFirestore.collection("Kullanıcılar").document(uid);
        mDocReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData().get("Name"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

 */

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
                                Intent intent = new Intent(MainActivity.this, HomePage.class);
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

