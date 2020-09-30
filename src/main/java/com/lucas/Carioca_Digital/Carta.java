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


public class Carta extends JLabel {
    /**
     * Esta clase se encarga de de asignar los valores, el palo a las cartas
     * @param PALOS_SIMBOLOS son los símbolos de los palos de los naipes ingleses pica, diamante, trebol, corazon
     * @param PALOS definición Es la inicial de cada palo (en ingles) C = trébol, D = diamante, H = Corazon, S = Pica
     * @param Valores son los valores de cada carta
     * @param palo de la carta
     * @param WIDTH Ancho de la carta
     * @param HEIGHT Alto de la carta
     * @param valor son los números o letra de la carta
     * @param precio puntaje de la carta (si es numero es ese valor. Si es J= Q= K= A= JKR=30)
     * @param imagenCarta es la imagen que le corresponde a  cada carta
     */
    // Colores que se usaran para algunos textos

    // Todos las posibilidades de palos y valores de las cartas
    final static public String[] PALOS_SIMBOLOS = {"♠", "♦", "♣", "♥"};
    final static public String[] PALOS = {"S", "D", "C", "H"};

    final static public String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private final String palo;

    public static final int WIDTH = 74;
    public static final int HEIGHT = 98;
    private final String valor;
    private final int precio;
    private ImageIcon imagenCarta;



    /**
     * se establece el valor y palo a cada carta, ademas de sus dimensiones y color
     * @param palo otorga una clase de palo (corazon, pica, trebol, diamante) pertenese la carta
     * @param valor otorga un valor numérico a cada carta
     */
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

    }

    /**
     *
     * @return Esta función que devuelve la carta en formato String
     */

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
    /**
     * Se importa el valor de la carta no el palo al que pertenece
     * Cada carta tiene un precio:
     * si la carta es una J,Q o K, el precio es de 10
     * si la carta es una A el precio en 20
     * si la carta en un JOKER (JKR) también conocido comodín el precio es 30
     * @return precio de la carta no el palo ni el valor al que pertenece
     */
    public int calcularPrecio() {

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

    /**
     *
     * @return Que palo corresponde la carta
     */
    public String getPalo() {
        return palo;
    }
    /**
     *
     * @return Que valor corresponde la carta
     */
    public String getValor() {
        return valor;
    }

    /**
     *
     * @return Que precio corresponde la carta
     */
    protected int getPrecio() {
        return precio;
    }

}

