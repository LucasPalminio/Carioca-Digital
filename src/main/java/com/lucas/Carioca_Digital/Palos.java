package com.lucas.Carioca_Digital;

public enum Palos {
    PICA("♠"),
    CORAZON("♥"),
    TREBOL("♣"),
    DIAMANTE("♦");
    String palo;
    Palos(String palo){
        this.palo = palo;
    }
    protected String getPalo(){
        return palo;
    }
}
