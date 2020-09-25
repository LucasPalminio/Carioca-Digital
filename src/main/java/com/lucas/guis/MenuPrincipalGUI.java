package com.lucas.guis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI extends JFrame implements ActionListener {
    private JButton jugarUnaPartidaButton;
    private JButton mejoresPuntajesButton;
    private JButton configuracionButton;
    private JButton salirButton;
    private JPanel panelMain;
    private JLabel logo;
    private JLabel tituloLabel;
    protected menuJuego jugar = new menuJuego();
    protected menuDebug debug = new menuDebug();
    protected tableroDePuntuaciones puntaje = new tableroDePuntuaciones();
    ImageIcon imagenLogo = new ImageIcon("src//images//varios//logoPrincipal.png");


    public MenuPrincipalGUI() {

        add(panelMain);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        logo.setIcon(imagenLogo);
        jugarUnaPartidaButton.addActionListener(this);
        mejoresPuntajesButton.addActionListener(this);
        configuracionButton.addActionListener(this);
        salirButton.addActionListener(this);
    }

    public static void main(String[] args) {
        new MenuPrincipalGUI().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == configuracionButton) {
            this.setVisible(false);

        }
        if (e.getSource() == configuracionButton) {
            this.setVisible(false);

        }
        if (e.getSource() == configuracionButton) {
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

    public menuJuego getJugar() {
        return jugar;
    }

    public menuDebug getDebug() {
        return debug;
    }

    public tableroDePuntuaciones getPuntaje() {
        return puntaje;
    }
}
