package com.example.proyectomov;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectomov.Realm.MovieRealm;
import com.example.proyectomov.Realm.RealmList;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ItemListFragment extends Fragment {

    VentInicial context;

    Realm realm;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MovieAdapter movieAdapter;
    List<MovieRealm> movieRealm;

    Button buttonEdit;
    Button buttonDel;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = (VentInicial) context;
        super.onAttach(context);
    }

    public static RealmList newInstance() {
        RealmList RealmList = new RealmList();
        return RealmList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getAllMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //RealmListSingleton.getItemList().listar();  //listar
        //getAllMovies();  //listar en Logcat


        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        realm=Realm.getDefaultInstance();
        recyclerView=view.findViewById(R.id.itemList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        buttonEdit = view.findViewById(R.id.buttonEdit);
        buttonDel = view.findViewById(R.id.buttonEliminar);

        //Listar
        movieRealm=listarMovies();

        movieAdapter=new MovieAdapter(movieRealm);

        recyclerView.setAdapter(movieAdapter);

        //Editar
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditItemFragment editItemFragment = new EditItemFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, editItemFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //Eliminar
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarFragment eliminarFragment = new EliminarFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frameLayout, eliminarFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    //Mostrar por Logcat
    public final static List<MovieRealm> getAllMovies(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MovieRealm> movies = realm.where(MovieRealm.class).findAll();
        Log.d("TAG1","listando...");
        for (MovieRealm movie: movies){
            Log.d("TAG", "Título: " + movie.getTitulo() + " Director: " + movie.getDirector() + " Género: " + movie.getGenero());
        }
        return movies;
    }


    private static class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

        private final List<MovieRealm> movies;

        private MovieAdapter(List<MovieRealm> movies) {
            this.movies = movies;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            MovieRealm movie = movies.get(position);
            holder.title.setText(movie.getTitulo());
            holder.maker.setText(movie.getDirector());
            holder.description.setText(movie.getGenero());
        }

        @Override
        public int getItemCount() {return  movies.size();}

        public interface OnItemClickListener {
            public void onItemClick(MovieRealm item);
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView title;
            private TextView maker;
            private TextView description;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title_tv);
                maker = (TextView) itemView.findViewById(R.id.maker_tv);
                description = (TextView) itemView.findViewById(R.id.description_tv);
            }
        }
    }

    private List<MovieRealm> listarMovies() {
        RealmResults<MovieRealm> movies = realm.where(MovieRealm.class)
                .findAll();
        return realm.copyFromRealm(movies);
    }

}