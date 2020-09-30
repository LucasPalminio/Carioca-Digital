package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;
import java.util.Arrays;

public class Ronda {
    /**
     * Una clase que genera todo lo relacionado a la ronda, tanto el nivel que se jugara como las opcines
     * que podra hacer el jugador
     * @param nivel El nivel actual de la ronda
     * @param mazo baraja de cartas ordenas aleatoriamente
     * @param Jugador arreglo de los jugadores que jugaran
     * @param Carta arreglo de las cartas de cada jugador
     * @param NROESCALAS numero de escalas que hay que formar en esta ronda
     * @param NROTRIOS Numero de trios que hay que formar en esta ronda
     * @param turnoActual turno actual que se usa como indice para el arreglo de jugadores
     *
     */
    public static final int[][] RONDAS = { //Hacer ENUM
            //{Escala, Trio} ; 6,7,8,9,10,11
            {0, 2}, // 0*4+2*3 = 6 (2 trios) nivel 1
            {1, 1}, //1*4+1*3 = 7 (1 escala y 1 trio) nivel 2
            {2, 0}, // 2 escalas, nivel 3
            {0, 3}, // 3 trios, nivel 4
            {1, 2}, // 1 escala y 2 trios, nivel 5
            {2, 1} //2 escalas y 1 trio, nivel 6
    };
    private int nivel; // El nivel actual de la ronda
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private ArrayList<Carta> pozo = new ArrayList<Carta>();
    private final int NROESCALAS_A_FORMAR; //Nro de escalas que hay que formar en esta ronda
    private final int NROTRIOS_A_FORMAR; //Nro de trios que hay que formar en esta ronda
    private int turnoActual; //turno actual que se usa como indice para el arreglo de jugadores

    /**
     * En este metodo se instancian las variables en la clase actual
     * @param jugadores es la cantidad de jugadores que jugaran
     * @param nivel especifica la cantidad de trios y escalas
     */
    public Ronda(ArrayList<Jugador> jugadores, int nivel) {
        this.nivel = nivel;
        this.jugadores = jugadores;
        mazo = new Mazo();
        pozo.add(mazo.sacarCarta());
        NROESCALAS_A_FORMAR = RONDAS[nivel][0];
        NROTRIOS_A_FORMAR = RONDAS[nivel][1];
    }

    public Jugador getJugadorActual(){
        return jugadores.get(turnoActual);
    }

