package com.lucas.Juego_Carioca;

import java.util.ArrayList;
import java.util.Scanner;

public class MainCarioca {
    static Scanner in = new Scanner(System.in);
    public static final Mazo mazo = new Mazo();

    public static final int[][] rondas = {
            //{Escala, Trio} ; 6,7,8,9,10,11
            {0, 2}, // 0*4+2*3 = 6 (2 trios)
            {1, 1}, //1*4+1*3 = 7 (1 escala y 1 trio)
            {2, 0}, // 2 escalas
            {0, 3}, // 3 trios
            {1, 2}, // 1 escala y 2 trios
            {2, 1} //2 escalas y 1 trio
    };

    public static void main(String[] args) {

        ArrayList<Jugador> jugadores = ingresarJugadores(); //Se da la bienvenida, ingresa el numero de Jugadores y el nombre de cada Jugador
        int rondaActual = 0;// Se comienza desde la ronda 0 pero en Juego es la ronda 1
        while (rondaActual < rondas.length) {
            menuJuego(rondaActual, jugadores);
            //Agregar: Por cada juego completo, se muestra en pantalla los puntajes de cada jugador y quien lleva la delantera (gana el que tiene menor puntaje)
            rondaActual++;
        }
        //Cuando termine todos los juegos gana el jugador con el menor Puntaje y muestra en pantalla la tabla de Puntajes
    }

    private static void menuJuego(int nivelActual, ArrayList<Jugador> jugadores) {
        //Se crea un juego
        int nroEscala = rondas[nivelActual][0];
        int nroTrios = rondas[nivelActual][1];
        Mazo mazo = new Mazo();

        //Antes de comenzar a jugar, Entregamos a cada jugador doce cartas
        for (int index = 0; index < jugadores.size(); index++) {
            jugadores.get(index).setCartas(mazo.sacarUnNumeroDeCartas(12));
        }

        Carta cartaEnLaMesa = mazo.sacarCarta(); //Se extrae una carta del mazo para dejarla en la mesa
        int turnoActual = (int) Math.random() * jugadores.size(); //y el primer turno se hara de forma aleatoria

        //Imprimimos un mensaje que da comienzo el juego y cuantas escalas o/y trios hay que formar
        imprimirTitulo("Comienza el juego nivel " + (nivelActual + 6) + "\n"
                + "Escala: " + nroEscala + " Trios: " + nroTrios);
        //Aqui comienza el juego, deberia haber un bucle donde va jugando cada jugador,
        //el juego termina cuando un jugador se queda sin cartas

        //Por el momento hay un bucle for para ver las cartas de cada jugador (Temporal)
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugadorActual = jugadores.get(turnoActual);
            System.out.println("Turno Actual: " + turnoActual + " Nombre: " + jugadorActual.getNombre());
            System.out.println();
            System.out.println("Carta en la mesa: " + cartaEnLaMesa.toStringEC());
            System.out.println();
            System.out.println("Cartas del Jugador: ");
            jugadorActual.imprimirCartas();
            turnoActual++;
            if (turnoActual == jugadores.size()) {
                turnoActual = 0;
            }


        }
    }

    //Este es el menu se muestra solamente una vez y es cuando se comienza jugar el Carioca
    //Basicamente en esta funcion se ingresa el numero y nombre de cada Jugador (Se crean los Jugadores)
    // y retorna un ArrayList de los objetos de la clase Jugador
    private static ArrayList<Jugador> ingresarJugadores() {
        imprimirTitulo("Bienvenido al juego del carioca");
        //System.out.print("Ingrese el numero de jugadores a jugar");
        int numeroJugadores = ingresarNumeroDeJugadores();
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        for (int i = 1; i <= numeroJugadores; i++) {
            System.out.print("Ingrese el nombre del Jugador " + i + ": ");
            String nombre = in.nextLine();
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;

    }

    //Para imprimir mensajes importantes, Titulos
    private static void imprimirTitulo(String mensaje) {

        System.out.println("\n" + "/".repeat(mensaje.length()));
        System.out.println(mensaje);
        System.out.println("/".repeat(mensaje.length()) + "\n");
    }

    //Funcion que retorna el numero de jugadores que ingreso el usuario
    private static int ingresarNumeroDeJugadores() {
        int nroJugadores = 0;
        while (true) {
            try {
                System.out.print("Ingrese el numero de Jugadores (debe ser minimo 3): ");

                nroJugadores = in.nextInt();
                in.nextLine();
                if (nroJugadores >= 3) {
                    return nroJugadores;
                } else {
                    System.out.println("Error: el numero de Jugadores no es valido");

                }

            } catch (Exception e) {
                System.out.println("Error: usted a introducido un caracter no valiudo: " + e.getMessage());
                in.nextLine();
            }
            System.out.println("Por favor Intentelo nuevamente");

        }

    }


}


