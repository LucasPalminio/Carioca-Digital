package com.lucas.Juego_Carioca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class JugadorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calcularPuntaje() {
    }

    @Test
    public void ingresarIndicesTrio() {
    }

    @Test
    public void ingresarIndicesEscala() {
        String stringInput = "0 1 2\n";
        MainCarioca.entradaAProbar(stringInput);
        Jugador jugador1 = new Jugador("Carlos");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            cartasJugador1.add(new Carta("♠","3"));
        }

        cartasJugador1.add(new Carta("JKR",""));
        jugador1.setCartas(cartasJugador1);
        int[] indicesTriosEsperado = {0,1,2};
        int[] indicesTriosReal = jugador1.ingresarIndicesTrio();
        assertArrayEquals(indicesTriosEsperado,indicesTriosReal);
    }

    @Test
    public void ingresarIndices_test1() {
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "0 1 2\n";
        MainCarioca.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test2() {
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "1 1 2\n0 1 2\n";
        MainCarioca.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test3() {
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "e e e\n0 1 2\n";
        MainCarioca.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test4() {
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "12 13 15\n0 1 2\n";
        MainCarioca.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void menuBajarse_test1(){

    }
}