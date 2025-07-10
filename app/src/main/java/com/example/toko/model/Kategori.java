package com.example.toko.model;

import java.io.Serializable;

public class Kategori implements Serializable {
    private int id;
    private String namaKategori;

    public int getId() {
        return id;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
