package com.lucas.guis.RondaGUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.lucas.Carioca_Digital.Carta;
import com.lucas.Carioca_Digital.Jugador;
import com.lucas.Carioca_Digital.Reglas;
import com.lucas.Carioca_Digital.Ronda;
import com.lucas.guis.menuConfiguracion;
import com.lucas.guis.resultadosGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MesaGUI extends JFrame implements ActionListener {
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
    private JButton modoDebugButton;
    private JLabel nombreJugadorLabel;
    private JLabel nroCartasLabel;
    private JLabel seHaBajadoLabel;
    private JLabel yaSacoCartaLabel;
    private JButton izquierdaBoton;
    private JButton derechaBoton;
    private JButton configuracionesButton;
    private JLabel nroDeCartasJugadorLabel;
    int nivelFinal;


    private Ronda ronda; // Composición


    public MesaGUI(Ronda ronda) {
        this.ronda = ronda;


        $$$setupUI$$$();
        ronda.comenzarRonda();
        actualizar_CartasJugadorActualLista();
        mazoBoton.addActionListener(this);
        pozoBoton.addActionListener(this);
        botarCartaBoton.addActionListener(this);
        bajarseBoton.addActionListener(this);
        agregarTrioBoton.addActionListener(this);
        agregarEscalaBoton.addActionListener(this);
        izquierdaBoton.addActionListener(this);
        derechaBoton.addActionListener(this);
        modoDebugButton.addActionListener(this);
        configuracionesButton.addActionListener(this);


        this.add(panel);//IMPORTANTE AGREGAR EL PANEL AL FRAMES

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mazoBoton) {
            mazoBotonEvento();
        }
        if (e.getSource() == pozoBoton) {
            pozoBotonEvento();
        }
        if (e.getSource() == botarCartaBoton) {
            botarCartaBotonEvento();
        }
        if (e.getSource() == bajarseBoton) {
            bajarseBotonEvento();
        }
        if (e.getSource() == izquierdaBoton) izquierdaBotonEvento();
        if (e.getSource() == derechaBoton) derechaBotonEvento();
        if (e.getSource() == modoDebugButton) modoDebugButtonEvento();
        if(ronda.getJugadorActual().getNroCartas()==0){
            finRonda();
        }

        if (e.getSource() == configuracionesButton) configuracionesButtonEvento();

        modoDebugButton.setVisible(Reglas.isModoDebug());
        modoDebugButton.setEnabled(Reglas.isModoDebug());


    }

    private void configuracionesButtonEvento() {
        new menuConfiguracion().setVisible(true);

    }
    private void finRonda(){
        if(ronda.getNivel()== nivelFinal){
            ArrayList<Integer> puntajesFinales= new ArrayList<>();
            for(int i=0;i<ronda.getJugadores().size();i++){
                ronda.getJugadores().get(i).calcularPuntajeRonda();
                ronda.getJugadores().get(i).calcularPuntaje();
                puntajesFinales.add(ronda.getJugadores().get(i).getPuntaje());
            }
            new resultadosGUI(ronda.getJugadores(),puntajesFinales).setVisible(true);
        }else{
            for(int i=0;i<ronda.getJugadores().size();i++){
                ronda.getJugadores().get(i).calcularPuntajeRonda();
                ronda.getJugadores().get(i).calcularPuntaje();
                ronda.getJugadores().get(i).setPuntajeRonda(0);
            }
            new MesaGUI(new Ronda(ronda.getJugadores(),ronda.getNivel()+1));

        }
        this.dispose();
    }

    private void modoDebugButtonEvento() {
        new MenuDebugGUI(ronda).setVisible(true);
    }

    private void mazoBotonEvento() {
        if (ronda.getJugadorActual().isYaSacoCarta()) { //Si el jugador actual saco carta, no puede volver a sacar carta
            JOptionPane.showMessageDialog(this, "Usted ya saco carta, elija otra opción", "Error Jugador Actual ya saco carta", JOptionPane.ERROR_MESSAGE);
        } else {
            ronda.jugadorActualSacaCartaDelMazo(); //saca carta del mazo, se agrega a la lista de cartas del jugador y se hace un set que el jugadorActual ya saco carta
            Panel panel = new Panel();
            //Despliega un mensaje diciendo al jugador que carta obtuvo del mazo
            String mensaje = "Obtuvo un ";
            panel.add(new JLabel(mensaje));
            panel.add(ronda.getJugadorActual().getCartas().get(ronda.getJugadorActual().getCartas().size() - 1));
            JOptionPane.showMessageDialog(this, panel, "Saco carta del mazo", JOptionPane.DEFAULT_OPTION);
            actualizar_CartasJugadorActualLista(); //Se actuliza el JList
        }
        //Refrescar JList
    }

    private void pozoBotonEvento() {
        if (ronda.getJugadorActual().isYaSacoCarta()) { //Si el jugador actual saco carta, no puede volver a sacar carta
            JOptionPane.showMessageDialog(this, "Usted ya saco carta, elija otra opción", "Error Jugador Actual ya saco carta", JOptionPane.ERROR_MESSAGE);
        } else {
            ronda.jugadorActualSacaCartaDeLaMesa(); //saca carta de la mesa y se hace un set que el jugadorActual ya saco carta
            actualizar_CartasJugadorActualLista();
            if (ronda.getPozo().size() != 0) {
                pozoBoton.setIcon(ronda.getPrimeraCartaDelPozo().getIcon());
            } else {
                pozoBoton.setIcon(new ImageIcon()); //AQUI CAMBIAR Y PONER UN ICONO CUANDO EL POZO NO TENGA CARTAS
            }
        }
    }

    private void botarCartaBotonEvento() {
        //Si el jugador desea botar una carta
        if (ronda.getJugadorActual().isYaSacoCarta()) { //Se verifica si saco una carta previamente
            if (cartasJugadorLista.getSelectedIndices().length == 1) { //Se verifica si selecciono solamente una carta


                int indice = cartasJugadorLista.getSelectedIndex(); //Se obtiene el indice de la carta seleccionada
                String mensaje = "¿Usted desea botar la carta: " + ronda.getJugadorActual().getCartas().get(indice).toString();
                int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmar botar carta", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) {//si el jugador ha seleccionado que si
                    ronda.jugadorActualBotaCartaAlPozo(indice); // Se bota la carta
                    actualizar_CartasJugadorActualLista(); //Se actualiza la lista
                    pozoBoton.setIcon(ronda.getPrimeraCartaDelPozo().getIcon()); //El boton del pozo se actualiza la imagen

                    //Se despliega un mensaje de confirmación para avisar el jugador actual ha finalizado su turno
                    String nombreJugadorAnterior = ronda.getJugadorActual().getNombre();
                    ronda.siguienteTurnoJugador();
                    String nombreJugadorSiguiente = ronda.getJugadorActual().getNombre();
                    String mensajeDeConfirmacion = "El turno de " + nombreJugadorAnterior + " a finalizado su turno.\nA continuación juega " + nombreJugadorSiguiente;

                    JOptionPane.showConfirmDialog(this, mensajeDeConfirmacion, "Turno finalizado", JOptionPane.DEFAULT_OPTION);
                    actualizar_CartasJugadorActualLista();
                }
            } else {
                if (cartasJugadorLista.getSelectedIndices().length > 1)
                    JOptionPane.showMessageDialog(this, "Usted ha seleccionado mas de una carta\nNo puede botar mas de una carta", "Error al finalizar turno", JOptionPane.ERROR_MESSAGE);
                else if (cartasJugadorLista.getSelectedIndices().length == 0)
                    JOptionPane.showMessageDialog(this, "Usted no ha seleccionado una carta", "Error al finalizar turno", JOptionPane.ERROR_MESSAGE);
            }
        } else {//El jugado no ha sacado carta debe mostrar un mensaje de error
            //JOptionPane errorDialogo = new JOptionPane("Usted no ha sacado una carta\nAun no puede finalizar su turno", JOptionPane.ERROR_MESSAGE);

            JOptionPane.showMessageDialog(this, "Usted no ha sacado una carta\nAun no puede finalizar su turno", "Error al finalizar turno", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bajarseBotonEvento() {
        if (ronda.getJugadorActual().isBajoSusCarta()) {
            JOptionPane.showMessageDialog(this, "¡Usted ya bajo sus Cartas!");
        } else {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Usted esta seguro de bajarse?", "El jugador desea bajarse", JOptionPane.YES_NO_OPTION);
            if (opcion == 0) {
                new BajarseGUI(ronda.getJugadorActual(), ronda.getNROESCALAS_A_FORMAR(), ronda.getNROTRIOS_A_FORMAR()).setVisible(true);


            }
        }
    }

    private void izquierdaBotonEvento() {
        if (cartasJugadorLista.getSelectedIndices().length == 1) { //Si selecciono solamente un indice
            int indice = cartasJugadorLista.getSelectedIndex();
            int nuevoIndice = 0;

            if (indice - 1 == -1) {
                nuevoIndice = ronda.getJugadorActual().getCartas().size() - 1;
            } else {
                nuevoIndice = indice - 1;
            }
            Collections.swap(ronda.getJugadorActual().getCartas(), indice, nuevoIndice);
            actualizar_CartasJugadorActualLista();


        } else {
            JOptionPane.showMessageDialog(this, "Error: Para mover cartas debe hacerlo de a uno", "Error: Mover Carta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void derechaBotonEvento() {
        if (cartasJugadorLista.getSelectedIndices().length == 1) { //Si selecciono solamente un indice
            int indice = cartasJugadorLista.getSelectedIndex();
            int nuevoIndice = 0;
            if (indice + 1 == ronda.getJugadorActual().getCartas().size()) {
                nuevoIndice = 0;
            } else {
                nuevoIndice = indice + 1;
            }
            Collections.swap(ronda.getJugadorActual().getCartas(), indice, nuevoIndice);
            actualizar_CartasJugadorActualLista();

        } else {
            JOptionPane.showMessageDialog(this, "Error: Para mover cartas debe hacerlo de a uno", "Error: Mover Carta", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizar_CartasJugadorActualLista() {
        nombreJugadorLabel.setText(ronda.getJugadorActual().getNombre());
        nroCartasLabel.setText(String.valueOf(ronda.getJugadorActual().getCartas().size()));
        seHaBajadoLabel.setText(String.valueOf(ronda.getJugadorActual().isBajoSusCarta()));
        yaSacoCartaLabel.setText(String.valueOf(ronda.getJugadorActual().isYaSacoCarta()));
        cartasJugadorListaModelo.clear();

        for (Carta carta : ronda.getJugadorActual().getCartas()) {
            cartasJugadorListaModelo.addElement(carta);
        }

        cartasJugadorLista.setModel(cartasJugadorListaModelo);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.setBounds(0, 0, 1360, 768);
        //this.setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setResizable(true);
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
        pozoBoton.setIcon(ronda.getPrimeraCartaDelPozo().getIcon());
        pozoBoton.setVisible(true);

        String[] columnasJugadoresTabla = {"Nombre", "Cartas", "Puntaje"};
        jugadoresDefaultTableModel = new DefaultTableModel(ronda.getArrayObjectJugadores(), columnasJugadoresTabla);
        jugadoresTabla = new JTable(jugadoresDefaultTableModel);

        String[] columnasTriosEnLaMesa = {"Valor", "NroCartas"};
        Object[][] testTriosTabla = {{}};
        triosEnLaMesaDefaultTableModel = new DefaultTableModel(testTriosTabla, columnasTriosEnLaMesa);
        triosEnLaMesaTabla = new JTable(triosEnLaMesaDefaultTableModel);

        String[] columnasEscalasEnLaMesa = {"PrimeraCarta", "Palo", "UltimaCarta", "NroCartas"};
        Object[][] testEscalaTabla = {{}};
        escalasEnLaMesaDefaultTableModel = new DefaultTableModel(testEscalaTabla, columnasEscalasEnLaMesa);

        escalasEnLaMesaTabla = new JTable(escalasEnLaMesaDefaultTableModel);

        cartasJugadorListaModelo = new DefaultListModel();
        cartasJugadorLista = new JList<>(cartasJugadorListaModelo);
        cartasJugadorLista.setCellRenderer(new CartaListCellRendererComponent());


    }

    public static void main(String[] args) throws IOException {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Lucas"));
        jugadores.add(new Jugador("Lorenzo"));
        jugadores.add(new Jugador("Fernando"));

        new MesaGUI(new Ronda(jugadores, 0)).setVisible(true);
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
        panel.setBorder(BorderFactory.createTitledBorder(null, "Mesa", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel1.setBackground(new Color(-14786275));
        panel.add(panel1, BorderLayout.WEST);
        botarCartaBoton = new JButton();
        botarCartaBoton.setText("Botar Carta y Finalizar Turno");
        panel1.add(botarCartaBoton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bajarseBoton = new JButton();
        bajarseBoton.setText("Bajarse");
        panel1.add(bajarseBoton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarTrioBoton = new JButton();
        agregarTrioBoton.setText("Agregar a trio");
        panel1.add(agregarTrioBoton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agregarEscalaBoton = new JButton();
        agregarEscalaBoton.setText("Agregar a escala");
        panel1.add(agregarEscalaBoton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 40), null, 0, false));
        jugadoresTabla.setBackground(new Color(-14123225));
        scrollPane1.setViewportView(jugadoresTabla);
        modoDebugButton = new JButton();
        modoDebugButton.setText("Modo Debug");
        panel1.add(modoDebugButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configuracionesButton = new JButton();
        configuracionesButton.setText("Configuraciones");
        panel1.add(configuracionesButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel3.setLayout(new GridLayoutManager(5, 7, new Insets(10, 10, 10, 10), -1, -1));
        panel3.setBackground(new Color(-14786275));
        panel.add(panel3, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setOpaque(false);
        panel3.add(panel4, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setOpaque(false);
        panel3.add(panel5, new GridConstraints(1, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mazoBoton.setText("");
        panel5.add(mazoBoton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel5.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pozoBoton.setText("");
        panel5.add(pozoBoton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel5.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel5.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setOpaque(false);
        panel3.add(panel6, new GridConstraints(2, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 11, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setOpaque(false);
        panel3.add(panel7, new GridConstraints(4, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel7.add(scrollPane4, new GridConstraints(1, 0, 1, 11, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        cartasJugadorLista.setBackground(new Color(-14123225));
        cartasJugadorLista.setLayoutOrientation(2);
        cartasJugadorLista.setOpaque(false);
        cartasJugadorLista.setPreferredSize(new Dimension(1270, 20));
        cartasJugadorLista.setRequestFocusEnabled(false);
        cartasJugadorLista.setSelectionMode(2);
        cartasJugadorLista.setVisibleRowCount(1);
        scrollPane4.setViewportView(cartasJugadorLista);
        final JLabel label1 = new JLabel();
        label1.setForeground(new Color(-1));
        label1.setText("Jugador Actual:");
        panel7.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nombreJugadorLabel = new JLabel();
        nombreJugadorLabel.setForeground(new Color(-1));
        nombreJugadorLabel.setText("");
        panel7.add(nombreJugadorLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-5390));
        label2.setText("NroCartas");
        panel7.add(label2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroCartasLabel = new JLabel();
        nroCartasLabel.setForeground(new Color(-5390));
        nroCartasLabel.setText("");
        panel7.add(nroCartasLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-5390));
        label3.setText("¿Jugador se ha bajado?");
        panel7.add(label3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seHaBajadoLabel = new JLabel();
        seHaBajadoLabel.setForeground(new Color(-5390));
        seHaBajadoLabel.setText("");
        panel7.add(seHaBajadoLabel, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-5390));
        label4.setText("¿Jugador ya saco carta?");
        panel7.add(label4, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        yaSacoCartaLabel = new JLabel();
        yaSacoCartaLabel.setForeground(new Color(-5390));
        yaSacoCartaLabel.setText("");
        panel7.add(yaSacoCartaLabel, new GridConstraints(0, 7, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        izquierdaBoton = new JButton();
        izquierdaBoton.setText("<");
        panel7.add(izquierdaBoton, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        derechaBoton = new JButton();
        derechaBoton.setText(">");
        panel7.add(derechaBoton, new GridConstraints(0, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 6, new Insets(10, 10, 10, 10), -1, -1));
        panel8.setBackground(new Color(-14786275));
        panel.add(panel8, BorderLayout.NORTH);
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-1));
        label5.setText("Ronda:");
        panel8.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-1));
        label6.setText("Número de trios a formar:");
        panel8.add(label6, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroTriosLabel.setForeground(new Color(-1));
        panel8.add(nroTriosLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-1));
        label7.setText("Número de escalas a formar:");
        panel8.add(label7, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroEscalasLabel.setForeground(new Color(-1));
        panel8.add(nroEscalasLabel, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nroRondaLabel.setForeground(new Color(-1));
        panel8.add(nroRondaLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }


}