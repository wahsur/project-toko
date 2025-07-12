package com.example.toko;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.response.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText etNamaToko, etUsername, etPassword;
    Button btnUpdate, btnKembali, btnLogout;
    apiService api;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etNamaToko = findViewById(R.id.etNamaToko);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnKembali = findViewById(R.id.btnKembali);
        btnLogout = findViewById(R.id.btnLogout);

        prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("id", -1);
        String namaToko = prefs.getString("namaToko", "");
        String username = prefs.getString("username", "");

        etNamaToko.setText(namaToko);
        etUsername.setText(username);

        api = apiClient.getClient().create(apiService.class);

        btnUpdate.setOnClickListener(v -> {
            String nama = etNamaToko.getText().toString().trim();
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            api.updateUser(userId, nama, user, pass).enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    if (response.isSuccessful() && response.body().isSuccess()) {
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putString("namaToko", nama);
                        edit.putString("username", user);
                        edit.apply();

                        Toast.makeText(EditProfileActivity.this, "Profil diperbarui", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Gagal update", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnKembali.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(EditProfileActivity.this)
                    .setTitle("Konfirmasi Logout")
                    .setMessage("Apakah Anda yakin ingin logout?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        // Hapus SharedPreferences
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();

                        // Pindah ke LoginActivity dan hapus aktivitas sebelumnya
                        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Tidak", null) // Tidak melakukan apa-apa jika memilih "Tidak"
                    .show();
        });
    }
}
