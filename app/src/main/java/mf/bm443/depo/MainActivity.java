package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    private Button girisYap, kaydol, signWithGoogle;
    private TextInputEditText girisEmail;
    private TextInputEditText sifre;
    private TextInputEditText eMail;
    private TextInputEditText adiSoyadi;
    private TextInputEditText kayitSifre;
    private TextView sifremiUnuttum;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Google Giriş Bağlantısı
       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
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
        //btnGoogleGirisIslevi();
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
        eMail = findViewById(R.id.ePosta);
        signWithGoogle = findViewById(R.id.btnGoogleGiris);
        adiSoyadi = findViewById(R.id.adSoyad);
        kayitSifre = findViewById(R.id.sifre);
    }


    public void sifremiUnuttum() {
        //Kullanıcıya Firebase üzerinden Şifremi unuttum emaili gönderilecek.
        sifremiUnuttum.setMovementMethod(LinkMovementMethod.getInstance());
    }

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
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
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


    //Google Giriş İşlemi
  /*  private void btnGoogleGirisIslevi() {
        signWithGoogle.setOnClickListener(view -> sign());
    }

    private void sign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            task.addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                @Override
                public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                    Log.d(TAG, "signInWithGoogle:success");
                    Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    GoogleSignInAccount result = task.getResult();
                    String email = result.getEmail();
                    Account account = result.getAccount();
                    updateUI(account);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "signInWithGoogle:failure", task.getException());
                    updateUI(null);
                }
            });
        }
    }

    public void updateUI(Account account){
        if(account != null){
            Toast.makeText(this,"Başarıyla bağlandı.",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomePage.class));

        }else {
            Toast.makeText(this,"Google ile bağlanılamadı.",Toast.LENGTH_LONG).show();
        }

    }

   */


//My Codes
   /* private void btnGoogleGirisIslevi() {
        signWithGoogle.setOnClickListener(view -> sign());
    }

    private void sign() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }

        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Firebase ile Giriş : Başarılı");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "Firebase ile Giriş : Başarısız", task.getException());

                    }
                });
    }

    */


}

