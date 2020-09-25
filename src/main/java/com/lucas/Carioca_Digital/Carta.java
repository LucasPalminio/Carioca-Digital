package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;


public class Carta extends JLabel{
    // Colores que se usaran para algunos textos

    // Todos las posibilidades de palos y valores de las cartas
    final static public String[] PALOS_SIMBOLOS = {"♠", "♦", "♣", "♥"}; // {pica, diamante, trebol, corazon}
    final static public String[] PALOS = {"S", "D", "C", "H"};
    //C = trebol, D = diamente, H = Corazon, S = Pica ; Esto es por su inicial en ingles
    final static public String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private final String palo;
    private final String color;
    //public static final ImageIcon imagenCartaAtras = new ImageIcon("src//images//cartas//blue_back.png");

    public static final int WIDTH = 74; //Ancho de la carta
    public static final int HEIGHT = 98; //Alto de la carta
    private final String valor;
    private final int precio;
    private ImageIcon imagenCarta;



    public Carta(String palo, String valor) {
        this.palo = palo;
        this.valor = valor;

        setSize(WIDTH+20,HEIGHT+20);
        setVisible(true);
        setText("");

        Border border = this.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        this.setBorder(new CompoundBorder(border, margin));

        this.precio = calcularPrecio();
        String rutaImagen = "src//images//cartas//" + valor+palo+".png";
        try {
            imagenCarta = new ImageIcon(ImageIO.read(new File(rutaImagen)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
            setIcon(imagenCarta);
            setOpaque(true);
        }catch (IOException e){
            System.err.println("Error "+toString()+": "+e.getMessage());
        }

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

    public ImageIcon getImagenCarta() {
        return imagenCarta;
    }



    //Funcion que devuelve la carta en formato String
    public String toString(){
        String simbolo = "?";
        if (this.palo.equals("JKR")){
            simbolo = "JKR";
        }else {
            for (int i = 0; i < PALOS.length; i++) {
                if (PALOS[i].equals(this.palo)) {
                    simbolo = PALOS_SIMBOLOS[i];
                    break;
                }
            }
        }
        return this.valor + " " + simbolo+" "+this.valor;
    }
    //Funcion que devuelve la carta en formato String y con su color ANSII respectivo,
    // ideal para una mejor visualizacion en terminal
    protected String toStringEC(){
        return "|"+this.color+toString()+Utilidades.ANSI_RESET+"|";

    }
    //Cada carta tiene un precio,
    // Cuando el juego finaliza y el jugador tiene cartas en mano la suma de los precios de cada carta se adiciona a su puntaje
    //El objetivo de cada juego es ser el primero en quedar sin cartas, por lo tanto el ganador del Carioca es aquel que tenga el menor puntaje
    public int calcularPrecio() {
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

    public static Carta menuCrearCarta(){
        String[] pintas = new String[5];
        for (int i = 0; i < PALOS.length; i++) {
            pintas[i] = PALOS[i];

        }
        pintas[4] = "JKR";
        System.out.println("Seleccione una pinta: ");
        int indicePinta = Integer.parseInt(Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(pintas)) - 1;

        if (indicePinta == 4){
            return new Carta("JKR","");
        }
        String paloElegido = pintas[indicePinta];
        System.out.println("Seleccione el valor: ");
        for (String valor:VALORES) {
            Carta c = new Carta(PALOS[indicePinta],valor);
            System.out.println(c.toStringEC());
        }
        System.out.print("Ingrese un valor entre 2 y el 10 ó una A,J,Q,K : ");
        String valorElegido = Utilidades.tecladoNext();
        if (Utilidades.isNumeric(valorElegido)){
            int valorNumerico = Integer.parseInt(valorElegido);
            if (valorNumerico>=2 && valorNumerico<=10)
                return new Carta(paloElegido,valorElegido);
        }else if(valorElegido.equals("A") || valorElegido.equals("J") || valorElegido.equals("Q") || valorElegido.equals("K"))
            return new Carta(paloElegido,valorElegido);
        System.out.println("Error el valor ingresado es incorrecto, intentelo nuevamente");
        return menuCrearCarta();



    }
}

