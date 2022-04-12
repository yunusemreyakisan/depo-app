package mf.bm443.depo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends AppCompatActivity {

    private TextView name, email;
    private DatabaseReference mReferance;
    private Button btnDepoIslemleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initComponents();
       // veriOkuma();
        depoIslemleri();
    }

    private void depoIslemleri() {
        btnDepoIslemleri.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(HomePage.this, Depolarim.class);
                startActivity(intent);
            }
        });
    }




    //Burası ÖNEMLİ! Beyaz ekran verip başlangıca atıyor.
   /* private void veriOkuma() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mReferance = FirebaseDatabase.getInstance().getReference("Users").child("Name");
        mReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "Veritabanı hatası!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    */


    private void initComponents() {
        name = findViewById(R.id.txtFirebaseAd);
        btnDepoIslemleri = findViewById(R.id.btnDepoIslemleri);
    }
}


