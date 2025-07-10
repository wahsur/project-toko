package com.example.toko.response;

import com.example.toko.model.Kategori;
import java.util.List;

public class ResponseKategori {
    private String message;
    private List<Kategori> data;

    public String getMessage() {
        return message;
    }

    public List<Kategori> getData() {
        return data;
    }
}
