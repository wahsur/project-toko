package com.example.toko.response;

import com.example.toko.model.Barang;

import java.util.List;

public class ResponseBarang {
    private String message;
    private List<Barang> data;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<Barang> getData() { return data; }
    public void setData(List<Barang> data) { this.data = data; }
}
