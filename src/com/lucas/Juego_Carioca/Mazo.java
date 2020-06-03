package com.lucas.Juego_Carioca;


import java.util.ArrayList;
import java.util.Collections;


public class Mazo {
    private ArrayList<Carta> mazo;

    public Mazo() {
        mazo = generarMazoOrdenado(); //generamos un mazo de cartas ordenado
        Collections.shuffle(mazo); //Desordenamos el mazo
        while(mazo.get(0).getPalo().equals("JKR")) Collections.shuffle(mazo); //Esto es para verificar que la primera carta del mazo no sea un JKR
    }

    public ArrayList<Carta> toArrayList() {
        return mazo;
    }

    private static ArrayList<Carta> generarMazoOrdenado() {
        ArrayList<Carta> mazo = new ArrayList<Carta>();
        for (int i = 1; i <= 2; i++) {
            for (String palo : Carta.PALOS) {
                for (String valor : Carta.VALORES) {
                    Carta carta = new Carta(palo, valor);
                    mazo.add(carta);
                }
            }
            mazo.add(new Carta("JKR", " "));
            mazo.add(new Carta("JKR"," "));
        }
        return mazo;
    }

    public int numeroDeCartas(){
        return mazo.size();
    }

    //Cuando se saca una carta del mazo, la funciona retorna carta que esta en la primera posicion
    // y a la vez la elimina del mazo
    public Carta sacarCarta(){
        Carta carta = mazo.get(0);
        mazo.remove(0);
        return carta;
    }

    //La funcion devuelve un ArrayList de cartas con un numero de cartas que saca del mazo
    public ArrayList<Carta> sacarUnNumeroDeCartas(int nroCartas){
        ArrayList<Carta> arrayCartas = new ArrayList<Carta>();
        for (int i = 0; i < nroCartas; i++) {
            arrayCartas.add(mazo.get(0));
            mazo.remove(0);
        }
        return arrayCartas;

    }
    public void devolverCarta(Carta carta){ //En caso de devolver una carta al mazo, este se devuelve y se revuelve el mazo
        mazo.add(carta);
        Collections.shuffle(mazo);
    }
}
