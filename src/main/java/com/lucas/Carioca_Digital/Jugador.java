package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jugador {
    /**
     * Esta clase se encarga de las cartas que posee el jugador, tanto como para asignar el puntaje al final de
     * cada ronda, como verificar si los trios y escalas son coerentes
     *  @param nombre
     * @param cartas
     * @param puntaje
     * @param puntajeRonda
     * @param bajoSuCarta
     * @param yaSacoCarta
     * @param matrizTrios
     * @param matrizEscalas
     * @param NROTRIOS_a_formar
     * @param NROESCALAS_a_formar
     * @param in
     *
     */
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    private int puntajeRonda;
    private boolean bajoSusCarta;
    private boolean yaSacoCarta;
    private ArrayList<ArrayList<Carta>> matrizTrios = new ArrayList<ArrayList<Carta>>(); //Matrices cuando se baja
    private ArrayList<ArrayList<Carta>> matrizEscalas =  new ArrayList<ArrayList<Carta>>();

    public static Scanner in = new Scanner(System.in);

    /**
     * Constructor del jugador, indicando nombre puntaje y el estado de sus cartas
     *
     * @param nombre nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.bajoSusCarta = false;
        this.yaSacoCarta = false;
    }
    /**
     * @return devuelve el nombre del jugador
     */

    public String getNombre() {
        return nombre;
    }
    /**
     * @return devuelve el puntaje del jugador
     */

    public int getPuntaje() {
        return puntaje;
    }
    /**
     * @return devuelve el puntaje de la ronda
     */

    public int getPuntajeRonda() {
        return puntajeRonda;
    }

    /**
     * este metodo calcula el puntaje de la mano del jugador total
     */
    public void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }

    /**
     * este metodo calcula el puntaje de la mano del jugador en cada ronda
     */
    public void calcularPuntajeRonda(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntajeRonda += carta.getPrecio();
            }
        }
    }

    /**
     * @return retorna las cartas que obtiene el jugador
     */
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    /**
     * se instancia la variable PuntajeRonda en la clase actual
     *
     * @param puntajeRonda puntaje obtenido en la ronda actual
     */
    public void setPuntajeRonda(int puntajeRonda) {
        this.puntajeRonda = puntajeRonda;
    }

    /**
     * se instancia el arreglo de cartas en la clase actual
     *
     * @param cartas son las cartas de cada jugador
     */
    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    /**
     * es la carta que agregara el jugador a su mano
     *
     * @param carta es la carta que se encuentra en la mesa
     */
    protected void agregarCarta(Carta carta){
        cartas.add(carta);
    }

    /**
     * @return devuelne el valor de la carta
     */
    public int getNroCartas(){ return cartas.size(); }

    /**
     * @return????
     */
    public boolean isBajoSusCarta() {
        return bajoSusCarta;
    }

    /**
     * se instancia ?? en la clase actual
     *
     * @param bajoSusCarta
     */
    public void setBajoSusCarta(boolean bajoSusCarta) {
        this.bajoSusCarta = bajoSusCarta;
    }

    /**
     * @return luego de sacar una carta se le habilita la opcion de botar carta en el pozo
     */
    public boolean isYaSacoCarta() {
        return yaSacoCarta;
    }

    /**
     * se instancia yaSacoCarta en la clase actual
     *
     * @param yaSacoCarta
     */
    public void setYaSacoCarta(boolean yaSacoCarta) {
        this.yaSacoCarta = yaSacoCarta;
    }

    /**
     * Este metodo funciona solamente cuando finaliza la ronda,su funcion es limpia las matrices
     * de trios y escalas que el jugador tenga en la mesa
     */
    protected void limpiarMatriz(){
        matrizTrios.clear();
        matrizEscalas.clear();
    }

    /**
     * @param indice posicion de la carta en la mano del jugador
     * @return Este metodo se encarga de guardar la carta que selecciono el jugador para botarla en el pozo
     */
    public Carta botarCarta(int indice){
        Carta cartaABotar = cartas.get(indice);
        cartas.remove(indice);
        return  cartaABotar;
    }

    public ArrayList<ArrayList<Carta>> getMatrizTrios() {
        return matrizTrios;
    }

    public ArrayList<ArrayList<Carta>> getMatrizEscalas() {
        return matrizEscalas;
    }

    public void setMatrizTrios(ArrayList<ArrayList<Carta>> matrizTrios) {
        this.matrizTrios = matrizTrios;
    }

    public void setMatrizEscalas(ArrayList<ArrayList<Carta>> matrizEscalas) {
        this.matrizEscalas = matrizEscalas;
    }

    public Object[] getArrayObject(){
        Object[] array = {nombre, this.getNroCartas(), this.getPuntaje(),this.isBajoSusCarta()};
        return array;
    }

    public Object[][] getArrayObjectEscalas(){
        Object[][] data = new Object[matrizEscalas.size()][4]; // Primera Carta, Palo, Ultima Carta, nroCartas
        for (int i = 0; i < matrizEscalas.size(); i++) {
            ArrayList<Carta> escala = matrizEscalas.get(i);
            data[i][0] = escala.get(0).getValor();
            data[i][1] = escala.get(0).getPalo();
            data[i][2]  = escala.get(escala.size()-1).getValor();
            data[i][3] = escala.size();
        }
        return data;
    }

    public Object[][] getArrayObjectTrios(){
        //Valor y Nro Carta
        Object[][] data = new Object[matrizTrios.size()][2];
        for (int i = 0; i < matrizTrios.size(); i++) {
            ArrayList<Carta> trio = matrizTrios.get(i);
            String valor = "";
            for (int j = 0; j < trio.size(); j++) {
                valor = trio.get(j).getValor();
                if (!valor.equals("JKR")) break;

            }
            data[i][0] = valor;
            data[i][1] = trio.size();
        }
        return data;

    }
}