//Asocio el paquete y la clase
package com.leonardoestudiante.recyclercontacts;

//Importo las librerias necesarias
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

import android.content.Context;

//Declaro la clase de contactos
public class Contact {

    //Atributos de la clase
    private UUID Id;
    private String phoneContact;
    private String emailContact;
    private long imageContact;
    private String nameContact;

    //Declaro la lista e inserto datos
    public static List<Contact> contacts = new ArrayList<>(asList(
            new Contact("123456789","contacto1@gmail.com",
                    R.drawable.fernando_simon,"Fernardo Simón", UUID.randomUUID()),

            new Contact("9876543212","contacto2@gmail.com",
                    R.drawable.pablo_casado,"Pablo Casado", UUID.randomUUID()),

            new Contact("112233445","contacto3@gmail.com",
                    R.drawable.pablo_iglesias,"Pablo Iglesias", UUID.randomUUID()),

            new Contact("566778890","contacto5@gmail.com",
                    R.drawable.pedro_sanchez,"Pedro Sanchez", UUID.randomUUID()),

            new Contact("321654987","contacto6@gmail.com",
                    R.drawable.salvador_illa,"Salvador Illa", UUID.randomUUID()),

            new Contact("525522523","contacto7@gmail.com",
                    R.drawable.santiago_abascal,"Santiago Abascal" , UUID.randomUUID())
    ));

    //Declaro el constructor
    public Contact(String phone, String email, long image, String name, UUID Id){

        this.phoneContact = phone;
        this.emailContact = email;
        this.imageContact = image;
        this.nameContact = name;
        this.Id = Id;

    }

    //Declaro todos los get para cada atributo
    public String getPhoneContact(){return phoneContact;}
    public String getEmailContact(){return emailContact;}
    public long getImageContact(){return imageContact;}
    public String getNameContact(){return nameContact;}
    public UUID getId(){return this.Id;}

    //Metodo para actualizar el nombre del contacto
    public void setNameContact(String newName){this.nameContact = newName;}

    //Metodo para buscar contacts mediante id
    public static Contact searchContact(UUID id){
        for(Contact contact : contacts){
            if(contact.getId().equals(id)){
                return contact;
            }
        }
        return null;
    }

}
