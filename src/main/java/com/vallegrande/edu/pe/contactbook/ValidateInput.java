package com.vallegrande.edu.pe.contactbook;

import javax.swing.*;

public class ValidateInput {

    public Boolean validarNombre(String nombre) {
        if (nombre != null && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            if (nombre.length() <= 3) {
                JOptionPane.showMessageDialog(null, "Ese es tu nombre???", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        }
        return false;
    }

    public Boolean validarCorreo(String correo) {
        if (correo == null || !correo.contains("@")) {
            return false;
        }

        String[] parts = correo.split("@");
        if (parts.length != 2) {
            return false;
        }

        String nameEmail = parts[0];
        if (nameEmail.length() <= 2){
            JOptionPane.showMessageDialog(null,
                    "Te dejarron poner ese nombre de correo???",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String dominio = parts[1].toLowerCase();

        return dominio.equals("gmail.com") ||
                dominio.equals("outlook.com") ||
                dominio.equals("hotmail.com") ||
                dominio.equals("yahoo.com") ||
                dominio.equals("icloud.com");
    }

    public Boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("9\\d{8}");
    }

}
