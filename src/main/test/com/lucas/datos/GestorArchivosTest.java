package com.lucas.datos;

import org.junit.Test;

import static org.junit.Assert.*;

public class GestorArchivosTest {
    /**
     * Este test verifica la existencia de una carpeta, si no esta el gestor de archivo debe crearla
     */
    @Test
    public void crearCarpeta() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String nombreCarpeta = "Test";
        gestorArchivos.crearCarpeta(nombreCarpeta);
        assertTrue(gestorArchivos.verificarSiExiste("Carioca_Digital_Datos//"+nombreCarpeta));

    }

    @Test
    public void crearArchivo() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String nombreCarpeta = "Test//TestCrearArchivo";
        gestorArchivos.crearCarpeta(nombreCarpeta);
        gestorArchivos.crearArchivo(nombreCarpeta+"//archivoTest");
        assertTrue(gestorArchivos.verificarSiExiste("Carioca_Digital_Datos//"+nombreCarpeta+"//archivoTest.txt"));

    }

    @Test
    public void escribir() {
    }

    @Test
    public void leer() {
    }

    /**
     * Este test intenta escribir el archivo "Test.txt" ubicado en la carpeta "Carioca_Digital_Datos/Test",
     * si la carpeta o el archivo no existe el gestor de archivo debe ser capaz de crearlos.
     */
    @Test
    public void escribirEnElArchivoTest() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String nombreCarpeta = "Test";
        String ruta = nombreCarpeta+"//archivoTest";
        gestorArchivos.crearCarpeta(nombreCarpeta);
        gestorArchivos.crearArchivo(ruta);
        gestorArchivos.escribir("Testeando",ruta);
        assertTrue(gestorArchivos.verificarSiExiste("Carioca_Digital_Datos//"+ruta+".txt"));
    }
}