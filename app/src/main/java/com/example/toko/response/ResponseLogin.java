package com.example.toko.response;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    // Constructor kosong (optional)
    public ResponseLogin() {}

    // Getter dan Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Inner class User sesuai response JSON user
    public static class User {
        @SerializedName("id")
        private int id;

        @SerializedName("namaToko")
        private String namaToko;

        @SerializedName("username")
        private String username;

        // Constructor kosong (optional)
        public User() {}

        // Getter dan Setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNamaToko() {
            return namaToko;
        }

        public void setNamaToko(String namaToko) {
            this.namaToko = namaToko;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
