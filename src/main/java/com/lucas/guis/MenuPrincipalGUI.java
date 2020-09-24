package com.lucas.guis;

import com.lucas.Carioca_Digital.Carta;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuPrincipalGUI extends JFrame implements ActionListener {
    private JButton jugarUnaPartidaButton;
    private JButton mejoresPuntajesButton;
    private JButton modoDebugButton;
    private JButton salirButton;
    private JPanel panelMain;
    private JLabel logo;
    ImageIcon imagenLogo = new ImageIcon("src//images//varios//Logo_carioca.png");


    public MenuPrincipalGUI() {
        add(panelMain);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        logo.setIcon(imagenLogo);
        jugarUnaPartidaButton.addActionListener(this);
        mejoresPuntajesButton.addActionListener(this);
        modoDebugButton.addActionListener(this);
        salirButton.addActionListener(this);
    }

    public static void main(String[] args) {
        new MenuPrincipalGUI().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modoDebugButton) {
            this.setVisible(false);

        }
        if (e.getSource() == modoDebugButton) {
            this.setVisible(false);

        }
        if (e.getSource() == modoDebugButton) {
            this.setVisible(false);

        }
        if (e.getSource() == salirButton) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir realmente?");
            if (respuesta == 0) {
                System.exit(0);
            }

        }
    }

    private void createUIComponents(){
        // TODO: place custom component creation code here

    }
}
