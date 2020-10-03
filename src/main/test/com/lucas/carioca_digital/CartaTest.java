package com.lucas.carioca_digital;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartaTest {
    /**
     *
     */
    Carta carta;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        carta = null;
    }

    /**
     * Este test comprueba si se calcula correctamente el precio de una carta numerada.
     * En este caso se escogió la carta 10 de pica, y se espera que el programa devuelva el precio de 10
     */
    @Test
    public void calcularPrecio_test01() { //
        carta = new Carta("♠","10");
        carta.calcularPrecio();
        assertEquals(10,carta.getPrecio());
    }

    /**
     *  Este test comprueba si el código calcula correctamente el precio de una carta con un precio 10 pero de valor K
     */
    @Test
    public void calcularPrecio_test02() { //
        carta = new Carta("♥","K");
        carta.calcularPrecio();
        assertEquals(10,carta.getPrecio());
    }

    /**
     *Este test comprueba si se calcula correctamente el precio de un As que debería ser 20
     */
    @Test
    public void calcularPrecio_test03() {
        carta = new Carta("♦","A");
        carta.calcularPrecio();
        assertEquals(20,carta.getPrecio());
    }
    @Test

    /**
     * Este test comprueba si se calcula correctamente el precio de un Joker, el cual no posee valor.
     */
    public void calcularPrecio_test04() {
        carta = new Carta("JKR","");
        carta.calcularPrecio();
        assertEquals(30,carta.getPrecio());
    }
}