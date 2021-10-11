package com.example.tp42;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button bBuscar;
    private EditText edtMovieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        setContentView(R.layout.activity_main);
        ObtenerReferencias();
        SetearListeners();


    }
    private void ObtenerReferencias(){
        bBuscar = findViewById(R.id.buttonBuscar);
        edtMovieName = findViewById(R.id.editTextMovieName);
    }

    private void SetearListeners(){
        bBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragment();
            }
        });
    }

    private void abrirFragment(){
        String nombrePelicula = edtMovieName.getText().toString();
        Log.d("nombre", nombrePelicula);
        ListaFragment fgLista = new ListaFragment(nombrePelicula);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.fragmentcontainer, fgLista);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}