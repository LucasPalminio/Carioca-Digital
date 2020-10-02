package com.lucas.datos;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class GestorCSV extends GestorArchivos{
    /**
     *
     */


    /**
     * Sobrecarga del método posterior
     * @param data
     * @param ruta
     */
    public void escribirCSV(List<String[]> data, String ruta){
        try {
            if(!verificarSiExiste(ruta)) {
                crearArchivo(ruta);
            }
            CSVWriter writer = new CSVWriter(new FileWriter(ruta, true));
            for (String[] fila:data){
                writer.writeNext(fila);
            }
            writer.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Crea el archivo a guardar si no existe
     * @param fila linea que se edita
     * @param ruta donde se almacena el archivo
     * @throw IOException ex Error producido por una operación de entrada o salida que falla o se interpreta
     */
    public void escribirCSV(String[] fila, String ruta){
        try {
            if(!verificarSiExiste(ruta)) {
                crearArchivo(ruta);
            }
            CSVWriter writer = new CSVWriter(new FileWriter(ruta, true));
            writer.writeNext(fila);
            writer.close();


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param ruta Es donde se almacenará el archivo
     * @throws IOException ex Error producido por una operación de entrada o salida que falla o se interpreta
     * @return Toma la lista para devolver los datos de cada fila
     */
    public List<String[]> leerCSV(String ruta){
        try {
            if (!super.verificarSiExiste(ruta)){
                crearArchivo(ruta);
            }
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(ruta));

            // create csv reader
            CSVReader csvReader = new CSVReader(reader);

            // read all records at once, [columna]
            List<String[]> dataImportada = csvReader.readAll();

            // close readers
            csvReader.close();
            reader.close();
            return dataImportada;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
