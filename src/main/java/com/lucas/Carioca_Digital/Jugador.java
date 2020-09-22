package com.lucas.Carioca_Digital;


import com.lucas.Utilidades_y_Launcher.Utilidades;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    private int puntajeRonda;
    private boolean bajoSusCarta;
    private boolean yaSacoCarta;
    private ArrayList<ArrayList<Carta>> matrizTrios = new ArrayList<ArrayList<Carta>>(); //Matrices cuando se baja
    private ArrayList<ArrayList<Carta>> matrizEscalas =  new ArrayList<ArrayList<Carta>>();

    private static int NROTRIOS_a_formar;
    private static int NROESCALAS_a_formar;

    public static Scanner in = new Scanner(System.in);

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.bajoSusCarta = false;
        this.yaSacoCarta = false;
    }

    protected static void setNROTRIOSyNROESCALAS(int nroEscalasRonda,int nroTriosRonda) {
        NROTRIOS_a_formar = nroTriosRonda;
        NROESCALAS_a_formar = nroEscalasRonda;
    }
    public String getNombre() {
        return nombre;
    }
    protected int getPuntaje() {
        return puntaje;
    }

    protected int getPuntajeRonda() {
        return puntajeRonda;
    }

    protected void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }
    protected void calcularPuntajeRonda(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntajeRonda += carta.getPrecio();
            }
        }
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    protected void setPuntajeRonda(int puntajeRonda) {
        this.puntajeRonda = puntajeRonda;
    }

    protected void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
    protected void agregarCarta(Carta carta){
        cartas.add(carta);
    }
    protected int getNroCartas(){ return cartas.size(); }

    protected boolean isBajoSusCarta() {
        return bajoSusCarta;
    }
    protected void setBajoSusCarta(boolean bajoSusCarta) {
        this.bajoSusCarta = bajoSusCarta;
    }

    protected boolean isYaSacoCarta() {
        return yaSacoCarta;
    }

    protected void setYaSacoCarta(boolean yaSacoCarta) {
        this.yaSacoCarta = yaSacoCarta;
    }

    protected void limpiarMatriz(){
        //Este metodo funciona solamente cuando finaliza la ronda,
        // su funcion es limpia las matrices de trios y escalas que el jugador tenga en la mesa
        matrizTrios.clear();
        matrizEscalas.clear();
    }

    protected Carta menu_BotarCarta(){
        imprimirCartas();
        int indiceCarta = Utilidades.ingresarUnNumero("¿Que cartas quieres botar?: ");
        if (indiceCarta >= 0 && indiceCarta < cartas.size()){
            Carta cartaABotar = cartas.get(indiceCarta);
            cartas.remove(indiceCarta);
            return cartaABotar;
        }else{
            System.out.println("Error el numero ingresado esta fuera del rango, intentelo nuevamente");
            return menu_BotarCarta();
        }

    }
    protected void menu_Bajarse(){
        System.out.println("¿Seguro que quiere bajarse? " +
                "\nEscriba "+Utilidades.ANSI_RED+"si"+ Utilidades.ANSI_RESET+" para confirmar, de lo contrario escriba otra palabra para cancelar la operacion");
        if (!in.nextLine().equalsIgnoreCase("si")){
            //En caso de que el usuario no ingreso si, se detiene la ejecucion del metodo
            System.out.println("Usted se arrepintio de bajarse");
            return;
        }
        if(NROTRIOS_a_formar > 0){
            for (int i = 0; i < NROTRIOS_a_formar; i++) {
                imprimirCartas();
                System.out.println("Ingrese el trio "+i);
                matrizTrios.add(crearUnTrio());

            }
        }
        if (NROESCALAS_a_formar > 0){
            for (int i = 0; i < NROESCALAS_a_formar; i++) {
                imprimirCartas();
                System.out.println("Ingrese la escala "+i);
                matrizEscalas.add(crearUnaEscala());

            }
        }
        bajoSusCarta = true;

    }

    protected void imprimirCartas(){ //Este metodo imprime las cartas del jugador
        System.out.println("Cartas de "+nombre);
        imprimirCartas(cartas);
    }
    public static void imprimirCartas(ArrayList<Carta> cartas){
        String contenido = "";
        String primeraLinea = "";
        String segundaLinea = "";
        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            primeraLinea += carta.toStringEC();
            int numeroEspacios = (carta.toString().length()+2)/2;
            segundaLinea += Utilidades.repetirString(" ",numeroEspacios) +i+ Utilidades.repetirString(" ",numeroEspacios);
        }
        contenido = primeraLinea + "\n" + segundaLinea;
        System.out.println(contenido);

    }
    protected void imprimirInformacionJugador(ArrayList<Carta> pozo){
        System.out.println("Turno: " + nombre);
        imprimirCartas();


    }
    protected void intercambiarCartas(){

        int primera_carta = 0;
        int segunda_carta = 0;

        primera_carta = Utilidades.ingresarUnNumero("Primera Carta a intercambiar",0,cartas.size()-1);

        segunda_carta = Utilidades.ingresarUnNumero("Segunda Carta a intercambiar",0,cartas.size()-1);
        Collections.swap(cartas, primera_carta, segunda_carta);

        imprimirCartas();


    }

    protected ArrayList<Carta> crearUnTrio(){ //Este metodo es para crear un solo trio
        int[] indices=ingresarIndicesTrio();
        //Primero ingresamos los indices de las cartas que conforman el trio,
        // verificando que estos indices no se repitan, y correspondan realmente a un trio
        ArrayList<Carta> trio = new ArrayList<Carta>(); //Arraylist donde se almacenara temporalmente el trio
        for (int i = 0; i < 3; i++) {
            //Por cada indice de la carta se almacena en trio
            int ind = indices[i];
            trio.add(cartas.get(ind));

        }
        cartas.removeAll(trio);//Una vez hecho el trio, para evitar la duplicacion de cartas, estas se debe borrar de las cartas del jugador
        imprimirCartas(trio);
        return trio;

    }
    protected ArrayList<Carta> crearUnaEscala(){
        int[] indices = ingresarIndicesEscala();
        ArrayList<Carta> escala = new ArrayList<Carta>();
        for (int i = 0; i < 4; i++) {
            int ind = indices[i];
            escala.add(cartas.get(ind));

        }
        cartas.removeAll(escala);//Una vez hecho el trio, para evitar la duplicacion de cartas, estas se debe borrar de las cartas del jugador
        imprimirCartas(escala);
        return escala;
    }

    protected  int[] ingresarIndicesTrio(){
        System.out.println("Ingrese los indices de un trio");
        int[] indicesTrio = ingresarIndices(3);
        String valorEsperado = cartas.get(indicesTrio[0]).getValor();
        for (int i = 0; i < 3; i++) {
            int ind = indicesTrio[i];
            String valor = cartas.get(ind).getValor();
            String palo = cartas.get(ind).getPalo();
            if (!(valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")){//Si los valores son diferentes
                //Vuelve a preguntar
                System.out.println("Error los indices que ingresaste no corresponde a un trio");
                return ingresarIndicesTrio();
            }
        }
        return indicesTrio;
    }
    protected int[] ingresarIndicesEscala(){
        System.out.println("Ingrese los indices de una escala");
        int[] indicesEscala = ingresarIndices(4);
        String paloEsperado = cartas.get(indicesEscala[0]).getPalo(); //Corazon
        String valorEsperado = cartas.get(indicesEscala[0]).getValor(); //K
        //int indiceValor = Carta.VALORES.toString().indexOf(valorEsperado); //12, En que posicion del arreglo de VALORES esta el valor: K
        int indiceValor = Utilidades.StringArrayindexOf(Carta.VALORES,valorEsperado);
        for (int i = 0; i < 4; i++) {
            int ind = indicesEscala[i];
            Carta carta = cartas.get(ind);
            String palo = carta.getPalo();
            String valor = carta.getValor();
            if (!(palo.equalsIgnoreCase(paloEsperado) && valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")){
                //si los indices ingresado no corresponden a una escala, pregunte de nuevo
                return ingresarIndicesEscala();
            }else{
                indiceValor++;
                if (indiceValor == Carta.VALORES.length){
                    indiceValor = 0;
                }
                valorEsperado = Carta.VALORES[indiceValor];
            }
        }
        return indicesEscala;
    }
    protected int[] ingresarIndices(int nroIndices) { // Este metodo es para ingresar los indices de las cartas del jugador, nroIndices puede ser 3 o 4
        int[] indices = new int[nroIndices];

        System.out.println("Ingrese los indices de las cartas (separado por espacios): ");
        String[] indicesString = Utilidades.tecladoNext().split(" ");//Se ingresa los indices separados por espacios, posteriormente esos indices se almacenan en un arreglo
        if (indicesString.length == nroIndices) {
            //Primero verificamos si el nroIndices ingresados corresponde al nro de indices solicitados
            try {
                for (int i = 0; i < indicesString.length; i++) {
                    int numero = Integer.parseInt(indicesString[i]);//Convertimos los indices de string a int

                    if (numero >= 0 && numero < cartas.size()) { //si el numero esta dentro del rango de los indices
                        indices[i] = numero; //Se guarda el numero dentro del arreglo de los indices
                        if (i == nroIndices-1) { //Una vez ingresado el ultimo indice
                            if(Utilidades.sonNumerosDiferentes(indices)) { //Se verifica que los indices sean numeros diferentes
                                return indices;
                            }else{
                                System.out.println("Los numeros deben ser diferentes, intentelo nuevamente");
                            }
                        }
                    } else {
                        System.out.println("El numero: " + numero + " no esta dentro del rango");
                        System.out.println("Intentelo nuevamente");
                        break;
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Error el caracter ingresado no es un numero");
            }
        } else {
            System.out.println("Usted ingresado mas numero de lo permitido, intentelo nuevamnete");
        }
        return ingresarIndices(nroIndices); //Si a ocurrido un error, volvera a preguntar al usuario por los indices
    }

    protected ArrayList<ArrayList<Carta>> getMatrizTrios() {
        return matrizTrios;
    }

    protected ArrayList<ArrayList<Carta>> getMatrizEscalas() {
        return matrizEscalas;
    }
    protected  void imprimirTrios_y_Escalas() {
        if (isBajoSusCarta()) {
            System.out.println(getNombre());
            if (matrizTrios.size() > 0) {
                System.out.println("Trios:\n");
                for (int j = 0; j < getMatrizTrios().size(); j++) {
                    System.out.println("Trio " + j + ": ");
                    imprimirCartas(getMatrizTrios().get(j));
                }
            }
            if (matrizEscalas.size()>0) {
                System.out.println("\nEscalas:\n");
                for (int j = 0; j < getMatrizEscalas().size(); j++) {
                    System.out.println("Escala " + j + ": ");
                    imprimirCartas(getMatrizEscalas().get(j));

                }
            }
        }
    }

    public Object[] getArrayObject(){
        Object[] array = {nombre, this.getNroCartas(), this.getPuntaje()};
        return array;
    }
}
