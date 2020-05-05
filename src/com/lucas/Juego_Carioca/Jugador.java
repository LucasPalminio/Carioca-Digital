package com.lucas.Juego_Carioca;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
    }
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public int getPuntaje() {
        return puntaje;
    }
    public void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void imprimirCartas(){
        String contenido = "";
        String primeraLinea = "";
        String segundaLinea = "";
        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            primeraLinea += carta.toStringEC();
            int numeroEspacios = (carta.toString().length()+2)/2;
            segundaLinea += " ".repeat(numeroEspacios)+i+" ".repeat(numeroEspacios);
        }
        contenido = primeraLinea + "\n" + segundaLinea;
        System.out.println(contenido);
    }
}
