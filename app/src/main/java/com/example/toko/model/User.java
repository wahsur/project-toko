package com.example.toko.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String namaToko;
    private String username;

    public int getId() { return id; }
    public String getNamaToko() { return namaToko; }
    public String getUsername() { return username; }
}
