package com.lucas.Juego_Carioca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje;
    private ArrayList<ArrayList<Carta>> matrizTrios;
    private ArrayList<ArrayList<Carta>> matrizEscalas;
    private int NROTRIOS;
    private int NROESCALAS;
    public static Scanner in = new Scanner(System.in);
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
    }
    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setNROTRIOSyNROESCALAS(int NROTRIOS,int NROESCALAS) {
        this.NROTRIOS = NROTRIOS;
        this.NROESCALAS = NROESCALAS;
    }


    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }
    public int getNroCartas(){ return cartas.size(); }
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
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void imprimirCartas(){ //Este metodo imprime las cartas del jugador
        System.out.println("Cartas de "+nombre);
        imprimirCartas(cartas);
    }
    private void imprimirCartas(ArrayList<Carta> cartas){
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
    public void menu(ArrayList<Carta> pozo, Mazo mazo){
        System.out.println("(1) Sacar Carta de la mesa");
        System.out.println("(2) Sacar Carta del mazo ");
        System.out.println("(3) Intercambiar el lugar de dos cartas ");
        System.out.println("(4) ¿Desea Bajarse?");
        System.out.print("Eliga una opcion: ");
        String opcion = in.nextLine();
        switch (opcion){
            case "1": //Sacar Carta de la mesa
                Carta cartaEnLaMesa = pozo.get(0);
                cartas.add(cartaEnLaMesa);
                pozo.remove(0);
                cartaEnLaMesa = menu_BotarCarta();
                pozo.add(0,cartaEnLaMesa);
                break;
            case "2": //Sacar Carta del mazo
                Carta cartaDelMazo = mazo.sacarCarta();
                System.out.println("obtuviste esta carta del mazo: "+cartaDelMazo.toStringEC());
                cartas.add(cartaDelMazo);
                pozo.add(0,menu_BotarCarta());
                break;
            case "3": //Intercambiar dos cartas dentro de la misma mano
                intercambiarCartas(opcion,pozo,mazo);
                break;
            case "4":
                bajarse();
                break;
            default:
                System.out.println("Erro la opcion ingresada es incorrecta, intentelo nuevamente");
                menu(pozo,mazo);

        }
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
    private int toInt(String caracter){
        return Integer.parseInt(caracter);
    }
    private void intercambiarCartas(String opcion,ArrayList<Carta> pozo,Mazo mazo ){

            int primera_carta = 0;
            int segunda_carta = 0;
        try {
            System.out.println("Primera carta a intercambiar:");

            opcion = in.nextLine();
            primera_carta = toInt(opcion);

            System.out.println("Segunda carta a intercambiar:");

            opcion = in.nextLine();
            segunda_carta = toInt(opcion);
            }catch(Exception e){
            System.out.println("Ingresaste un número de carta inválido,\npor favor inténtalo de nuevo");
                intercambiarCartas(opcion,pozo,mazo);
            }
            Collections.swap(cartas, primera_carta, segunda_carta);
            imprimirCartas();
            menu(pozo, mazo);

    }

    public ArrayList<Carta> crearUnTrio(){
        int[] indices=ingresarIndicesTrio();
        ArrayList<Carta> trio = new ArrayList<Carta>();
        for (int i = 0; i < 3; i++) {
            int ind = indices[i];
            trio.add(cartas.get(ind));
            cartas.remove(ind);
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
            cartas.remove(ind);
        }
        imprimirCartas(escala);
        return escala;
    }
    private  int[] ingresarIndicesTrio(){
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

    private int[] ingresarIndicesEscala(){
        int[] indicesEscala = ingresarIndices(4);
        String paloEsperado = cartas.get(indicesEscala[0]).getPalo(); //Corazon
        String valorEsperado = cartas.get(indicesEscala[0]).getValor(); //K
        int indiceValor = Carta.VALORES.toString().indexOf(valorEsperado); //12
        for (int i = 0; i < 4; i++) {
            int ind = indicesEscala[i];
            Carta carta = cartas.get(ind);
            String palo = carta.getPalo();
            String valor = carta.getValor();
            if (!(palo.equalsIgnoreCase(paloEsperado) && valor.equalsIgnoreCase(valorEsperado))){
                //si los indices ingresado no corresponden a una escala, pregunte de nuevo
                return indicesEscala;
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
    private int[] ingresarIndices(int nroIndices) { // nroIndices puede ser 3 o 4
        int[] indices = new int[nroIndices];
        String EscalaOTrio = "";
        if (nroIndices == 3) {
            EscalaOTrio = "trio";
        } else if (nroIndices == 4) {
            EscalaOTrio = "escala";
        }

        System.out.println("Ingrese los indices de las cartas que formaran al " + EscalaOTrio + "(separado por espacios): ");
        String[] indicesString = in.nextLine().split(" ");
        if (indicesString.length == nroIndices) {
            try {
                for (int i = 0; i < indicesString.length; i++) {
                    int numero = Integer.parseInt(indicesString[i]);

                    if (numero >= 0 && numero < cartas.size()) {
                        indices[i] = numero;
                        if (i == 2) {
                            if(MainCarioca.sonNumerosDiferentes(indices)) {
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
        return ingresarIndices(nroIndices);
    }

    public void bajarse(){
        if(NROTRIOS > 0){
            for (int i = 0; i < NROTRIOS ; i++) {
                imprimirCartas();
                System.out.println("Ingrese el trio "+i);
                matrizTrios.add(crearUnTrio());

            }
        }
        if (NROESCALAS > 0){

        }

    }
}
