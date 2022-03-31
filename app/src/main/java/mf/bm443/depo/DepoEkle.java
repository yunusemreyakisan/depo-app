package mf.bm443.depo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepoEkle extends AppCompatActivity {

    private Button depolarimaEkle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depo_ekle);

        initComponents();
        btndepolarimaEkle();
    }

    private void btndepolarimaEkle() {
        depolarimaEkle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(DepoEkle.this, Depolarim.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        depolarimaEkle = findViewById(R.id.btnDepolarimaEkle);
    }
}