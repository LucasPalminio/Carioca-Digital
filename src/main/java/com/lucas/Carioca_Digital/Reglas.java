package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

public class Reglas {
    private static boolean[] valoresReglas = {false,false};

    //Regla[0] = Modo DIOS
    // Regla[1] = ¿Si el jugador se bajo , puede sacar carta del pozo?

    public static boolean getValorRegla(int i) {
        return valoresReglas[i];
    }

    public static void setRegla(int i, boolean booleano) {
        valoresReglas[i] = booleano;
    }
    public static void alternarValorRegla(int i){
        valoresReglas[i] = !valoresReglas[i];
    }
    public static boolean isModoDebug(){
        return valoresReglas[0];
    }
    public static void menuReglas(){ //metodo que desplega un menu para cambiar las reglas
        System.out.println("Ingrese el numero de la regla a cambiar: ");
        String[] opciones = {"Modo Dios: "+valoresReglas[0],"¿Si el jugador se bajo , puede sacar carta del pozo?: "+valoresReglas[1],"SALIR"};
        String opcion = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones);
        if(Integer.parseInt(opcion) == opciones.length) //En caso de que la opcion elegida es SALIR
            return; //se detiene la ejecucion del metodo
        else //De lo contrario se modifica la regla ingresada
            alternarValorRegla(Integer.parseInt(opcion)-1);
            menuReglas();

    }

    public static void menuTrampas(Jugador jugadorActual) {
        jugadorActual.imprimirCartas();
        String[] opciones = {"Agregar Carta","Eliminar Carta","Salir"};
        String opcion = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones);
        switch (opcion){
            case "1": //Agregar Carta
                Carta cartaCreada = Carta.crearCarta();
                jugadorActual.agregarCarta(cartaCreada);
                break;
            case "2": // Eliminar Carta
                break;
            case "3":
                System.out.println("");
                return;
            default:
                System.out.println("Error la opcion ingresada es incorrecta, intentelo nuevamente");
        }
    }

}
