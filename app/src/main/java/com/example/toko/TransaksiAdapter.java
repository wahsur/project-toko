package com.example.toko;

import android.content.Context;
import android.view.*;
import android.widget.*;

import com.example.toko.R;
import com.example.toko.model.Transaksi;

import java.util.List;

public class TransaksiAdapter extends BaseAdapter {
    private Context context;
    private List<Transaksi> list;

    public TransaksiAdapter(Context context, List<Transaksi> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() { return list.size(); }
    public Object getItem(int i) { return list.get(i); }
    public long getItemId(int i) { return list.get(i).getId(); }

    public View getView(int i, View view, ViewGroup parent) {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_transaksi, parent, false);

        Transaksi trx = list.get(i);

        ((TextView) view.findViewById(R.id.txtNamaPelanggan)).setText(trx.getNama_pelanggan());
        ((TextView) view.findViewById(R.id.txtTotal)).setText("Total: Rp " + trx.getTotal_bayar());
        ((TextView) view.findViewById(R.id.txtMetode)).setText("Metode: " + trx.getMetode_pembayaran());

        return view;
    }
}

