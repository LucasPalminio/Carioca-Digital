package com.lucas.carioca_digital;

import org.junit.Test;

import java.util.ArrayList;

public class RondaTest {


    @Test
    public void finRonda() {

        Mazo mazo = new Mazo();
        ArrayList<Jugador> jugadores = new ArrayList<>();
        // Se crean dos jugadores normales que terminaran la partida con una mano al azar
        for (int i = 0; i < 3; i++) {
            jugadores.add(new Jugador("jugador" + i));
            jugadores.get(i).setCartas(mazo.sacarUnNumeroDeCartas(5));
        }
        // Se crea a un jugador que sera el ganador, lo que significa que habra obtenido 0 puntos en la
        // ronda de ese momento
        jugadores.add(new Jugador("Jugador_ganador_3"));
        jugadores.get(3).setCartas(new ArrayList<>());//arreglo de cartas sin cartas
        Ronda ronda = new Ronda(jugadores, 0);
        ronda.finRonda();
    }
}