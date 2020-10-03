package com.lucas.carioca_digital;

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

    /**
     * Se crea un jugador con una mano de cartas pre-establecido, luego calculamos el puntaje
     * y verificamos que el método haya hecho bien el calculo de las cartas
     */
    @Test
    public void calcularPuntaje() {
        Jugador jugador = new Jugador("jugadorTest");
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta("JKR",""));
        cartas.add(new Carta("C","Q"));
        cartas.add(new Carta("H","2"));

        jugador.setCartas(cartas);
        jugador.calcularPuntajeRonda();

        assertEquals(42,jugador.getPuntajeRonda());

    }

    /**
     * Este test su uso para comprobar si las cartas que eligio el jugador son iguales para crear un trio
     */
    @Test
    public void getArrayObject() {
        Jugador jugador = new Jugador("test");
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta("JKR",""));
        cartas.add(new Carta("C","Q"));
        cartas.add(new Carta("H","2"));
        jugador.setCartas(cartas);
        Object[] esperado = {"test",3,0,false};

        assertArrayEquals(esperado,jugador.getArrayObject());
    }

    @Test
    public void getArrayObjectEscalas() {
    }

    @Test
    public void getArrayObjectTrios() {
    }
}