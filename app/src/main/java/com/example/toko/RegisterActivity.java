package com.example.toko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toko.api.apiClient;
import com.example.toko.api.apiService;
import com.example.toko.response.ResponseRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNamaToko, etUsername, etPassword;
    Button btnRegister;
    TextView tvLogin;
    apiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaToko = findViewById(R.id.etNamaToko);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        api = apiClient.getClient().create(apiService.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void registerUser() {
        String namaToko = etNamaToko.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (namaToko.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<ResponseRegister> call = api.register(namaToko, username, password);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Register berhasil, silakan login", Toast.LENGTH_SHORT).show();
                    // Kembali ke LoginActivity
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Register gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
