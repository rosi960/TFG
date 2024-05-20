package com.example.tfg;

import android.content.Intent;
import android.media.MediaPlayer;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;

import com.example.tfg.model.Dialogo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Arco1 {
    private final MainActivity mainActivity;

    private MediaPlayer mediaPlayer;

    private int counter;
    private int counter2;
    private int counter3;
    private int counter4;

    private int counter5a;
    private int counter5b;

    private int counter6a;

    private int counter6b;
    private int counterENDb;

    /* TODO: Hacer que las funcionalidades propias de un arco/escena sean genéricos:
    mostrar diálogo, mostrar botones para decisiones, cambios de escena...
     */
    public Arco1(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.counter = 0;
    }

    public void iniciar() throws IOException {
        mediaPlayer = MediaPlayer.create(mainActivity, R.raw.musica1);
        // Archivo "arco1"
        ArrayList<Dialogo> conversacionArco1 = cargarConversacionDesdeArchivo("arco1.csv");
        ArrayList<Dialogo> conversacionArco2 = cargarConversacionDesdeArchivo("arco2.csv");
        ArrayList<Dialogo> conversacionArco3 = cargarConversacionDesdeArchivo("arco3.csv");
        ArrayList<Dialogo> conversacionArco4 = cargarConversacionDesdeArchivo("arco4.csv");
        ArrayList<Dialogo> conversacionArco5a = cargarConversacionDesdeArchivo("arco5a.csv");
        ArrayList<Dialogo> conversacionArco5b = cargarConversacionDesdeArchivo("arco5b.csv");
        ArrayList<Dialogo> conversacionArco6a = cargarConversacionDesdeArchivo("arco6a.csv");
        ArrayList<Dialogo> conversacionArco6b = cargarConversacionDesdeArchivo("arco6b.csv");
        ArrayList<Dialogo> conversacionArcoFinala = cargarConversacionDesdeArchivo("arcofinala.csv");
        ArrayList<Dialogo> conversacionArcoFinalb = cargarConversacionDesdeArchivo("arcofinalb.csv");
        ArrayList<Dialogo> opciones = cargarConversacionDesdeArchivo("choices.csv");


        // Archivo "badEnding"
        // Pasamos la lista de diálogos al método de mostrar conversación
        mostrarConversacion(conversacionArco1, conversacionArco2, conversacionArco3, conversacionArco4, conversacionArco5a, conversacionArco5b, conversacionArco6a, conversacionArco6b, conversacionArcoFinalb, conversacionArcoFinala, opciones);

        reproducirSonido();
    }

    private ArrayList<Dialogo> cargarConversacionDesdeArchivo(String nombreArchivo) throws IOException {
        try (InputStream is = mainActivity.getApplicationContext().getAssets().open(nombreArchivo)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            CSVReader csvReader = new CSVReader(reader);
            // Array de líneas del fichero y lista que almacenará los diálogos
            String[] line;
            ArrayList<Dialogo> conversacion = new ArrayList<>();
            // Por cada línea del fichero creamos un elemento de diálogo para añadirlo a la conversación
            while ((line = csvReader.readNext()) != null) {
                // Cada elemento corresponde a personaje, reacción, fondo y línea.
                Dialogo d = new Dialogo(line[0], line[1], line[2], line[3]);
                conversacion.add(d);
            }
            return conversacion;
        } catch (CsvValidationException e) {
            // TODO: Controlar mejor la excepción
            throw new RuntimeException(e);
        }
    }

    public void mostrarConversacion(ArrayList<Dialogo> conversacion, ArrayList<Dialogo> arco2, ArrayList<Dialogo> arco3, ArrayList<Dialogo> arco4, ArrayList<Dialogo> arco5a, ArrayList<Dialogo> arco5b, ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        mediaPlayer = MediaPlayer.create(mainActivity, R.raw.musica1);
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);
        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, conversacion.get(0));
        // Agregar clic para mostrar más líneas
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llevar la cuenta de los clicks que llevamos para avanzar en la lista de mensajes
                counter = counter + 1;
                // Añadimos un listener a los botones y en base al contador mostramos una cosa u otra
                //Toast.makeText(mainActivity.getApplicationContext(), "Hiciste click", Toast.LENGTH_SHORT).show();
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter < conversacion.size()) {
                    mostrarDialogo(textViewGameInfo, conversacion.get(counter));
                    if (counter == 36) {
                        conversationalButton1.setText("Necesito más tiempo...");
                        conversationalButton2.setText("Voy a entrar");
                        conversationalButton3.setText("Arreglarse la chaqueta");
                        conversationalButton4.setText("Esto es un error");
                    }
                }
                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter > 35) {
                            acto2(arco2, arco3, arco4, arco5a, arco5b, arco6a, arco6b, arcofinalb, arcofinala, choices);
                        }
                    }
                });
                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter > 36) {
                            mostrarDialogo(textViewGameInfo, choices.get(0));
                            counter = 36;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter > 36) {
                            mostrarDialogo(textViewGameInfo, choices.get(1));
                            counter = 36;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter > 36) {
                            mostrarDialogo(textViewGameInfo, choices.get(2));
                            counter = 36;
                        }
                    }
                });

            }
        });
    }

    public void acto2(ArrayList<Dialogo> arco2, ArrayList<Dialogo> arco3, ArrayList<Dialogo> arco4, ArrayList<Dialogo> arco5a, ArrayList<Dialogo> arco5b, ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");

        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco2.get(0));
        // Agregar clic para mostrar más líneas
        counter2 = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter2 = counter2 + 1;
                // Añadimos un listener a los botones y en base al contador mostramos una cosa u otra
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter2 < arco2.size()) {
                    mostrarDialogo(textViewGameInfo, arco2.get(counter2));

                    if (counter2 == 16) {
                        conversationalButton1.setText("Coger la llave y marcharte");
                        conversationalButton2.setText("Decidir esperar por si aparece Dahlia");
                        conversationalButton3.setText("Mirar a tu alrededor");
                        conversationalButton4.setText("Intentar ojear el libro de Thomas");
                    }
                }

                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter2 > 15) {
                            acto3(arco3, arco4, arco5a, arco5b, arco6a, arco6b, arcofinalb, arcofinala, choices);
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter2 > 16) {
                            mostrarDialogo(textViewGameInfo, choices.get(4));
                            counter2 = 16;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter2 > 16) {
                            mostrarDialogo(textViewGameInfo, choices.get(5));
                            counter2 = 16;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter2 > 16) {
                            mostrarDialogo(textViewGameInfo, choices.get(6));
                            counter2 = 16;
                        }
                    }
                });
            }
        });
    }

    public void acto3(ArrayList<Dialogo> arco3, ArrayList<Dialogo> arco4, ArrayList<Dialogo> arco5a, ArrayList<Dialogo> arco5b, ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");

        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco3.get(0));
        // Agregar clic para mostrar más líneas
        counter3 = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter3 = counter3 + 1;
                // Añadimos un listener a los botones y en base al contador mostramos una cosa u otra
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter3 < arco3.size()) {
                    mostrarDialogo(textViewGameInfo, arco3.get(counter3));

                    if (counter3 == 19) {
                        conversationalButton1.setText("Deshacer el equipaje");
                        conversationalButton2.setText("Abrir la botella de whiskey de la mesa y beber");
                        conversationalButton3.setText("Leer un libro que trajiste del viaje");
                        conversationalButton4.setText("Elegir habitación para acostarte");
                    }
                }
                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter3 > 19) {
                            mostrarDialogo(textViewGameInfo, choices.get(7));
                            counter3 = 19;
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter3 > 19) {
                            mostrarDialogo(textViewGameInfo, choices.get(8));
                            counter3 = 19;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter3 > 19) {
                            mostrarDialogo(textViewGameInfo, choices.get(9));
                            counter3 = 19;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter3 > 18) {
                            acto4(arco4, arco5a, arco5b, arco6a, arco6b, arcofinalb, arcofinala, choices);
                        }
                    }
                });

            }
        });
    }

    public void acto4(ArrayList<Dialogo> arco4, ArrayList<Dialogo> arco5a, ArrayList<Dialogo> arco5b, ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");

        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco4.get(0));
        // Agregar clic para mostrar más líneas
        counter4 = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter4 = counter4 + 1;
                // Añadimos un listener a los botones y en base al contador mostramos una cosa u otra
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter4 < arco4.size()) {
                    mostrarDialogo(textViewGameInfo, arco4.get(counter4));

                    if (counter4 == 11) {
                        conversationalButton1.setText("Podría acostarme en la habitación roja");
                        conversationalButton2.setText("Podría mover el colchón de la habitación verde...");
                        conversationalButton3.setText("Podría intentar dormir en esta habitación");
                        conversationalButton4.setText("Podría acostarme en la habitación azul");
                    }
                }
                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter4 > 10) {
                            preludio_a(arco5a, arco6a, arcofinala, choices);
                            conversationalButton1.setText("-");
                            conversationalButton2.setText("-");
                            conversationalButton3.setText("-");
                            conversationalButton4.setText("-");
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter4 > 10) {
                            preludio_b(arco5b, arco6b, arcofinalb, choices);
                            conversationalButton1.setText("-");
                            conversationalButton2.setText("-");
                            conversationalButton3.setText("-");
                            conversationalButton4.setText("-");
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter4 > 11) {
                            mostrarDialogo(textViewGameInfo, choices.get(10));
                            counter4 = 11;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter4 > 11) {
                            mostrarDialogo(textViewGameInfo, choices.get(11));
                            counter4 = 11;
                        }
                    }
                });

            }
        });
    }

    public void preludio_a(ArrayList<Dialogo> arco5a, ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");

        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco5a.get(0));
        // Agregar clic para mostrar más líneas
        counter5a = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter5a = counter5a + 1;
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter5a < arco5a.size()) {
                    mostrarDialogo(textViewGameInfo, arco5a.get(counter5a));

                    // Modificar los botones en función del contador
                    if (counter5a == 34) {
                        conversationalButton1.setText("Sí");
                        conversationalButton2.setText("N0");
                        conversationalButton3.setText("No...");
                        conversationalButton4.setText("No me obligues porfavor");
                    }
                }

                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5a > 34) {
                            finalA(arco6a, arcofinala, choices);
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5a > 34) {
                            mostrarDialogo(textViewGameInfo, choices.get(19));
                            counter5a = 34;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5a > 34) {
                            mostrarDialogo(textViewGameInfo, choices.get(20));
                            counter5a = 34;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5a > 34) {
                            mostrarDialogo(textViewGameInfo, choices.get(18));
                            counter5a = 34;
                        }
                    }
                });

            }
        });
    }

    public void preludio_b(ArrayList<Dialogo> arco5b, ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");

        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);
        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());
        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco5b.get(0));
        // Agregar clic para mostrar más líneas
        counter5b = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter5b = counter5b + 1;
                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter5b < arco5b.size()) {
                    mostrarDialogo(textViewGameInfo, arco5b.get(counter5b));

                    // Modificar los botones en función del contador
                    if (counter5b == 38) {
                        conversationalButton1.setText("Continuar tocando un poco más");
                        conversationalButton2.setText("Besar dulcemente a Dahlia");
                        conversationalButton3.setText("Preguntar sobre su estado");
                        conversationalButton4.setText("Preguntar que hizo durante la guerra");
                    }
                }
                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5b > 38) {
                            mostrarDialogo(textViewGameInfo, choices.get(12));
                            counter5b = 38;
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5b > 38) {
                            mostrarDialogo(textViewGameInfo, choices.get(13));
                            counter5b = 38;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5b > 38) {
                            mostrarDialogo(textViewGameInfo, choices.get(14));
                            counter5b = 38;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter5b > 37) {
                            finalB(arco6b, arcofinalb, choices);
                        }
                    }
                });
            }
        });
    }


    public void finalB(ArrayList<Dialogo> arco6b, ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");


        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);

        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());

        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco6b.get(0));

        // Agregar clic para mostrar más líneas
        counter6b = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter6b = counter6b + 1;

                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter6b < arco6b.size()) {
                    mostrarDialogo(textViewGameInfo, arco6b.get(counter6b));

                    // Modificar los botones en función del contador
                    if (counter6b == 18) {
                        conversationalButton1.setText("Ir con ella");
                        conversationalButton2.setText("¿A donde vamos?");
                        conversationalButton3.setText("¿Que vamos a hacer?");
                        conversationalButton4.setText("No estoy seguro...");
                    }
                }
                conversationalButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter6b > 17) {
                            endB(arcofinalb, choices);
                        }
                    }
                });

                conversationalButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter6b > 18) {
                            mostrarDialogo(textViewGameInfo, choices.get(15));
                            counter6b = 18;
                        }
                    }
                });

                conversationalButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter6b > 18) {
                            mostrarDialogo(textViewGameInfo, choices.get(16));
                            counter6b = 18;
                        }
                    }
                });

                conversationalButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter6b > 18) {
                            mostrarDialogo(textViewGameInfo, choices.get(17));
                            counter6b = 18;
                        }
                    }
                });
            }
        });
    }

    public void finalA(ArrayList<Dialogo> arco6a, ArrayList<Dialogo> arcofinala, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");


        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);

        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());

        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arco6a.get(0));

        // Agregar clic para mostrar más líneas
        counter6a = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter6a = counter6a + 1;

                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counter6a < arco6a.size()) {
                    mostrarDialogo(textViewGameInfo, arco6a.get(counter6a));
                } else {
                    // Se ha mostrado todo el diálogo, redirigir a la página de créditos
                    Intent intent = new Intent(mainActivity, CreditosActivity.class);
                    mainActivity.startActivity(intent);
                }
            }
        });
    }

    public void endB(ArrayList<Dialogo> arcofinalb, ArrayList<Dialogo> choices) {
        TextView textViewGameInfo = mainActivity.findViewById(R.id.textViewGameInfo);
        Button conversationalButton1 = mainActivity.findViewById(R.id.myConversacionalButton1);
        Button conversationalButton2 = mainActivity.findViewById(R.id.myConversacionalButton2);
        Button conversationalButton3 = mainActivity.findViewById(R.id.myConversacionalButton3);
        Button conversationalButton4 = mainActivity.findViewById(R.id.myConversacionalButton4);

        conversationalButton1.setText("-");
        conversationalButton2.setText("-");
        conversationalButton3.setText("-");
        conversationalButton4.setText("-");


        // Establecer el límite de líneas inicial
        textViewGameInfo.setMaxLines(2);

        // Configurar el desplazamiento vertical en el TextView
        textViewGameInfo.setMovementMethod(new ScrollingMovementMethod());

        // Configurar el texto inicial con la primera línea
        mostrarDialogo(textViewGameInfo, arcofinalb.get(0));

        // Agregar clic para mostrar más líneas
        counterENDb = 0;
        textViewGameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterENDb = counterENDb + 1;

                // Mostrar el siguiente texto en el TextView mientras haya contenido en la conversación
                if (counterENDb < arcofinalb.size()) {
                    mostrarDialogo(textViewGameInfo, arcofinalb.get(counterENDb));
                } else {
                    // Se ha mostrado todo el diálogo, redirigir a la página de créditos
                    Intent intent = new Intent(mainActivity, CreditosActivity.class);
                    mainActivity.startActivity(intent);
                }
            }
        });
    }

    public void mostrarDialogo(TextView textViewGameInfo, Dialogo dialogo) {

        // Configura el texto a partir de la línea de diálogo
        textViewGameInfo.setText(dialogo.getLinea());
        // Sólo en caso de que se nos informe el fondo lo cambiamos (provisional)
        // TODO: Debería calcularse en cada línea de diálogo como el personaje/reacción
        if (dialogo.getFondo() != null) {
            cambiarFondo(dialogo.getFondo());
        }

        // Si no hay personaje definido en el diálogo, solo aparece el texto
        if (dialogo.getPersonaje() == null || dialogo.getPersonaje().isEmpty()) {
            hacerPersonajeInvisible();
        } else { // En caso afirmativo, muestra personaje y reacción alternativa
            hacerPersonajeVisible(dialogo.getPersonaje(), dialogo.getReaccion());
        }

    }

    private void cambiarFondo(String fondo) {
        // Obtén la referencia a la ImageView del fondo
        ImageView imageViewBackground = mainActivity.findViewById(R.id.imageViewBackground);

        System.out.println("FONDO: " + fondo);

        // Obtén el ID del recurso dibujable que necesitas mostrar a partir del nombre
        int resID = mainActivity.getResources().getIdentifier
                (fondo, "drawable", mainActivity.getPackageName());

        System.out.println("RESID: " + resID);

        // Reestablece el recurso a partir del ID calculado.
        imageViewBackground.setImageResource(resID);
    }

    private void hacerPersonajeInvisible() {
        // Obtén la referencia a la ImageView del personaje
        ImageView characterImageView = mainActivity.findViewById(R.id.characterImageView);

        // Oculta la ImageView para que desaparezca
        characterImageView.setVisibility(View.INVISIBLE);
    }

    private void hacerPersonajeVisible(String personaje, String reaccion) {
        // Obtén la referencia a la ImageView del personaje
        ImageView characterImageView = mainActivity.findViewById(R.id.characterImageView);

        String imageName = "";
        // Si no hay reacción definida, obtén el recurso con el nombre del personaje
        if (reaccion == null || reaccion.isEmpty()) {
            imageName = personaje;
        } else {
            // Si hay reacción, obtén el recurso como concatenación de los dos parámetros
            imageName = personaje.concat("_").concat(reaccion);
        }

        System.out.println("IMAGENAME: " + imageName);

        // Obtén el ID del recurso dibujable que necesitas mostrar a partir del nombre
        int resID = mainActivity.getResources().getIdentifier
                (imageName, "drawable", mainActivity.getPackageName());

        System.out.println("RESID: " + resID);

        // Reestablece el recurso a partir del ID calculado.
        characterImageView.setImageResource(resID);

        // Muestra la ImageView para que aparezca
        characterImageView.setVisibility(View.VISIBLE);
    }

    private void reproducirSonido() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

}
