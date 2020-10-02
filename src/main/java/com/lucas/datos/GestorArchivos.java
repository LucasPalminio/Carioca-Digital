package com.lucas.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorArchivos {
    /**
     * Esta clase se encarga de guardar, leer y gestionar los datos a guardar
     * Ademas de funcionar como clase padre para la clase GestorCSV
     */

    public GestorArchivos(){

    }
    /**
     * Este método se encarga de crear una carpeta, verificar su existencia y si existe algún error al crearla
     * @param nombreCarpeta Es el nombre de la carpeta en donde se almacenaran los datos
     * @throws Exception e  Evento que ocurre durante la ejecución de un programa que interrumpe el flujo normal de instrucciones.
     */
    public void crearCarpeta(String nombreCarpeta){
        try {
            File carpeta = new File("Carioca_Digital_Datos//"+nombreCarpeta);
            if(!carpeta.exists()) {
                if (carpeta.mkdir()) {
                    System.out.println("Se ha creado la carpeta de datos");
                }
            }else{
                System.out.println("La carpeta ya existe");
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido un error al crear la carpeta");
            System.out.println(e.getMessage());
        }


    }

    /**
     * Este método se encarga de crear una carpeta, verificar su existencia y si existe algún error al crearla
     */
    public void crearCarpeta(){
        try {
            File carpeta = new File("Carioca_Digital_Datos");
            if(!carpeta.exists()) {
                if (carpeta.mkdir()) {
                    System.out.println("Se ha creado la carpeta de datos");
                }
            }else{
                System.out.println("La carpeta ya existe");
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido un error al crear la carpeta");
            System.out.println(e.getMessage());
        }
    }
    /**
     * Este método se encarga de crear el archivo, obteniendo los datos y usando la ruta para almacenarlo
     * @param ruta Es donde se encuentra el fichero
     * @throws IOException e Error producido por una operación de entrada o salida que falla o se interpreta.
     */
    public void crearArchivo(String ruta){
        try {

            File archivo = new File(ruta);
            if(archivo.getParentFile() != null){
                archivo.getParentFile().mkdirs();
            }
            if(!archivo.exists()) {

                if (archivo.createNewFile()) {
                    FileWriter escriba = new FileWriter(archivo.getPath());
                    escriba.write("");
                    escriba.close();
                    System.out.println("Se ha creado el archivo: " + ruta);
                }
            }else{
                System.out.println("Ya existe el archivo");
            }

        }catch(IOException e){
            System.out.println("No se ha podido crear un nuevo archivo");
            e.printStackTrace();
        }
    }

    /**
     *  Este método funciona como método padre para el almacenamiento de datos
     * @param mensaje lo que se escribe en el archivo
     * @param ruta Es donde se encuentra el fichero
     * @throws IOException e Error producido por una operación de entrada o salida que falla o se interpreta
     */
    public void escribir(String mensaje, String ruta){
        try {
            File archivo = new File( ruta);
            if (!archivo.exists()) {
                crearArchivo(ruta);
            }
            String filaString;
            FileWriter escriba = new FileWriter(archivo.getPath());

            if(archivo.exists()){
                escriba.write(mensaje);
                escriba.close();
            }else{
                System.out.println("El archivo no existe");
            }
        }catch(IOException e){
            System.out.println("No se ha podido escribir en el archivo");
            e.printStackTrace();
        }

    }

    /**
     * Este método se encarga de leer los datos guardados del juego
     * @param ruta Es donde se encuentra el fichero
     * @return en caso de que el archivo sea funcional
     */
    public String leer(String ruta){
        String contenido = "";
        try {
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                System.out.println(data);
                contenido = contenido +data+"\n";
            }
            lector.close();
        }catch(FileNotFoundException e){
            System.out.println("No se pudo encontrar el archivo");
            e.printStackTrace();
        }
        return  contenido;
    }

    /**
     *
     * @param nombre nombre del archivo
     * @return la validación de que el archivo existe
     */
    public boolean verificarSiExiste(String nombre){
        File archivo = new File(nombre);
        return archivo.exists();
    }

}
