package com.example.tfg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.db.DBHelper;
import com.example.tfg.model.Jugador;
import com.example.tfg.model.Partida;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MenuActivity es una actividad principal de la aplicación que proporciona un menú con opciones para
 * iniciar un nuevo juego, ver los créditos y salir de la aplicación.
 *
 * @version 1.0
 * @since 2024-05-20
 *
 * Autor: Rosa Martinez
 */
public class MenuActivity extends AppCompatActivity {

    /**
     * Método llamado cuando la actividad es creada. Configura el contenido de la actividad con el
     * layout correspondiente.
     *
     * @param savedInstanceState Si la actividad está siendo recreada, este parámetro contiene los datos
     *                           que proporcionó más recientemente {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * Método llamado al hacer clic en el botón para iniciar un nuevo juego. Muestra un cuadro de diálogo
     * para ingresar el nombre de un nuevo jugador y crea una nueva partida asociada a ese jugador.
     *
     * @param view La vista que fue clickeada.
     */
    public void newGame(View view) {
        // Crear un LayoutInflater para el diseño personalizado
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_new_player, null);

        // Configurar el EditText en el diseño personalizado
        EditText playerNameEditText = dialogView.findViewById(R.id.editTextPlayerName);

        // Crear el cuadro de diálogo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Nuevo Jugador")
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción a realizar al hacer clic en "Crear"
                        String playerName = playerNameEditText.getText().toString();

                        // Verificar si el nombre del jugador no está vacío
                        if (!playerName.isEmpty()) {
                            // Registrar al jugador en la base de datos
                            DBHelper dbHelper = new DBHelper(MenuActivity.this);
                            long jugadorId = dbHelper.registrarJugador(new Jugador(playerName));

                            // Verificar si el jugador se registró correctamente
                            if (jugadorId != -1) {
                                // Crear una nueva partida asociada al jugador
                                String fechaCreacion = getCurrentDateTime();
                                Partida nuevaPartida = new Partida((int) jugadorId, fechaCreacion);
                                long partidaId = dbHelper.registrarPartida(nuevaPartida);

                                // Verificar si la partida se registró correctamente
                                if (partidaId != -1) {
                                    Toast.makeText(MenuActivity.this, "Nuevo jugador y partida creados", Toast.LENGTH_SHORT).show();

                                    // Iniciar MainActivity después de crear el jugador y la partida
                                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MenuActivity.this, "Error al crear la partida", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MenuActivity.this, "Error al crear el jugador", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MenuActivity.this, "Ingresa un nombre de jugador válido", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acción a realizar al hacer clic en "Cancelar"
                        dialog.dismiss();  // Cierra el cuadro de diálogo
                    }
                });

        // Mostrar el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Método para obtener la fecha y hora actual en el formato especificado.
     *
     * @return La fecha y hora actual en formato "yyyy-MM-dd HH:mm:ss".
     */
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat =        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Método llamado al hacer clic en el botón para salir de la aplicación. Cierra todas las actividades
     * relacionadas y finaliza la aplicación.
     *
     * @param view La vista que fue clickeada.
     */
    public void exitApp(View view) {
        finishAffinity(); // Cierra la actividad actual y todas las actividades relacionadas
        System.exit(        0); // Finaliza completamente la aplicación
    }

    /**
     * Método llamado al hacer clic en el botón para mostrar los créditos de la aplicación. Inicia la
     * actividad CreditosActivity.
     *
     * @param view La vista que fue clickeada.
     */
    public void showCredits(View view) {
        Intent intent = new Intent(MenuActivity.this, CreditosActivity.class);
        startActivity(intent);
    }
}
