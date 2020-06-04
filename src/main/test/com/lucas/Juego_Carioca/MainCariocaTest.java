package com.lucas.Juego_Carioca;


import org.junit.*;

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

}