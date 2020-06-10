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
    }

    @Test
    public void ingresarIndices() {
    }

    @Test
    public void menuBajarse_test1(){

    }
    @Test
    public void imprimirEscalasyTrios(){
        Jugador jugador = new Jugador("test");
        Mazo mazo = new Mazo();
        jugador.setCartas(mazo.sacarUnNumeroDeCartas(6));//El ju
        ArrayList<Carta> escala1 = mazo.sacarUnNumeroDeCartas(4);
        ArrayList<Carta> escala2 = mazo.sacarUnNumeroDeCartas(4);
        ArrayList<ArrayList<Carta>> escalas = new ArrayList<>();
        escalas.add(escala1);
        escalas.add(escala2);
        ArrayList<Carta> trio1 = mazo.sacarUnNumeroDeCartas(3);
        ArrayList<Carta> trio2 = mazo.sacarUnNumeroDeCartas(3);
        ArrayList<ArrayList<Carta>> trios = new ArrayList<>();
        trios.add(trio1);
        trios.add(trio2);
        jugador.setMatrizEscalas(escalas);
        jugador.setMatrizTrios(trios);

        jugador.imprimirEscalasyTrios();
    }
}