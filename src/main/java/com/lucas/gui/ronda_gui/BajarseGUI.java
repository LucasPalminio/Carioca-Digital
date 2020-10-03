package com.lucas.gui.ronda_gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.lucas.carioca_digital.Carta;
import com.lucas.carioca_digital.Ronda;
import com.lucas.utilidad.Utilidad;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class BajarseGUI extends JFrame implements ActionListener {
    /**
     * Mesa a la cual esta ventana está vinculada para enviar las escalas o trios bajados
     */

    private final MesaGUI mesaDeJuego;
    /**
     * Ronda que actualmente se está jugando
     */
    private final Ronda ronda;
    /**
     * Panel principal que despliega esta GUI
     */
    private JPanel panel1;
    /**
     * Lista donde se mostrarán las cartas de la mano del jugador actual
     */
    private JList<Carta> list1;
    /**
     * Cartas del jugador actual
     */
    private final ArrayList<Carta> cartasArrayList;
    /**
     * Botón usado para formar un trío con las cartas seleccionadas
     */
    private JButton formarTrioButton;
    /**
     * Tabla que contiene las escalas formadas por el jugador
     */
    private JTable escalaTabla;
    /**
     * Modelo usado para crear la escalaTabla
     */
    private DefaultTableModel escalaTablaModelo;
    /**
     * Tabla que contiene los tríos formados por el jugador
     */
    private JTable triosTabla;
    /**
     * Modelo usado para crear la triosTabla
     */
    private DefaultTableModel triosTablaModelo;
    /**
     * Botón que permite anular la acción de bajarse y vuelve a la MesaGUI
     */
    private JButton abortarBajarseButton;
    /**
     * Botón que permite formar escalas válidas a partir de las cartas seleccionadas de la mano
     */
    private JButton formarEscalaButton;
    /**
     * Etiqueta que representa las escalas a formar necesarias en la ronda actual
     */
    private JLabel escalasPorFormarLabel;
    /**
     * Etiqueta que representa los tríos a formar necesarios en la ronda actual
     */
    private JLabel triosPorFormarLabel;
    /**
     * Botón que permite subir de posición las cartas de la mano
     */
    private JButton subirButton;
    /**
     * Botón que permite bajar de posición las cartas de la mano
     */
    private JButton bajarButton;
    /**
     * Modelo que sirve para formar la lista que contiene las cartas de la mano del jugador actual
     */
    private DefaultListModel modeloLista;
    /**
     * Escalas a formar en la ronda actual
     */
    private int escalasAFormar;
    /**
     * Trios a formar en la ronda actual
     */
    private int triosAFormar;
    /**
     * Matriz que contiene los tríos que se han formado
     */

    private final ArrayList<ArrayList<Carta>> matrizTrios;
    /**
     * Matriz que contiene las escalas que se han formado
     */
    private final ArrayList<ArrayList<Carta>> matrizEscalas;


    /**
     *  Este método crea la ventana para bajarse
     * @param mesaDeJuego
     */
    public BajarseGUI(MesaGUI mesaDeJuego) {
        this.mesaDeJuego = mesaDeJuego;
        this.ronda = mesaDeJuego.getRonda();


        this.cartasArrayList = new ArrayList<>(ronda.getJugadorActual().getCartas());

        this.escalasAFormar = ronda.getNROESCALAS_A_FORMAR();
        this.triosAFormar = ronda.getNROTRIOS_A_FORMAR();
        matrizTrios = new ArrayList<>();
        matrizEscalas = new ArrayList<>();

        $$$setupUI$$$();
        formarTrioButton.addActionListener(this);
        formarEscalaButton.addActionListener(this);
        abortarBajarseButton.addActionListener(this);
        subirButton.addActionListener(this);
        bajarButton.addActionListener(this);
        this.add($$$getRootComponent$$$());
    }

    /**
     * Este método verifica  que las cartas que conforman a un trio sean de igual valor
     * @param trio Son las cartas que conforman a un trio
     * @return una escala formada
     */
    public boolean esUnTrio(ArrayList<Carta> trio) {
        String valorEsperado = trio.get(0).getValor();
        for (int i = 0; i < 3; i++) {

            String valor = trio.get(i).getValor();
            String palo = trio.get(i).getPalo();
            if (!(valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")) {//Si los valores son diferentes
                //Vuelve a preguntar
                System.out.println("Error los indices que ingresaste no corresponde a un trio");
                return false;
            }
        }
        return true;
    }

    /**
     * Este método verifica  que las cartas que conforman a la escala sean del mismo palo
     * @param escala Son las cartas que conforman a la escala
     * @return una escala formada
     */
    public boolean esUnaEscala(ArrayList<Carta> escala) {

        String paloEsperado = escala.get(0).getPalo(); //Corazon
        String valorEsperado = escala.get(0).getValor(); //K
        //FALTA VERIFICAR SI LA PRIMERA CARTA ES UN JKR
        //int indiceValor = Carta.VALORES.toString().indexOf(valorEsperado); //12, En que posicion del arreglo de VALORES esta el valor: K
        int indiceValor = Utilidad.StringArrayindexOf(Carta.VALORES, valorEsperado);

        for (int i = 0; i < 4; i++) {

            Carta carta = escala.get(i);
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

    /**
     * Este método ocupa al método "esUnTrio" para verificar si las cartas son iguales
     * @return en caso de que las cartas seleccionadas sean correctas se creara un trio y en caso contrario el programa
     * avisara que hay un problema
     */
    public ArrayList<Carta> bajarTrio() {
        String mensajeDeError = "";
        ArrayList<Carta> trio = (ArrayList<Carta>) list1.getSelectedValuesList();
        
        if (triosAFormar > 0) {
            if (trio.size() == 3) {
                if (esUnTrio(trio)) { //Si las cartas seleccionadas corresponden a un trio
                    for (Carta cartaAeliminar : trio) {
                        cartasArrayList.remove(cartaAeliminar);
                    }
                    actualizarLista();
                    return trio;
                } else {
                    mensajeDeError = "No ha seleccionado un trio de cartas \nIntentelo Nuevamente";

                }
            } else {
                mensajeDeError = "No ha seleccionado 3 cartas \nIntentelo Nuevamente";
            }
        } else {
            mensajeDeError = "No puede seguir construyendo Trios";
        }
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error: El jugador no selecciono un trio", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    /**
     * Este método ocupa al método "esUnaEscala" para verificar si las cartas son de igual palo
     * @return en caso de que las cartas seleccionadas sean correctas se creara una escala y en caso contrario el programa
     * avisara que hay un problema
     */
    public ArrayList<Carta> bajarEscala() {
        String mensajeDeError = "";
        ArrayList<Carta> escala = (ArrayList<Carta>) list1.getSelectedValuesList();

        if (escalasAFormar > 0) {
            if (escala.size() == 4) {
                if (esUnaEscala(escala)) { //Si las cartas seleccionadas corresponden a una escala
                    for (Carta cartaAeliminar : escala) {
                        cartasArrayList.remove(cartaAeliminar);
                    }
                    actualizarLista();
                    return escala;
                } else {
                    mensajeDeError = "No ha seleccionado una escala de cartas \nIntentelo Nuevamente";
                }
            } else {
                mensajeDeError = "No ha seleccionado 4 cartas \nIntentelo Nuevamente";
            }
        } else {
            mensajeDeError = "No puede seguir construyendo escalas";
        }
        //revisar
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error: El jugador no selecciono un trio (una escala???)", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    public void actualizarLista() {
        modeloLista.clear();
        for (Carta carta : cartasArrayList) {
            modeloLista.addElement(carta);
        }
        list1.setModel(modeloLista);

    }

    private void formarTrioButtonEvento() {
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

    /**
     * Este método crea el botón para poder bajarse con una escala
     */
    private void formarEscalaButtonEvento() {
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

    /**
     * Este método se usa cuando el usuario se arrepiente de bajarse
     */
    private void abortarBajarseButtonEvento() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está que usted no desea bajarse?", "Abortar Bajarse", JOptionPane.YES_NO_OPTION);
        if (confirmacion == 0) { //Si realmente desea confirmar
            dispose();

        }
    }

    private void subirButtonEvento() {
        if (list1.getSelectedIndices().length == 1) { //Si selecciono solamente un indice
            int indice = list1.getSelectedIndex();
            int nuevoIndice = 0;

            if (indice - 1 == -1) {
                nuevoIndice = cartasArrayList.size() - 1;
            } else {
                nuevoIndice = indice - 1;
            }
            Collections.swap(cartasArrayList, indice, nuevoIndice);
            actualizarLista();


        } else {
            JOptionPane.showMessageDialog(this, "Error: Para mover cartas debe hacerlo de a uno", "Error: Mover Carta", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     */
    private void bajarButtonEvento() {
        if (list1.getSelectedIndices().length == 1) { //Si selecciono solamente un indice
            int indice = list1.getSelectedIndex();
            int nuevoIndice = 0;
            if (indice + 1 == cartasArrayList.size()) {
                nuevoIndice = 0;
            } else {
                nuevoIndice = indice + 1;
            }
            Collections.swap(cartasArrayList, indice, nuevoIndice);
            actualizarLista();

        } else {
            JOptionPane.showMessageDialog(this, "Error: Para mover cartas debe hacerlo de a uno", "Error: Mover Carta", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Este método muestra un panel confirmando que el usuario se bajo
     */
    private void bajarseCompletado() {
        JOptionPane.showConfirmDialog(this, "Usted se ha bajado correctamente", "Usted se ha bajado", JOptionPane.OK_OPTION);
        ronda.getJugadorActual().setBajoSusCarta(true);
        ronda.getJugadorActual().setMatrizEscalas(matrizEscalas);
        ronda.getJugadorActual().setMatrizTrios(matrizTrios);
        ronda.getJugadorActual().setCartas(cartasArrayList);


        mesaDeJuego.setRonda(ronda);
        mesaDeJuego.actualizarTablaEscala();
        mesaDeJuego.actualizarTablaTrios();
        mesaDeJuego.actualizar_CartasJugadorActualLista();
        dispose();
    }

    /**
     * Este método es invocado cuando se produce una acción.
     *
     * @param e Es el evento a ser procesado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formarTrioButton) {
            formarTrioButtonEvento();
        }
        if (e.getSource() == formarEscalaButton) {
            formarEscalaButtonEvento();
        }
        if (e.getSource() == abortarBajarseButton) {
            abortarBajarseButtonEvento();
        }
        if (e.getSource() == subirButton) {
            subirButtonEvento();
        }
        if (e.getSource() == bajarButton) {
            bajarButtonEvento();
        }
        if (escalasAFormar == 0 && triosAFormar == 0) {
            bajarseCompletado();
        }
    }

    /**
     * Este método se encarga de crear toda la parte visual de la clase
     */
    private void createUIComponents() {

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        modeloLista = new DefaultListModel();
        for (Carta carta : cartasArrayList) {
            modeloLista.addElement(carta);
        }
        list1 = new JList(modeloLista);
        list1.setCellRenderer(new CartaListCellRendererComponent());

        String[] columnasTriosEnLaMesa = {"Valor", "NroCartas"};
        Object[][] testTriosTabla = {};
        triosTablaModelo = new DefaultTableModel(testTriosTabla, columnasTriosEnLaMesa);
        triosTabla = new JTable(triosTablaModelo);

        String[] columnasEscalasEnLaMesa = {"PrimeraCarta", "Palo", "UltimaCarta", "NroCartas"};
        Object[][] testEscalaTabla = {};
        escalaTablaModelo = new DefaultTableModel(testEscalaTabla, columnasEscalasEnLaMesa);
        escalaTabla = new JTable(escalaTablaModelo);
        //escalaTabla.

        escalasPorFormarLabel = new JLabel(String.valueOf(escalasAFormar));
        triosPorFormarLabel = new JLabel(String.valueOf(triosAFormar));


        setBounds(0, 0, 800, 600);
        setLocationRelativeTo(null);
        setTitle("Bajarse: " + ronda.getJugadorActual().getNombre());
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
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, BorderLayout.EAST);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        escalaTabla = new JTable();
        escalaTabla.setPreferredSize(new Dimension(50, 40));
        scrollPane1.setViewportView(escalaTabla);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        triosTabla = new JTable();
        scrollPane2.setViewportView(triosTabla);
        subirButton = new JButton();
        subirButton.setText("Subir");
        panel3.add(subirButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bajarButton = new JButton();
        bajarButton.setText("Bajar");
        panel3.add(bajarButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
