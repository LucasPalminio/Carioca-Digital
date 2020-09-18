package com.lucas.guis;

import com.lucas.Carioca_Digital.Jugador;
import com.lucas.Carioca_Digital.Ronda;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class mesaDeJuegoGUI extends JFrame implements MouseListener , MouseMotionListener {
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
    private JList cartasJugadorActualLista;
    private JButton mazoBoton;
    private JButton pozoBoton;
    private JLabel nroRondaLabel;
    private JLabel nroTriosLabel;
    private JLabel nroEscalasLabel;

    private Ronda ronda;




    private boolean moviendoCarta = false;
    public mesaDeJuegoGUI(Ronda ronda)  {
        this.ronda = ronda;
        createUIComponents();


    }

    public mesaDeJuegoGUI(ArrayList<Jugador> jugadores, int nivel) {
        this.ronda = new Ronda(jugadores,nivel);
        createUIComponents();

    }

    public static void main(String[] args) throws IOException {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Lucas"));
        jugadores.add(new Jugador("Lorenzo"));
        jugadores.add(new Jugador("Fernando"));
        new mesaDeJuegoGUI(jugadores,1).setVisible(true);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//
//        //carta.paint(g);
//
//    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("x:"+e.getX()+"y:"+e.getY());
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getComponent().getClass().getName().equals("com.lucas.Carioca_Digital.Carta")) { //si lo que seleccione es una carta
            //cartaMoviendose = (Carta) e.getComponent();
            moviendoCarta = true;

        }

        //TransferHandler th = carta.getTransferHandler();
        //th.exportAsDrag(carta,e,TransferHandler.COPY);

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(moviendoCarta){
            //animate(carta,carta.getLocation(),30,5);
            //cartaMoviendose.setLocation(MouseInfo.getPointerInfo().getLocation());
            moviendoCarta=false;

        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }


    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        //this.setBounds(0,0,1360,768);
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

        mazoBoton = new JButton();
        String rutaImagenMazo = "src//images//cartas//blue_back.png";
        try {
            mazoBoton.setIcon(new ImageIcon(ImageIO.read(new File(rutaImagenMazo)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)));
        }catch (IOException ioe){
            System.err.println(ioe.getMessage());
        }

        pozoBoton = new JButton();
        pozoBoton.setVisible(false);

        String[] columnasJugadoresTabla = {"Nombre", "Cartas","Puntaje"};
        jugadoresDefaultTableModel = new DefaultTableModel(ronda.getArrayObjectJugadores(),columnasJugadoresTabla);
        jugadoresTabla = new JTable(jugadoresDefaultTableModel);


        triosEnLaMesaDefaultTableModel = new DefaultTableModel();
        triosEnLaMesaTabla = new JTable(triosEnLaMesaDefaultTableModel);
        escalasEnLaMesaDefaultTableModel = new DefaultTableModel();
        escalasEnLaMesaTabla = new JTable(escalasEnLaMesaDefaultTableModel);








    }

//    private void animate(JComponent component, Point newPoint, int frames, int interval) {
//        Rectangle compBounds = component.getBounds();
//
//        Point oldPoint = new Point(compBounds.x, compBounds.y),
//                animFrame = new Point((newPoint.x - oldPoint.x) / frames,
//                        (newPoint.y - oldPoint.y) / frames);
//
//        new Timer(interval, new ActionListener() {
//            int currentFrame = 0;
//            public void actionPerformed(ActionEvent e) {
//                component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
//                        oldPoint.y + (animFrame.y * currentFrame),
//                        compBounds.width,
//                        compBounds.height);
//
//                if (currentFrame != frames)
//                    currentFrame++;
//                else
//                    ((Timer)e.getSource()).stop();
//            }
//        }).start();
//    }
}
