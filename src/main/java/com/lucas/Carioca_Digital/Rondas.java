package com.lucas.Carioca_Digital;

import java.util.ArrayList;

public enum Rondas {
    PRIMERA_RONDA(0,2),
    SEGUNDA_RONDA(1,1),
    TERCERA_RONDA(2,0),
    CUARTA_RONDA(0,3),
    QUINTA_RONDA(1,2),
    SEXTA_RONDA(2,1);
    int[] trios_y_escalas;
    Rondas(int trios, int escalas){
        this.trios_y_escalas = new int[]{trios, escalas};
    }
    public int[] getTrios_y_escalas(){
        return trios_y_escalas;
    }
}
