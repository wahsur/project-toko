package com.example.toko.response;

public class ResponseRegister {
    private String message;
    private User user;

    // Getter & Setter
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

    // Nested class untuk user
    public static class User {
        private int id;
        private String namaToko;
        private String username;
        private String password; // biasanya jangan simpan password di response
        private String created_at;
        private String updated_at;

        // Getter & Setter untuk semua atribut
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

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreated_at() {
            return created_at;
        }
        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
