package com.lucas.Utilidades_y_Launcher;

import com.lucas.guis.MenuPrincipalGUI;

public class Launcher {

    public static void main(String[] args) {
/*
        Utilidades.setIn(new Scanner(System.in).useDelimiter("\n"));
        MenuPrincipal.menuPrincipal();
        */
        MenuPrincipalGUI a = new MenuPrincipalGUI();
        a.setVisible(true);
    }


}
