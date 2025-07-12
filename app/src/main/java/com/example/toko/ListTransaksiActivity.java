package com.example.toko;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.model.Transaksi;
import com.example.toko.TransaksiAdapter;
import com.example.toko.response.ResponseTransaksi;

import retrofit2.*;

import java.util.List;

public class ListTransaksiActivity extends Activity {

    ListView listView;
    Button btnBack;
    List<Transaksi> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi);

        listView = findViewById(R.id.listViewTransaksi);
        btnBack = findViewById(R.id.btnBack);

        apiService api = apiClient.getClient().create(apiService.class);
        api.getTransaksi().enqueue(new Callback<ResponseTransaksi>() {
            @Override
            public void onResponse(Call<ResponseTransaksi> call, Response<ResponseTransaksi> response) {
                if (response.isSuccessful() && response.body(). isSuccess()) {
                    list = response.body().getData();
                    TransaksiAdapter adapter = new TransaksiAdapter(ListTransaksiActivity.this, list);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        Transaksi selected = list.get(position);
                        Intent intent = new Intent(ListTransaksiActivity.this, DetailTransaksiActivity.class);
                        intent.putExtra("transaksi", selected);
                        startActivity(intent);
                    });
                } else {
                    Toast.makeText(ListTransaksiActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTransaksi> call, Throwable t) {
                Toast.makeText(ListTransaksiActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListTransaksiActivity.this, MainActivity.class));
            }
        });
    }
}

