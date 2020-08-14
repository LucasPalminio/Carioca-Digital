package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {
    private int nivelActual = 0;
    private int ultimoNivel = 1;

    public void empezarJuego() {
//        String stringInput = "2\nLucas Palminio\nLorenzo\n";
//        entradaAProbar(stringInput);
        Utilidades.setIn(new Scanner(System.in).useDelimiter("\n"));
        ArrayList<Jugador> jugadores = Utilidades.ingresarJugadores(); //Se da la bienvenida, ingresa el numero de Jugadores y el nombre de cada Jugador
        ingresarNivelAJugar();// Se ingresa desde que nivel hasta que nivel desea Jugar

        while (nivelActual <= ultimoNivel) {
            Ronda rondaActual = new Ronda(jugadores, nivelActual);
            rondaActual.comenzarRonda();
            //Agregar: Por cada juego completo, se muestra en pantalla los puntajes de cada jugador y quien lleva la delantera (gana el que tiene menor puntaje)
            nivelActual++;
        }

        //Cuando termine todos los juegos gana el jugador con el menor Puntaje y muestra en pantalla la tabla de Puntajes
    }

    private void ingresarNivelAJugar() {
        Utilidades.imprimirTitulo("Niveles");
        //Creo las opciones del menu
        String[] opciones = new String[Ronda.RONDAS.length];
        for (int i = 0; i < Ronda.RONDAS.length; i++) {
            opciones[i] = Ronda.RONDAS[i][0] + " escalas, " + Ronda.RONDAS[i][1] + " trios";
        }
        Utilidades.imprimirSoloMenuOpciones(opciones);
        System.out.println();
        String mensajeDeConfirmacion;
        nivelActual = Utilidades.ingresarUnNumero("Ingrese el primer nivel que desea Jugar", 1, Ronda.RONDAS.length) - 1;
        if (nivelActual == Ronda.RONDAS.length - 1) { //Si el jugador quiere jugar el ultimo nivel, no es necesario preguntar hasta que nivel va a jugar
            ultimoNivel = nivelActual;
            mensajeDeConfirmacion = "Usted quiere jugar solamente el ultimo nivel: " + Ronda.RONDAS[ultimoNivel][0] + " escalas y " + Ronda.RONDAS[ultimoNivel][1] + " trios.";
        } else {
            ultimoNivel = Utilidades.ingresarUnNumero("Ingrese hasta que nivel desea Jugar", nivelActual + 1, Ronda.RONDAS.length) - 1;
            mensajeDeConfirmacion = "Usted desea Jugar desde el nivel " + (nivelActual + 1) + " hasta el nivel " + (ultimoNivel + 1);
        }
        if (!Utilidades.confirmarOpcion(mensajeDeConfirmacion)) { //Si el jugador se equivoco, este el momento para arrepentirse
            ingresarNivelAJugar();
        }
    }
}
