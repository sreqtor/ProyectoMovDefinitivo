package com.example.proyectomov;

import static android.app.Activity.RESULT_OK;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectomov.Realm.MovieRealm;
import com.example.proyectomov.Realm.RealmList;
import com.example.proyectomov.Realm.RealmListSingleton;

import java.io.IOException;
import java.util.UUID;

public class AddItemFragment extends Fragment {
    //private String id= UUID.randomUUID().toString();
    private static Realm myRealm;
    private String id;
    private int cont=0;
    private EditText title;
    private EditText maker;
    private EditText description;
    private ImageView photo;

    private Button saveButton;
    private String image;
    //private Bitmap image;
    private Uri selectedImage;
    private int REQUEST_CODE = 1;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private VentInicial context;


    View rootView;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = (VentInicial) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //myRealm = Realm.getDefaultInstance();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myRealm = Realm.getDefaultInstance();


        rootView = inflater.inflate(R.layout.fragment_add_item, container, false);
        title = (EditText) rootView.findViewById(R.id.title);
        maker = (EditText) rootView.findViewById(R.id.maker);
        description = (EditText) rootView.findViewById(R.id.description);
        photo = (ImageView) rootView.findViewById(R.id.image_view);
        photo.setImageResource(R.drawable.logo_up);
        saveButton = (Button) rootView.findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title_str = title.getText().toString();
                String maker_str = maker.getText().toString();
                String description_str = description.getText().toString();

                if (title_str.equals("")) {
                    title.setError("Obligatorio");
                    return;
                }

                if (maker_str.equals("")) {
                    maker.setError("Obligatorio");
                    return;
                }

                if (description_str.equals("")) {
                    description.setError("Obligatorio");
                    return;
                }

                //addMovieToDB(title_str, maker_str, description_str);
                Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                ResetFragmentFields();


                myRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        cont++;
                        id = "id"+cont;

                        MovieRealm myMovie = myRealm.createObject(MovieRealm.class, RealmList.calculateIndex());


                        myMovie.setTitulo(title_str);
                        myMovie.setDirector(maker_str);
                        myMovie.setGenero(description_str);
                        /*myMovie.setTitulo(title.getText().toString());
                        myMovie.setDirector(maker.getText().toString());
                        myMovie.setGenero(description.getText().toString());*/

                    }
                });

            }
        });
        /*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saveItem(view);
                String title_str = title.getText().toString();
                String maker_str = maker.getText().toString();
                String description_str = description.getText().toString();

                if (title_str.equals("")) {
                    title.setError("Obligatorio");
                    return;
                }

                if (maker_str.equals("")) {
                    maker.setError("Obligatorio");
                    return;
                }

                if (description_str.equals("")) {
                    description.setError("Obligatorio");
                    return;
                }

                addMovieToDB(title_str, maker_str, description_str);
                Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
                ResetFragmentFields();

            }
        });*/


        ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Bundle extras = result.getData().getExtras();
                    image = extras.get("data").toString();
                    byte [] encodeByte = Base64.decode(image,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    photo.setImageBitmap(bitmap);
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



        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            try {
                photo.setImageURI(selectedImage);
                image = String.valueOf(MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void deletePhoto(View view) {
        image = null;
        photo.setImageResource(R.drawable.logo_up);
    }

    public static void listar(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmTransaction) {
                RealmResults<MovieRealm> movies = myRealm.where(MovieRealm.class).findAll();
                for (MovieRealm movie: movies){
                    movie.getTitulo();
                    movie.getDirector();
                    movie.getGenero();
                }


                //MovieRealm movieRealm = realmTransaction.createObject(MovieRealm.class, title_txt);
                //movieRealm.setTitulo(title_txt);
                //movieRealm.setDirector(director_txt);
                //movieRealm.setGenero(genero_txt);
                //movieRealm.setImage_base64(imagen_img);
            }
        });
    }

    public void saveItem (View view) {
        String title_str = title.getText().toString();
        String maker_str = maker.getText().toString();
        String description_str = description.getText().toString();

        if (title_str.equals("")) {
            title.setError("Obligatorio");
            return;
        }

        if (maker_str.equals("")) {
            maker.setError("Obligatorio");
            return;
        }

        if (description_str.equals("")) {
            description.setError("Obligatorio");
            return;
        }

        try {
            //Item item = new Item(title_str, maker_str, description_str, image, null );
            //ItemListSingleton itemList = ItemListSingleton.getInstance();
            //itemList.getItemList().addItem(item);
            //itemList.getItemList().saveItems(context);
            //ResetFragmentFields();

            //MovieRealm movieRealm = new MovieRealm(id, title_str, maker_str, description_str, image);
            //RealmListSingleton realmList = RealmListSingleton.getInstance();

            //MovieRealm mr = new MovieRealm(); //de momento de sobrescribe cada vez que guardas (prueba)
            MovieRealm movie = new MovieRealm(title_str, maker_str, description_str, image);
            Realm realm = Realm.getDefaultInstance();
            realm.copyToRealm(movie);
            Toast.makeText(context, "Añadido con exito", Toast.LENGTH_SHORT).show();
            ResetFragmentFields();

            /*mr.setId(id);
            mr.setTitulo(title_str); //probar a pasar los campos a mano
            mr.setDirector(maker_str);
            mr.setGenero(description_str);
            ResetFragmentFields();*/
            //movie.setId(id);
            /*movie.setTitulo(title_str); //probar a pasar los campos a mano
            movie.setDirector(maker_str);
            movie.setGenero(description_str);
            ResetFragmentFields();*/
            //RealmList.addMovie(title_str, maker_str, description_str);
            //RealmList.aniadir(mr.getId(), mr.getTitulo(), mr.getDirector(), mr.getGenero(), mr.getImage_base64());
            /*realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(mr);
                    //RealmList.aniadir(id, title_str, maker_str, description_str, image);
                }
            });*/
            //https://www.geeksforgeeks.org/how-to-install-and-add-data-to-realm-database-in-android/
            //RealmList.listar();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMovieToDB(String title_txt, String director_txt, String genero_txt){
        Realm realm = Realm.getDefaultInstance();
        MovieRealm movie = new MovieRealm();
        Number id = realm.where(MovieRealm.class).max("id");
        long nextId;

        if (id == null){
            nextId=1;
        }else{
            nextId=id.intValue()+1;
        }

        movie.setTitulo(movie.getTitulo());
        movie.setDirector(movie.getDirector());
        movie.setGenero(movie.getGenero());
        //movie.setImage_base64(movie.getImage_base64());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.copyToRealm(movie);
            }
        });
    }

    private void ResetFragmentFields() {
        title.setText("");
        maker.setText("");
        description.setText("");
        photo.setImageResource(R.drawable.logo_up);
    }


}