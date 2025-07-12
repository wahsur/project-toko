package com.example.toko.response;

import com.example.toko.model.Transaksi;

import java.util.List;

public class   ResponseTransaksi {
    private boolean success;
    private List<Transaksi> data;

    public boolean isSuccess() { return success; }
    public List<Transaksi> getData() { return data; }
}

