package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;

public class Reglas {
    private static boolean[] valoresReglas = {false,false,false};

    //Regla[0] = Modo DIOS
    //Regla[1] = ¿Si el jugador se bajo , puede sacar carta del pozo?
    //Regla[2] = ¿Existe la posibilidad de bajarse en el primer turno del juego?
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

    public static void menuTrampas(Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        jugadorActual.imprimirCartas();
        String[] opciones = {"Agregar Carta al jugador Actual","Eliminar Carta al jugador Actual","Mostrar las cartas de los demas Jugadores","Salir"};
        String opcion = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones);
        switch (opcion){
            case "1": //Agregar Carta al jugador Actual
                Carta cartaCreada = Carta.crearCarta();
                jugadorActual.agregarCarta(cartaCreada);
                break;
            case "2": // Eliminar Carta al jugador Actual
                System.out.println("Usted desea eliminar una carta");
                jugadorActual.menu_BotarCarta();
                //jugadorActual.imprimirCartas();
                //int indice = Utilidades.ingresarUnNumero("Ingrese el indice de la carta que desea Eliminar: ",1,jugadorActual.getNroCartas());
                break;
            case "3": //Mostrar las cartas de los demas Jugadores
                for (Jugador jugador:jugadores) {
                    if(jugadorActual != jugador) { //Para evitar mostrar las cartas del JugadorActual dos veces
                        jugador.imprimirCartas();
                    }
                }

                break;
            case "4":
                System.out.println("");
                return;
            default:
                System.out.println("Error la opcion ingresada es incorrecta, intentelo nuevamente");
        }
        menuTrampas(jugadorActual,jugadores);
    }

}
