package com.lucas.Juego_Carioca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MazoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testMazo_primeraCarta(){
        Mazo mazo = new Mazo();
        Carta cartaQueNoDebeSalir = new Carta("JKR","");
        assertNotEquals(cartaQueNoDebeSalir.getPalo(),mazo.toArrayList().get(0).getPalo());


    }
}