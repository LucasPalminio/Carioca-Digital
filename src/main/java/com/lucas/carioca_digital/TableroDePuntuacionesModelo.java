package com.lucas.carioca_digital;


import com.lucas.datos.GestorCSV;


import javax.swing.table.DefaultTableModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TableroDePuntuacionesModelo extends DefaultTableModel {
    private GestorCSV gestorCSV ;
    private String rutaCSV = "src//datos//tabla_resultado.csv";
    private final Object[] COLUMNAS = {"Ganador", "puntaje","nro Jugadores","nivel inicial", "nivel final", "fecha y hora"};

    public TableroDePuntuacionesModelo() {
        super();
        super.setColumnIdentifiers(COLUMNAS);
        gestorCSV = new GestorCSV();
        cargarRondas();

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


    public void agregarRonda(Ronda ronda, int nivelInicial){
        //Ganador, puntaje,nro Jugadores,nivel inicial, nivel final, fecha y hora
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
