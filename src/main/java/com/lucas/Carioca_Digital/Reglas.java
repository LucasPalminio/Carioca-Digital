package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;

public class Reglas {
    /**
     * En la clase Reglas es donde el jugador puede eligir que reglas que desea utilizar en el juego. Estas son:
     *- Modo DIOS :Al activar esta regla se activa en el menú del jugador un submenú para hacer trampas, esto con la
     * finalidad de hacer un debug más rápido y detectar fácilmente fallas (Se esta implementando)
     * -¿Si el jugador se bajo , puede sacar carta del pozo?
     * -¿Existe la posibilidad de bajarse en el primer turno del juego?
     *
     * @param valoresReglas establece que al iniciar el juego, las reglas no esten activadas
     * @param NombreMejorPuntaje es el jugador que obtuvo la menor puntuacion
     * @param MejorPuntaje es el puntaje mas bajo
     */
    private static String NombreMejorPuntaje;
    private static int MejorPuntaje;
    private static boolean[] valoresReglas = {false, false, false};


    /**
     * @param i
     * @return genera un arreglo de la cantidad de reglas implementadas
     */
    public static boolean getValorRegla(int i) {
        return valoresReglas[i];
    }

    /**
     * @param i  posicion de la regla en la tabla
     * @param booleano verifica si la regla esta activada o no
     */
    public static void setRegla(int i, boolean booleano) {
        valoresReglas[i] = booleano;
    }

    /**
     * @param i
     */
    public static void alternarValorRegla(int i) {
        valoresReglas[i] = !valoresReglas[i];
    }

    /**
     * @return
     */
    public static boolean isModoDebug() {
        return valoresReglas[0];
    }


    /**
     *
     * @return muestra el nombre del jugador con menor puntaje
     */
    public static String getNombreMejorPuntaje() {
        return NombreMejorPuntaje;
    }

    /**
     *
     * @return muestra el puntaje del jugador con menor puntaje
     */
    public static int getMejorPuntaje() {
        return MejorPuntaje;
    }

    /**
     * Se establece el nombre del jugador que obtuvo el mejor puntaje
     * @param nombreMejorPuntaje Es el jugador con el menor puntaje
     */
    public static void setNombreMejorPuntaje(String nombreMejorPuntaje) {
        NombreMejorPuntaje = nombreMejorPuntaje;
    }

    /**
     *  Se establece el puntaje mejor puntaje entre los jugadores
     * @param mejorPuntaje
     */
    public static void setMejorPuntaje(int mejorPuntaje) {
        MejorPuntaje = mejorPuntaje;
    }
}
