package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle, btnDepoA;
    private RecyclerView depoListesi;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolarim);


        //Methods
        initComponents();
        ornekDepo();
        yeniDepoEkle();

    }

    //Örnek Depo Kullanımı
    private void ornekDepo() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert mUser != null;
        DocumentReference docRef = db.collection("Kullanıcılar").document(mUser.getUid()).collection("Depolarım").document("DepoA");
        docRef.get().

                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    private static final String TAG = "123";

                    @Override
                    public void onComplete (@NonNull Task< DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String depoadi = document.getString("Depo-Adi");
                                btnDepoA.setText(depoadi);
                            } else {
                                Log.d(TAG, "Böyle bir döküman yok!");
                            }
                        } else {
                            Log.d(TAG, "Hata:  ", task.getException());
                        }
                    }
                });


    }


    private void yeniDepoEkle() {
        btnYeniDepoEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Depolarim.this, DepoEkle.class);
            startActivity(intent);
        });
    }


    private void initComponents() {
        btnDepoA = findViewById(R.id.btnDepoA);
        btnYeniDepoEkle = findViewById(R.id.btnYeniDepoEkle);
    }


}