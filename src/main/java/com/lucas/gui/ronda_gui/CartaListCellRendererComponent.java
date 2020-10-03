package com.lucas.gui.ronda_gui;

import com.lucas.carioca_digital.Carta;

import javax.swing.*;
import java.awt.*;

/**
 * Esta clase sirve para renderizar las cartas en el resto de GUI relacionadas con jugar una ronda de carioca
 */
public class CartaListCellRendererComponent implements ListCellRenderer {
    /**
     * Este método retorna las cartas renderizadas para su uso en otras clases, como MenuGUI
     *
     * @param list         El JList que se está manipulando
     * @param value        El valor retornado por list.getModel().getElementAt(index).
     * @param index        El identificador de las celdas
     * @param isSelected   Es verdadero si la celda especificada está seleccionada
     * @param cellHasFocus Es verdadero si la celda especificada está siendo enfocada
     * @return El componente con el renderizado de las cartas
     * @see JList
     * @see ListSelectionModel
     * @see ListModel
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Carta carta = (Carta) value;

        if (isSelected) {
            carta.setBackground(list.getSelectionBackground());
            carta.setForeground(list.getSelectionForeground());
        } else {
            carta.setBackground(list.getBackground());
            carta.setForeground(list.getForeground());
        }

        return carta;
    }
}
