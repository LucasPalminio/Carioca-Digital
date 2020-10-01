package com.lucas.gui.ronda_gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.lucas.carioca_digital.Carta;
import com.lucas.carioca_digital.Jugador;
import com.lucas.carioca_digital.Reglas;
import com.lucas.carioca_digital.Ronda;
import com.lucas.utilidad.Utilidad;
import com.lucas.gui.MenuPrincipalGUI;
import com.lucas.gui.ResultadosGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MesaGUI extends JFrame implements ActionListener {


    private JPanel panel;

    private JTable jugadoresTabla;
    private DefaultTableModel jugadoresTablaModelo;

    private JTable escalasEnLaMesaTabla;
    private DefaultTableModel escalasEnLaMesaModelo;

    private JTable triosEnLaMesaTabla;
    private DefaultTableModel triosEnLaMesaModelo;

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

    private final int nivelFinal;
    private final int nivelInicial;


    private Ronda ronda; // Asociación

    public MesaGUI(Ronda ronda, int nivelInicial, int nivelFinal) {
        this.ronda = ronda;
        this.nivelFinal = nivelFinal;
        this.nivelInicial = nivelInicial;
        if (ronda.getNivel() <= nivelFinal) {
            ronda.comenzarRonda();

            $$$setupUI$$$();
            mazoBoton.addActionListener(this);
            pozoBoton.addActionListener(this);
            botarCartaBoton.addActionListener(this);
            bajarseBoton.addActionListener(this);
            agregarTrioBoton.addActionListener(this);
            agregarEscalaBoton.addActionListener(this);
            izquierdaBoton.addActionListener(this);
            derechaBoton.addActionListener(this);
            modoDebugButton.addActionListener(this);
            this.add(panel);//IMPORTANTE AGREGAR EL PANEL AL FRAME


            actualizar_CartasJugadorActualLista();
        } else {
            JOptionPane.showMessageDialog(this, "Error: nivelActual > nivelFinal!", "Error al crear Ronda", JOptionPane.ERROR_MESSAGE);
            //dispose();
        }


    }

    public int getNivelInicial() {
        return nivelInicial;
    }

    public int getNivelFinal() {
        return nivelFinal;
    }

    public Ronda getRonda() {
        return ronda;
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public void actualizarTablaEscala() {
        while (jugadoresTablaModelo.getRowCount() > 0) jugadoresTablaModelo.removeRow(0);

        for (Jugador jugador : ronda.getJugadores()) {
            if (jugador.isBajoSusCarta()) {
                for (int i = 0; i < jugador.getArrayObjectEscalas().length; i++) {
                    escalasEnLaMesaModelo.addRow(jugador.getArrayObjectEscalas()[i]);
                }
            }
        }
        escalasEnLaMesaTabla.setModel(escalasEnLaMesaModelo);
    }

    public void actualizarTablaTrios() {
        while (triosEnLaMesaModelo.getRowCount() > 0) triosEnLaMesaModelo.removeRow(0);

        for (Jugador jugador : ronda.getJugadores()) {
            if (jugador.isBajoSusCarta()) {
                for (int i = 0; i < jugador.getArrayObjectTrios().length; i++) {
                    triosEnLaMesaModelo.addRow(jugador.getArrayObjectTrios()[i]);
                }
            }

        }
        triosEnLaMesaTabla.setModel(triosEnLaMesaModelo);

    }

    public void actualizarTablaJugadores() {

        while (jugadoresTablaModelo.getRowCount() > 0) jugadoresTablaModelo.removeRow(0);

        for (Jugador jugador : ronda.getJugadores()) {
            jugadoresTablaModelo.addRow(jugador.getArrayObject());
        }
        jugadoresTabla.setModel(jugadoresTablaModelo);
    }

    public void actualizar_CartasJugadorActualLista() {
        nombreJugadorLabel.setText(ronda.getJugadorActual().getNombre());
        nroCartasLabel.setText(String.valueOf(ronda.getJugadorActual().getCartas().size()));
        seHaBajadoLabel.setText(String.valueOf(ronda.getJugadorActual().isBajoSusCarta()));
        yaSacoCartaLabel.setText(String.valueOf(ronda.getJugadorActual().isYaSacoCarta()));

        agregarEscalaBoton.setVisible((ronda.getNROESCALAS_A_FORMAR() > 0) && (ronda.getJugadorActual().isBajoSusCarta()));
        agregarTrioBoton.setVisible((ronda.getNROTRIOS_A_FORMAR() > 0) && ronda.getJugadorActual().isBajoSusCarta());
        //Se muestra el boton de bajar escala o trio si el jugador se ha bajado y si la ronda lo permite
        bajarseBoton.setVisible(!ronda.getJugadorActual().isBajoSusCarta());
        //Si el jugador se ha bajado, se oculta el boton bajarse
        modoDebugButton.setVisible(Reglas.isModoDebug());

        cartasJugadorListaModelo.clear();

        for (Carta carta : ronda.getJugadorActual().getCartas()) {
            cartasJugadorListaModelo.addElement(carta);
        }

        cartasJugadorLista.setModel(cartasJugadorListaModelo);

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mazoBoton) mazoBotonEvento();

        if (e.getSource() == pozoBoton) pozoBotonEvento();

        if (ronda.getJugadorActual().getNroCartas() == 0) finRonda();

        if (e.getSource() == botarCartaBoton) botarCartaBotonEvento();

        if (e.getSource() == bajarseBoton) bajarseBotonEvento();

        if (e.getSource() == izquierdaBoton) izquierdaBotonEvento();
        if (e.getSource() == derechaBoton) derechaBotonEvento();
        if (e.getSource() == modoDebugButton) modoDebugButtonEvento();
        if (e.getSource() == agregarEscalaBoton) agregarEscalaBotonEvento();
        if (e.getSource() == agregarTrioBoton) agregarTrioBotonEvento();

        modoDebugButton.setVisible(Reglas.isModoDebug());
        modoDebugButton.setEnabled(Reglas.isModoDebug());
        agregarTrioBoton.setVisible(ronda.getJugadorActual().isBajoSusCarta());
        agregarEscalaBoton.setVisible(ronda.getJugadorActual().isBajoSusCarta());

    }

    private void agregarEscalaBotonEvento() {
        String mensajeDeError = "";

        if (cartasJugadorLista.getSelectedIndices().length == 1 && escalasEnLaMesaTabla.getSelectedRow() != -1 && escalasEnLaMesaTabla.getSelectedRows().length == 1) {
            int fila = escalasEnLaMesaTabla.getSelectedRow();
            Carta cartaSeleccionada = cartasJugadorLista.getSelectedValue();
            int nroCartasEscala = (int) escalasEnLaMesaModelo.getValueAt(fila, 3);
            String paloEscala = (String) escalasEnLaMesaModelo.getValueAt(fila, 1);
            String[] opciones = {"Primera posición", "Ultima Posicición"};
            if (paloEscala.equals(cartaSeleccionada.getPalo()) || cartaSeleccionada.getPalo().equals("JKR")) {
                int opcion = JOptionPane.showOptionDialog(this, "¿En que posición desea agregar la carta?", "Agregar carta a una escala", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
                String valorEsperado = "";
                int indiceValorEsperado = 0;
                int columna = 0;
                if (opcion == 0) { //Si el jugador desea agregar la carta a la primera posición
                    columna = 0;
                    indiceValorEsperado = Utilidad.StringArrayindexOf(Carta.VALORES, (String) escalasEnLaMesaModelo.getValueAt(fila, columna));
                    indiceValorEsperado = (indiceValorEsperado - 1) % Carta.VALORES.length;

                } else if (opcion == 1) {// Si el jugador desea agregar la carta a la ultima posición
                    columna = 2;
                    indiceValorEsperado = Utilidad.StringArrayindexOf(Carta.VALORES, (String) escalasEnLaMesaModelo.getValueAt(fila, columna));
                    indiceValorEsperado = (indiceValorEsperado + 1) % Carta.VALORES.length;

                }
                valorEsperado = Carta.VALORES[indiceValorEsperado];
                if (valorEsperado.equals(cartaSeleccionada.getValor()) || cartaSeleccionada.getPalo().equals("JKR")) {
                    escalasEnLaMesaModelo.setValueAt(valorEsperado, fila, columna);
                    nroCartasEscala++;
                    escalasEnLaMesaModelo.setValueAt(nroCartasEscala, fila, 3);
                    escalasEnLaMesaTabla.setModel(escalasEnLaMesaModelo);
                    ronda.getJugadorActual().getCartas().remove(cartaSeleccionada);
                    actualizar_CartasJugadorActualLista();
                    return;
                }
                mensajeDeError = "La carta seleccionada no aplica para la escala, se necesita un " + valorEsperado + " ó un JKR";
            } else {
                mensajeDeError = "La carta seleccionada no aplica para la escala";
            }

        }
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error al agregar carta hacia una escala", JOptionPane.ERROR_MESSAGE);
    }

    private void agregarTrioBotonEvento() {
        String mensajeDeError = "";
        if (cartasJugadorLista.getSelectedIndices().length == 1 && triosEnLaMesaTabla.getSelectedRow() != -1 && triosEnLaMesaTabla.getSelectedRows().length == 1) {
            int fila = triosEnLaMesaTabla.getSelectedRow();
            Carta cartaSeleccionada = cartasJugadorLista.getSelectedValue();
            String valorTrio = (String) triosEnLaMesaModelo.getValueAt(fila, 0);
            int nroCartasTrio = (int) triosEnLaMesaModelo.getValueAt(fila, 1);
            if (cartaSeleccionada.getValor().equals(valorTrio) || cartaSeleccionada.getPalo().equals("JKR")) {
                nroCartasTrio++;
                ronda.getJugadorActual().getCartas().remove(cartaSeleccionada);
                actualizar_CartasJugadorActualLista();
                triosEnLaMesaModelo.setValueAt(nroCartasTrio, fila, 1);
                triosEnLaMesaTabla.setModel(triosEnLaMesaModelo);
                return;
            } else {
                mensajeDeError = "La carta seleccionada no aplica para el Trio";
            }
        } else {
            mensajeDeError = "Usted debe seleccionar una carta de su lista y una fila de la tabla de Trios";
        }
        JOptionPane.showMessageDialog(this, mensajeDeError, "Error al agregar carta a un trio", JOptionPane.ERROR_MESSAGE);
    }

    private void finRonda() {
        if (ronda.getNivel() == nivelFinal) {
           for (int i = 0; i < ronda.getJugadores().size(); i++) {
                ronda.getJugadores().get(i).calcularPuntajeRonda();
                ronda.getJugadores().get(i).calcularPuntaje();
            }
            new ResultadosGUI(this).setVisible(true);
        } else {
            for (int i = 0; i < ronda.getJugadores().size(); i++) {
                ronda.getJugadores().get(i).calcularPuntajeRonda();
                ronda.getJugadores().get(i).calcularPuntaje();
                ronda.getJugadores().get(i).setPuntajeRonda(0);
            }
            new MesaGUI(new Ronda(ronda.getJugadores(), ronda.getNivel() + 1), nivelInicial, nivelFinal).setVisible(true);

        }
        this.dispose();
    }

    private void modoDebugButtonEvento() {
        new MenuDebugGUI(this).setVisible(true);
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

            Panel panel = new Panel();
            //Despliega un mensaje diciendo al jugador que carta obtuvo del mazo
            String mensaje = "Obtuvo un ";
            panel.add(new JLabel(mensaje));
            panel.add(ronda.getJugadorActual().getCartas().get(ronda.getJugadorActual().getCartas().size() - 1));
            JOptionPane.showMessageDialog(this, panel, "Saco carta del pozo", JOptionPane.DEFAULT_OPTION);

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
                Panel panel = new Panel();
                String mensaje = "Usted desea botar la carta: ";
                panel.add(new JLabel(mensaje));
                panel.add(ronda.getJugadorActual().getCartas().get(indice));
                int opcion = JOptionPane.showConfirmDialog(this, panel, "Confirmar botar carta", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) {//si el jugador ha seleccionado que si
                    ronda.jugadorActualBotaCartaAlPozo(indice); // Se bota la carta
                    actualizar_CartasJugadorActualLista(); //Se actualiza la lista
                    pozoBoton.setIcon(ronda.getPrimeraCartaDelPozo().getIcon()); //El boton del pozo se actualiza la imagen

                    if (ronda.getJugadorActual().getNroCartas() == 0) {//Si el jugador Actual se ha quedado sin cartas, entonces la ronda finaliza
                        finRonda();
                    } else { //De lo contrario, continua el juego y se finaliza la ronda
                        //Se despliega un mensaje de confirmación para avisar el jugador actual ha finalizado su turno
                        String nombreJugadorAnterior = ronda.getJugadorActual().getNombre();
                        ronda.siguienteTurnoJugador();
                        String nombreJugadorSiguiente = ronda.getJugadorActual().getNombre();
                        String mensajeDeConfirmacion = "El turno de " + nombreJugadorAnterior + " a finalizado su turno.\nA continuación juega " + nombreJugadorSiguiente;

                        JOptionPane.showConfirmDialog(this, mensajeDeConfirmacion, "Turno finalizado", JOptionPane.DEFAULT_OPTION);
                        actualizar_CartasJugadorActualLista();
                        actualizarTablaJugadores(); //se actualiza la tabla de jugadores
                    }
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
        if (ronda.getJugadorActual().isYaSacoCarta()) {
            if (ronda.getJugadorActual().isBajoSusCarta()) {
                JOptionPane.showMessageDialog(this, "¡Usted ya bajo sus Cartas!", "Error: Jugador intenta bajarse pero ya se habia bajado", JOptionPane.ERROR_MESSAGE);
            } else {
                int opcion = JOptionPane.showConfirmDialog(this, "¿Usted esta seguro de bajarse?", "El jugador desea bajarse", JOptionPane.YES_NO_OPTION);
                if (opcion == 0) {
                    new BajarseGUI(this).setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "¡Usted no puede bajarse porque no ha sacado carta!", "Error: Jugador aún no ha sacado carta", JOptionPane.ERROR_MESSAGE);
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


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.setBounds(0, 0, 1360, 768);
        //this.setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setResizable(true);
        //setLayout(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                String[] opciones = {"Volver al menu principal", "Salir al escritorio", "Cancelar"};
                int opcion = JOptionPane.showOptionDialog(e.getComponent(), "", "Menu Salir", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION, null, opciones, null);
                switch (opcion) {
                    case 0:
                        new MenuPrincipalGUI().setVisible(true);
                        e.getWindow().dispose();
                        break;
                    case 1:
                        System.exit(0);
                        break;
                }
            }
        });


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

        String[] columnasJugadoresTabla = {"Nombre", "Cartas", "Puntaje", "¿Se ha bajado?"};
        jugadoresTablaModelo = new DefaultTableModel(ronda.getArrayObjectJugadores(), columnasJugadoresTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jugadoresTabla = new JTable(jugadoresTablaModelo);


        String[] columnasTriosEnLaMesa = {"Valor", "NroCartas"};
        Object[][] testTriosTabla = {{}};
        triosEnLaMesaModelo = new DefaultTableModel(testTriosTabla, columnasTriosEnLaMesa) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        triosEnLaMesaTabla = new JTable(triosEnLaMesaModelo);

        String[] columnasEscalasEnLaMesa = {"PrimeraCarta", "Palo", "UltimaCarta", "NroCartas"};
        Object[][] testEscalaTabla = {{}};
        escalasEnLaMesaModelo = new DefaultTableModel(testEscalaTabla, columnasEscalasEnLaMesa) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        escalasEnLaMesaTabla = new JTable(escalasEnLaMesaModelo);

        cartasJugadorListaModelo = new DefaultListModel();
        cartasJugadorLista = new JList<>(cartasJugadorListaModelo);
        cartasJugadorLista.setCellRenderer(new CartaListCellRendererComponent());
        cartasJugadorLista.setDragEnabled(true);


    }

    public static void main(String[] args) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Lucas"));
        jugadores.add(new Jugador("Lorenzo"));
        Ronda ronda = new Ronda(jugadores, 1);
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(new Carta("S", "3"));
        cartas.add(new Carta("D", "3"));
        cartas.add(new Carta("C", "3"));
        cartas.add(new Carta("JKR", ""));
        cartas.add(new Carta("H", "J"));
        cartas.add(new Carta("H", "Q"));
        cartas.add(new Carta("H", "K"));
        cartas.add(new Carta("H", "A"));
        cartas.add(new Carta("H", "2"));


        MesaGUI a = new MesaGUI(ronda, 1, 2);
        a.setVisible(true);
        a.getRonda().getJugadorActual().setCartas(cartas);

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
        panel1.setLayout(new GridLayoutManager(6, 1, new Insets(10, 10, 10, 10), -1, -1));
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
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel2.setBackground(new Color(-14786275));
        panel.add(panel2, BorderLayout.EAST);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel2.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        scrollPane2.setViewportView(escalasEnLaMesaTabla);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel2.add(scrollPane3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
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
        panel7.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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