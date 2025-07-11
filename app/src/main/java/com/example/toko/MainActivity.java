package com.example.toko;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvNamaToko;
    LinearLayout btnBarang, btnKategori, btnTransaksi, btnRiwayat;
    ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNamaToko = findViewById(R.id.tvNamaToko);
        btnBarang = findViewById(R.id.btnBarang);
        btnKategori = findViewById(R.id.btnKategori);
        btnTransaksi = findViewById(R.id.btnTransaksi);
        btnRiwayat = findViewById(R.id.btnRiwayat);
        ivProfile = findViewById(R.id.ivProfile);

        // Klik ikon profil → buka edit profil
        ivProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        updateNamaToko();

        btnBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BarangActivity.class));
            }
        });

        btnKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        });

        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
                startActivity(intent);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListTransaksiActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNamaToko(); // saat kembali dari edit profil
    }

    private void updateNamaToko() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String namaToko = prefs.getString("namaToko", "Nama Toko");
        tvNamaToko.setText("Selamat datang di " + namaToko);
    }
}
