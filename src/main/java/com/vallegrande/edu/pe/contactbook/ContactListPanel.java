package com.vallegrande.edu.pe.contactbook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase que representa el panel donde se muestra la lista de contactos
public class ContactListPanel extends JPanel {
   // Modelo de lista que almacena los contactos
   private static DefaultListModel<Contact> contactListModel;
   // Lista visual que muestra los contactos
   private JList<Contact> contactList;
   // Botones para editar y eliminar contactos
   private JButton btnEditContact;
   private JButton btnDeleteContact;

   // Constructor del panel
   public ContactListPanel() {
       // Establece el color de fondo del panel
       setBackground(new Color(255, 251, 202));
       // Usa un layout que organiza los componentes en regiones (norte, sur, centro, etc.)
       setLayout(new BorderLayout());

       // Inicializa el modelo de lista y la lista de contactos
       contactListModel = new DefaultListModel<>();
       contactList = new JList<>(contactListModel);
       // Permite seleccionar solo un contacto a la vez
       contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

       // Inicialización de los botones
       btnEditContact = new JButton("Editar");
       btnDeleteContact = new JButton("Eliminar");

       // Acción del botón "Editar"
       btnEditContact.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int selectedIndex = contactList.getSelectedIndex(); // Índice del contacto seleccionado
               if (selectedIndex != -1) {
                   Contact contact = contactListModel.get(selectedIndex); // Obtiene el contacto seleccionado
                   // Solicita un nuevo nombre al usuario
                   String newName = JOptionPane.showInputDialog(null,
                           "Edite el nombre del contacto",
                           contact.getName());
                   // Si se ingresa un nuevo nombre válido, se actualiza el contacto
                   if (newName != null && !newName.isEmpty()) {
                       ContactInputPanel contactInputPanel = new ContactInputPanel();
                       contact.setName(contactInputPanel.capitalizeFirstLetter(newName));
                       contactListModel.setElementAt(contact, selectedIndex);
                   }
               }
           }
       });

       // Acción del botón "Eliminar"
       btnDeleteContact.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               int selectedIndex = contactList.getSelectedIndex(); // Índice del contacto seleccionado
               // Si hay un contacto seleccionado, se elimina del modelo
               if (selectedIndex != -1) {
                   contactListModel.removeElementAt(selectedIndex);
                   ContactInfoPanel.actualizarInfo();
               }
           }
       });

       // Panel que contiene los botones de acción
       JPanel buttonPanel = new JPanel();
       buttonPanel.add(btnEditContact);
       buttonPanel.add(btnDeleteContact);

       // Agrega la lista de contactos al centro del panel con barra de desplazamiento
       add(new JScrollPane(contactList), BorderLayout.CENTER);
       // Agrega el panel de botones en la parte inferior
       add(buttonPanel, BorderLayout.SOUTH);
   }

   // Método estático que permite añadir un contacto a la lista
   public static void addContact(Contact contact) {
       contactListModel.addElement(contact);
   }

   // Método estático que devuelve el número total de contactos
   public static int getContactCount() {
       return contactListModel.size();
   }
}
