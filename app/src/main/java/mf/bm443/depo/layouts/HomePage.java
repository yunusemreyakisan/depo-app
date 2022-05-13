package mf.bm443.depo.layouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import mf.bm443.depo.R;
import mf.bm443.depo.layouts.depoIslemleri.Depolarim;
import mf.bm443.depo.layouts.urunIslemleri.Urunlerim;

public class HomePage extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private TextView name;
    private Button btnDepoIslemleri, btnCikis, btnUrunIslemleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);





        isimOkuma();
        initComponents();
        depoIslemleri();
        urunIslemleri();
        //popupMenuCikis();
        logout();


    }

    //Geri tuşuna basıldığında çalışacak method.
    @Override
    public void onBackPressed() {}




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


    private void isimOkuma(){
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Name");


        mDatabase.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    String isim = snapshot.getValue().toString();
                    name.setText(isim);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase İsim Çekme", "Böyle bir döküman yok!");
            }
        });

    }







/*
    //İsimi ekrana çekme (Firestore)
    private void isimOkuma() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        assert mUser != null;
        DocumentReference docRef = db.collection("Kullanıcılar").document(mUser.getUid());
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

 */


    private void depoIslemleri() {
        btnDepoIslemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Depolarim.class);
                startActivity(intent);
            }
        });
    }



    private void urunIslemleri() {
        btnUrunIslemleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Urunlerim.class);
                startActivity(intent);
            }
        });
    }


    private void initComponents() {
        name = findViewById(R.id.txtFirebaseAd);
        btnDepoIslemleri = findViewById(R.id.btnDepoIslemleri);
        btnCikis = findViewById(R.id.btnCikis);
        btnUrunIslemleri = findViewById(R.id.btnUrunIslemleri);
    }
}


