package com.lucas.guis;

import com.lucas.Carioca_Digital.Carta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class mesaDeJuegoGUI extends JFrame implements MouseListener , MouseMotionListener {
    private JPanel panel;
    private JTable tablaPuntajes;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JList list1;
    private JTable table1;
    private JTable table2;
    private JButton button1;
    private JButton button2;
    private Carta cartaMoviendose;

    private boolean moviendoCarta = false;
    public mesaDeJuegoGUI()  {
        this.setBounds(0,0,1360,768);
        this.setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseListener(this);
        addMouseMotionListener(this);
        cartaMoviendose = new Carta("H","4",30,30);
        cartaMoviendose.addMouseListener(this);
        cartaMoviendose.setTransferHandler(new TransferHandler("Carta"));
        add(cartaMoviendose);

    }

    public static void main(String[] args) throws IOException {

        new mesaDeJuegoGUI().setVisible(true);
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
            cartaMoviendose = (Carta) e.getComponent();
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
            cartaMoviendose.setLocation(MouseInfo.getPointerInfo().getLocation());
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
