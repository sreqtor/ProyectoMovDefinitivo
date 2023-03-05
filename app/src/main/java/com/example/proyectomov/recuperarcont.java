package com.example.proyectomov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class recuperarcont extends AppCompatActivity {

    private Context context;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperarcont);
        context = this;

        back = findViewById(R.id.imageButtonBackRec);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,iniciosesion.class);
                context.startActivity(intent);
            }
        });
    }
}