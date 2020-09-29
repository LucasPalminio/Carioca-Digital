package com.lucas.Carioca_Digital;


import java.util.ArrayList;
import java.util.Collections;


public class Mazo {
    private ArrayList<Carta> mazo;

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
     * EL metodo  devuelve un ArrayList de cartas con un numero de cartas sacadas del mazo
     * @param nroCartas
     * @return
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
     * En caso de devolver una carta al mazo, este se devuelve y se revuelve el mazo
     * @param carta
     */
    protected void devolverCarta(Carta carta){
        mazo.add(carta);
        Collections.shuffle(mazo);
    }
    /**
     *     Cuando el mazo se encuentre vacío se usará este método para transferir todas las cartas del pozo
     *     excepto la primera al mazo y se mezclan
     *     @param pozo
     */
    // Cuando el mazo se encuentre vacío se usará este método para transferir todas las cartas del pozo excepto la primera al mazo y se mezclan
    protected void mezclarConPozo(ArrayList<Carta> pozo) {
        int largoOriginalDelPozo = pozo.size(); // Por si el largo del pozo varía durante el for()
        for (int i = largoOriginalDelPozo - 1; i > 0; i--) {
            mazo.add(pozo.get(i));
            pozo.remove(i);
        }
        Collections.shuffle(mazo);
    }
    protected Carta getCarta(int i){
        return mazo.get(i);
    }
    protected void removeCarta(int i){
        mazo.remove(i);
    }
    protected void addCarta(Carta i){
        mazo.add(i);
    }
}
