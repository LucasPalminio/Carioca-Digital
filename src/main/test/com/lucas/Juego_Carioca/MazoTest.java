package com.lucas.Juego_Carioca;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MazoTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMazo_primeraCarta() {
        Mazo mazo = new Mazo();
        Carta cartaQueNoDebeSalir = new Carta("JKR", "");
        assertNotEquals(cartaQueNoDebeSalir.getPalo(), mazo.toArrayList().get(0).getPalo());


    }

    @Test
    public void testmMezclarConPozo() {
        Mazo mazo = new Mazo();
        ArrayList<Carta> pozo = new ArrayList<>();
        ArrayList<Carta> aux = new ArrayList<>();
        for (int a = 0; a < mazo.numeroDeCartas(); a++) {
            pozo.add(mazo.getCarta(a));
            mazo.removeCarta(a);


        }
        for(int i=0;mazo.numeroDeCartas()!=0;i++) {
            for (int a = 0; a < mazo.numeroDeCartas(); a++) {

                mazo.removeCarta(a);


            }
        }
        aux.add(pozo.get(0));
        mazo.mezclarConPozo(pozo);
        Assert.assertEquals(1,pozo.size());
        Assert.assertEquals(53,mazo.numeroDeCartas());
        Assert.assertEquals(aux.get(0),pozo.get(0));

    }
}