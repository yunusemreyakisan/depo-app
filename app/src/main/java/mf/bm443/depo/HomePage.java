package mf.bm443.depo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    private TextView veri;
    private Button btnVeriOku;
    private String name, email;
    SwitchCompat switchCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Duurm Kontrolü
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
        veriOku();
        veriYaz();
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



    private void veriOku() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Ad, soyad, email ve fotoğraf istemi.
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Kullanıcın onaylı olup olmadığının kontrolü.
            boolean emailVerified = user.isEmailVerified();

            // Firebase projesine özel kullanıcının kimliği.
            String uid = user.getUid();
        }
    }

    private void veriYaz() {
        btnVeriOku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            }
        });

    }

    private void initComponents() {
        veri = findViewById(R.id.veriOkuma);
        btnVeriOku = findViewById(R.id.btnVeriOkuma);
        switchCompat = findViewById(R.id.bt_theme_switch);

    }
}


