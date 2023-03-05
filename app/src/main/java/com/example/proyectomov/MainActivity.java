package com.example.proyectomov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Context context;
    Button irRegistro;
    Button irLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        irRegistro = findViewById(R.id.buttonInicio);
        irLogin = findViewById(R.id.buttonLogin);

        irRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,registro.class);
                context.startActivity(intent);
            }
        });

        irLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,iniciosesion.class);
                context.startActivity(intent);
            }
        });
    }
}