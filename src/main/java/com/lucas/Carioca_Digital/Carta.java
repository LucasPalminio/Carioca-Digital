package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;

public class Carta {
    // Colores que se usaran para algunos textos

    // Todos las posibilidades de palos y valores de las cartas
    final static public String[] PALOS = {"♠", "♦", "♣", "♥"}; // {pica, diamante, trebol, corazon}
    final static public String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private final String palo;
    private final String color;
    private final String valor;
    private final int precio;


    protected Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
        this.precio = calcularPrecio();
        //Aqui definimos el color de la carta:
        // diamante y corazon es rojo , pica y trebol es negro (pero en terminal es blanco) y el joker es Amarillo
        if (this.palo.equals(PALOS[1]) || this.palo.equals(PALOS[3])) {
            this.color = Utilidades.ANSI_RED;
        } else if (this.palo.equalsIgnoreCase("JKR")) {
            this.color = Utilidades.ANSI_YELLOW;
        }else{
            this.color = Utilidades.ANSI_BLACK;
        }
    }

    //Funcion que devuelve la carta en formato String
    public String toString(){
        return this.valor + " " + this.palo+" "+this.valor;
    }
    //Funcion que devuelve la carta en formato String y con su color ANSII respectivo,
    // ideal para una mejor visualizacion en terminal
    protected String toStringEC(){
        return "|"+this.color+toString()+Utilidades.ANSI_RESET+"|";

    }
    //Cada carta tiene un precio,
    // Cuando el juego finaliza y el jugador tiene cartas en mano la suma de los precios de cada carta se adiciona a su puntaje
    //El objetivo de cada juego es ser el primero en quedar sin cartas, por lo tanto el ganador del Carioca es aquel que tenga el menor puntaje
    protected int calcularPrecio() {
        //Aqui importa el valor de la carta no el palo al que pertenece
        for (int i = 2; i <= 10; i++) {
            //el precio de la cartas con numero es ese numero
            if (valor.equalsIgnoreCase(Integer.toString(i))) {
                return i;

            }
        }
        // si la carta es una J,Q o K, el precio es de 10
        if (valor.equals("J") || valor.equals("Q") || valor.equals("K")) {
            return 10;

        }
        // si la carta es una A el precio en 20
        if (valor.equals("A")) {
            return 20;

        }
        //si la carta en un JOKER (JKR) tambien conocido comodin el precio es 30
        if (palo.equalsIgnoreCase("JKR")) {
            return 30;
        }
        return 0;

    }

    protected String getPalo() {
        return palo;
    }

    protected String getValor() {
        return valor;
    }

    protected int getPrecio() {
        return precio;
    }


}
