package com.lucas.Utilidades_y_Launcher;

import com.lucas.Carioca_Digital.Juego;
import com.lucas.Carioca_Digital.MenuPrincipal;
import com.lucas.Carioca_Digital.Reglas;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        Utilidades.setIn(new Scanner(System.in).useDelimiter("\n"));
        MenuPrincipal.menuPrincipal();



    }


}
