package com.lucas.launcher;

import com.lucas.gui.MenuPrincipalGUI;

/**
 * Esta clase solo cumple el propósito de ejecutar el resto del programa
 */
public class Launcher {
    /**
     * Lanzador del programa, haciendo visible la ventana de la interfaz grafica
     * @param args son los argumentos que recibe el método
     */

    public static void main(String[] args) {

/*
        Utilidades.setIn(new Scanner(System.in).useDelimiter("\n"));
        MenuPrincipal.menuPrincipal();
        */
        MenuPrincipalGUI a = new MenuPrincipalGUI();
        a.setVisible(true);
    }


}
