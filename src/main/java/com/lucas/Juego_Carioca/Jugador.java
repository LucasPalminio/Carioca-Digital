package com.lucas.Juego_Carioca;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    private boolean seBajo;
    private ArrayList<ArrayList<Carta>> matrizTrios = new ArrayList<ArrayList<Carta>>(); //Matrices cuando se baja
    private ArrayList<ArrayList<Carta>> matrizEscalas =  new ArrayList<ArrayList<Carta>>();

    private static int NROTRIOS;
    private static int NROESCALAS;

    public static Scanner in = new Scanner(System.in);
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.seBajo = false;
    }
    public static void setNROTRIOSyNROESCALAS(int nroEscalasRonda,int nroTriosRonda) {
        NROTRIOS = nroTriosRonda;
        NROESCALAS = nroEscalasRonda;
    }
    public String getNombre() {
        return nombre;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
    public int getNroCartas(){ return cartas.size(); }

    public void limpiarMatriz(){
        //Este metodo funciona solamente cuando finaliza la ronda,
        // su funcion es limpia las matrices de trios y escalas que el jugador tenga en la mesa
        matrizTrios.clear();
        matrizEscalas.clear();
    }

    public void menu(ArrayList<Carta> pozo, Mazo mazo){

        if (seBajo){ //Si el jugador se bajo, se desplegara el menu correspondiente con sus respectivas opciones
            menu_SiSeBajo(pozo, mazo);
        }else{
            menu_NoSeBajo(pozo,mazo);
        }
    }
    private void menu_NoSeBajo(ArrayList<Carta> pozo, Mazo mazo){
        String[] opciones = {"Sacar Carta de la mesa","Sacar Carta del mazo","Intercambiar el lugar de dos cartas","¿Desea Bajarse?","Finalizar Turno"};
        boolean yaSacoCarta = false;
        do {
            //Se desplegara en pantalla cada una de las opciones para una persona que aun no se ha bajado
            for (int i = 0; i < opciones.length; i++) {
                System.out.println("(" + (i + 1) + ") " + opciones[i]);
            }
            System.out.print("Eliga una opcion: ");
            String opcion = in.nextLine();
            switch (opcion) {
                case "1": //Sacar Carta de la mesa
                case "2": //Sacar Carta del mazo
                    if(!yaSacoCarta) {
                        if(opcion.equals("1")) {
                            sacarCartaDeLaMesa(pozo);
                        }else if (opcion.equals("2")){
                            sacarCartaDelMazo(pozo,mazo);
                        }

                        yaSacoCarta = true;
                    }else{
                        System.out.println("Usted ya saco una carta, eliga otra opcion");
                    }
                    break;
                case "3": //Intercambiar dos cartas dentro de la misma mano
                    intercambiarCartas(pozo, mazo);
                    break;
                case "4":
                    menu_Bajarse();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                    menu(pozo, mazo);

            }
        }while (true);


    }
    private void menu_SiSeBajo(ArrayList<Carta> pozo, Mazo mazo){
        String[] opciones = {"Sacar Carta del mazo","Intercambiar el lugar de dos cartas","¿Desea agregar cartas a los trios o escalas en la mesa?","Finalizar Turno"};
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("("+(i+1)+") "+opciones[i]);
        }
        System.out.print("Eliga una opcion: ");
        String opcion = in.nextLine();
        switch (opcion){
            case"1":
                sacarCartaDelMazo(pozo, mazo);
                break;
            case "2":
                intercambiarCartas(pozo,mazo);
                break;
            case "3":
                //Pendiente
                System.out.println("Aun no disponible");
                break;
            case "4":
                return;
            default:
                System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                menu(pozo,mazo);
        }
    }
    private void sacarCartaDeLaMesa(ArrayList<Carta> pozo){
        Carta cartaEnLaMesa = pozo.get(0);
        cartas.add(cartaEnLaMesa);
        pozo.remove(0);
        cartaEnLaMesa = menu_BotarCarta();
        pozo.add(0,cartaEnLaMesa);
    }
    private void sacarCartaDelMazo(ArrayList<Carta>pozo ,Mazo mazo){
        Carta cartaDelMazo = mazo.sacarCarta();
        System.out.println("obtuviste esta carta del mazo: "+cartaDelMazo.toStringEC());
        cartas.add(cartaDelMazo);
        pozo.add(0,menu_BotarCarta());
    }


    private Carta menu_BotarCarta(){
        imprimirCartas();
        int indiceCarta = MainCarioca.ingresarUnNumero("¿Que cartas quieres botar?: ");
        if (indiceCarta >= 0 && indiceCarta < cartas.size()){
            Carta cartaABotar = cartas.get(indiceCarta);
            cartas.remove(indiceCarta);
            return cartaABotar;
        }else{
            System.out.println("Error el numero ingresado esta fuera del rango, intentelo nuevamente");
            return menu_BotarCarta();
        }

    }
    public void menu_Bajarse(){
        System.out.println("¿Seguro que quiere bajarse? " +
                "\nEscriba si para confirmar, de lo contrario escriba otra palabra para cancelar la operacion");
        if (!in.nextLine().equalsIgnoreCase("si")){
            //En caso de que el usuario no ingreso si, se detiene la ejecucion del metodo
            return;
        }
        if(NROTRIOS > 0){
            for (int i = 0; i < NROTRIOS ; i++) {
                imprimirCartas();
                System.out.println("Ingrese el trio "+i);
                matrizTrios.add(crearUnTrio());

            }
        }
        if (NROESCALAS > 0){
            for (int i = 0; i < NROESCALAS ; i++) {
                imprimirCartas();
                System.out.println("Ingrese la escala "+i);
                matrizEscalas.add(crearUnaEscala());

            }
        }

    }

    public void imprimirCartas(){ //Este metodo imprime las cartas del jugador
        System.out.println("Cartas de "+nombre);
        imprimirCartas(cartas);
    }
    private static void imprimirCartas(ArrayList<Carta> cartas){
        String contenido = "";
        String primeraLinea = "";
        String segundaLinea = "";
        for (int i = 0; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            primeraLinea += carta.toStringEC();
            int numeroEspacios = (carta.toString().length()+2)/2;
            segundaLinea += MainCarioca.repetirString(" ",numeroEspacios) +i+MainCarioca.repetirString(" ",numeroEspacios);
        }
        contenido = primeraLinea + "\n" + segundaLinea;
        System.out.println(contenido);

    }

    private void intercambiarCartas(ArrayList<Carta> pozo,Mazo mazo ){

            int primera_carta = 0;
            int segunda_carta = 0;

            primera_carta = MainCarioca.ingresarUnNumero("Primera Carta a intercambiar",0,cartas.size()-1);

            segunda_carta = MainCarioca.ingresarUnNumero("Segunda Carta a intercambiar",0,cartas.size()-1);
            Collections.swap(cartas, primera_carta, segunda_carta);

            imprimirCartas();
            menu(pozo, mazo);

    }


    public ArrayList<Carta> crearUnTrio(){ //Este metodo es para crear un solo trio
        int[] indices=ingresarIndicesTrio();
        //Primero ingresamos los indices de las cartas que conforman el trio,
        // verificando que estos indices no se repitan, y correspondan realmente a un trio
        ArrayList<Carta> trio = new ArrayList<Carta>(); //Arraylist donde se almacenara temporalmente el trio
        for (int i = 0; i < 3; i++) {
            //Por cada indice de la carta se almacena en trio
            int ind = indices[i];
            trio.add(cartas.get(ind));

        }
        for (int i = 0; i < 3; i++) {
            //Una vez hecho el trio, para evitar la duplicacion de cartas, estas se debe borrar de las cartas del jugador
            cartas.remove(indices[i]);
        }
        imprimirCartas(trio);
        return trio;

    }
    public ArrayList<Carta> crearUnaEscala(){
        int[] indices = ingresarIndicesEscala();
        ArrayList<Carta> escala = new ArrayList<Carta>();
        for (int i = 0; i < 4; i++) {
            int ind = indices[i];
            escala.add(cartas.get(ind));

        }
        for (int i = 0; i < 4; i++) {
            cartas.remove(indices[i]);
        }
        imprimirCartas(escala);
        return escala;
    }

    public  int[] ingresarIndicesTrio(){
        System.out.println("Ingrese los indices de un trio");
        int[] indicesTrio = ingresarIndices(3);
        String valorEsperado = cartas.get(indicesTrio[0]).getValor();
        for (int i = 0; i < 3; i++) {
            int ind = indicesTrio[i];
            String valor = cartas.get(ind).getValor();
            if (!(valor.equalsIgnoreCase(valorEsperado))){//Si los valores son diferentes
                //Vuelve a preguntar
                System.out.println("Error los indices que ingresaste no corresponde a un trio");
                return ingresarIndicesTrio();
            }
        }
        return indicesTrio;
    }
    public int[] ingresarIndicesEscala(){
        System.out.println("Ingrese los indices de una escala");
        int[] indicesEscala = ingresarIndices(4);
        String paloEsperado = cartas.get(indicesEscala[0]).getPalo(); //Corazon
        String valorEsperado = cartas.get(indicesEscala[0]).getValor(); //K
        int indiceValor = Carta.VALORES.toString().indexOf(valorEsperado); //12, En que posicion del arreglo de VALORES esta el valor: K
        for (int i = 0; i < 4; i++) {
            int ind = indicesEscala[i];
            Carta carta = cartas.get(ind);
            String palo = carta.getPalo();
            String valor = carta.getValor();
            if (!(palo.equalsIgnoreCase(paloEsperado) && valor.equalsIgnoreCase(valorEsperado))){
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
    public int[] ingresarIndices(int nroIndices) { // Este metodo es para ingresar los indices de las cartas del jugador, nroIndices puede ser 3 o 4
        int[] indices = new int[nroIndices];

        System.out.println("Ingrese los indices de las cartas (separado por espacios): ");
        String[] indicesString = in.nextLine().split(" ");//Se ingresa los indices separados por espacios, posteriormente esos indices se almacenan en un arreglo
        if (indicesString.length == nroIndices) {
            //Primero verificamos si el nroIndices ingresados corresponde al nro de indices solicitados
            try {
                for (int i = 0; i < indicesString.length; i++) {
                    int numero = Integer.parseInt(indicesString[i]);//Convertimos los indices de string a int

                    if (numero >= 0 && numero < cartas.size()) { //si el numero esta dentro del rango de los indices
                        indices[i] = numero; //Se guarda el numero dentro del arreglo de los indices
                        if (i == nroIndices-1) { //Una vez ingresado el ultimo indice
                            if(MainCarioca.sonNumerosDiferentes(indices)) { //Se verifica que los indices sean numeros diferentes
                                return indices;
                            }else{
                                System.out.println("Los numeros deben ser diferentes, intentelo nuevamnete");
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


}
