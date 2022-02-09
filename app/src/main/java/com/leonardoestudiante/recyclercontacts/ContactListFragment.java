//Asocio la clase al paquete
package com.leonardoestudiante.recyclercontacts;

//Importamos de androidx que es importante por la compatibilidad
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Declaro la clase y heredo de fragment
public class ContactListFragment extends Fragment {

    //Elemento que nos servira para mantener un numero de elementos y no sobrecargar la memoria
    private RecyclerView contactRecyclerView;

    //Declaro un ContactAdapter que servira para usar el recyclerview desde este fragment
    private ContactAdapter contactAdapter;

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inserto en una view el layout que contiene el fragmentlist
        View view = inflater.inflate(R.layout.fragment_list_contact, container, false);

        //Asocio el widget recyclerView con su ID
        this.contactRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recycler_view);

        //Asigno a nuestro recyclerView un layoutManager que sera linear enviando como parametro una activity
        this.contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Transfiero la lista creada en contact al adapter
        this.contactAdapter = new ContactAdapter(Contact.contacts);

        //Asigno el adaptador al recyclerView
        this.contactRecyclerView.setAdapter(contactAdapter);

        //Devuelvo la view
        return view;

    }

    //Metodo que se ejecuta segun el ciclo de vida del fragment
    @Override
    public void onResume() {
        super.onResume();

        //Cada vez que se ejecute onResume llamamos a este metodo
        updateUI();

    }

    //Defino el metodo para actualizar el fragment
    private void updateUI(){

        //Si el adapter es null acccedemos
        if(this.contactAdapter == null){

            //Transfiero la lista creada en contact al adapter
            this.contactAdapter = new ContactAdapter(Contact.contacts);

            //Asigno el adaptador al recyclerView
            this.contactRecyclerView.setAdapter(contactAdapter);

        } else {

            //Indicamos que los datos han cambiado
            this.contactAdapter.notifyDataSetChanged();

        }

    }

    //Declaro una clase interna privada para definir
    //ViewHolder describe los metadatos de nuestras view y como estas estan situadas
    //Heredamos el metodo onClick de la interfaz onclicklistener
    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Declaro el TextView que mostrara el nombre del contacto
        private TextView nameTextView;

        //Contacto que sera utilizado para identificar a cual accedemos mediante el recycler
        private Contact contact;

        //Declaramos un constructor y declaramos el elemento itemView
        //ItemView es un elemento que sirve para que accedamos a elementos que forman la  vista
        public ContactHolder(@NonNull View itemView) {
            super(itemView);
        }

        //Declaro un segundo constructor
        public ContactHolder(LayoutInflater inflater, ViewGroup parent){

            //Asocio la clase al layout que define la estructura de contactos
            super(inflater.inflate(R.layout.structure_list_contact,parent,false));

            //Asocio la ID de la view con el widget
            this.nameTextView =(TextView) itemView.findViewById(R.id.contact_name);

            //Asocio mediante el itemView nuestro listener sin ser necesario una clase anonima
            this.itemView.setOnClickListener(this);

        }

        //Metodo que cambia el atributo contact de la clase y actualiza su textview
        public void bind(Contact contact){

            //Cambiamos nuestro atributo Contact
            this.contact = contact;

            //Cambiamos el textView de nuestra clase
            this.nameTextView.setText(this.contact.getNameContact());

        }

        //Metodo heredado de nuestra interfaz al hacer click
        @Override
        public void onClick(View v) {

            //Llamamos al metodo de ContactPagerActivity para instanciar un nuevo intent con un UUID
            //Intent intent = ContactPagerActivity.newIntent(getActivity(),this.contact.getId());
            Intent intent = ContactPagerActivity.newIntent(getActivity(),contact.getId());
            startActivity(intent);

        }

    }

    //Declaro una clase privada para usar el recycler view
    //El adapter sirve para unir datos de una aplicacion de Views que estan desplegados en el recycler view
    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{

        //Declaro la lista que contendra el ContactAdapter
        private List<Contact> contactList;

        //Declaramos un constructor que asocia una lista con el atributo de clase
        public ContactAdapter(List<Contact> contacts){
            this.contactList = contacts;
        }

        //Metodo que se ejecuta al crear la view
        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            //Defino un layoutinflater y le asocio la activity en la cual nos encontramos
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            //Devolvemos el layout asociado a la activity y el viewgroup que contiene varias vistas
            return new ContactHolder(layoutInflater,parent);

        }

        //Metodo rellena las view con datos segun su posicion y los actualiza segun nos desplazamos
        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

            //Declaro un contact y le asocio uno de la lista segun una posicion
            Contact contact = this.contactList.get(position);

            //Mediante ContactHolder cambio el contact que posee el objeto a traves del metodo bind
            holder.bind(contact);

        }

        //Metodo que devuelve la cantidad de elementos de la lista contacts
        @Override
        public int getItemCount() {
            return this.contactList.size();
        }

    }

}