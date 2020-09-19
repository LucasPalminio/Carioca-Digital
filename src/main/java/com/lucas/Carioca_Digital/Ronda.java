package com.lucas.Carioca_Digital;

import com.lucas.Utilidades_y_Launcher.Utilidades;

import java.util.ArrayList;
import java.util.Arrays;

public class Ronda {

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

    public Ronda(ArrayList<Jugador> jugadores, int nivel) {
        this.nivel = nivel;
        this.jugadores = jugadores;
        mazo = new Mazo();
        pozo.add(mazo.sacarCarta());
        NROESCALAS_A_FORMAR = RONDAS[nivel][0];
        NROTRIOS_A_FORMAR = RONDAS[nivel][1];
        Jugador.setNROTRIOSyNROESCALAS(NROESCALAS_A_FORMAR, NROTRIOS_A_FORMAR);

    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public ArrayList<Carta> getPozo() {
        return pozo;
    }

    public void setPozo(ArrayList<Carta> pozo) {
        this.pozo = pozo;
    }

    public int getNROESCALAS_A_FORMAR() {
        return NROESCALAS_A_FORMAR;
    }

    public int getNROTRIOS_A_FORMAR() {
        return NROTRIOS_A_FORMAR;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    //Se comienza la ronda, este metodo solo se ejecuta una vez durante la ronda
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

    protected void desarrolloRonda() {
        Jugador jugadorActual = jugadores.get(turnoActual);

        //mostrar menu del jugador
        if (jugadorActual.isBajoSusCarta()) { //Si el jugador se bajo, se desplegara el menu correspondiente con sus respectivas opciones
            menu_jugador_siBajoSusCartas(jugadorActual);
        } else {
            menu_jugador_noBajoSusCartas(jugadorActual);
        }

        if (jugadorActual.getNroCartas() == 0) {
            //Si el jugador Actual se quedo sin cartas, quiere decir que gano la partida
            finRonda();
            return;
        }
        turnoActual++;
        if (turnoActual == jugadores.size()) {
            turnoActual = 0;
        }
        desarrolloRonda();
    }

    //Si el jugador aun no se ha bajado sus cartas, debe mostrar este menu de opciones
    private void menu_jugador_noBajoSusCartas(Jugador jugadorActual) {
        ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Sacar Carta de la mesa", "Sacar Carta del mazo", "Intercambiar el lugar de dos cartas", "¿Desea Bajarse?", "Botar Carta y Finalizar Turno"));

        if(Reglas.isModoDebug()){
            opciones.add("Menu Trampas");
        }

        boolean yaSacoCarta = jugadorActual.isYaSacoCarta();

        if (yaSacoCarta) {
            //Cuando la persona ya saco una carta, la opciones de Sacar Carta de la mesa y Sacar carta del mazo se bloquean
            opciones.set(0,"Sacar Carta de la mesa (Opcion bloqueada)");
            opciones.set(1,"Sacar Carta del mazo (Opcion bloqueada)");
        }
        imprimirInformacionRonda();
        imprimirTrios_y_EscalasEnLaMesa(jugadores);
        jugadorActual.imprimirInformacionJugador(pozo);
        String opcion = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones); //Imprimo el menu de opciones y almaceno la opcion elegida

        switch (opcion) {
            case "1": //Sacar Carta de la mesa
            case "2": //Sacar Carta del mazo
                if (!yaSacoCarta) {
                    if (opcion.equals("1")) {
                        sacarCartaDeLaMesa(jugadorActual);
                    } else if (opcion.equals("2")) {
                        sacarCartaDelMazo(jugadorActual);
                    }

                    jugadorActual.setYaSacoCarta(true);
                } else {
                    System.out.println("Usted ya saco una carta, eliga otra opcion");
                }
                break;
            case "3": //Intercambiar dos cartas dentro de la misma mano
                jugadorActual.intercambiarCartas();
                //menu_jugador(jugadorActual);
                break;
            case "4": //¿Desea Bajarse?
                if (yaSacoCarta){
                    jugadorActual.menu_Bajarse();
                }else{
                    System.out.println("Usted aun no ha sacado una carta, por lo tanto aun no puede bajarse");
                }
                break;
            case "5": //Botar Carta y Finalizar Turno
                if (yaSacoCarta) {
                    pozo.add(0, jugadorActual.menu_BotarCarta());
                    jugadorActual.setYaSacoCarta(false);
                    System.out.println("Fin turno de " + jugadorActual.getNombre());
                    System.out.println("////////////////////////////////////////");
                    return;

                } else {
                    System.out.println("Usted no ha sacado una carta, por lo tanto no puede botar carta aun");
                    break;
                }
            case "6": //Menu Trampa
                if (Reglas.isModoDebug()){
                    Reglas.menuTrampas(jugadorActual,jugadores);
                    break;
                }//Sino esta en modo debug, se despliega el mensaje de error

            default:
                System.out.println("Error la opcion ingresada es incorrecta, intentelo nuevamente");
                break;

        }
        // Si el jugador llegase a bajarse durante la ronda este metodo evitara que tenga que esperar a la siguiente ronda
        // para que se le considere bajado, ya que de no estar el jugador se podria bajar dos veces
        if(jugadorActual.isBajoSusCarta()) {
            menu_jugador_siBajoSusCartas(jugadorActual);
        }else{
            menu_jugador_noBajoSusCartas(jugadorActual);
        }

    }
    //Si el jugador se ha bajado sus cartas, debe mostrar este menu de opciones
    private void menu_jugador_siBajoSusCartas(Jugador jugadorActual) {
        ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Sacar Carta del mazo","Sacar Carta de la mesa", "Intercambiar el lugar de dos cartas", "¿Desea agregar cartas a los trios o escalas en la mesa?", "Finalizar Turno"));
        if(Reglas.isModoDebug()){
            opciones.add("Menu Trampas");
        }
        boolean yaSacoCarta = jugadorActual.isYaSacoCarta();

        if (yaSacoCarta) {
            opciones.set(0,"Sacar Carta del mazo (Opcion bloqueada)");
            opciones.set(1,"Sacar Carta de la mesa (Opcion bloqueada)");
        }
        imprimirInformacionRonda();
        imprimirTrios_y_EscalasEnLaMesa(jugadores);
        jugadorActual.imprimirInformacionJugador(pozo);
        String opcion = Utilidades.imprimirMenuOpciones_e_ingresarUnaOpcion(opciones);

        switch (opcion) {
            case "1":
                if (!yaSacoCarta) {
                    sacarCartaDelMazo(jugadorActual);
                    jugadorActual.setYaSacoCarta(true);
                } else {
                    System.out.println("Usted ya saco una carta, eliga otra opcion");
                }
                break;
            case "2":
                if (!yaSacoCarta) {
                    sacarCartaDeLaMesa(jugadorActual);
                    jugadorActual.setYaSacoCarta(true);
                } else {
                    System.out.println("Usted ya saco una carta, eliga otra opcion");
                }
                break;
            case "3":
                jugadorActual.intercambiarCartas();
                break;
            case "4":
                //Pendiente
                if(yaSacoCarta){
                    System.out.println("Aun no disponible");
                }else{
                    System.out.println("Usted aun no ha sacado carta, asique aun no puede agregar cartas a trios y/o escalas que hay en la mesa");
                }

                break;
            case "5":
                if (yaSacoCarta) {
                    pozo.add(0, jugadorActual.menu_BotarCarta());
                    jugadorActual.setYaSacoCarta(false);
                    System.out.println("Fin turno de " + jugadorActual.getNombre());
                    System.out.println("////////////////////////////////////////");
                    return;

                } else {
                    System.out.println("Usted no ha sacado una carta, por lo tanto no puede botar carta aun");
                    break;
                }
            case "6":
                if (Reglas.isModoDebug()){
                    Reglas.menuTrampas(jugadorActual,jugadores);
                    break;
                }
            default:
                System.out.println("Error la opcion ingresada es incorrecta, intentelo nuevamente");


        }
        menu_jugador_siBajoSusCartas(jugadorActual);

    }
    //metodo cuando un jugador en su turno saca una carta de la mesa
    private void sacarCartaDeLaMesa(Jugador jugador) {
        Carta cartaEnLaMesa = pozo.get(0);
        jugador.agregarCarta(cartaEnLaMesa);
        pozo.remove(0);
    }
    //metodo cuando un jugador en su turno saca una carta del mazo
    private void sacarCartaDelMazo(Jugador jugador) {
        Carta cartaDelMazo = mazo.sacarCarta();
        System.out.println("obtuviste esta carta del mazo: " + cartaDelMazo.toStringEC());
        jugador.agregarCarta(cartaDelMazo);
    }
    //Este metodo imprime quien es el turnoActual y que carta hay en la mesa
    protected void imprimirInformacionRonda() {
        System.out.println("///////////////////////////////////");
        System.out.println("Turno Actual: " + turnoActual);
        if (pozo.size() > 0) {
            System.out.println("Carta en la mesa: " + pozo.get(0).toStringEC() + "\n");
        } else {
            System.out.println("Carta en la mesa: |   |");
        }
    }
    //método para imprimir todos los trios y escalas de los jugadores que se hayan bajado
    private  void imprimirTrios_y_EscalasEnLaMesa(ArrayList<Jugador> jugadores){
        System.out.println("Trios y/o escalas en la mesa:\n");
        for(int i=0;i<jugadores.size();i++){
            jugadores.get(i).imprimirTrios_y_Escalas();
        }
    }
    //Este metodo es cuando la ronda termina (uno de los jugadores se queda sin carta en la mano)
    // muestra la tabla de puntajes que sacaron en esta ronda los jugadores (dice quien fue el ganador de la ronda)
    // y muestra la tabla de sus puntajes finales (dice tambien quien lleva la delantera)
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
                System.out.print(Utilidades.ANSI_YELLOW + "(Ganador)" + Utilidades.ANSI_RESET + jugador.getNombre() + ": " + jugador.getPuntajeRonda() + " ");
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
                System.out.print(Utilidades.ANSI_YELLOW + "(Delantera)" + Utilidades.ANSI_RESET + jugadores.get(i).getNombre() + ": " + jugadores.get(i).getPuntajeRonda() + " ");
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

}
