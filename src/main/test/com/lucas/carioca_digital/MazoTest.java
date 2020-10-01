package com.lucas.carioca_digital;

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
        // Verifica que al generar el mazo y mezclarlo no quede un Joker en primera posición, ya que teoricamente
        // deberia revolver el mazo otra vez si ese es el caso hasta que la primera carta no sea un Joker
        Mazo mazo = new Mazo();
        Carta cartaQueNoDebeSalir = new Carta("JKR", "");
        assertNotEquals(cartaQueNoDebeSalir.getPalo(), mazo.toArrayList().get(0).getPalo());


    }

    @Test //Test cuando el mazo se queda sin cartas y agregamos las cartas al pozo
    public void testMezclarConPozo() {
        //En este test creamos un mazo y un pazo, luego vaciamos el mazo,
        Mazo mazo = new Mazo();
        ArrayList<Carta> pozo = new ArrayList<>();
        ArrayList<Carta> aux = new ArrayList<>();
        //Vaciamos mazo y agregarlo intencionalmente al pozo
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
        // Comprueba si el pozo posea una sola carta
        Assert.assertEquals(1,pozo.size());
        //Comprueba si el mazo posee el resto de las cartas
        Assert.assertEquals(53,mazo.numeroDeCartas());
        // Comprueba si el la carta "encima"(en la primera posicion) del pozo es igual a la que tenia
        // anterior a mezclarse con el mazo
        Assert.assertEquals(aux.get(0),pozo.get(0));

    }
}