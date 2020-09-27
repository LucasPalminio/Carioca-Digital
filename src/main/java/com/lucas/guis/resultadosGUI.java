package com.lucas.guis;

import com.lucas.Carioca_Digital.Jugador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JLabel jugador1Label;
    private JLabel jugador2Label;
    ImageIcon icono = new ImageIcon("src//images//varios//cartasMoviendose.gif");
    ArrayList<Integer> puntajes;
    ArrayList<Jugador> jugadores;

    public resultadosGUI(ArrayList<Jugador> jugadores, ArrayList<Integer> puntajes){
        add(panel1);
        iconoLabel.setIcon(icono);
        this.puntajes = puntajes;
        puntaje1Label.setText(String.valueOf(puntajes.get(0)));
        puntaje2Label.setText(String.valueOf(puntajes.get(1)));
        jugador1Label.setText(jugadores.get(0).getNombre());
        jugador2Label.setText(jugadores.get(1).getNombre());
        if(puntajes.size()<3){
            puntaje3Label.setVisible(false);
            jugador3Label.setVisible(false);
        }else{
            puntaje3Label.setText(String.valueOf(puntajes.get(2)));
            jugador3Label.setText(jugadores.get(2).getNombre());
        }
        if(puntajes.size() <4){
            puntaje4Label.setVisible(false);
            jugador4Label.setVisible(false);
        }else{
            puntaje4Label.setText(String.valueOf(puntajes.get(3)));
            jugador4Label.setText(jugadores.get(3).getNombre());
        }
        this.pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        volverResultadosButton.addActionListener(this);


    }

    public static void main(String[] args){
        ArrayList<Integer> ejemplo = new ArrayList<>();
        ejemplo.add(2);
        ejemplo.add(3);
        ejemplo.add(4);
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Pedro"));
        jugadores.add(new Jugador("Jose"));
        jugadores.add(new Jugador("Juan"));
        new resultadosGUI(jugadores,ejemplo).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() == volverResultadosButton){
            new MenuPrincipalGUI().setVisible(true);
            this.dispose();
        }

    }
}
