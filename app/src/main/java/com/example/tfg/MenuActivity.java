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

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

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
                                dbHelper.registrarPartida(new Partida((int) jugadorId, fechaCreacion));

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


                                Toast.makeText(MenuActivity.this, "Nuevo jugador y partida creados", Toast.LENGTH_SHORT).show();
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

    // Método para obtener la fecha y hora actual (puedes modificarlo según tus necesidades)
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Menu para salir
    public void exitApp(View view) {
        finishAffinity(); // Cierra la actividad actual y todas las actividades relacionadas
        System.exit(0); // Finaliza completamente la aplicación
    }

    public void showCredits(View view) {
        Intent intent = new Intent(MenuActivity.this, CreditosActivity.class);
        startActivity(intent);
    }

}
