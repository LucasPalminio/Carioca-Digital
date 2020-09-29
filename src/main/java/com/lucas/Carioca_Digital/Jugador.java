package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    private int puntajeRonda;
    private boolean bajoSusCarta;
    private boolean yaSacoCarta;
    private ArrayList<ArrayList<Carta>> matrizTrios = new ArrayList<ArrayList<Carta>>(); //Matrices cuando se baja
    private ArrayList<ArrayList<Carta>> matrizEscalas =  new ArrayList<ArrayList<Carta>>();

    public static Scanner in = new Scanner(System.in);

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.bajoSusCarta = false;
        this.yaSacoCarta = false;
    }

    public String getNombre() {
        return nombre;
    }
    public int getPuntaje() {
        return puntaje;
    }

    public int getPuntajeRonda() {
        return puntajeRonda;
    }

    public void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }
    public void calcularPuntajeRonda(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntajeRonda += carta.getPrecio();
            }
        }
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setPuntajeRonda(int puntajeRonda) {
        this.puntajeRonda = puntajeRonda;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
    protected void agregarCarta(Carta carta){
        cartas.add(carta);
    }
    public int getNroCartas(){ return cartas.size(); }

    public boolean isBajoSusCarta() {
        return bajoSusCarta;
    }
    public void setBajoSusCarta(boolean bajoSusCarta) {
        this.bajoSusCarta = bajoSusCarta;
    }

    public boolean isYaSacoCarta() {
        return yaSacoCarta;
    }

    public void setYaSacoCarta(boolean yaSacoCarta) {
        this.yaSacoCarta = yaSacoCarta;
    }

    protected void limpiarMatriz(){
        //Este metodo funciona solamente cuando finaliza la ronda,
        // su funcion es limpia las matrices de trios y escalas que el jugador tenga en la mesa
        matrizTrios.clear();
        matrizEscalas.clear();
    }

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