    public int getNivel() {
        return nivel;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public ArrayList<Carta> getPozo() {
        return pozo;
    }

    public int getNROESCALAS_A_FORMAR() {
        return NROESCALAS_A_FORMAR;
    }

    public int getNROTRIOS_A_FORMAR() {
        return NROTRIOS_A_FORMAR;
    }


    /**
     * EN este metodo se comienza la ronda, y  solo se ejecuta una vez durante esta
     * Se entrga a cada jugador sus doce cartas
     * Se genera de forma aleatoria el orden para jugar
     * Se muestra por pantalla la cantida de trios y escalas que hay hacer en esta ronda
     */
    public void comenzarRonda() {


        //Antes de comenzar a jugar, Entregamos a cada jugador doce cartas
        for (Jugador jugador : jugadores) {
            jugador.setCartas(mazo.sacarUnNumeroDeCartas(12));
        }
        //ArrayList<Carta> pozo = new ArrayList<Carta>(); //Este es el pozo donde los jugadores botan sus cartas

        //pozo.add(mazo.sacarCarta()); //Se extrae una carta del mazo para dejarla en la mesa, al principio de la ronda (esta carta nunca sera un JKR)

        turnoActual = (int) (Math.random() * jugadores.size()); //y el primer turno se hara de forma aleatoria

        //Imprimimos un mensaje que da comienzo el juego y cuantas escalas o/y trios hay que formar
        Utilidades.imprimirTitulo("Comienza el juego nivel " + (nivel + 1) + "\n"
                + "Escala: " + NROESCALAS_A_FORMAR + " Trios: " + NROTRIOS_A_FORMAR);
        //Aqui comienza el juego, deberia haber un bucle donde va jugando cada jugador,
        //el juego termina cuando un jugador se queda sin cartas

        //Este Bucle funciona hasta que alguien se quede sin cartas (temporal, lo ideal remplezarlo por un metodo)
        //Se llama al sgte metodo: desarrolloRonda,
        //desarrolloRonda();
    }


    public Carta getPrimeraCartaDelPozo(){
        return pozo.get(0);

    }

    public void jugadorActualSacaCartaDeLaMesa(){
        Carta cartaEnLaMesa = pozo.get(0);
        jugadores.get(turnoActual).agregarCarta(cartaEnLaMesa);
        pozo.remove(0);
        jugadores.get(turnoActual).setYaSacoCarta(true);
    }

    public void jugadorActualSacaCartaDelMazo(){
        Carta cartaDelMazo = mazo.sacarCarta();
        jugadores.get(turnoActual).agregarCarta(cartaDelMazo);
        jugadores.get(turnoActual).setYaSacoCarta(true);
    }

    public void jugadorActualBotaCartaAlPozo(int indice){
        pozo.add(0, jugadores.get(turnoActual).botarCarta(indice));
        jugadores.get(turnoActual).setYaSacoCarta(false);
    }

    public void siguienteTurnoJugador(){
        turnoActual++;
        if(turnoActual >= jugadores.size()){
            turnoActual = 0;
        }

    }



    /**
     * Este metodo es cuando la ronda termina (uno de los jugadores se queda sin carta en la mano)
     * muestra la tabla de puntajes que sacaron en esta ronda los jugadores
     * (dice quien fue el ganador de la ronda) y muestra la tabla de sus
     * puntajes finales (dice tambien quien lleva la delantera)
     */
    protected void finRonda() {
        int jugador_con_delantera = 0;
        int puntaje_con_delantera = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.print(Utilidades.repetirString("/", 20));
        }
        System.out.println("\nResultados de esta ronda");
        for (Jugador jugador : jugadores) {
            jugador.calcularPuntajeRonda();
            if (jugador.getPuntajeRonda() > 0) {
                System.out.print(jugador.getNombre() + ": " + jugador.getPuntajeRonda() + " ");
            } else {
                System.out.print("(Ganador)" + jugador.getNombre() + ": " + jugador.getPuntajeRonda() + " ");
            }
            jugador.setPuntajeRonda(0);
        }
        for (Jugador jugador : jugadores) {
            jugador.calcularPuntaje();

        }
        for(int i= 0;i<jugadores.size();i++){
            if(i>0){
                if(puntaje_con_delantera>jugadores.get(i).getPuntaje()){
                    puntaje_con_delantera = jugadores.get(i).getPuntaje();
                    jugador_con_delantera = i;
                }
            }else{

                jugador_con_delantera = i;
                puntaje_con_delantera = jugadores.get(i).getPuntaje();
            }
        }

        System.out.println("\nResultados de la partida en esta ronda");
        for (int i = 0; i < jugadores.size(); i++) {
            if(jugador_con_delantera == i){
                System.out.print("(Delantera)" + jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntajeRonda() + " ");
            }else {
                System.out.print(jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntaje() + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.print(Utilidades.repetirString("/", 20));
        }
        System.out.println("\n");
    }

    public Object[][] getArrayObjectJugadores(){
        Object[][] data = new Object[jugadores.size()][3];
        for (int i = 0; i < jugadores.size() ; i++) {
            data[i] = jugadores.get(i).getArrayObject();

        }
        return data;
    }

    public Object[][] getArrayObjectCartasJugadores(){
        Object[][] data = new Object[jugadores.size()][2];
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            String nombre = jugador.getNombre();
            String cartasToString = "";
            for (int j = 0; j < jugador.getNroCartas(); j++) {
                Carta carta = jugador.getCartas().get(j);
                cartasToString += "|"+carta.toString()+"| ";
            }
            data[i][0] = nombre;
            data[i][1] = cartasToString;
        }
        return data;
    }


}
