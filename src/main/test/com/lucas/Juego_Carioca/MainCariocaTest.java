package com.lucas.Juego_Carioca;


import org.junit.*;
//import sun.tools.jar.Main;


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
    public void ingresarJugadoresTest(){
        //Este test ingresa una letra cuando el metodo pedia en realidad, el metodo maneja bien la excepcion (nose cayo) y posterior puede ingresar los datos de cada jugador
        String stringInput = "e\n2\n"+"Lucas\nLorenzo";
        MainCarioca.entradaAProbar(stringInput);

        ArrayList<Jugador> jugadoresEsperado = new ArrayList<>();
        jugadoresEsperado.add(new Jugador("Lucas"));
        jugadoresEsperado.add(new Jugador("Lorenzo"));
        ArrayList<Jugador> jugadoresReal = MainCarioca.ingresarJugadores();
        for (int i = 0; i < 2; i++) {
            assertEquals(jugadoresEsperado.get(i).getNombre(),jugadoresReal.get(i).getNombre());
        }


    }
    @Test
    public void repetirString_test1() {
        String esperado = "//////";
        String real = MainCarioca.repetirString("/",6);
        assertEquals(esperado,real);
        esperado=null;
        real=null;
    }
    @Test
    public void repetirString_test2() {
        String esperado = "";
        String real = MainCarioca.repetirString("/",0);
        assertEquals(esperado,real);
        esperado=null;
        real=null;
    }
    @Test
    public void repetirString_test3() {
        String esperado = "";
        String real = MainCarioca.repetirString("/",-5);
        assertEquals(esperado,real);
        esperado=null;
        real=null;
    }
    @Test
    public void sonNumerosDiferentes_test1() {
        int[] test = {1,2,3,3};
        boolean esperado = false;
        assertEquals(esperado,MainCarioca.sonNumerosDiferentes(test));
    }
    @Test
    public void sonNumerosDiferentes_test2() {
        int[] test = {1,2,3,4};
        boolean esperado = true;
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