package com.lucas.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * Esta clase se encarga de guardar, leer y gestionar los datos a guardar
 * Ademas de funcionar como clase padre para la clase GestorCSV
 */
public class GestorArchivos {
    /**
     * Como medida de seguridad, el gestor archivos esta limitado solamente en la carpeta src//datos//
     */
    protected final String CARPETA_DATOS = "src//datos//";
    /**
     * Crea el gestor de archivos genérico
     */
    public GestorArchivos(){

    }
    /**
     * Este método se encarga de crear una carpeta, verificar su existencia y si existe algún error al crearla
     * @param ruta Es la ruta de la carpeta en donde se almacenaran los datos
     *
     */
    public void crearCarpeta(String ruta){
        try {
            File carpeta = new File(CARPETA_DATOS +ruta);
            if(!carpeta.exists()) {
                if(carpeta.getParentFile() != null){
                    carpeta.getParentFile().mkdirs();
                }
                if (carpeta.mkdir()) {
                    System.out.println("Se ha creado la carpeta "+ruta);
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
     *
     */
    public void crearArchivo(String ruta){
        try {

            File archivo = new File(CARPETA_DATOS +ruta);
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
     *  Este método es para escribir en un archivo
     * @param mensaje lo que se escribe en el archivo
     * @param ruta Es donde se encuentra el fichero
     *
     */
    public void escribir(String mensaje, String ruta){
        ruta = CARPETA_DATOS + ruta;
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
        ruta = CARPETA_DATOS + ruta;
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
     * @param ruta ruta del archivo, puede ser relativa o absoluta
     * @return la validación de que el archivo existe
     */
    public boolean verificarSiExiste(String ruta){
        ruta = CARPETA_DATOS + ruta;
        File archivo = new File(ruta);
        return archivo.exists();
    }

    /**
     *
     * @param ruta ruta del archivo a borrar, puede ser relativa o absoluta
     */

    public void eliminarArchivo(String ruta){
        ruta = CARPETA_DATOS + ruta;
        File archivo = new File(ruta);
        if (archivo.exists()) {
            archivo.delete();
        }

    }

}
