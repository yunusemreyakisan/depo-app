package mf.bm443.depo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {

    private TextView name;
    private Button btnDepoIslemleri, btnCikis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        isimOkuma();
        initComponents();
        depoIslemleri();
        //popupMenuCikis();
        logout();


    }




    //Popup Menu Çıkış yap Gösterilmesi
   /* private void popupMenuCikis() {
        PopupMenu popupMenu = new PopupMenu(this, btnCikis);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Çıkış yap");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == 0) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Remember", "false");
                    editor.apply();

                    finish();
                }
                return false;
            }
        });
        //Popup göster
        btnCikis.setOnClickListener(view -> popupMenu.show());
    }

    */



    private void logout() {
        btnCikis.setOnClickListener(view -> {
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Remember", "false");
            editor.apply();

            finish();
        });
    }



    //İsimi ekrana çekme
    private void isimOkuma() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert mUser != null;
        DocumentReference docRef = db.collection("Kullanıcılar").document(mUser.getUid());
        docRef.get().

                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    private static final String TAG = "123";

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String isim = document.getString("Name");
                                name.setText(isim);
                            } else {
                                Log.d(TAG, "Böyle bir döküman yok!");
                            }
                        } else {
                            Log.d(TAG, "Hata:  ", task.getException());
                        }
                    }
                });
    }


    private void depoIslemleri() {
        btnDepoIslemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Depolarim.class);
                startActivity(intent);
            }
        });
    }


    private void initComponents() {
        name = findViewById(R.id.txtFirebaseAd);
        btnDepoIslemleri = findViewById(R.id.btnDepoIslemleri);
        btnCikis = findViewById(R.id.btnCikis);
    }
}


