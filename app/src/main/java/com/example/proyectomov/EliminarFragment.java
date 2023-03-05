package com.example.proyectomov;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectomov.Realm.MovieRealm;

import io.realm.Realm;
import io.realm.RealmResults;

public class EliminarFragment extends Fragment {
    private static final String ARG1 = "p1";
    private static final String ARG2 = "p2";
    private String mP1;
    private String mP2;

    View rootView;

    Realm realm;

    public EliminarFragment() {

    }

    public static EliminarFragment newInstance(String par1, String par2) {
        EliminarFragment fragment = new EliminarFragment();
        Bundle args = new Bundle();
        args.putString(ARG1, par1);
        args.putString(ARG2, par2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mP1 = getArguments().getString(ARG1);
            mP2 = getArguments().getString(ARG2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_eliminar, container, false);
        realm = Realm.getDefaultInstance();
        Button delMovieBtn = rootView.findViewById(R.id.buttonDel);
        delMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delMovie();
            }
        });

        return rootView;
    }

    private void delMovie() {
        EditText tituloEditText = rootView.findViewById(R.id.titleDel);
        realm.beginTransaction();
        RealmResults<MovieRealm> movieEliminar = realm.where(MovieRealm.class).equalTo("titulo", tituloEditText.getText().toString()).findAll();
        movieEliminar.deleteAllFromRealm();
        Toast.makeText(getActivity(), "Película eliminada", Toast.LENGTH_SHORT).show();
        realm.commitTransaction();
    }

}