package com.lucas.guis;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.lucas.Carioca_Digital.Carta;
import com.lucas.Carioca_Digital.Jugador;
import com.lucas.Utilidades_y_Launcher.Utilidades;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BajarseGUI extends JFrame implements ActionListener {
    private JPanel panel1;
    private JList list1;
    private ArrayList<Carta> cartasArrayList;
    private JButton formarTrioButton;
    private JTable escalaTabla;
    private DefaultTableModel escalaTablaModelo;

    private JTable triosTabla;
    private DefaultTableModel triosTablaModelo;
    private JButton abortarBajarseButton;
    private JButton formarEscalaButton;
    private JLabel escalasPorFormarLabel;
    private JLabel triosPorFormarLabel;

    private DefaultListModel modeloLista;
    private Jugador jugadorActual;
    private int escalasAFormar;
    private int triosAFormar;

    private ArrayList<ArrayList<Carta>> matrizTrios;
    private ArrayList<ArrayList<Carta>> matrizEscalas;


    public BajarseGUI(Jugador jugadorActual, int escalasAFormar, int triosAFormar) {
        this.jugadorActual = jugadorActual;

        this.cartasArrayList = new ArrayList<>(jugadorActual.getCartas());

        this.escalasAFormar = escalasAFormar;
        this.triosAFormar = triosAFormar;
        matrizTrios = new ArrayList<>();
        matrizEscalas = new ArrayList<>();

        $$$setupUI$$$();
        formarTrioButton.addActionListener(this);
        formarEscalaButton.addActionListener(this);
        abortarBajarseButton.addActionListener(this);
        this.add($$$getRootComponent$$$());
    }

    public boolean esUnTrio(int[] indicesTrio) {
        String valorEsperado = cartasArrayList.get(indicesTrio[0]).getValor();
        for (int i = 0; i < 3; i++) {
            int ind = indicesTrio[i];
            String valor = cartasArrayList.get(ind).getValor();
            String palo = cartasArrayList.get(ind).getPalo();
            if (!(valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")) {//Si los valores son diferentes
                //Vuelve a preguntar
                System.out.println("Error los indices que ingresaste no corresponde a un trio");
                return false;
            }
        }
        return true;
    }

    public boolean esUnaEscala(int[] indicesEscala) {
        String paloEsperado = cartasArrayList.get(indicesEscala[0]).getPalo(); //Corazon
        String valorEsperado = cartasArrayList.get(indicesEscala[0]).getValor(); //K
        //int indiceValor = Carta.VALORES.toString().indexOf(valorEsperado); //12, En que posicion del arreglo de VALORES esta el valor: K
        int indiceValor = Utilidades.StringArrayindexOf(Carta.VALORES, valorEsperado);

        for (int i = 0; i < 4; i++) {
            int ind = indicesEscala[i];
            Carta carta = cartasArrayList.get(ind);
            String palo = carta.getPalo();
            String valor = carta.getValor();
            if (!(palo.equalsIgnoreCase(paloEsperado) && valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")) {
                //si los indices ingresado no corresponden a una escala, pregunte de nuevo
                return false;
            } else {
                indiceValor++;
                if (indiceValor == Carta.VALORES.length) {
                    indiceValor = 0;
                }
                valorEsperado = Carta.VALORES[indiceValor];
            }
        }
        return true;
    }

    public ArrayList<Carta> bajarTrio() {
        String mensajeDeError = "";
        ArrayList<Carta> trio = new ArrayList<>();
        int[] indicesSeleccionados = list1.getSelectedIndices();
        if (indicesSeleccionados.length == 3) {
            if (esUnTrio(indicesSeleccionados)) { //Si las cartas seleccionadas corresponden a un trio
                for (int indiceCarta : indicesSeleccionados) {
                    trio.add(cartasArrayList.get(indiceCarta));
                    cartasArrayList.remove(indiceCarta);

                }
                actualizarLista();
                return trio;
            } else {
                mensajeDeError = "No ha seleccionado un trio de cartas \nIntentelo Nuevamente";

            }
        } else {
            mensajeDeError = "No ha seleccionado 3 cartas \nIntentelo Nuevamente";
        }
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error: El jugador no selecciono un trio", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    public ArrayList<Carta> bajarEscala() {
        String mensajeDeError = "";
        ArrayList<Carta> escala = new ArrayList<>();
        int[] indicesSeleccionados = list1.getSelectedIndices();
        if (indicesSeleccionados.length == 4) {
            if (esUnaEscala(indicesSeleccionados)) { //Si las cartas seleccionadas corresponden a una escala
                for (int indiceCarta : indicesSeleccionados) {
                    escala.add(cartasArrayList.get(indiceCarta));
                    cartasArrayList.remove(indiceCarta);

                }
                actualizarLista();
                return escala;
            } else {
                mensajeDeError = "No ha seleccionado un trio de cartas \nIntentelo Nuevamente";

            }
        } else {
            mensajeDeError = "No ha seleccionado 4 cartas \nIntentelo Nuevamente";
        }
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error: El jugador no selecciono un trio", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modeloLista = new DefaultListModel();
        for (Carta carta : cartasArrayList) {
            modeloLista.addElement(carta);
        }
        list1 = new JList(modeloLista);

        String[] columnasTriosEnLaMesa = {"Valor", "NroCartas"};
        Object[][] testTriosTabla = {};
        triosTablaModelo = new DefaultTableModel(testTriosTabla, columnasTriosEnLaMesa);
        triosTabla = new JTable(triosTablaModelo);

        String[] columnasEscalasEnLaMesa = {"PrimeraCarta", "Palo", "UltimaCarta", "NroCartas"};
        Object[][] testEscalaTabla = {};
        escalaTablaModelo = new DefaultTableModel(testEscalaTabla, columnasEscalasEnLaMesa);
        escalaTabla = new JTable(escalaTablaModelo);

        escalasPorFormarLabel = new JLabel(String.valueOf(escalasAFormar));
        triosPorFormarLabel = new JLabel(String.valueOf(triosAFormar));


        setBounds(0, 0, 400, 200);
        setLocationRelativeTo(null);
        setTitle("Bajarse: " + jugadorActual.getNombre());
    }

    public void actualizarLista() {
        modeloLista.clear();
        for (Carta carta : cartasArrayList) {
            modeloLista.addElement(carta);
        }
        list1.setModel(modeloLista);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formarTrioButton) {
            ArrayList<Carta> trio = bajarTrio();
            if (trio != null) {
                matrizTrios.add(trio);
                String[] filaTabla = {trio.get(0).getValor(), "3"};

                triosTablaModelo.addRow(filaTabla);
                triosTabla.setModel(triosTablaModelo);

                triosAFormar--;
                triosPorFormarLabel.setText(String.valueOf(triosAFormar));

            }

        }
        if (e.getSource() == formarEscalaButton) {
            ArrayList<Carta> escala = bajarEscala();
            if (escala != null) {
                matrizEscalas.add(escala);
                String[] filaTabla = {escala.get(0).getValor(), "4"};

                escalaTablaModelo.addRow(filaTabla);
                escalaTabla.setModel(escalaTablaModelo);

                escalasAFormar--;
                escalasPorFormarLabel.setText(String.valueOf(escalasAFormar));
            }
        }
        if (e.getSource() == abortarBajarseButton) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está que usted no desea bajarse?", "Abortar Bajarse", JOptionPane.YES_NO_OPTION);
            if (confirmacion == 0) { //Si realmente desea confirmar
                dispose();

            }
        }
        if (matrizEscalas.size() == escalasAFormar && matrizTrios.size() == triosAFormar) {
            jugadorActual.setMatrizEscalas(matrizEscalas);
            jugadorActual.setMatrizTrios(matrizTrios);
            jugadorActual.setCartas(cartasArrayList);

        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setText("Escalas Por Formar");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(escalasPorFormarLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Trios Por Formar");
        panel2.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(triosPorFormarLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, BorderLayout.EAST);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        escalaTabla = new JTable();
        escalaTabla.setPreferredSize(new Dimension(50, 40));
        scrollPane1.setViewportView(escalaTabla);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        triosTabla = new JTable();
        scrollPane2.setViewportView(triosTabla);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel1.add(scrollPane3, BorderLayout.CENTER);
        scrollPane3.setViewportView(list1);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, BorderLayout.SOUTH);
        abortarBajarseButton = new JButton();
        abortarBajarseButton.setText("Abortar Bajarse");
        panel4.add(abortarBajarseButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        formarEscalaButton = new JButton();
        formarEscalaButton.setText("FormarEscala");
        panel4.add(formarEscalaButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        formarTrioButton = new JButton();
        formarTrioButton.setText("Formar Trio");
        panel4.add(formarTrioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
