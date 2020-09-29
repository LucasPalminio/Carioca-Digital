package com.lucas.Utilidades_y_Launcher;

import com.lucas.Carioca_Digital.Jugador;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Utilidades {
    /**
     * @param in se encarga de leer lo escrito por el usario
     * @param input
     * @param ANSI_BLACK es el color negro en en ANSI
     * @param ANSI_RED es el color rojo en ANSI
     * @param ANSI_YELLOW es el color amarillo en ANSI
     * @param ANSI_RESET  este color es gris en ANSI, se encarga de resetear el color
     */
    private static Scanner in;
    private static ByteArrayInputStream input;
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";


    /**
     *
     * @param stringInput
     */
    public static void entradaAProbar(String stringInput) {
        input = new ByteArrayInputStream(stringInput.getBytes());
        in = new Scanner(input).useDelimiter("\n");

//        System.out.println(in.nextInt());
//        System.out.println(tecladoNext());
//        System.out.println(tecladoNext());
//        System.out.println(".");
        //System.setIn(input);

    }
    /**
     *
     * @return muestra lo escrito por el usuario
     */
    public static Scanner getIn() {
        return in;
    }

    public static void setIn(Scanner in) {
        Utilidades.in = in;
    }
    /////Test: PARA CREAR UN JUEGO ARREGLADO Y ASI PROBAR FACILMENTE/////
    /*public static void main(String[] args) {
        Jugador jugador1 = new Jugador("Tester trios o escalas");
        Jugador jugador2 = new Jugador("Tester trio o escalas 2");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            cartasJugador1.add(new Carta("♠","3"));
            cartasJugador1.add(new Carta("♦","2"));
        }

        cartasJugador1.add(new Carta("JKR",""));
        ArrayList<Carta> cartas2 = new ArrayList<>();
        cartasJugador2 = (ArrayJugadorList<Carta>) cartasJugador1.clone();
        jugador1.setCartas(cartasJugador1);
        jugador2.setCartas(cartasJugador2);
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Ronda ronda1 = new Ronda(jugadores,0);
        ronda1.comenzarRonda();
}*/



    /**
     * Este es el menu se muestra solamente una vez y es cuando se comienza jugar el Carioca.
     * Al comienzo del juego, se llama este metodo para ingresar el nro de jugadores y sus respectivos nombres
     * Basicamente en esta funcion se ingresa el numero y nombre de cada Jugador (Se crean los Jugadores)
     * @return  ArrayList de los objetos de la clase Jugador
     */
    public static ArrayList<Jugador> ingresarJugadores() {
        Utilidades.imprimirTitulo("Ingrese los Jugadores");

        int numeroJugadores = ingresarNumeroDeJugadores();
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        for (int i = 1; i <= numeroJugadores; i++) {
            System.out.print("Ingrese el nombre del Jugador " + i + ": ");
            String nombre = tecladoNext();
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;

    }

    //Posterior a eso, se llama a este metodo que pregunta al usuario desde que nivel hasta que nivel desea jugar


    /**
     * Para imprimir mensajes importantes, como por ejemplo titulos
     * @param mensaje es el mensaje que se quiere mostrar
     */
    public static void imprimirTitulo(String mensaje) {

        System.out.println("\n" + repetirString("/", mensaje.length()));
        System.out.println(mensaje);
        System.out.println(repetirString("/", mensaje.length()) + "\n");
    }


    /**
     *
     * @return retorna el numero de jugadores que ingreso el usuario
     */
    private static int ingresarNumeroDeJugadores() {

        int nroJugadores = ingresarUnNumero("Ingrese el numero de Jugadores (debe ser minimo 2 y maximo 4): ");
        //in.nextLine();
        if (nroJugadores >= 2 && nroJugadores <= 4) {
            return nroJugadores;
        }
        System.out.println("Error: el numero de Jugadores no es valido");
        System.out.println("Por favor Intentelo nuevamente");
        return ingresarNumeroDeJugadores();

    }



    /**
     *  Sirve para ingresar algo por teclado
     * @return  devuelve lo ingresado por teclado como cadena String
     */
    public static String tecladoNext() {
        String entrada = in.next();
        entrada.replaceAll("\n", "");
        entrada = entrada.toString();
        if (entrada.equalsIgnoreCase("") || entrada.equalsIgnoreCase(" ")) {
            tecladoNext();
        }
        return entrada;
    }


    /**
     * Sirve para Cuando se quiere ingresar un numero por terminal, se desplega un mensaje diciendo que
     * numero debe ingresar
     * @param mensaje asereje
     //* @param min valor minimo que se puede ingresar
     //* @param max valor maximo que se puede ingresar
     * @return ??
     */
    public static int ingresarUnNumero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(tecladoNext());
            } catch (Exception e) {
                System.out.println("Error: usted a introducido un caracter no valido: " + e);
                System.out.println("Intentelo nuevamente");
                in.nextLine();
            }

        }

    }


    /**
     * Este metodo sirve para Cuando se quiere ingresar un numero por terminal, ingresando
     * un numero dentro de un rango
     * @param mensaje ??
     * @param min nuemro maximo aceptado
     * @param max nuemro maximo aceptado
     * @return
     */
    public static int ingresarUnNumero(String mensaje, int min, int max) {
        if (min > max) {
            int aux = min;
            min = max;
            max = aux;
        }
        int numeroIngresado = ingresarUnNumero(mensaje + "\nIngrese un numero entre " + min + " y " + max + ": ");
        if (numeroIngresado <= max && numeroIngresado >= min) {
            return numeroIngresado;
        } else {
            System.out.println("El numero ingresado no es el correcto");
            return ingresarUnNumero(mensaje, min, max);
        }
    }


    /**
     *Este metodo repite un string una cantidad de veces (es un remplazo al metodo repeat de la clase String)
     * @param stringARepetir
     * @param nro
     * @return
     */
    public static String repetirString(String stringARepetir, int nro) {
        String stringADevolver = "";
        if (nro > 0) {
            for (int i = 0; i < nro; i++) {
                stringADevolver += stringARepetir;
            }
        }
        return stringADevolver;

    }



    /**
     * Este metodo se ingresa un arreglo de numeros
     * @param array
     * @return verificacion si los numeros son diferentes
     */
    public static boolean sonNumerosDiferentes(int[] array) {
        int[] array2 = array.clone();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (array[j] == array[i] && i != j) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * imprime un menu de opciones (esas opciones se ingresa como arreglo de string)
     * @param opciones son las opciones que se pueden elegir
     * @return Devuelve la opcion elegida (que es un numero entre 1 hasta n opciones) en una cadena String
     */
    public static String imprimirMenuOpciones_e_ingresarUnaOpcion(String[] opciones) {
        imprimirSoloMenuOpciones(opciones);
        String opcion = String.valueOf(ingresarUnNumero("", 1, opciones.length));
        System.out.println();
        return opcion;
    }
    public static String imprimirMenuOpciones_e_ingresarUnaOpcion(ArrayList<String> opciones) {
        imprimirSoloMenuOpciones(opciones);
        String opcion = String.valueOf(ingresarUnNumero("", 1, opciones.size()));
        System.out.println();
        return opcion;
    }



    /**
     * Solo imprime solo el menu de opciones
     * @param opciones
     */
    public static void imprimirSoloMenuOpciones(String[] opciones) {
        //Imprime el menu de opciones pero las opciones comienzan desde el 1
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("(" + (i + 1) + ") " + opciones[i]);
        }
    }
    public static void imprimirSoloMenuOpciones(ArrayList<String> opciones) {
        //Imprime el menu de opciones pero las opciones comienzan desde el 1
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println("(" + (i + 1) + ") " + opciones.get(i));
        }
    }

    /**
     * Se encarga de preguntar al usuario por una decision irreversible
     * (si esta seguro de la opcion elegida)
     * @param mensaje ??
     * @return si esta seguro de su decision devuelve un true sino devuelve un falso
     */
    public static boolean confirmarOpcion(String mensaje) {

//        if (in.hasNextLine()){//Para limpiar el buffer
//            //in.next();
//        }
        System.out.println(mensaje);
        System.out.println("¿Usted esta seguro de su eleccion?, \nEscriba " + ANSI_RED + "si" + ANSI_RESET + " para confirmar, de lo contrario escriba otra palabra para cancelar la operacion");
        return tecladoNext().equalsIgnoreCase("si");
    }

    /**
     *
     * @param array
     * @param target
     * @return
     */
    public static int StringArrayindexOf(String[] array,String target){
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)){
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param strNum
     * @return
     * @throws NumberFormatException nfe  ocurre cuando intenta hacer algo como convertir un String
     * en un valor numérico, como un int, float, double, long, etc.
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}


