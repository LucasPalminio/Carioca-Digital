package com.lucas.Utilidades_y_Launcher;

import com.lucas.Guis.MenuPrincipalGUI;

public class Launcher {
    /**
     *  Se encarga de iniciar el programa, haciendo visible la ventana de la interfaz gráfica
     * @param args son los argumentos que recibe el metodo
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
