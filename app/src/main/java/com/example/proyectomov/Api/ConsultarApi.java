package com.example.proyectomov.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConsultarApi {
    public static String URL = "https://pokeapi.co/api/v2/";
    public  static Retrofit retro;
    public ModeloDevuelve modeloDevuelve = new ModeloDevuelve();
    public void getRetrofitClient(String id) {
        retro = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServiceInterface consultas = retro.create(ApiServiceInterface.class);
        Call<ModeloPoke> call = consultas.consultar(id);
        call.enqueue(new Callback<ModeloPoke>() {
            @Override
            public void onResponse(Call<ModeloPoke> call, Response<ModeloPoke> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ModeloPoke modeloPoke = response.body();
                        modeloDevuelve.setId(modeloPoke.getId());
                        modeloDevuelve.setName(modeloPoke.getName());
                        modeloDevuelve.setHeight(modeloPoke.getHeight());
                        modeloDevuelve.setWeight(modeloPoke.getWeight());
                        modeloDevuelve.setFront_default(modeloPoke.getSprites().getFront_default());

                    } else {
                        System.out.println("Error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ModeloPoke> call, Throwable t) {
                System.out.println("ERROR " + t);
            }
        });

    }


}
