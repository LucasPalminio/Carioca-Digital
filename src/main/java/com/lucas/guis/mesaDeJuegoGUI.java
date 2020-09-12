package com.lucas.guis;

import com.lucas.Carioca_Digital.Carta;

import javax.swing.*;
import java.awt.*;

public class mesaDeJuegoGUI extends JFrame{
    private JPanel panel;

    public mesaDeJuegoGUI() {
        this.setBounds(0,0,1024,800);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new mesaDeJuegoGUI().setVisible(true);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        Carta carta = new Carta("H","4",30,30);
        carta.paint(g);

    }
}
