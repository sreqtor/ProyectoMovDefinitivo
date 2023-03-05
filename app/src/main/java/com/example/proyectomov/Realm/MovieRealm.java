package com.example.proyectomov.Realm;

import android.graphics.Bitmap;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MovieRealm extends RealmObject {
    @PrimaryKey
    int id;
    @Required
    String titulo = "";
    @Required
    String director = "";
    @Required
    String genero = "";
    String image_base64 = null;

    public MovieRealm() {}

    /*public MovieRealm(String id, String titulo, String director, String genero, String image_base64) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.image_base64 = image_base64;
    }*/

    public MovieRealm(String titulo, String director, String genero, String image_base64) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.image_base64 = image_base64;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getImage_base64() {
        return image_base64;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }

    @Override
    public String toString() {
        return "MovieRealm{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", genero='" + genero + '\'' +
                ", image_base64='" + image_base64 + '\'' +
                '}';
    }


}




