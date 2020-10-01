package com.lucas.carioca_digital;


import java.util.ArrayList;
import java.util.Collections;


public class Mazo {
    private final ArrayList<Carta> mazo;

    /**
     * Se genera un mazo de cartas ordenado, para luego ser desordenados
     */
    protected Mazo() {
        mazo = generarMazoOrdenado(); //
        Collections.shuffle(mazo); //
        while(mazo.get(0).getPalo().equals("JKR")) {Collections.shuffle(mazo); }//Esto es para verificar que la primera carta del mazo no sea un JKR
    }

    /**
     *
     * @return  utilizar el mazo como arraylist
     */
    protected ArrayList<Carta> toArrayList() {
        return mazo;
    }
    /**
     * Este metodo es para generar un mazo ordenado
     * @return  2 barajas de cartas inglesas y 4 comodines(108 cartas en total)
     */
    private static ArrayList<Carta> generarMazoOrdenado() {
        ArrayList<Carta> mazo = new ArrayList<Carta>();
        for (int i = 1; i <= 2; i++) {
            for (String palo : Carta.PALOS) {
                for (String valor : Carta.VALORES) {
                    Carta carta = new Carta(palo, valor);
                    mazo.add(carta);
                }
            }
            mazo.add(new Carta("JKR", ""));
            mazo.add(new Carta("JKR",""));
        }
        return mazo;
    }
    /**
     * Este metodo devuelve el numero de cartas que hay en el mazo
     * @return
     */
    protected int numeroDeCartas(){
        return mazo.size();
    }

    /**
     * Cuando se saca una carta del mazo, el metodo retorna carta que se encuentra en la primera posicion
     * y a la vez la elimina del mazo
     * @return
     */
    protected Carta sacarCarta(){
        Carta carta = mazo.get(0);
        mazo.remove(0);
        return carta;
    }

    /**
     *
     * @param nroCartas
     * @return devuelve un ArrayList de cartas con un numero de cartas sacadas del mazo
     */
    protected ArrayList<Carta> sacarUnNumeroDeCartas(int nroCartas){
        ArrayList<Carta> arrayCartas = new ArrayList<Carta>();
        for (int i = 0; i < nroCartas; i++) {
            arrayCartas.add(mazo.get(0));
            mazo.remove(0);
        }
        return arrayCartas;

    }

    /**
     *     Cuando el mazo se encuentre vacío se usará este método para transferir todas las cartas del pozo
     *     excepto la primera al mazo y se mezclan
     *     @param pozo son las cartas desechadas por los jugadores al final de cada turno
     */

    protected void mezclarConPozo(ArrayList<Carta> pozo) {
        int largoOriginalDelPozo = pozo.size(); // Por si el largo del pozo varía durante el for()
        for (int i = largoOriginalDelPozo - 1; i > 0; i--) {
            mazo.add(pozo.get(i));
            pozo.remove(i);
        }
        Collections.shuffle(mazo);
    }

    /**
     *
     * @param i indice de la carta deseada que esta en el mazo
     * @return la carta botada
     */
    protected Carta getCarta(int i){
        return mazo.get(i);
    }

    /**
     *
     * @param i indice de la carta desasda que sera desechada
     */
    protected void removeCarta(int i){
        mazo.remove(i);
    }

    /**
     *
     * @param i indice de la carta desasda que sera añadida a la mano del jugador
     */
    protected void addCarta(Carta i){
        mazo.add(i);
    }
}
