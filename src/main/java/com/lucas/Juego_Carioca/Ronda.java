package com.lucas.Juego_Carioca;

import java.util.ArrayList;
import java.util.Scanner;

public class Ronda {
    public static Scanner in = new Scanner(System.in);
    public static final int[][] RONDAS = {
            //{Escala, Trio} ; 6,7,8,9,10,11
            {0, 2}, // 0*4+2*3 = 6 (2 trios)
            {1, 1}, //1*4+1*3 = 7 (1 escala y 1 trio)
            {2, 0}, // 2 escalas
            {0, 3}, // 3 trios
            {1, 2}, // 1 escala y 2 trios
            {2, 1} //2 escalas y 1 trio
    };
    private int nivel;
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private ArrayList<Carta> pozo = new ArrayList<Carta>();
    private final int NROESCALAS;
    private final int NROTRIOS;
    public Ronda(ArrayList<Jugador> jugadores, int nivel) {
        this.nivel = nivel;
        this.jugadores = jugadores;
        mazo = new Mazo();
        pozo.add(mazo.sacarCarta());
        NROESCALAS = RONDAS[nivel][0];
        NROTRIOS = RONDAS[nivel][1];
        Jugador.setNROTRIOSyNROESCALAS(NROESCALAS,NROTRIOS);
    }

    public void comenzarRonda() {
        //Se crea un juego

        //Antes de comenzar a jugar, Entregamos a cada jugador doce cartas
        for (int index = 0; index < jugadores.size(); index++) {
            jugadores.get(index).setCartas(mazo.sacarUnNumeroDeCartas(12));
        }
        //ArrayList<Carta> pozo = new ArrayList<Carta>(); //Este es el pozo donde los jugadores botan sus cartas

        //pozo.add(mazo.sacarCarta()); //Se extrae una carta del mazo para dejarla en la mesa, al principio de la ronda (esta carta nunca sera un JKR)

        int turnoActual = (int) (Math.random() * jugadores.size()); //y el primer turno se hara de forma aleatoria

        //Imprimimos un mensaje que da comienzo el juego y cuantas escalas o/y trios hay que formar
        MainCarioca.imprimirTitulo("Comienza el juego nivel " + (nivel + 1) + "\n"
                + "Escala: " + NROESCALAS + " Trios: " + NROTRIOS);
        //Aqui comienza el juego, deberia haber un bucle donde va jugando cada jugador,
        //el juego termina cuando un jugador se queda sin cartas

        //Este Bucle funciona hasta que alguien se quede sin cartas (temporal, lo ideal remplezarlo por un metodo)
        do {
            menu_TurnoActual(turnoActual);
            turnoActual++;
            if (turnoActual == jugadores.size()) {
                turnoActual = 0;
            }
        }while(true);
    }
    public void menu_TurnoActual(int turnoActual){
        //Carta cartaEnLaMesa = pozo.get(0); //La carta que esta mas arriba en el pozo, este objeto es solo para proposito de imprimir en pantalla
        Jugador jugadorActual = jugadores.get(turnoActual);
        System.out.println("Turno Actual: " + turnoActual);
        menu_jugador(jugadorActual);
        if(jugadorActual.getNroCartas()==0){
            //Si el jugador Actual se quedo sin cartas, quiere decir que gano la partida
            System.out.println("Fin de la ronda, la ronda gano ");
        }


    }

    public void menu_jugador(Jugador jugadorActual){


        if (jugadorActual.isBajoSusCarta()){ //Si el jugador se bajo, se desplegara el menu correspondiente con sus respectivas opciones
            menu_jugador_siBajoSusCartas(jugadorActual);
        }else{
            menu_jugador_noBajoSusCartas(jugadorActual);
        }
    }
    private void menu_jugador_noBajoSusCartas(Jugador jugadorActual){
        String[] opciones = {"Sacar Carta de la mesa","Sacar Carta del mazo","Intercambiar el lugar de dos cartas","¿Desea Bajarse?","Botar Carta y Finalizar Turno"};
        boolean yaSacoCarta = false;
        OUTER1:
        do {
            if(yaSacoCarta){
                //Cuando la persona ya saco una carta, la opciones de Sacar Carta de la mesa y Sacar carta del mazo se bloquean
                opciones[0] = "Sacar Carta de la mesa (Opcion bloqueada)";
                opciones[1] = "Sacar Carta del mazo (Opcion bloqueada)";
            }
            imprimirInformacionRonda();
            jugadorActual.imprimirInformacionJugador(pozo);

            String opcion = MainCarioca.imprimirMenuOpciones(opciones);
            switch (opcion) {
                case "1": //Sacar Carta de la mesa
                case "2": //Sacar Carta del mazo
                    if(!yaSacoCarta) {
                        if(opcion.equals("1")) {
                            sacarCartaDeLaMesa(jugadorActual);
                        }else if (opcion.equals("2")){
                            sacarCartaDelMazo(jugadorActual);
                        }

                        yaSacoCarta = true;
                    }else{
                        System.out.println("Usted ya saco una carta, eliga otra opcion");
                    }
                    break;
                case "3": //Intercambiar dos cartas dentro de la misma mano
                    jugadorActual.intercambiarCartas();
                    menu_jugador(jugadorActual);
                    break;
                case "4":
                    jugadorActual.menu_Bajarse();
                    break;
                case "5":
                    if (yaSacoCarta){
                        pozo.add(0,jugadorActual.menu_BotarCarta());
                        break OUTER1;
                    }else{
                        System.out.println("Usted no ha sacado una carta, por lo tanto no puede botar carta aun");
                        break;
                    }

                default:
                    System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                    menu_jugador(jugadorActual);

            }
        }while (true);


    }
    private void menu_jugador_siBajoSusCartas(Jugador jugadorActual){

        String[] opciones = {"Sacar Carta del mazo","Intercambiar el lugar de dos cartas","¿Desea agregar cartas a los trios o escalas en la mesa?","Finalizar Turno"};
        boolean yaSacoCarta = false;
        do {
            if (yaSacoCarta){
                opciones[0] += "(Opcion bloqueada)";
            }
            String opcion = MainCarioca.imprimirMenuOpciones(opciones);
            jugadorActual.imprimirInformacionJugador(pozo);
            switch (opcion) {
                case "1":
                    sacarCartaDelMazo(jugadorActual);
                    break;
                case "2":
                    jugadorActual.intercambiarCartas();
                    break;
                case "3":
                    //Pendiente
                    System.out.println("Aun no disponible");
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                    menu_jugador(jugadorActual);
            }
        }while (true);
    }
    private void sacarCartaDeLaMesa(Jugador jugador){
        Carta cartaEnLaMesa = pozo.get(0);
        jugador.agregarCarta(cartaEnLaMesa);
        pozo.remove(0);
    }
    private void sacarCartaDelMazo(Jugador jugador){
        Carta cartaDelMazo = mazo.sacarCarta();
        System.out.println("obtuviste esta carta del mazo: "+cartaDelMazo.toStringEC());
        jugador.agregarCarta(cartaDelMazo);
    }
    public void imprimirInformacionRonda(){
        if (pozo.size() > 0) {
            System.out.println("Carta en la mesa: " + pozo.get(0).toStringEC() + "\n");
        }else{
            System.out.println("Carta en la mesa: |   |");
        }
    }

}
