//Asocio el paquete a la clase
package com.leonardoestudiante.recyclercontacts;

//Importo las librerias necesarias
import static java.util.Arrays.asList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Declaro la clase y heredo de Fragment
public class ContactFragment extends Fragment {

    //Declaramos un contact del fragmento
    private Contact contact;

    //Declaro una variable que sirve para intercambiar informacion del bundle
    private static  final String ARG_CONTACT_ID = "crime_id";

    //Defino un metodo para instanciar fragments y conservar datos
    public static ContactFragment newInstance(UUID contactId){

        //Defino un bundle para tranferir informacion
        Bundle args = new Bundle();

        //Introduzco un ID
        args.putSerializable(ARG_CONTACT_ID,contactId);

        //Declaro un fragment
        ContactFragment fragment = new ContactFragment();

        //Asociamos los datos al fragment para que se conserven
        fragment.setArguments(args);

        //Devolvemos el fragment
        return fragment;

    }

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtenemos el ID del fragment y lo introducimos en una variable
        UUID contactId = (UUID) getArguments().getSerializable(ARG_CONTACT_ID);

        //Usamos un metodo de la clase Contact para buscar contacts en su lista static mediante su ID
        this.contact = Contact.searchContact(contactId);

    }

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Declaro una view para poder interactuar con sus metodos y cargar el content
        View view = inflater.inflate(R.layout.fragment_detail_contact,container,false);

        //Definimos los widget que vamos a utilizar y los asocio a su id
        EditText textName = (EditText) view.findViewById(R.id.text_detail_name);
        TextView textPhone = (TextView) view.findViewById(R.id.text_detail_phone);
        TextView textEmail = (TextView) view.findViewById(R.id.text_detail_email);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_contact);

        //Establezco los textos de cada contacto y su imagen
        textName.setText(this.contact.getNameContact());
        textPhone.setText( this.contact.getPhoneContact());
        textEmail.setText(this.contact.getEmailContact());
        imageView.setImageResource((int)this.contact.getImageContact());

        //Creamos un listener para cambiar los datos del contacto implementando la interfaz
        textName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            //Metodo que se ejecuta en caso de que el texto cambie
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Actualizo el contacto del fragment
                contact.setNameContact(charSequence.toString());

                //Busco la posicion del contacto en la lista mediante su UUID e intercambio el antiguo contacto
                int listIndex = 0;

                //Recorremos la lista
                for(int index = 0; index < Contact.contacts.size(); index++){

                    //Si el contacto encontrado coincide en UUID optenemos la posicion
                    //(Esto se puede conseguir con indexOf pero daba un error de index -1 que es que no lo encuentra)
                    if(Contact.contacts.get(index).getId().equals(contact.getId())){
                        listIndex = index;
                        break;
                    }
                }

                //Actualizo la lista de contactos
                Contact.contacts.set(listIndex,contact);

            }

            @Override
            public void afterTextChanged(Editable editable) { }

        });

        //Implementamos la interfaz de listener para darle un comportamiento al numero de telefono
        textPhone.setOnClickListener(new View.OnClickListener() {

            //Metodo que se ejecuta al darle click
            @Override
            public void onClick(View view) {

                //Declaro un intent para enviar informacion a las posibles aplicaciones que contengan esta funcion
                Intent intent = new Intent(Intent.ACTION_DIAL);

                //Inserto el telefono del contacto en el intent
                intent.putExtra(Intent.ACTION_DIAL,Uri.parse(contact.getPhoneContact()));

                //Inicio el intent
                startActivity(intent);

            }

        });

        //Implementamos la interfaz de listener para darle un comportamiento al correo
        textEmail.setOnClickListener(new View.OnClickListener() {

            //Metodo que se ejecuta al darle click
            @Override
            public void onClick(View view) {

                //Declaro un intent para enviar informacion a las posibles aplicaciones que contengan esta funcion
                Intent intent = new Intent(Intent.ACTION_SEND);

                //Inserto el email del contacto en el intent
                intent.putExtra(Intent.ACTION_SEND,contact.getEmailContact());

                //Establezco el tipo de dato
                intent.setType("text/plain");

                //Inicio el intent
                startActivity(intent);

            }

        });

        //Devuelvo los elementos mediante el return
        return view;

    }

}
