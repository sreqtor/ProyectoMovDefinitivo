package com.example.proyectomov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registro extends AppCompatActivity {

    private Context context;
    ImageButton back;
    private Button registro;
    private EditText editTextCorreo, editTextContrasenia;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        context = this;

        mAuth = FirebaseAuth.getInstance();

        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextContrasenia = findViewById(R.id.editTextPass2);

        //Comparar pass2 con confirmarPass

        registro = findViewById(R.id.buttonInicio);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });

        back = findViewById(R.id.imageButtonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void registerNewUser()
    {

        // Take the value of two edit texts in Strings
        String email, password;
        email = editTextCorreo.getText().toString();
        password = editTextContrasenia.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Introduce un mail",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Introduce una contraseña",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registro exitoso",
                                            Toast.LENGTH_LONG)
                                    .show();



                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(registro.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registro fallido"
                                                    + " Prueba de nuevo más tarde",
                                            Toast.LENGTH_LONG)
                                    .show();


                        }
                    }
                });
    }
}