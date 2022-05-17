package mf.bm443.depo.layouts.urunIslemleri;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import javax.sql.DataSource;

import mf.bm443.depo.R;
import mf.bm443.depo.adapter.UrunAdapter;
import mf.bm443.depo.layouts.HomePage;
import mf.bm443.depo.models.UrunlerimModel;

public class Urunlerim extends AppCompatActivity {

    private RecyclerView urunlerRecyclerView;
    private ImageView depoLogoUrunlerim;
    private Button btnUrunEkle;
    private FirebaseFirestore db;
    ArrayList<UrunlerimModel> urunlerimList;
    UrunAdapter urunadapter;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunlerim);
        mAuth = FirebaseAuth.getInstance();
        RecyclerView urunlerRecyclerView = findViewById(R.id.urunlerimRecyclerView);

        db = FirebaseFirestore.getInstance();
        urunlerimList = new ArrayList<UrunlerimModel>();
        urunadapter = new UrunAdapter(Urunlerim.this, urunlerimList);

        urunlerRecyclerView.setHasFixedSize(true);
        urunlerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(urunlerRecyclerView);
        urunlerRecyclerView.setAdapter(urunadapter);

        //Realtime DB
        String user_id = mAuth.getCurrentUser().getUid();
        mUser = mAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Kullanıcılar")
                .child(user_id)
                .child("Ürünlerim");

        //Methods
        UrunlerEventChangeListener();
        initComponents();
        urunEkleyeGit();









    }


    //Geri tuşuna basıldığında çalışacak method.
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Urunlerim.this, HomePage.class);
        startActivity(intent);
    }



    private void UrunlerEventChangeListener() {
        FirebaseUser mUser = mAuth.getCurrentUser();
        mAuth = FirebaseAuth.getInstance();


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UrunlerimModel model = dataSnapshot.getValue(UrunlerimModel.class);
                    urunlerimList.add(model);

                }
                urunadapter.notifyDataSetChanged();

                //snapshot.getRef().removeValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Veritabanı hatası!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    //SwipeToAction
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
            Toast.makeText(Urunlerim.this, "Ürün Silindi", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            int position = holder.getAdapterPosition(); // this is how you can get the position
            urunlerimList.remove(position);

            mDatabaseReference.child(String.valueOf(position)).removeValue();
            urunadapter.notifyItemRemoved(position);
            urunadapter.notifyDataSetChanged();



        }

    };




    //Firestore DB
       /*
        CollectionReference cRef = (CollectionReference) db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Ürünler");

        DocumentReference dRef = db
                .collection("Kullanıcılar")
                .document(mUser.getUid())
                .collection("Ürünler")
                .document();


        cRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore hatası!", error.getMessage());
                            return;
                        }


                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                urunlerimList.add(dc.getDocument().toObject(UrunlerimModel.class));
                            }
                        }


                        urunadapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();


                    }
                });

        */


    private void urunEkleyeGit() {
        btnUrunEkle.setOnClickListener(view -> {
            Intent intent = new Intent(Urunlerim.this, UrunEkle.class);
            startActivity(intent);
        });
    }

    private void initComponents() {

        urunlerRecyclerView = findViewById(R.id.urunlerimRecyclerView);
        btnUrunEkle = (Button) findViewById(R.id.btnYeniUrunEkle);
        //depoLogoUrunlerim = findViewById(R.id.depoLogoUrunlerim);
    }


}
