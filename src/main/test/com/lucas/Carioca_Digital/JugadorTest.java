package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;
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
    public void ingresarIndicesEscala_test01() { // Crea un jugadores con una escala (solo numeros 2 3 4 5) en la mano y comprueba si esta se ingresa correctamente
        String stringInput = "0 1 2 3\n";
        Utilidades.entradaAProbar(stringInput);
        Jugador jugador1 = new Jugador("Carlos");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        for (int i = 2; i <8; i++) {
            cartasJugador1.add(new Carta("♠", String.valueOf(i)));
        }
        jugador1.setCartas(cartasJugador1);
        jugador1.imprimirCartas();
        int[] indicesEscalaEsperado = {0,1,2,3};
        int[] indicesEscalaReal = jugador1.ingresarIndicesEscala(); // (Error) Aqui el metodo se ejecuta indefinidamente
        assertArrayEquals(indicesEscalaEsperado,indicesEscalaReal);
    }
    @Test
    public void ingresarIndicesEscala_test02() { // Crea un jugadores con una escala(letras y numeros Q, K, A, 2) en la mano y comprueba si esta se ingresa correctamente
        String stringInput = "0 1 2 3\n";
        Utilidades.entradaAProbar(stringInput);
        Jugador jugador1 = new Jugador("Carlos");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        cartasJugador1.add(new Carta("♠", "Q"));
        cartasJugador1.add(new Carta("♠", "K"));
        cartasJugador1.add(new Carta("♠", "A"));
        cartasJugador1.add(new Carta("♠", "2"));
        jugador1.setCartas(cartasJugador1);
        jugador1.imprimirCartas();
        int[] indicesEscalaEsperado = {0,1,2,3};
        int[] indicesEscalaReal = jugador1.ingresarIndicesEscala(); // (Error) Aqui el metodo se ejecuta indefinidamente
        assertArrayEquals(indicesEscalaEsperado,indicesEscalaReal);
    }
    @Test
    public void ingresarIndicesTrio_test01() {// Se comprueba si se acepta un trio formado por dos cartas iguales y un Joker
        String stringInput = "0 1 2\n";
        Utilidades.entradaAProbar(stringInput);
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
    public void ingresarIndices_test1() { //  Comprueba un ingreso valido de indices
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));
        String stringInput = "0 1 2 3\n";
        Utilidades.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2, 3};
        int[] real = jugador.ingresarIndices(4);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test2() { // Comprueba que aunque se ingresen indices erroneos(Se repite un indice) de todas maneras
        // solo tome los correctos para ejecutar el metodo
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "1 1 2\n0 1 2\n";
        Utilidades.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test3() { // Comprueba que aunque se ingresen indices erroneos(letras) de todas maneras
        // solo tome los correctos para ejecutar el metodo
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "e e e\n0 1 2\n";
        Utilidades.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void ingresarIndices_test4() {// Comprueba que aunque se ingresen indices erroneos(fuera del tamaño de la mano del jugador)
        // de todas maneras solo tome los correctos para ejecutar el metodo
        Jugador jugador = new Jugador("Carlos");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));

        String stringInput = "12 13 15\n0 1 2\n";
        Utilidades.entradaAProbar(stringInput);
        int[] esperado = {0, 1, 2};
        int[] real = jugador.ingresarIndices(3);
        assertArrayEquals(esperado,real);


    }
    @Test
    public void menuBajarse_test1(){

    }
}