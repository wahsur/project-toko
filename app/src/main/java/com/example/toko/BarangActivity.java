package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.widget.Button;
import android.widget.Toast;

import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.AddEditBarang;
import com.example.toko.model.Barang;
import com.example.toko.BarangAdapter;
import com.example.toko.response.ResponseBarang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {

    RecyclerView rvBarang;
    Button btnTambahBarang, btnback;
    apiService api;
    BarangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        rvBarang = findViewById(R.id.rvBarang);
        btnTambahBarang = findViewById(R.id.btnTambahBarang);
        btnback = findViewById(R.id.btnBack);

        api = apiClient.getClient().create(apiService.class);

        rvBarang.setLayoutManager(new LinearLayoutManager(this));

        loadBarang();

        btnTambahBarang.setOnClickListener(v -> {
            Intent intent = new Intent(BarangActivity.this, AddEditBarang.class);
            startActivity(intent);
        });

        btnback.setOnClickListener(v -> {
            startActivity(new Intent(BarangActivity.this, MainActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBarang();
    }

    private void loadBarang() {
        api.getBarangList().enqueue(new Callback<ResponseBarang>() {
            @Override
            public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Barang> barangList = response.body().getData();
                    adapter = new BarangAdapter(BarangActivity.this, barangList, api);
                    rvBarang.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseBarang> call, Throwable t) {
                Toast.makeText(BarangActivity.this, "Gagal mengambil data: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
