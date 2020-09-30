package com.lucas.Datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorArchivos {

    public GestorArchivos(){

    }

    /**
     * Este metodo se encarga de crear una carpeta, verificar su existencia y si existe algun error al crearla
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
     *
     * @param nombre
     */
    public void crearArchivo(String nombre){
        try {

            File archivo = new File("Carioca_Digital_Datos//" + nombre+".txt");
            FileWriter escriba = new FileWriter(archivo.getPath());
            if(!archivo.exists()) {
                if (archivo.createNewFile()) {
                    escriba.write("");
                    escriba.close();
                    System.out.println("Se ha creado el archivo: " + nombre + ".txt");
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
     *
     * @param mensaje
     * @param nombre
     */
    public void escribir(String mensaje, String nombre){
        try {
            File archivo = new File("Carioca_Digital_Datos//" + nombre + ".txt");
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
     * Este metodo se encarga de leer los datos guardados del juego
     * @param ruta Es donde se encuetra el fichero
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
     * Este metodo se encarga
     * @param nombre nombre del archivo
     * @return la validacion de que el archivo existe
     */
    public boolean verificarSiExiste(String nombre){
        File archivo = new File(nombre);
        return archivo.exists();
    }

}
