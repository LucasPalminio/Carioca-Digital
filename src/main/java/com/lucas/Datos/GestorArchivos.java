package com.lucas.Datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestorArchivos {

    public GestorArchivos(){

    }
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
    public void escribir(String mensaje, String nombre){
        try {
            File archivo = new File("Carioca_Digital_Datos//" + nombre + ".txt");
            if (!archivo.exists()) {
                crearArchivo(nombre);
            }
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
    public boolean verificarSiExiste(String nombre){
        File archivo = new File(nombre);
        return archivo.exists();
    }

}
