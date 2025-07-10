package com.example.toko.api;

import com.example.toko.response.ResponseBarang;
import com.example.toko.response.ResponseKategori;
import com.example.toko.response.ResponseLogin;
import com.example.toko.response.ResponseRegister;
import com.example.toko.response.ResponseUser;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface apiService {

    //    Login dan Register
    @FormUrlEncoded
    @POST("register")
    Call<ResponseRegister> register(
            @Field("namaToko") String namaToko,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login(
            @Field("username") String username,
            @Field("password") String password
    );

    //    Barang
    @GET("barang")
    Call<ResponseBarang> getBarangList();

    @FormUrlEncoded
    @POST("barang")
    Call<ResponseBarang> createBarang(
            @Field("namaBarang") String namaBarang,
            @Field("kategori_id") int kategoriId,
            @Field("jumlah") int jumlah,
            @Field("harga") int harga
    );

    @FormUrlEncoded
    @PUT("barang/{id}")
    Call<ResponseBarang> updateBarang(
            @Path("id") int id,
            @Field("namaBarang") String namaBarang,
            @Field("kategori_id") int kategoriId,
            @Field("jumlah") int jumlah,
            @Field("harga") int harga
    );

    @DELETE("barang/{id}")
    Call<ResponseBarang> deleteBarang(@Path("id") int id);

    //    Kategori
    @GET("kategori")
    Call<ResponseKategori> getKategoriList();

    @FormUrlEncoded
    @POST("kategori")
    Call<ResponseKategori> createKategori(@Field("namaKategori") String namaKategori);

    @FormUrlEncoded
    @PUT("kategori/{id}")
    Call<ResponseKategori> updateKategori(@Path("id") int id, @Field("namaKategori") String namaKategori);

    @DELETE("kategori/{id}")
    Call<ResponseKategori> deleteKategori(@Path("id") int id);

    //    Profile
    @FormUrlEncoded
    @POST("user/update/{id}")
    Call<ResponseUser> updateUser(
            @Path("id") int id,
            @Field("namaToko") String namaToko,
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("user/{id}")
    Call<ResponseUser> getUser(@Path("id") int id);

    //    Transaksi
    // Ambil semua kategori
    @GET("kategori")
    Call<ResponseKategori> getKategori();


    // Ambil barang berdasarkan ID kategori
    @GET("barang/kategori/{id}")
    Call<ResponseBarang> getBarangByKategori(@Path("id") int kategoriId);

    // Simpan transaksi ke server
    @POST("transaksi")
    Call<ResponseBody> simpanTransaksi(@Body Map<String, Object> data);


}
