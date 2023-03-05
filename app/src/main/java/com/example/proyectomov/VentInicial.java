package com.example.proyectomov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VentInicial extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    //barra de busqueda no funcional aún

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vent_inicial);
        //replaceFragment(new ItemListFragment());
        replaceFragment(new ItemListFragment());
        Realm.init(this);
        String realmName = "ProyectoMov";
        RealmConfiguration configuracion = new RealmConfiguration.Builder()
                .name(realmName)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .compactOnLaunch()
                .build();
        Realm.setDefaultConfiguration(configuracion);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            menuItemNavigation(item);
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            menuItemNavigation(item);
            return true;
        });

        popup.show();
    }

    private void menuItemNavigation(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemListFragment:
                replaceFragment(new ItemListFragment());
                break;
            case R.id.userFragment:
                replaceFragment(new userFragment());
                break;
            case R.id.addFragment:
                replaceFragment(new AddItemFragment());
                break;
            case R.id.settingsFragment:
                replaceFragment(new SettingsFragment());
                break;
            default:
                break;
        }
    }
}