package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;

public class Reglas {
    /**
     *En la clase Reglas es donde el jugador puede eligir que reglas que desea utilizar en el juego. Estas son:
     * Modo DIOS : Al activar esta regla se activa en el menu del jugador un sub-menu para hacer trampas, esto con la finalidad de hacer un debug mas rapido y detectar facilmente fallas (Se esta implementando)
     * ¿Si el jugador se bajo , puede sacar carta del pozo?
     * ¿Existe la posibilidad de bajarse en el primer turno del juego?
     * @param valoresReglas establece que al iniciar el juego, las reglas no esten activadas
     * @param NombreMejorPuntaje es el jugador que obtuvo la menor puntuacion
     * @param MejorPuntaje es el puntaje mas bajo
     */
    private static String NombreMejorPuntaje;
    private static int MejorPuntaje;
    private static boolean[] valoresReglas = {false,false,false};


    /**
     * genera un arreglo de la cantidad de reglas implementadas
     * @param i
     * @return ??
     */
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

    public static String getNombreMejorPuntaje() {
        return NombreMejorPuntaje;
    }

    public static int getMejorPuntaje() {
        return MejorPuntaje;
    }

    public static void setNombreMejorPuntaje(String nombreMejorPuntaje) {
        NombreMejorPuntaje = nombreMejorPuntaje;
    }

    public static void setMejorPuntaje(int mejorPuntaje) {
        MejorPuntaje = mejorPuntaje;
    }
}
