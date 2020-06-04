package com.lucas.Juego_Carioca;

import java.util.ArrayList;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    public static Scanner in = new Scanner(System.in);
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

    public void imprimirCartas(){ //Este metodo imprime las cartas del jugador
        String contenido = "";
        String primeraLinea = "";
        String segundaLinea = "";
        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            primeraLinea += carta.toStringEC();
            int numeroEspacios = (carta.toString().length()+2)/2;
            segundaLinea += MainCarioca.repetirString(" ",numeroEspacios) +i+MainCarioca.repetirString(" ",numeroEspacios);
        }
        contenido = primeraLinea + "\n" + segundaLinea;
        System.out.println(contenido);
    }
    public void menu_SacarCarta(ArrayList<Carta> pozo, Mazo mazo){
        System.out.println("(1) Sacar Carta de la mesa");
        System.out.println("(2) Sacar Carta del mazo ");
        System.out.print("Eliga una opcion: ");
        String opcion = in.nextLine();
        switch (opcion){
            case "1": //Sacar Carta de la mesa
                Carta cartaEnLaMesa = pozo.get(0);
                cartas.add(cartaEnLaMesa);
                pozo.remove(0);
                cartaEnLaMesa = menu_BotarCarta();
                pozo.add(0,cartaEnLaMesa);
                break;
            case "2": //Sacar Carta del mazo
                Carta cartaDelMazo = mazo.sacarCarta();
                System.out.println("obtuviste esta carta del mazo: "+cartaDelMazo.toStringEC());
                cartas.add(cartaDelMazo);
                pozo.add(0,menu_BotarCarta());
                break;
            default:
                System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                menu_SacarCarta(pozo,mazo);

        }
    }

    private Carta menu_BotarCarta(){
        imprimirCartas();
        int indiceCarta = MainCarioca.ingresarUnNumero("¿Que cartas quieres botar?: ");
        if (indiceCarta >= 0 && indiceCarta < cartas.size()){
            Carta cartaABotar = cartas.get(indiceCarta);
            cartas.remove(indiceCarta);
            return cartaABotar;
        }else{
            System.out.println("Error el numero ingresado esta fuera del rango, intentelo nuevamente");
            return menu_BotarCarta();
        }

    }

}
