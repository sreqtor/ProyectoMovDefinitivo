package com.example.proyectomov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomov.Api.ConsultarApi;
import com.example.proyectomov.Api.ModeloDevuelve;
import com.squareup.picasso.Picasso;

public class SettingsFragment extends Fragment {

    private RecyclerView recyclerView;

    public Button btnBuscar;
    public EditText txtBusqueda;

    private TextView txtDevolver;

    private ImageView imageViewPokemon;

    private ModeloDevuelve pokedex = new ModeloDevuelve();

    private String respuesta = "", imagen = "";

    //View rootView;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        btnBuscar = rootView.findViewById(R.id.buttonSearchApi);
        txtBusqueda = rootView.findViewById(R.id.txtApi);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Buscando...", Toast.LENGTH_SHORT).show();
                ConsultarApi res = new ConsultarApi();
                try {
                    res.getRetrofitClient(txtBusqueda.getText().toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pokedex = res.modeloDevuelve;
                            if (res.modeloDevuelve == null) {
                                respuesta = "ERROR";
                            } else {
                                respuesta = "ID: " + pokedex.getId() + "\n" +
                                        "Nombre: " + pokedex.getName() + "\n" +
                                        "Altura: " + pokedex.getHeight() + "\n" +
                                        "Peso: " + pokedex.getWeight();
                                imagen = pokedex.getFront_default();
                            }

                            txtDevolver = rootView.findViewById(R.id.txtDevolver);
                            txtDevolver.setText(respuesta);

                            imageViewPokemon = rootView.findViewById(R.id.imageViewPokemon);
                            if (imagen != null && !imagen.isEmpty()) {
                                Picasso.get().load(imagen).into(imageViewPokemon);
                            } else {
                                imageViewPokemon.setImageResource(R.drawable.logo_up);
                            }


                        }
                    }, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

}