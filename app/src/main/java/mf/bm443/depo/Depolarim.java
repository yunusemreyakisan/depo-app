package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class Depolarim extends AppCompatActivity {
    private Button btnYeniDepoEkle;
    private RecyclerView depoListesi;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depolarim);


        //Methods
        initComponents();
        yeniDepoEkle();

    }


    private void yeniDepoEkle() {
        btnYeniDepoEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Depolarim.this, DepoEkle.class);
                startActivity(intent);
            }
        });
    }


    private void initComponents() {
        depoListesi = findViewById(R.id.depoListesi);
        btnYeniDepoEkle = findViewById(R.id.btnYeniDepoEkle);
    }


}