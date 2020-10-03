package com.lucas.carioca_digital;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Clase más básica que se utiliz para jugar carioca
 */

public class Carta extends JLabel {
    /**
     * Son los simbolos de los palos de los naipes ingleses pica, diamante, trebol, corazon
     */
    final static public String[] PALOS_SIMBOLOS = {"♠", "♦", "♣", "♥"};
    /**
     * Son los simbolos de los palos de los naipes ingleses pica, diamante, trebol, corazon
     */
    final static public String[] PALOS = {"S", "D", "C", "H"};
    /**
     * Son los valores de cada carta
     */

    final static public String[] VALORES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    /**
     * Palo de la carta
     */

    private final String palo;
    /**
     * Ancho de la carta
     */
    public static final int WIDTH = 74;
    /**
     * Alto de la carta
     */
    public static final int HEIGHT = 98;
    /**
     * Son los numeros o letra de la carta
     */
    private final String valor;
    /**
     * Puntaje de la carta (si es numero es ese valor. Si es J= Q= K=10, A=20, JKR=30)
     */

    private final int precio;
    /**
     * Es la imagen que le corresponde a  cada carta
     */
    private ImageIcon imagenCarta;



    /**
     * se establece el valor y palo a cada carta, ademas de sus dimensiones y color
     * @param palo otorga una clase de palo (corazon, pica, trebol, diamante) pertenese la carta
     * @param valor otorg un valor numerico a cada carta
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
     * @deprecated
     * @return carta en formato String
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
     * si la carta es una J,Q o K, el precio es de 10,
     * si la carta es una A el precio en 20,
     * si la carta en un JOKER (JKR) tambien conocido comodin el precio es 30
     * @return precio de la carta no el palo ni el valor al que pertenece
     */
    public int calcularPrecio() {

        for (int i = 2; i <= 10; i++) {

            if (valor.equalsIgnoreCase(Integer.toString(i))) {
                return i;

            }
        }

        if (valor.equals("J") || valor.equals("Q") || valor.equals("K")) {
            return 10;

        }

        if (valor.equals("A")) {
            return 20;

        }

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

