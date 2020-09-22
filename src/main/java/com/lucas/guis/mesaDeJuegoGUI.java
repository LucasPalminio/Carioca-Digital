package com.lucas.guis;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.lucas.Carioca_Digital.Carta;
import com.lucas.Carioca_Digital.Jugador;
import com.lucas.Carioca_Digital.Ronda;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class mesaDeJuegoGUI extends JFrame implements ListCellRenderer {
    private JPanel panel;

    private JTable jugadoresTabla;
    private DefaultTableModel jugadoresDefaultTableModel;

    private JTable escalasEnLaMesaTabla;
    private DefaultTableModel escalasEnLaMesaDefaultTableModel;

    private JTable triosEnLaMesaTabla;
    private DefaultTableModel triosEnLaMesaDefaultTableModel;

    private JButton botarCartaBoton;
    private JButton bajarseBoton;
    private JButton agregarTrioBoton;
    private JButton agregarEscalaBoton;

    private JList<Carta> cartasJugadorLista;
    private DefaultListModel cartasJugadorListaModelo;
    private JButton mazoBoton;
    private JButton pozoBoton;
    private JLabel nroRondaLabel;
    private JLabel nroTriosLabel;
    private JLabel nroEscalasLabel;


    private Ronda ronda; // Composición


    public mesaDeJuegoGUI(Ronda ronda) {
        this.ronda = ronda;
        ronda.comenzarRonda();
        $$$setupUI$$$();
        this.add(panel);//IMPORTANTE AGREGAR EL PANEL AL FRAMES


    }

    public mesaDeJuegoGUI(ArrayList<Jugador> jugadores, int nivel) {
        this.ronda = new Ronda(jugadores, nivel);
        ronda.comenzarRonda();
        $$$setupUI$$$();
        this.add(panel); //IMPORTANTE AGREGAR EL PANEL AL FRAME
        actualizar_CartasJugadorActualLista(ronda.getJugadorActual());


    }

    public static void main(String[] args) throws IOException {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Lucas"));
        jugadores.add(new Jugador("Lorenzo"));
        jugadores.add(new Jugador("Fernando"));

        new mesaDeJuegoGUI(jugadores, 0).setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.setBounds(0, 0, 1360, 768);
        //this.setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setResizable(false);
        //setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //addMouseListener(this);
        //addMouseMotionListener(this);

        nroTriosLabel = new JLabel(String.valueOf(ronda.getNROTRIOS_A_FORMAR()));
        nroEscalasLabel = new JLabel(String.valueOf(ronda.getNROESCALAS_A_FORMAR()));
        nroRondaLabel = new JLabel(String.valueOf(ronda.getNivel()));

        nroTriosLabel.setVisible(true);
        nroEscalasLabel.setVisible(true);
        nroRondaLabel.setVisible(true);

        mazoBoton = new JButton();
        mazoBoton.setSize(Carta.WIDTH, Carta.HEIGHT);
        String rutaImagenMazo = "src//images//cartas//blue_back.png";
        try {
            mazoBoton.setIcon(new ImageIcon(ImageIO.read(new File(rutaImagenMazo)).getScaledInstance(Carta.WIDTH, Carta.HEIGHT, Image.SCALE_SMOOTH)));
        } catch (IOException ioe) {
            System.err.println("Error Mazo: " + ioe.getMessage());
        }

        pozoBoton = new JButton();
        pozoBoton.setSize(Carta.WIDTH, Carta.HEIGHT);
        pozoBoton.setVisible(true);

        String[] columnasJugadoresTabla = {"Nombre", "Cartas", "Puntaje"};
        jugadoresDefaultTableModel = new DefaultTableModel(ronda.getArrayObjectJugadores(), columnasJugadoresTabla);
        jugadoresTabla = new JTable(jugadoresDefaultTableModel);

        String[] columnasTriosEnLaMesa = {"Valor", "NroCartas"};
        Object[][] testTriosTabla = {{"4", 12}};
        triosEnLaMesaDefaultTableModel = new DefaultTableModel(testTriosTabla, columnasTriosEnLaMesa);
        triosEnLaMesaTabla = new JTable(triosEnLaMesaDefaultTableModel);

        String[] columnasEscalasEnLaMesa = {"PrimeraCarta", "Palo", "UltimaCarta", "NroCartas"};
        Object[][] testEscalaTabla = {{"4", "C", "7", 4}};
        escalasEnLaMesaDefaultTableModel = new DefaultTableModel(testEscalaTabla, columnasEscalasEnLaMesa);
        escalasEnLaMesaTabla = new JTable(escalasEnLaMesaDefaultTableModel);

        cartasJugadorListaModelo = new DefaultListModel();
        cartasJugadorLista = new JList<>(cartasJugadorListaModelo);
        cartasJugadorLista.setCellRenderer(this::getListCellRendererComponent);


    }

    private void actualizar_CartasJugadorActualLista(Jugador jugadorActual) {
        cartasJugadorListaModelo.clear();

        for (Carta carta : jugadorActual.getCartas()) {
            cartasJugadorListaModelo.addElement(carta);
        }

        cartasJugadorLista.setModel(cartasJugadorListaModelo);

    }

    /**
     * Return a component that has been configured to display the specified
     * value. That component's <code>paint</code> method is then called to
     * "render" the cell.  If it is necessary to compute the dimensions
     * of a list because the list cells do not have a fixed size, this method
     * is called to generate a component on which <code>getPreferredSize</code>
     * can be invoked.
     *
     * @param list         The JList we're painting.
     * @param value        The value returned by list.getModel().getElementAt(index).
     * @param index        The cells index.
     * @param isSelected   True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Carta carta = (Carta) value;

        if (isSelected) {
            carta.setBackground(list.getSelectionBackground());
            carta.setForeground(list.getSelectionForeground());
        } else {
            carta.setBackground(list.getBackground());
            carta.setForeground(list.getForeground());
        }

        return carta;
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
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBackground(new Color(-14786275));
        panel.setEnabled(true);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(1360, 680));
        panel.setBorder(BorderFactory.createTitledBorder(null, "mesa de juego", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel1.setBackground(new Color(-14786275));
        panel.add(panel1, BorderLayout.WEST);
        botarCartaBoton = new JButton();
        botarCartaBoton.setText("Botar Carta");
        panel1.add(botarCartaBoton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bajarseBoton = new JButton();
        bajarseBoton.setText("Bajarse");
        panel1.add(bajarseBoton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarTrioBoton = new JButton();
        agregarTrioBoton.setText("Agregar a trio");
        panel1.add(agregarTrioBoton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarEscalaBoton = new JButton();
        agregarEscalaBoton.setText("Agregar a escala");
        panel1.add(agregarEscalaBoton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 40), null, 0, false));
        jugadoresTabla.setBackground(new Color(-14123225));
        scrollPane1.setViewportView(jugadoresTabla);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel2.setBackground(new Color(-14786275));
        panel.add(panel2, BorderLayout.EAST);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel2.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        escalasEnLaMesaTabla.setBackground(new Color(-14123225));
        scrollPane2.setViewportView(escalasEnLaMesaTabla);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel2.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        triosEnLaMesaTabla.setBackground(new Color(-14123225));
        scrollPane3.setViewportView(triosEnLaMesaTabla);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 3, new Insets(10, 10, 10, 10), -1, -1));
        panel3.setBackground(new Color(-14786275));
        panel.add(panel3, BorderLayout.CENTER);
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mazoBoton.setText("");
        panel3.add(mazoBoton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pozoBoton.setText("");
        panel3.add(pozoBoton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        scrollPane4.setAutoscrolls(true);
        panel3.add(scrollPane4, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cartasJugadorLista.setBackground(new Color(-14123225));
        cartasJugadorLista.setLayoutOrientation(2);
        cartasJugadorLista.setOpaque(false);
        cartasJugadorLista.setPreferredSize(new Dimension(1270, 20));
        cartasJugadorLista.setRequestFocusEnabled(false);
        cartasJugadorLista.setSelectionMode(2);
        cartasJugadorLista.setVisibleRowCount(1);
        scrollPane4.setViewportView(cartasJugadorLista);
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 6, new Insets(10, 10, 10, 10), -1, -1));
        panel4.setBackground(new Color(-14786275));
        panel.add(panel4, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-1));
        label1.setText("Ronda:");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-1));
        label2.setText("Número de trios a formar:");
        panel4.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroTriosLabel.setForeground(new Color(-1));
        panel4.add(nroTriosLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-1));
        label3.setText("Número de escalas a formar:");
        panel4.add(label3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroEscalasLabel.setForeground(new Color(-1));
        panel4.add(nroEscalasLabel, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroRondaLabel.setForeground(new Color(-1));
        panel4.add(nroRondaLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }


}