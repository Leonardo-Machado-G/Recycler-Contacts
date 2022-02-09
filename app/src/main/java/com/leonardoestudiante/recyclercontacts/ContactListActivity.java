//Asocio la clase y el paquete
package com.leonardoestudiante.recyclercontacts;

//Importo las librerias necesarias
import androidx.fragment.app.Fragment;

//Declaro la clase y heredo de activity compatible con otras versiones
public class ContactListActivity extends ManagerActivity {

    //Metodo heredado que indica que fragmento vamos a insertar en esta activity
    @Override
    protected Fragment identifierFragment() {
        return new ContactListFragment();
    }

}