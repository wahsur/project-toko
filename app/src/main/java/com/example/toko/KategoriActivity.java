package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.AddEditKategori;
import com.example.toko.model.Kategori;
import com.example.toko.response.ResponseKategori;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriActivity extends AppCompatActivity {
    ListView listView;
    Button btnTambah, btnBack;
    apiService api;
    List<Kategori> kategoriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        listView = findViewById(R.id.listViewKategori);
        btnTambah = findViewById(R.id.btnTambahKategori);
        btnBack = findViewById(R.id.btnBack);
        api = apiClient.getClient().create(apiService.class);

        btnTambah.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditKategori.class));
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(KategoriActivity.this, MainActivity.class));
            }
        });

        loadKategori();
    }

    private void loadKategori() {
        api.getKategoriList().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kategoriList = response.body().getData();

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            KategoriActivity.this,
                            android.R.layout.simple_list_item_1,
                            getKategoriNames(kategoriList));
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        Intent i = new Intent(KategoriActivity.this, AddEditKategori.class);
                        i.putExtra("kategori", kategoriList.get(position));
                        startActivity(i);
                    });
                } else {
                    Toast.makeText(KategoriActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(KategoriActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] getKategoriNames(List<Kategori> list) {
        String[] names = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            names[i] = list.get(i).getNamaKategori();
        }
        return names;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKategori(); // refresh data saat kembali ke activity ini
    }
}
