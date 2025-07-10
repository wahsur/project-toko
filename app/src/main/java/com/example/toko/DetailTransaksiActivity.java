package com.example.toko;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.toko.model.Transaksi;
import com.example.toko.model.TransaksiDetail;

public class DetailTransaksiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        Transaksi trx = (Transaksi) getIntent().getSerializableExtra("transaksi");

        TextView info = findViewById(R.id.txtDetailInfo);
        StringBuilder sb = new StringBuilder();

        sb.append("Pelanggan: ").append(trx.getNama_pelanggan()).append("\n");
        sb.append("Tanggal: ").append(trx.getCreated_at()).append("\n");
        sb.append("Metode: ").append(trx.getMetode_pembayaran()).append("\n");
        sb.append("Total Harga: Rp ").append(trx.getTotal_harga()).append("\n");
        sb.append("Diskon: Rp ").append(trx.getTotal_diskon()).append("\n");
        sb.append("Total Bayar: Rp ").append(trx.getTotal_bayar()).append("\n\n");
        sb.append("Barang Dibeli:\n");

        for (TransaksiDetail d : trx.getDetails()) {
            sb.append("- ").append(d.getBarang().getNamaBarang())
                    .append(" x").append(d.getJumlah())
                    .append(" (Rp ").append(d.getSubtotal()).append(")\n");
        }

        info.setText(sb.toString());
    }
}

