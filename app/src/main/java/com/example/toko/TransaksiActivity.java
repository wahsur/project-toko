    package com.example.toko;

    import android.app.Activity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.toko.api.apiClient;
    import com.example.toko.api.apiService;
    import com.example.toko.model.Barang;
    import com.example.toko.model.Kategori;
    import com.example.toko.response.ResponseBarang;
    import com.example.toko.response.ResponseKategori;

    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class TransaksiActivity extends Activity {

        private EditText edtNamaPelanggan, edtJumlah, edtDiskon;
        private Spinner spinnerKategori, spinnerBarang;
        private RadioGroup radioGroupPembayaran;
        private RadioButton radioCash, radioQris;
        private TextView txtTotal;
        private Button btnHitung, btnSimpan;

        private List<Kategori> kategoriList = new ArrayList<>();
        private List<Barang> barangList = new ArrayList<>();
        private List<Map<String, Object>> daftarBarang = new ArrayList<>();
        private Barang selectedBarang;

        private int totalHarga = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transaksi);

            edtNamaPelanggan = findViewById(R.id.edtNamaPelanggan);
            edtJumlah = findViewById(R.id.edtJumlah);
            edtDiskon = findViewById(R.id.edtDiskon);
            spinnerKategori = findViewById(R.id.spinnerKategori);
            spinnerBarang = findViewById(R.id.spinnerBarang);
            radioGroupPembayaran = findViewById(R.id.radioGrupPembayaran);
            radioCash = findViewById(R.id.rbCash);
            radioQris = findViewById(R.id.rbQRIS);
            txtTotal = findViewById(R.id.txtHasilTransaksi);
            btnHitung = findViewById(R.id.btnHitung);
            btnSimpan = findViewById(R.id.btnSimpan);

            loadKategori();

            spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Kategori selectedKategori = kategoriList.get(position);
                    loadBarangByKategori(selectedKategori.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            spinnerBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedBarang = barangList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            btnHitung.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBarang == null) {
                        Toast.makeText(TransaksiActivity.this, "Pilih barang terlebih dahulu", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String jumlahStr = edtJumlah.getText().toString();
                    if (jumlahStr.isEmpty()) {
                        Toast.makeText(TransaksiActivity.this, "Masukkan jumlah", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int jumlah = Integer.parseInt(jumlahStr);
                    int diskon = edtDiskon.getText().toString().isEmpty() ? 0 : Integer.parseInt(edtDiskon.getText().toString());

                    int harga = selectedBarang.getHarga();
                    double subtotal = harga * jumlah * (1 - (diskon / 100.0));

                    // Tambah ke total keseluruhan
                    totalHarga += (int) subtotal;

                    // Simpan barang yang dihitung
                    Map<String, Object> barangMap = new HashMap<>();
                    barangMap.put("barang_id", selectedBarang.getId());
                    barangMap.put("jumlah", jumlah);
                    barangMap.put("diskon", diskon);
                    daftarBarang.add(barangMap);

                    // Tampilkan total
                    txtTotal.setText("Total: Rp" + totalHarga);
                    Toast.makeText(TransaksiActivity.this, "Barang ditambahkan ke transaksi", Toast.LENGTH_SHORT).show();

                    // Reset input (opsional)
                    edtJumlah.setText("");
                    edtDiskon.setText("");
                }
            });


            btnSimpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    simpanTransaksi();
                }
            });
        }

        private void loadKategori() {
            apiService api = apiClient.getClient().create(apiService.class);
            api.getKategori().enqueue(new Callback<ResponseKategori>() {
                @Override
                public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        kategoriList = response.body().getData();
                        List<String> namaKategori = new ArrayList<>();
                        for (Kategori k : kategoriList) {
                            namaKategori.add(k.getNamaKategori());
                        }
                        spinnerKategori.setAdapter(new ArrayAdapter<>(TransaksiActivity.this, android.R.layout.simple_spinner_dropdown_item, namaKategori));
                    }
                }

                @Override
                public void onFailure(Call<ResponseKategori> call, Throwable t) {
                    Toast.makeText(TransaksiActivity.this, "Gagal memuat kategori", Toast.LENGTH_SHORT).show();
                }
            });
        }


        private void loadBarangByKategori(int kategoriId) {
            apiService api = apiClient.getClient().create(apiService.class);
            api.getBarangByKategori(kategoriId).enqueue(new Callback<ResponseBarang>() {
                @Override
                public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        barangList = response.body().getData();
                        List<String> namaBarang = new ArrayList<>();
                        for (Barang b : barangList) {
                            namaBarang.add(b.getNamaBarang());
                        }
                        spinnerBarang.setAdapter(new ArrayAdapter<>(TransaksiActivity.this, android.R.layout.simple_spinner_dropdown_item, namaBarang));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBarang> call, Throwable t) {
                    Toast.makeText(TransaksiActivity.this, "Gagal memuat barang", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void simpanTransaksi() {
            String namaPelanggan = edtNamaPelanggan.getText().toString();
            int selectedId = radioGroupPembayaran.getCheckedRadioButtonId();
            String metode = selectedId == R.id.rbCash ? "cash" : "qris";

            if (namaPelanggan.isEmpty()) {
                Toast.makeText(this, "Nama pelanggan wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (daftarBarang.isEmpty()) {
                Toast.makeText(this, "Belum ada barang yang ditambahkan ke transaksi", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("nama_pelanggan", namaPelanggan);
            data.put("metode_pembayaran", metode);
            data.put("barang", daftarBarang); // kirim semua barang yang ditambahkan sebelumnya

            apiService api = apiClient.getClient().create(apiService.class);
            Call<ResponseBody> call = api.simpanTransaksi(data);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(TransaksiActivity.this, "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show();
                        // reset data jika ingin transaksi baru
                        daftarBarang.clear();
                        totalHarga = 0;
                        txtTotal.setText("Total: Rp0");
                        finish(); // atau kosongkan input saja
                    } else {
                        Toast.makeText(TransaksiActivity.this, "Gagal menyimpan transaksi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TransaksiActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
