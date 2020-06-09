package com.lucas.Juego_Carioca;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RondaTest {


    @Test
    public void finRonda() {

        Mazo mazo = new Mazo();
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            jugadores.add(new Jugador("jugador" + i));
            jugadores.get(i).setCartas(mazo.sacarUnNumeroDeCartas(5));
        }
        jugadores.add(new Jugador("Jugador_ganador_3"));
        jugadores.get(3).setCartas(new ArrayList<>());//arreglo de cartas sin cartas
        Ronda ronda = new Ronda(jugadores, 0);
        ronda.finRonda();
    }
}