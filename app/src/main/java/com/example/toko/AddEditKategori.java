package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.example.toko.R;
import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.model.Kategori;
import com.example.toko.response.ResponseKategori;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditKategori extends AppCompatActivity {

    EditText etNamaKategori;
    Button btnSimpan, btnHapus, btnBack;
    apiService api;
    Kategori kategoriEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_kategori);

        etNamaKategori = findViewById(R.id.etNamaKategori);
        btnSimpan = findViewById(R.id.btnSimpanKategori);
        btnHapus = findViewById(R.id.btnHapusKategori);
        btnBack = findViewById(R.id.btnBack);
        api = apiClient.getClient().create(apiService.class);

        if (getIntent().hasExtra("kategori")) {
            kategoriEdit = (Kategori) getIntent().getSerializableExtra("kategori");
            etNamaKategori.setText(kategoriEdit.getNamaKategori());
            btnHapus.setVisibility(View.VISIBLE);
        } else {
            btnHapus.setVisibility(View.GONE);
        }

        btnSimpan.setOnClickListener(v -> simpanKategori());
        btnHapus.setOnClickListener(v -> hapusKategori());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEditKategori.this, KategoriActivity.class));
            }
        });
    }

    private void simpanKategori() {
        String nama = etNamaKategori.getText().toString().trim();
        if (nama.isEmpty()) {
            Toast.makeText(this, "Isi nama kategori", Toast.LENGTH_SHORT).show();
            return;
        }

        if (kategoriEdit == null) {
            api.createKategori(nama).enqueue(new Callback<ResponseKategori>() {
                @Override
                public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                    Toast.makeText(AddEditKategori.this, "Berhasil tambah", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseKategori> call, Throwable t) {
                    Toast.makeText(AddEditKategori.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            api.updateKategori(kategoriEdit.getId(), nama).enqueue(new Callback<ResponseKategori>() {
                @Override
                public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                    Toast.makeText(AddEditKategori.this, "Berhasil update", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseKategori> call, Throwable t) {
                    Toast.makeText(AddEditKategori.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void hapusKategori() {
        api.deleteKategori(kategoriEdit.getId()).enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                Toast.makeText(AddEditKategori.this, "Berhasil hapus", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(AddEditKategori.this, "Gagal hapus: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
