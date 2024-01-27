package com.example.tfg;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.db.DBHelper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Arco1 arco1;
    // private Arco2 arco2;
    // private Arco3 arco3;
    private TextView textViewGameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        dbHelper = new DBHelper(this);

        // Obtener referencias a las vistas
        textViewGameInfo = findViewById(R.id.textViewGameInfo);

        // Inicializar instancias de cada
        arco1 = new Arco1(this);
        // arco2 = new Arco2();
        // arco3 = new Arco3();

        // Iniciar el primer arco al principio del juego
        iniciarArco1();

        /* TODO: Definir cuáles son los triggers que abren o cierran el arco.
            Quizás una estructura superior a Diálogo que defina salidas y entradas...
        */
    }

    // Método para iniciar el primer arco
    private void iniciarArco1() {
        // Lógica para comenzar el primer arco
        try {
            arco1.iniciar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para cambiar al segundo arco
    private void cambiarAArco2() {
        // Detener el primer arco si es necesario
        // Iniciar lógica del segundo arco
    }

    // Método para cambiar al tercer arco
    private void cambiarAArco3() {
        // Detener el segundo arco si es necesario
        // Iniciar lógica del tercer arco
    }

    // Otros métodos y lógica del juego
}
