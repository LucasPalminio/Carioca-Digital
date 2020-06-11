package com.lucas.Juego_Carioca;




import java.util.ArrayList;
import java.util.Collections;


public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int puntaje; //Puntaje que lleva durante el juego (puntaje final)
    private int puntajeRonda; //Puntaje que saco en la ronda
    private boolean bajoSusCarta; //Bandera que sirve para avisar si un jugador se ha bajado
    private boolean yaSacoCarta; //Bandera que sirve para avisar si el jugador ya saco una carta (ya sea del mazo o de la mesa) en el turno actual
    //Matrices cuando se baja, aca dejan las escalas o trios de cartas que se han bajado en la ronda actual
    private ArrayList<ArrayList<Carta>> matrizTrios = new ArrayList<ArrayList<Carta>>();
    private ArrayList<ArrayList<Carta>> matrizEscalas =  new ArrayList<ArrayList<Carta>>();
    //variables que les dice al jugador cuantos trios y escalas deben formar
    private static int NROTRIOS;
    private static int NROESCALAS;

    //constructor para inicializar todas estas variables
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntaje = 0;
        this.bajoSusCarta = false;
        this.yaSacoCarta = false;
    }
    //metodo para setear la meta de cuantos trios o escalas deben formar en la ronda actual
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

    public int getPuntajeRonda() {
        return puntajeRonda;
    }

    public void calcularPuntaje(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntaje += carta.getPrecio();
            }
        }
    }

    public void calcularPuntajeRonda(){
        if(cartas.size()>0) {
            for (Carta carta : cartas) {
                this.puntajeRonda += carta.getPrecio();
            }
        }
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void setPuntajeRonda(int puntajeRonda) {
        this.puntajeRonda = puntajeRonda;
    }

    public void setCartas(ArrayList<Carta> cartas) {
        this.cartas = cartas;
    }

    public void agregarCarta(Carta carta){
        cartas.add(carta);
    }

    public int getNroCartas(){ return cartas.size(); }

    public boolean isBajoSusCarta() {
        return bajoSusCarta;
    }

    public void setBajoSusCarta(boolean bajoSusCarta) {
        this.bajoSusCarta = bajoSusCarta;
    }

    public boolean isYaSacoCarta() {
        return yaSacoCarta;
    }

    public void setYaSacoCarta(boolean yaSacoCarta) {
        this.yaSacoCarta = yaSacoCarta;
    }

    public void limpiarMatriz(){
        //Este metodo funciona solamente cuando finaliza la ronda,
        // su funcion es limpia las matrices de trios y escalas que el jugador tenga en la mesa
        matrizTrios.clear();
        matrizEscalas.clear();
    }

    public Carta menu_BotarCarta(){
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
        //System.out.println("¿Seguro que quiere bajarse? " +
         //       "\nEscriba "+Carta.ANSI_RED+"si"+Carta.ANSI_RESET+" para confirmar, de lo contrario escriba otra palabra para cancelar la operacion");
        if (!MainCarioca.confirmarOpcion("¿Seguro que quiere bajarse?")){
            //En caso de que el usuario no ingreso si, se detiene la ejecucion del metodo
            System.out.println("Usted se arrepintio de bajarse");
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
        bajoSusCarta = true;

    }

    public void imprimirCartas(){ //Este metodo imprime las cartas del jugador
        System.out.println("Cartas de "+nombre);
        imprimirCartas(cartas);
    }

    private static void imprimirCartas(ArrayList<Carta> cartas){ // Se imprime un conjunto de cartas dado como parametro
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

    public void imprimirInformacionJugador(ArrayList<Carta> pozo){
        System.out.println("Turno: " + nombre);
        imprimirCartas();


    }

    public void intercambiarCartas(){

            int primera_carta = 0;
            int segunda_carta = 0;

            primera_carta = MainCarioca.ingresarUnNumero("Primera Carta a intercambiar",0,cartas.size()-1);

            segunda_carta = MainCarioca.ingresarUnNumero("Segunda Carta a intercambiar",0,cartas.size()-1);
            Collections.swap(cartas, primera_carta, segunda_carta);

            imprimirCartas();


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
        cartas.removeAll(trio);//Una vez hecho el trio, para evitar la duplicacion de cartas, estas se debe borrar de las cartas del jugador
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
        cartas.removeAll(escala);//Una vez hecho el trio, para evitar la duplicacion de cartas, estas se debe borrar de las cartas del jugador
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
            String palo = cartas.get(ind).getPalo();
            if (!(valor.equalsIgnoreCase(valorEsperado)) && !palo.equalsIgnoreCase("JKR")){//Si los valores son diferentes
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

    public int[] ingresarIndices(int nroIndices) { // Este metodo es para ingresar los indices de las cartas del jugador, nroIndices puede ser 3 o 4
        int[] indices = new int[nroIndices];

        System.out.println("Ingrese los indices de las cartas (separado por espacios): ");
        String[] indicesString = MainCarioca.tecladoNext().split(" ");//Se ingresa los indices separados por espacios, posteriormente esos indices se almacenan en un arreglo
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
