//Asocio la clase y el paquete
package com.leonardoestudiante.recyclercontacts;

//Importo las librerias necesarias
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

//Declaro la clase y heredo de activity compatible con otras versiones
public abstract class ManagerActivity extends AppCompatActivity {

    //Declaro un metodo abstracto que se heredara
    //Este metodo se encargara de identificar que fragmento se insertara
    protected abstract Fragment identifierFragment();

    //Metodo que se ejecuta segun el ciclo de vida de una activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inserto un layout a la view
        setContentView(R.layout.activity_base);

        //Declaramos un fragment manager para administrar los fragment
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Inserto en el fragment su posible valor del ID en el layout
        Fragment insertedFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        //Si no existe un fragment asociado ya con ese ID accedemos al if
        if(insertedFragment == null){

            //Instancio el fragment segun el fragment transferido desde el metodo abstracto heredado
            insertedFragment = identifierFragment();

            //Inserto el fragment en su view mediante su ID
            fragmentManager.beginTransaction().add(R.id.fragment_container,identifierFragment()).commit();

        }

    }

}