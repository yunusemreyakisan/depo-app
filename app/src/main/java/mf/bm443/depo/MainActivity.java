package mf.bm443.depo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore mFirestore;
    private DocumentReference mDocReference;
    FirebaseAuth mAuth;
    private Button girisYap, kaydol;
    private TextInputEditText girisEmail;
    private TextInputEditText sifre;
    private TextView sifremiUnuttum, ad;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private String clientId = "41520331102-s8051jk0gittoobdlr53kg760j7pjgms.apps.googleusercontent.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Firestore Bağlantısı
        mFirestore = FirebaseFirestore.getInstance();


        //Google Giriş Bağlantısı
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

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
        ad = findViewById(R.id.txtFirebaseAd);
        mFirestoreList = findViewById(R.id.depoListesi);

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
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitliKullaniciGirisi();
            }
        });
    }

    private void kayitliKullaniciGirisi() {
        // Firebase üzerinden email ve şifre alınması
        String email, password;
        email = girisEmail.getText().toString();
        password = sifre.getText().toString();

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
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {
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
                            }
                        });
    }


}

