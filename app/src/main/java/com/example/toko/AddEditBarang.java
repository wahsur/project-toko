package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.example.toko.R;
import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.model.Barang;
import com.example.toko.model.Kategori;
import com.example.toko.response.ResponseKategori;
import com.example.toko.response.ResponseBarang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditBarang extends AppCompatActivity {

    EditText etNama, etJumlah, etHarga;
    Spinner spinnerKategori;
    Button btnSimpan, btnKembali;

    apiService api;

    Barang barangEdit;

    List<Kategori> kategoriList = new ArrayList<>();
    List<String> kategoriNames = new ArrayList<>();
    ArrayAdapter<String> adapterSpinner;
    int selectedKategoriId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_barang);

        etNama = findViewById(R.id.etNamaBarang);
        etJumlah = findViewById(R.id.etJumlah);
        etHarga = findViewById(R.id.etHarga);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnKembali = findViewById(R.id.btnBack);

        api = apiClient.getClient().create(apiService.class);

        adapterSpinner = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, kategoriNames);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterSpinner);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!kategoriList.isEmpty()) {
                    selectedKategoriId = kategoriList.get(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedKategoriId = -1;
            }
        });

        // Cek jika mode edit
        if (getIntent().hasExtra("barang")) {
            barangEdit = (Barang) getIntent().getSerializableExtra("barang");
            etNama.setText(barangEdit.getNamaBarang());
            etJumlah.setText(String.valueOf(barangEdit.getJumlah()));
            etHarga.setText(String.valueOf(barangEdit.getHarga()));
        }

        // Ambil data kategori
        loadKategoriFromApi();

        btnSimpan.setOnClickListener(v -> simpanBarang());
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditBarang.this, BarangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadKategoriFromApi() {
        api.getKategoriList().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kategoriList.clear();
                    kategoriNames.clear();

                    // Ambil data kategori dari response
                    kategoriList = response.body().getData();

                    // Isi kategoriNames dari kategoriList
                    for (Kategori k : kategoriList) {
                        kategoriNames.add(k.getNamaKategori());
                    }

                    adapterSpinner.notifyDataSetChanged();

                    // Jika sedang edit, set posisi spinner
                    if (barangEdit != null) {
                        for (int i = 0; i < kategoriList.size(); i++) {
                            if (kategoriList.get(i).getId() == barangEdit.getKategori_id()) {
                                spinnerKategori.setSelection(i);
                                break;
                            }
                        }
                    }

                } else {
                    Toast.makeText(AddEditBarang.this, "Gagal load kategori: kosong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(AddEditBarang.this, "Gagal load kategori: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void simpanBarang() {
        String nama = etNama.getText().toString().trim();
        String jumlahStr = etJumlah.getText().toString().trim();
        String hargaStr = etHarga.getText().toString().trim();

        if (nama.isEmpty() || jumlahStr.isEmpty() || hargaStr.isEmpty() || selectedKategoriId == -1) {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlah = Integer.parseInt(jumlahStr);
        int harga = Integer.parseInt(hargaStr);

        if (barangEdit == null) {
            // CREATE
            api.createBarang(nama, selectedKategoriId, jumlah, harga).enqueue(new Callback<ResponseBarang>() {
                @Override
                public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddEditBarang.this, "Berhasil tambah barang", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditBarang.this, "Gagal tambah barang", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBarang> call, Throwable t) {
                    Toast.makeText(AddEditBarang.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // UPDATE
            api.updateBarang(barangEdit.getId(), nama, selectedKategoriId, jumlah, harga).enqueue(new Callback<ResponseBarang>() {
                @Override
                public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddEditBarang.this, "Berhasil update barang", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditBarang.this, "Gagal update barang", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBarang> call, Throwable t) {
                    Toast.makeText(AddEditBarang.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
