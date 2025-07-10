package com.example.toko.model;

import java.io.Serializable;
import java.util.List;

public class Transaksi implements Serializable {
    private int id;
    private String nama_pelanggan;
    private String metode_pembayaran;
    private String total_harga;
    private String total_diskon;
    private String total_bayar;
    private String created_at;
    private List<TransaksiDetail> details;

    public int getId() { return id; }
    public String getNama_pelanggan() { return nama_pelanggan; }
    public String getMetode_pembayaran() { return metode_pembayaran; }
    public String getTotal_harga() { return total_harga; }
    public String getTotal_diskon() { return total_diskon; }
    public String getTotal_bayar() { return total_bayar; }
    public String getCreated_at() { return created_at; }
    public List<TransaksiDetail> getDetails() { return details; }
}
