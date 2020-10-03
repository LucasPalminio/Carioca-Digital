package com.lucas.datos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GestorArchivosTest {
    private final String RUTA_TEST = "gestor_archivo_test//";

    /**
     * Por cada test que se realice, se debe verificar que la carpeta donde se realice los test exista.
     * Sino existe dicha carpeta, se crea.
     *
     */
    @Before
    public void setUp() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        if (!gestorArchivos.verificarSiExiste(RUTA_TEST)){
            gestorArchivos.crearCarpeta(RUTA_TEST);
        }

    }

    /**
     * Este test es para probar la creacion de una carpeta, y verficar si la carpeta se ha creado correctamente
     */
    @Test
    public void crearCarpeta() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String ruta = RUTA_TEST+"carpetaTest//";
        gestorArchivos.crearCarpeta(ruta);
        assertTrue(gestorArchivos.verificarSiExiste(ruta));

    }

    /**
     * Este test crea un archivo y verifica si el archivo se ha creado correctamente
     */
    @Test
    public void crearArchivo() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String rutaDelArchivo = RUTA_TEST+"archivoPrueba.txt";

        gestorArchivos.crearArchivo(rutaDelArchivo);
        assertTrue(gestorArchivos.verificarSiExiste(rutaDelArchivo));

    }

    /**
     * Este test pone a prueba tres metodos del gestor de archivos: crear, escribir y leer
     */
    @Test
    public void escribir() {
        GestorArchivos gestorArchivos = new GestorArchivos();
        String rutaArchivo = RUTA_TEST+"archivoPrueba.txt";
        String mensaje = "Test test test";
        gestorArchivos.escribir(mensaje,rutaArchivo);

        assertEquals(mensaje+"\n",gestorArchivos.leer(rutaArchivo));
    }

}