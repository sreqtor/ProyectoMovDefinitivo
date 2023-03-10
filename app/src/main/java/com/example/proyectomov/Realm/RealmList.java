package com.example.proyectomov.Realm;

import android.graphics.Bitmap;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.proyectomov.AddItemFragment;
import com.example.proyectomov.Item;
import com.example.proyectomov.ItemListSingleton;
import com.example.proyectomov.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmList {
    private static EditText titulo;
    private static EditText director;
    private static EditText genero;

    //static String id_txt = null;

    private static Realm myRealm;

    static String title_txt;
    static String director_txt;
    static String genero_txt;
    static String imagen_img = null;

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

    public final static int calculateIndex(){
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(MovieRealm.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public static void addMovie(final MovieRealm movie){
        title_txt = titulo.toString();
        director_txt = director.toString();
        genero_txt = genero.toString();

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                int index = RealmList.calculateIndex();
                MovieRealm movie = realm.createObject(MovieRealm.class, index);
                movie.setTitulo(movie.getTitulo());
                movie.setDirector(movie.getDirector());
                movie.setGenero(movie.getGenero());
                //movie.setImage_base64(movie.getImage_base64());
            }
        });
    }

    public static void insertar() {
        MovieRealm movie = new MovieRealm(title_txt, director_txt, genero_txt, imagen_img);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(movie);
            }
        });
    }

    /*

    public static void aniadir(String id, String titulo, String director, String genero, String image_base64){
        MovieRealm pelicula = new MovieRealm(id_txt, title_txt, genero_txt, director_txt, imagen_img);
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realmTransaction) {
                    realmTransaction.insert(pelicula);
                }
            });
    }

    public static void actualizar(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realmTransaction) {
                MovieRealm pelicula = realmTransaction.where(MovieRealm.class)
                        .equalTo("id", id_txt)
                        .findFirst();
                pelicula.setTitulo("T??tulo");
                pelicula.setDirector("Director");
            }
        });
    }*/
}
