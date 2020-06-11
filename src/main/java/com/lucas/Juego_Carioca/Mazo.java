package com.lucas.Juego_Carioca;


import java.util.ArrayList;
import java.util.Collections;


public class Mazo {
    private ArrayList<Carta> mazo;

    public Mazo() {
        mazo = generarMazoOrdenado(); //generamos un mazo de cartas ordenado
        Collections.shuffle(mazo); //Desordenamos el mazo
        while(mazo.get(0).getPalo().equals("JKR")) {Collections.shuffle(mazo); }//Esto es para verificar que la primera carta del mazo no sea un JKR
    }

    public ArrayList<Carta> toArrayList() {
        return mazo; //este metodo es para utilizar el mazo como arraylist
    }
    //Este metodo es para generar un mazo ordenado conformado por 2 barajas de cartas inglesas y 4 comodines: 108 cartas en total
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
    //Este metodo devuelve el numero de cartas que hay en el mazo
    public int numeroDeCartas(){
        return mazo.size();
    }

    //Cuando se saca una carta del mazo, el metodo retorna carta que esta en la primera posicion
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
    //En caso de devolver una carta al mazo, este se devuelve y se revuelve el mazo
    public void devolverCarta(Carta carta){
        mazo.add(carta);
        Collections.shuffle(mazo);
    }
    // Cuando el mazo se encuentre vacío se usará este método para transferir todas las cartas del pozo excepto la primera al mazo y se mezclan
    public void mezclarConPozo(ArrayList<Carta> pozo){
        int largoOriginalDelPozo = pozo.size(); // Por si el largo del pozo varía durante el for()
        for(int i= largoOriginalDelPozo-1;i>0;i--){
            mazo.add(pozo.get(i));
            pozo.remove(i);
        }
        Collections.shuffle(mazo);
    }
    public Carta getCarta(int i){
        return mazo.get(i);
    }
    public void removeCarta(int i){
        mazo.remove(i);
    }
    public void addCarta(Carta i){
        mazo.add(i);
    }
}
