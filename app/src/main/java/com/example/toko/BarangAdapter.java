package com.example.toko;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toko.R;
import com.example.toko.api.apiService;
import com.example.toko.response.ResponseBarang;
import com.example.toko.model.Barang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    private Context context;
    private List<Barang> barangList;
    private apiService api;

    public BarangAdapter(Context context, List<Barang> barangList, apiService api) {
        this.context = context;
        this.barangList = barangList;
        this.api = api;
    }

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BarangViewHolder holder, int position) {
        Barang barang = barangList.get(position);

        holder.tvNamaBarang.setText(barang.getNamaBarang());
        holder.tvKategori.setText("Kategori: " +
                (barang.getKategori() != null ? barang.getKategori().getNamaKategori() : "null"));
        holder.tvJumlah.setText("Jumlah: " + barang.getJumlah());
        holder.tvHarga.setText("Harga: Rp " + barang.getHarga());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditBarang.class);
            intent.putExtra("barang", barang);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition < barangList.size()) {
                Barang currentBarang = barangList.get(currentPosition);

                api.deleteBarang(currentBarang.getId()).enqueue(new Callback<ResponseBarang>() {
                    @Override
                    public void onResponse(Call<ResponseBarang> call, Response<ResponseBarang> response) {
                        if (response.isSuccessful()) {
                            barangList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            Toast.makeText(context, "Barang dihapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Gagal menghapus barang", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBarang> call, Throwable t) {
                        Toast.makeText(context, "Gagal hapus: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList != null ? barangList.size() : 0;
    }

    class BarangViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvKategori, tvJumlah, tvHarga;
        Button btnEdit, btnDelete;

        public BarangViewHolder(View itemView) {
            super(itemView);
            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);
            tvKategori = itemView.findViewById(R.id.tvKategori);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
