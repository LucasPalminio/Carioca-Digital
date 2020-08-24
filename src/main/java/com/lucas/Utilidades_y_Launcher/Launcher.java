package com.lucas.Utilidades_y_Launcher;

import com.lucas.Carioca_Digital.Juego;
import com.lucas.Carioca_Digital.Reglas;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        Utilidades.setIn(new Scanner(System.in).useDelimiter("\n"));
        menuPrincipal();


    }
    public static void menuPrincipal(){
        Utilidades.imprimirTitulo("BIENVENIDO AL CARIOCA-DIGITAL");
        String[] opciones = {"Comenzar una nueva partida","Settings","Salir"};

        String opcionElegida = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones);
        switch (opcionElegida){
            case "1":
                comenzarNuevaPartida();
                break;
            case "2":
                menuSettings();

                break;
            case "3": //Salir
                System.out.println("Hasta la proxima!");
                return;

            default:
                break;

        }
        menuPrincipal();

    }

    public static void menuSettings(){
        Utilidades.imprimirTitulo("Settings");
        Reglas.menuReglas();
    }
    public static void comenzarNuevaPartida(){
        Juego juego = new Juego();
        juego.empezarJuego();
    }

}
