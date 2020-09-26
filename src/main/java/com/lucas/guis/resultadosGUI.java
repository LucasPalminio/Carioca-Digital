package com.lucas.guis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class resultadosGUI extends JFrame implements ActionListener {


    private JPanel panel1;
    private JLabel puntaje1Label;
    private JLabel puntaje2Label;
    private JLabel puntaje3Label;
    private JLabel puntaje4Label;
    private JButton volverResultadosButton;
    private JLabel jugador3Label;
    private JLabel jugador4Label;
    private JLabel iconoLabel;
    ImageIcon icono = new ImageIcon("src//images//varios//cartasMoviendose.gif");
    int[] puntajes;

    public resultadosGUI(int[] puntajes){
        add(panel1);
        iconoLabel.setIcon(icono);
        this.puntajes = puntajes;
        puntaje1Label.setText(String.valueOf(puntajes[0]));
        puntaje2Label.setText(String.valueOf(puntajes[1]));
        if(puntajes.length<3){
            puntaje3Label.setVisible(false);
            jugador3Label.setVisible(false);
        }else{
            puntaje3Label.setText(String.valueOf(puntajes[2]));
        }
        if(puntajes.length <4){
            puntaje4Label.setVisible(false);
            jugador4Label.setVisible(false);
        }else{
            puntaje3Label.setText(String.valueOf(puntajes[3]));
        }
        this.pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        volverResultadosButton.addActionListener(this);


    }

    public static void main(String[] args){
        int[] ejemplo = new int[]{2,3,4};
        new resultadosGUI(ejemplo).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == volverResultadosButton){
            new MenuPrincipalGUI().setVisible(true);
            this.dispose();
        }

    }
}
