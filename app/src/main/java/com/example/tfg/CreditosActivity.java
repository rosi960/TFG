package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * CreditosActivity es una actividad que muestra los créditos del juego.
 *
 * @version 1.0
 * @since 2024-05-20
 *
 * Autor: Rosa Martinez
 */
public class CreditosActivity extends AppCompatActivity {

    /**
     * Método llamado cuando la actividad es creada. Configura el contenido de la actividad con el
     * layout de creditos.
     *
     * @param savedInstanceState Si la actividad está siendo recreada, este parámetro contiene los datos
     *                           que proporcionó más recientemente {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
    }

    /**
     * Método para manejar el clic del botón "Volver". Inicia la actividad {@link MenuActivity}
     * y cierra la actividad actual.
     *
     * @param view La vista que fue clickeada.
     */
    public void volverAMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish(); // Opcional: cierra esta actividad después de abrir MenuActivity
    }
}
