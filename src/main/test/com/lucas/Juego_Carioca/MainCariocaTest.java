package com.lucas.Juego_Carioca;


import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainCariocaTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void repetirString() {
    }

    @Test
    public void sonNumerosDiferentes() {
        int[] test = {1,2,3,3};
        boolean esperado = false;
        assertEquals(esperado,MainCarioca.sonNumerosDiferentes(test));
    }

    @Test
    public void testmain(){
        Jugador jugador1 = new Jugador("Tester trios o escalas");
        Jugador jugador2 = new Jugador("Tester trio o escalas 2");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            cartasJugador1.add(new Carta("♠","3"));
            cartasJugador1.add(new Carta("♦","2"));
        }

        cartasJugador1.add(new Carta("JKR",""));
        jugador1.setCartas(cartasJugador1);
        jugador2.setCartas(cartasJugador1);
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Ronda ronda1 = new Ronda(jugadores,0);
        ronda1.comenzarRonda();

    }

}