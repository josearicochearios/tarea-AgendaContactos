package com.vallegrande.edu.pe.contactbook;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase que representa el panel donde se ingresan nuevos contactos
public class ContactInputPanel extends JPanel {

   // Campos de texto para nombre, teléfono y correo
   private JTextField txtName, txtPhoneNumber, txtEmail;
   // Botón para agregar contacto
   private JButton btnAddContact;

   private ValidateInput validateInput  = new ValidateInput();

   // Constructor del panel de ingreso
   public ContactInputPanel() {
       // Establece el color de fondo del panel
       setBackground(new Color(212, 235, 248));
       // Usa un layout flexible que permite posicionar los elementos en una cuadrícula
       setLayout(new GridBagLayout());

       // Etiquetas descriptivas para cada campo
       JLabel lblName = new JLabel("Nombre: ");
       JLabel lblPhoneNumber = new JLabel("Teléfono: ");
       JLabel lblEmail = new JLabel("Correo electrónico: ");

       // Inicialización de los campos de entrada de texto
       txtName = new JTextField(25);
       txtPhoneNumber = new JTextField(25);
       txtEmail = new JTextField(25);

       // Botón para agregar un nuevo contacto
       btnAddContact = new JButton("Agregar Contacto");
       // Define un tamaño preferido para el botón
       btnAddContact.setPreferredSize(new Dimension(120, 30));

       //campo
       DocumentListener listener = new DocumentListener() {
           @Override
           public void insertUpdate(DocumentEvent e) { verifyInput();}

           @Override
           public void removeUpdate(DocumentEvent e) { verifyInput();}

           @Override
           public void changedUpdate(DocumentEvent e) { verifyInput();}
       };

       txtName.getDocument().addDocumentListener(listener);
       txtPhoneNumber.getDocument().addDocumentListener(listener);
       txtEmail.getDocument().addDocumentListener(listener);

       // Acción al presionar el botón
       btnAddContact.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // Obtiene los valores ingresados por el usuario
               String name = txtName.getText().trim();
               String phoneNumber = txtPhoneNumber.getText().trim();
               String email = txtEmail.getText().trim();

               // Verifica que todos los campos estén llenos
               if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {

                   // validaciones
                   boolean nombreValido =  validateInput.validarNombre(name);
                   boolean telefonoValido = validateInput.validarTelefono(phoneNumber);
                   boolean correoValido = validateInput.validarCorreo(email);

                   if (nombreValido && telefonoValido && correoValido) {


                       // Crea un nuevo contacto y lo añade al panel de la lista
                       ContactListPanel.addContact(new Contact(capitalizeFirstLetter(name), phoneNumber, email.toLowerCase()));
                       ContactInfoPanel.actualizarInfo();
                       // Limpia los campos después de agregar
                       txtName.setText("");
                       txtPhoneNumber.setText("");
                       txtEmail.setText("");

                   }else {
                       StringBuilder errors = new StringBuilder("Corrige los sigueintes errores:\n");
                       if (!nombreValido) errors.append("- El nombre no debe contener números ni símbolos.\n");
                       if (!telefonoValido) errors.append("- El teléfono debe tener 9 dígitos y empezar con 9.\n");
                       if (!correoValido) errors.append("- El correo debe tener un formato válido y un dominio aceptado.\n");
                       JOptionPane.showMessageDialog(null,
                               errors.toString(),
                               "Error de validación",
                               JOptionPane.ERROR_MESSAGE);
                       btnAddContact.setEnabled(false);
                   }

               } else {
                   btnAddContact.setEnabled(false);
                   // Muestra un mensaje de error si algún campo está vacío
                   JOptionPane.showMessageDialog(null,
                           "Por favor ingrese toda la información del contacto",
                           "Campos Vacios",
                           JOptionPane.WARNING_MESSAGE);
                   btnAddContact.setEnabled(false);
               }
           }
       });

       // Restricciones para posicionar los elementos en el GridBagLayout
       GridBagConstraints gbc = new GridBagConstraints();
       // Margen entre componentes
       gbc.insets = new Insets(10, 5, 10, 5);

       // Posicionamiento de las etiquetas y campos en el panel
       gbc.gridx = 0;
       gbc.gridy = 0;
       add(lblName, gbc);

       gbc.gridx = 1;
       gbc.gridy = 0;
       add(txtName, gbc);

       gbc.gridx = 0;
       gbc.gridy = 1;
       add(lblPhoneNumber, gbc);

       gbc.gridx = 1;
       gbc.gridy = 1;
       add(txtPhoneNumber, gbc);

       gbc.gridx = 0;
       gbc.gridy = 2;
       add(lblEmail, gbc);

       gbc.gridx = 1;
       gbc.gridy = 2;
       add(txtEmail, gbc);

       gbc.gridx = 2;
       gbc.gridy = 3;
       add(btnAddContact, gbc);




   }
    private void verifyInput(){
       String name = txtName.getText().trim();
       String phone = txtPhoneNumber.getText().trim();
       String email = txtEmail.getText().trim();

        boolean hayTexto = !name.isEmpty() || !phone.isEmpty() || !email.isEmpty();

       btnAddContact.setEnabled(hayTexto);

    }

    public String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Devuelve la cadena vacía o null tal cual
        }
        // Convierte la primera letra a mayúsculas y el resto a minúsculas
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
