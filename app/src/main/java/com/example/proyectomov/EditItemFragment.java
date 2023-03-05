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

    /*private MovieRealm item;
    private EditText title;
    private EditText maker;
    private EditText description;
    private ImageView photo;
    private Bitmap image;
    private Uri selectedImage;
    private int REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private VentInicial context;*/

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

    //----------------

    /*
    public EditItemFragment(MovieRealm item) {
        this.item = item;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        this.context = (VentInicial) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_item, container, false);
        title = (EditText) rootView.findViewById(R.id.title);
        title.setText(item.getTitulo());
        maker = (EditText) rootView.findViewById(R.id.maker);
        maker.setText(item.getDirector());
        description = (EditText) rootView.findViewById(R.id.description);
        description.setText(item.getGenero());
        photo = (ImageView) rootView.findViewById(R.id.image_view);
        //image = item.getImage_base64();
        //photo.setImageBitmap(image);
        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(view);
            }
        });

        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelItem(view);
            }
        });

        ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Bundle extras = result.getData().getExtras();
                    image = (Bitmap) extras.get("data");
                    photo.setImageBitmap(image);
                }
            }
        });

        ImageButton addGalleryPhotoImageButton = (ImageButton) rootView.findViewById(R.id.add_gallery_image_button);
        addGalleryPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent, 3);
            }
        });



        ImageButton addPhotoImageButton = (ImageButton) rootView.findViewById(R.id.add_image_button);
        addPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });

        ImageButton deletePhotoImageButton = (ImageButton) rootView.findViewById(R.id.cancel_image_button);
        deletePhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { deletePhoto(view); }
        });

        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(rootView);
        return alertDialogBuilder.create();
        return rootView;
    }*/

    /*@Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }*/

    /*
    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(R.drawable.logo_up);
    }

    public void saveItem (View view) {
        String title_str = title.getText().toString();
        String maker_str = maker.getText().toString();
        String description_str = description.getText().toString();

        if (title_str.equals("")) {
            title.setError("Campo vacío");
            return;
        }

        if (maker_str.equals("")) {
            maker.setError("Campo vacío");
            return;
        }

        if (description_str.equals("")) {
            description.setError("Campo vacío");
            return;
        }

        realm.beginTransaction();
        MovieRealm movieEdit = realm.where(MovieRealm.class)
                .findFirst();
        if (movieEdit != null) {
            movieEdit.setTitulo(title_str);
            movieEdit.setDirector(maker_str);
            movieEdit.setGenero(description_str);
            Toast.makeText(context, "Película actualizada", Toast.LENGTH_SHORT).show();
        }

        /*try {
            Item newItem = new Item(title_str, maker_str, description_str, image, null );
            ItemListSingleton itemList = ItemListSingleton.getInstance();
            int itemIndex = itemList.getItemList().getIndex(item);
            itemList.getItemList().editItem(newItem, itemIndex);
            itemList.getItemList().saveItems(context);
            RecyclerView recycler = (RecyclerView) context.findViewById(R.id.itemList);
            recycler.getAdapter().notifyItemChanged(itemIndex);
            ResetFragmentFields();
            dismiss();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public final static void updateMovieByTit(MovieRealm movie){
        Realm realm = Realm.getDefaultInstance();
        MovieRealm rMovie = realm.where(MovieRealm.class).equalTo("id", id).findFirst();
        rMovie.setTitulo(movie.getTitulo());
        rMovie.setDirector(movie.getDirector());
        rMovie.setGenero(movie.getGenero());
        realm.insertOrUpdate(rMovie);
        realm.commitTransaction();
    }*/

    /*
    private void ResetFragmentFields() {
        title.setText("");
        maker.setText("");
        description.setText("");
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public void cancelItem(View view) {
        dismiss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            try {
                photo.setImageURI(selectedImage);
                image = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}