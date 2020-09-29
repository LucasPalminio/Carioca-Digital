package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;

public class Reglas {
    private static String NombreMejorPuntaje;
    private static int MejorPuntaje;
    private static boolean[] valoresReglas = {false,false,false};

    //La clase Reglas es una clase con atributos y métodos estáticos
    //donde el jugador puede eligir que reglas que desea utilizar en el juego, hasta el momento se tiene pensado implementar 3 reglas:
    //Regla[0] = Modo DIOS : Al activar esta regla se activa en el menu del jugador un sub-menu para hacer trampas, esto con la finalidad de hacer un debug mas rapido y detectar facilmente fallas (Se esta implementando)
    //Regla[1] = ¿Si el jugador se bajo , puede sacar carta del pozo? (Aun NO implementado)
    //Regla[2] = ¿Existe la posibilidad de bajarse en el primer turno del juego? (Aun NO implementado)
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
