package com.example.toko.model;

import java.io.Serializable;

public class TransaksiDetail implements Serializable {
    private int id;
    private int jumlah;
    private String harga_satuan;
    private String diskon;
    private String subtotal;
    private Barang barang;

    public int getId() { return id; }
    public int getJumlah() { return jumlah; }
    public String getHarga_satuan() { return harga_satuan; }
    public String getDiskon() { return diskon; }
    public String getSubtotal() { return subtotal; }
    public Barang getBarang() { return barang; }
}
