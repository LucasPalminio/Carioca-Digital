package com.lucas.Juego_Carioca;


public class Carta {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    final static public String[] PALOS = {"♠", "♦", "♣", "♥"}; // {pica, diamante, trebol, corazon}
    final static public String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private String palo;
    private String color;
    private String valor;
    private int precio;


    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;
        this.precio = calcularPrecio();
        //Aqui definimos el color de la carta:
        // diamante y corazon es rojo , pica y trebol es negro (pero en terminal es blanco) y el joker es Amarillo
        if (this.palo.equals(PALOS[1]) || this.palo.equals(PALOS[3])) {
            this.color = ANSI_RED;
        } else if (this.palo.equalsIgnoreCase("JKR")) {
            this.color = ANSI_YELLOW;
        }else{
            this.color = ANSI_BLACK;
        }
    }
//  funcion imprimirCarta (Formato antiguo)
//    public void imprimirCarta(){
//        String valorEC = this.color+this.valor+ANSI_RESET; // EC = En Color
//        String paloEC = this.color + this.palo+ANSI_RESET;
//
//        String cartaImagen = "|"+valorEC+"  |\n"; // | 3 caracteres |
//        cartaImagen += "| "+paloEC+" |\n";
//        cartaImagen += "|  "+valorEC+"|\n";
//        System.out.println(cartaImagen);
//
//    }

    //Funcion que devuelve la carta en formato String
    public String toString(){
        return this.valor + " " + this.palo+" "+this.valor;
    }
    //Funcion que devuelve la carta en formato String y con su color ANSII respectivo,
    // ideal para una mejor visualizacion en terminal
    public String toStringEC(){
        return "|"+this.color+toString()+ANSI_RESET+"|";

    }
    //Cada carta tiene un precio,
    // Cuando el juego finaliza y el jugador tiene cartas en mano la suma de los precios de cada carta se adiciona a su puntaje
    //El objetivo de cada juego es ser el primero en quedar sin cartas, por lo tanto el ganador del Carioca es aquel que tenga el menor puntaje
    private int calcularPrecio() {
        //Aqui importa el valor de la carta no el paloque pertenece
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

    public String getPalo() {
        return palo;
    }

    public String getValor() {
        return valor;
    }

    public int getPrecio() {
        return precio;
    }


}
