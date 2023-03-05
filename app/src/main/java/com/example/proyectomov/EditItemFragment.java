package com.example.proyectomov;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomov.Realm.MovieRealm;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditItemFragment extends DialogFragment {

    private static final String ARG1 = "p1";
    private static final String ARG2 = "p2";
    private String mP1;
    private String mP2;

    View rootView;

    Realm realm;

    public EditItemFragment() {

    }

    public static EditItemFragment newInstance(String par1, String par2) {
        EditItemFragment editItemFragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG1, par1);
        args.putString(ARG2, par2);
        editItemFragment.setArguments(args);
        return editItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            mP1 = getArguments().getString(ARG1);
            mP2 = getArguments().getString(ARG2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_item, container, false);
        realm = Realm.getDefaultInstance();
        Button guardar = rootView.findViewById(R.id.save_button);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });
        return rootView;
    }

    private void actualizar() {
        EditText titEditText = rootView.findViewById(R.id.title);
        EditText titEditText2 = rootView.findViewById(R.id.title2);
        EditText directorEditText = rootView.findViewById(R.id.maker);
        EditText generoEditText = rootView.findViewById(R.id.description);
        realm.beginTransaction();
        MovieRealm mEdit = realm.where(MovieRealm.class).equalTo("titulo", titEditText.getText().toString()).findFirst();

        if (mEdit != null) {
            mEdit.setTitulo(titEditText2.getText().toString());
            mEdit.setDirector(directorEditText.getText().toString());
            mEdit.setGenero(generoEditText.getText().toString());
            Toast.makeText(getActivity(), "Película actualizada", Toast.LENGTH_SHORT).show();
        }
        realm.commitTransaction();
    }

}