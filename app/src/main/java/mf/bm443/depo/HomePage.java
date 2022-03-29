package mf.bm443.depo;

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

    private TextView veri;
    private Button btnVeriOku;
    private TextView name, email;
    SwitchCompat switchCompat;
    private DatabaseReference mReferance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Durum Kontrolü
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            //When night mode is equal to yes
            //Set dark theme
            setTheme(R.style.Theme_Dark);
        } else {
            //When night mode is equal to no
            //Set light theme
            setTheme(R.style.Theme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initComponents();
       // veriOkuma();
        switchThemes();
    }

    private void switchThemes() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Check condition

                if (isChecked) {
                    //When switch button is checked
                    //Set night mode

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    //When switch button is unchecked
                    //Set light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                }
            }
        });
    }

/*
    private void veriOkuma() {
        btnVeriOku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mReferance = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                mReferance.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(HomePage.this, "Veritabanı hatası!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

 */


    private void initComponents() {
        veri = findViewById(R.id.veriOkuma);
        btnVeriOku = findViewById(R.id.btnVeriOkuma);
        switchCompat = findViewById(R.id.bt_theme_switch);
        name = findViewById(R.id.txtFirebaseAd);
    }
}


