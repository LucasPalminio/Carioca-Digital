package com.lucas.Juego_Carioca;

import java.util.ArrayList;
import java.util.Scanner;

public class MainCarioca {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        ArrayList<Jugador> jugadores = ingresarJugadores(); //Se da la bienvenida, ingresa el numero de Jugadores y el nombre de cada Jugador
        int nivelActual = 0;// Se comienza desde la ronda 0 pero en Juego es la ronda 1
        while (nivelActual < Ronda.RONDAS.length) {
            Ronda rondaActual = new Ronda(jugadores, nivelActual);
            rondaActual.comenzarRonda();
            //Agregar: Por cada juego completo, se muestra en pantalla los puntajes de cada jugador y quien lleva la delantera (gana el que tiene menor puntaje)
            nivelActual++;
        }
        //Cuando termine todos los juegos gana el jugador con el menor Puntaje y muestra en pantalla la tabla de Puntajes
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
    public static void imprimirTitulo(String mensaje) {

        System.out.println("\n" + repetirString("/",mensaje.length()));
        System.out.println(mensaje);
        System.out.println(repetirString("/",mensaje.length()) + "\n");
    }

    //Funcion que retorna el numero de jugadores que ingreso el usuario
    private static int ingresarNumeroDeJugadores() {

        int nroJugadores = ingresarUnNumero("Ingrese el numero de Jugadores (debe ser minimo 2): ");
        in.nextLine();
        if (nroJugadores >= 2) {
            return nroJugadores;
        }
        System.out.println("Error: el numero de Jugadores no es valido");
        System.out.println("Por favor Intentelo nuevamente");
        return ingresarNumeroDeJugadores();

    }

    //Este metodo sirve para Cuando se quiere ingresar un numero por terminal,
    // se desplega un mensaje diciendo que numero debe ingresar
    public static int ingresarUnNumero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return in.nextInt();
            } catch (Exception e) {
                System.out.println("Error: usted a introducido un caracter no valiudo: " + e.getMessage());
                System.out.println("Intentelo nuevamente");
                in.nextLine();
            }

        }

    }
    public static int ingresarUnNumero(String mensaje,int min, int max){
        if (min > max){
            int aux = min;
            min = max;
            max = aux;
        }
        int numeroIngresado = ingresarUnNumero(mensaje+"\nIngrese un numero entre "+min+" y "+max);
        if (numeroIngresado<=max && numeroIngresado>=min){
            return numeroIngresado;
        }else{
            System.out.println("El numero ingresado no es el correcto");
            return ingresarUnNumero(mensaje,min,max);
        }
    }
    //Este metodo repite un string una cantidad de veces (es un remplazo al metodo repeat de la clase String)
    public static String repetirString(String stringARepetir,int nro){
        String stringADevolver = "";
        if(nro>0) {
            for (int i = 0; i < nro; i++) {
                stringADevolver += stringARepetir;
            }
        }
        return stringADevolver;

    }

    public static boolean sonNumerosDiferentes(int[] array){
        int[] array2 = array.clone();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if(array[j]==array[i] && i!=j){
                    return false;
                }
            }
        }
        return true;
    }

}


