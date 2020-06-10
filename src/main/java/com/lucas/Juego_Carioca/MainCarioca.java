package com.lucas.Juego_Carioca;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainCarioca {
    public static Scanner in;
    public static ByteArrayInputStream input;
    private static int nivelActual = 0;
    private static int ultimoNivel = 1;

    public static void main(String[] args) {
//        String stringInput = "2\nLucas Palminio\nLorenzo\n";
//        entradaAProbar(stringInput);
        in = new Scanner(System.in).useDelimiter("\n");
        ArrayList<Jugador> jugadores = ingresarJugadores(); //Se da la bienvenida, ingresa el numero de Jugadores y el nombre de cada Jugador
        ingresarNivelAJugar();// Se ingresa desde que nivel hasta que nivel desea Jugar

        while (nivelActual <= ultimoNivel) {
            Ronda rondaActual = new Ronda(jugadores, nivelActual);
            rondaActual.comenzarRonda();
            //Agregar: Por cada juego completo, se muestra en pantalla los puntajes de cada jugador y quien lleva la delantera (gana el que tiene menor puntaje)
            nivelActual++;
        }

        //Cuando termine todos los juegos gana el jugador con el menor Puntaje y muestra en pantalla la tabla de Puntajes
    }

    public static void entradaAProbar(String stringInput) {
        input = new ByteArrayInputStream(stringInput.getBytes());
        in = new Scanner(input).useDelimiter("\n");

//        System.out.println(in.nextInt());
//        System.out.println(tecladoNext());
//        System.out.println(tecladoNext());
//        System.out.println(".");
        //System.setIn(input);

    }


    /////Test/////
    /*public static void main(String[] args) {
        Jugador jugador1 = new Jugador("Tester trios o escalas");
        Jugador jugador2 = new Jugador("Tester trio o escalas 2");
        ArrayList<Carta> cartasJugador1 = new ArrayList<>();
        for (int i = 0; i <3; i++) {
            cartasJugador1.add(new Carta("♠","3"));
            cartasJugador1.add(new Carta("♦","2"));
        }

        cartasJugador1.add(new Carta("JKR",""));
        ArrayList<Carta> cartasJugador2 = new ArrayList<>();
        cartasJugador2 = (ArrayList<Carta>) cartasJugador1.clone();
        jugador1.setCartas(cartasJugador1);
        jugador2.setCartas(cartasJugador2);
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        Ronda ronda1 = new Ronda(jugadores,0);
        ronda1.comenzarRonda();
}*/
    //Este es el menu se muestra solamente una vez y es cuando se comienza jugar el Carioca
    //Basicamente en esta funcion se ingresa el numero y nombre de cada Jugador (Se crean los Jugadores)
    // y retorna un ArrayList de los objetos de la clase Jugador

    //Al comienzo del juego, se llama este metodo para ingresar el nro de jugadores y sus respectivos nombres
    public static ArrayList<Jugador> ingresarJugadores() {
        imprimirTitulo("Bienvenido al juego del carioca");
        //System.out.print("Ingrese el numero de jugadores a jugar");
        int numeroJugadores = ingresarNumeroDeJugadores();
        ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
        for (int i = 1; i <= numeroJugadores; i++) {
            System.out.print("Ingrese el nombre del Jugador " + i + ": ");
            String nombre = in.next();
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;

    }

    //Posterior a eso, se llama a este metodo que pregunta al usuario desde que nivel hasta que nivel desea jugar
    private static void ingresarNivelAJugar() {
        imprimirTitulo("Niveles");
        //Creo las opciones del menu
        String[] opciones = new String[Ronda.RONDAS.length];
        for (int i = 0; i < Ronda.RONDAS.length; i++) {
            opciones[i] = Ronda.RONDAS[i][0] + " escalas, " + Ronda.RONDAS[i][1] + " trios";
        }
        imprimirSoloMenuOpciones(opciones);
        System.out.println();
        String mensajeDeConfirmacion;
        nivelActual = ingresarUnNumero("Ingrese el primer nivel que desea Jugar", 1, Ronda.RONDAS.length) - 1;
        if (nivelActual == Ronda.RONDAS.length - 1) { //Si el jugador quiere jugar el ultimo nivel, no es necesario preguntar hasta que nivel va a jugar
            ultimoNivel = nivelActual;
            mensajeDeConfirmacion = "Usted quiere jugar solamente el ultimo nivel: " + Ronda.RONDAS[ultimoNivel][0] + " escalas y " + Ronda.RONDAS[ultimoNivel][1] + " trios.";
        } else {
            ultimoNivel = ingresarUnNumero("Ingrese hasta que nivel desea Jugar", nivelActual + 1, Ronda.RONDAS.length) - 1;
            mensajeDeConfirmacion = "Usted desea Jugar desde el nivel " + (nivelActual + 1) + " hasta el nivel " + (ultimoNivel + 1);
        }
        if (!confirmarOpcion(mensajeDeConfirmacion)) { //Si el jugador se equivoco, este el momento para arrepentirse
            ingresarNivelAJugar();
        }
    }

    //Para imprimir mensajes importantes, Titulos
    public static void imprimirTitulo(String mensaje) {

        System.out.println("\n" + repetirString("/", mensaje.length()));
        System.out.println(mensaje);
        System.out.println(repetirString("/", mensaje.length()) + "\n");
    }

    //Funcion que retorna el numero de jugadores que ingreso el usuario
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


    //Este método sirve para ingresar algo por teclado, esta entrada se formatea y se devuelve como cadena String
    public static String tecladoNext() {
        String entrada = in.next();
        entrada.replaceAll("\n", "");
        entrada = entrada.toString();
        if (entrada.equalsIgnoreCase("") || entrada.equalsIgnoreCase(" ")) {
            tecladoNext();
        }
        return entrada;
    }

    //Este metodo sirve para Cuando se quiere ingresar un numero por terminal,
    // se desplega un mensaje diciendo que numero debe ingresar
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

    //Parecido al metodo anterior pero debe ingresar un numero dentro de un rango
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

    //Este metodo repite un string una cantidad de veces (es un remplazo al metodo repeat de la clase String)
    public static String repetirString(String stringARepetir, int nro) {
        String stringADevolver = "";
        if (nro > 0) {
            for (int i = 0; i < nro; i++) {
                stringADevolver += stringARepetir;
            }
        }
        return stringADevolver;

    }

    //Este metodo se ingresa un arreglo de numeros y verifica si todo estos numeros son diferentes
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

    //Este metodo imprime un menu de opciones (esas opciones se ingresa como arreglo de string)
    // y devuelve la opcion elegida (que es un numero entre 1 hasta n opciones) en una cadena String
    public static String imprimirMenuOpciones_e_ingresarUnaOpcion(String[] opciones) {
        imprimirSoloMenuOpciones(opciones);
        String opcion = String.valueOf(ingresarUnNumero("", 1, opciones.length));
        System.out.println();
        return opcion;
    }

    //Solo imprime solo el menu de opciones
    public static void imprimirSoloMenuOpciones(String[] opciones) {
        //Imprime el menu de opciones pero las opciones comienzan desde el 1
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("(" + (i + 1) + ") " + opciones[i]);
        }
    }

    //Este metodo es para preguntarle al usuario por una decision irreversible (si esta seguro de la opcion elegida)
    // si esta seguro de su decision devuelve un true sino devuelve un falso
    public static boolean confirmarOpcion(String mensaje) {

//        if (in.hasNextLine()){//Para limpiar el buffer
//            //in.next();
//        }
        System.out.println(mensaje);
        System.out.println("¿Usted esta seguro de su eleccion?, \nEscriba " + Carta.ANSI_RED + "si" + Carta.ANSI_RESET + " para confirmar, de lo contrario escriba otra palabra para cancelar la operacion");
        return tecladoNext().equalsIgnoreCase("si");
    }


}


