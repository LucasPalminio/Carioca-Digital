package com.lucas.carioca_digital;


import com.lucas.datos.GestorCSV;


import javax.swing.table.DefaultTableModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Esta clase administra el guardado de datos permanentes en el equipo a través del uso de gestores de archivos y los muestra a través de las GUI
 */
public class TableroDePuntuacionesModelo extends DefaultTableModel {

    /**
     * Fichero de texto donde se guardaran los datos
     */
    private GestorCSV gestorCSV ;
    /**
     * Es donde se guardara el archivo csv
     */
    private String rutaCSV = "src//datos//tabla_resultado.csv";
    /**
     * Son los objetos que se guardaran en el gestorCSV
     */
    private final Object[] COLUMNAS = {"Ganador", "puntaje","nro Jugadores","nivel inicial", "nivel final", "fecha y hora"};

    public TableroDePuntuacionesModelo() {
        super();
        super.setColumnIdentifiers(COLUMNAS);
        gestorCSV = new GestorCSV();
        cargarRondas();

    }
    /**
     * En este método es para deshabilitar la opción de editar  una  celda
     */
    @Override

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * En este método se gestiona lo que se va a guardara (Ganador, puntaje,nro Jugadores,nivel inicial, nivel final, fecha y hora)
     * @param ronda en que nivel están jugando los usuarios
     * @param nivelInicial en que nivel empezaron a jugar los usuarios
     */
    public void agregarRonda(Ronda ronda, int nivelInicial){

        String[] datos = new String[6];
        LocalDateTime reloj = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/LLLL/yyyy HH:mm:ss");
        ronda.getJugadores().sort(new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return o1.getPuntaje() - o2.getPuntaje();
            }
        });
        datos[0] = ronda.getJugadores().get(0).getNombre();
        datos[1] = String.valueOf(ronda.getJugadores().get(0).getPuntaje());
        datos[2] = String.valueOf(ronda.getJugadores().size());
        datos[3] = String.valueOf(nivelInicial);
        datos[4] = String.valueOf(ronda.getNivel());
        datos[5] = reloj.format(formato);
        gestorCSV.escribirCSV(datos,rutaCSV);
        cargarRondas();
    }

    /**
     * Carga la información del archivo csv en la tabla de puntuaciones
     */
    public void cargarRondas(){
        List<String[]> data = gestorCSV.leerCSV(rutaCSV);
        while (getRowCount()>0) removeRow(0);
        if (data.size()>0) {
            for (String[] fila : data) {
                addRow(fila);
            }
        }
    }


}
