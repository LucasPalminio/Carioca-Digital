package com.lucas.Juego_Carioca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartaTest {
    Carta carta;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        carta = null;
    }
    @Test
    public void calcularPrecio_test01() { // Comprueba si se calcula correctamente el precio de una carta numerada
        carta = new Carta("♠","10");
        carta.calcularPrecio();
        assertEquals(10,carta.getPrecio());
    }
    @Test
    public void calcularPrecio_test02() { // Comprueba si se calcula correctamente el precio de una carta con un precio 10 pero de valor K
        carta = new Carta("♥","K");
        carta.calcularPrecio();
        assertEquals(10,carta.getPrecio());
    }
    @Test
    public void calcularPrecio_test03() { // Comprueba si se calcula correctamente el precio de un As que deberia ser 20
        carta = new Carta("♦","A");
        carta.calcularPrecio();
        assertEquals(20,carta.getPrecio());
    }
    @Test
    public void calcularPrecio_test04() { // Comprueba si se calcula correctamente el precio de un Joker, el cual no posee valor.
        carta = new Carta("JKR","");
        carta.calcularPrecio();
        assertEquals(30,carta.getPrecio());
    }
}