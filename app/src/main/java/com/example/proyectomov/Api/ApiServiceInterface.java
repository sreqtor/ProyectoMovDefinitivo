package com.example.proyectomov.Api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiServiceInterface {
    @GET("pokemon/{id}")
    Call<ModeloPoke> consultar(@Path("id") String id);
}
