package com.example.toko.model;

import java.io.Serializable;

public class Barang implements Serializable {
    private int id;
    private String namaBarang;
    private int kategori_id;
    private int jumlah;
    private int harga;
    private Kategori kategori;

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public int getKategori_id() { return kategori_id; }
    public void setKategori_id(int kategori_id) { this.kategori_id = kategori_id; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public Kategori getKategori() { return kategori; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }
}
