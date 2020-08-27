package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

public class MenuPrincipal {
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
                System.out.println("Hasta la próxima!");
                return;

            default:
                break;

        }
        menuPrincipal();

    }

    public static void menuSettings(){
        Utilidades.imprimirTitulo("Settings");
        Reglas.menuModificarReglas();
    }
    public static void comenzarNuevaPartida(){
        Juego juego = new Juego();
        juego.empezarJuego();
    }
}
