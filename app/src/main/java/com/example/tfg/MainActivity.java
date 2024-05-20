package com.example.tfg;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.db.DBHelper;

import java.io.IOException;

/**
 * MainActivity es la actividad principal del juego que maneja la interfaz de usuario y el inicio de los arcos del juego.
 *
 * @version 1.0
 * @since 2024-05-20
 *
 * Autor: Rosa Martinez
 */
public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Arco1 arco1;
    // private Arco2 arco2;
    // private Arco3 arco3;
    private TextView textViewGameInfo;

    /**
     * Método llamado cuando la actividad es creada. Configura el contenido de la actividad con el
     * layout y establece la orientación de la pantalla a horizontal.
     *
     * @param savedInstanceState Si la actividad está siendo recreada, este parámetro contiene los datos
     *                           que proporcionó más recientemente {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        dbHelper = new DBHelper(this);

        // Obtener referencias a las vistas
        textViewGameInfo = findViewById(R.id.textViewGameInfo);

        // Inicializar instancias de cada arco
        arco1 = new Arco1(this);
        iniciarArco1();
    }

    /**
     * Método para iniciar el primer arco del juego.
     * Este método maneja la lógica necesaria para comenzar el primer arco y maneja posibles excepciones de IO.
     */
    private void iniciarArco1() {
        try {
            arco1.iniciar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
