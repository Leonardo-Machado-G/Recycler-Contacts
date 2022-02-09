//Asocio el paquete a la clase
package com.leonardoestudiante.recyclercontacts;

//Importo las librerias necesarias
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Declaro la clase y heredo
public class ContactPagerActivity extends FragmentActivity {

    //Declaro el viewPager
    private ViewPager2 viewPager;

    //Declaro una variable que servira para el intercambio de informacion
    public static final String EXTRA_CONTACT_ID = "contact_id";

    //Declaro un metodo para instanciar esta con una clave y un UUID
    public static Intent newIntent(Context packageContext, UUID crimeId){
        return new Intent(packageContext,ContactPagerActivity.class).putExtra(EXTRA_CONTACT_ID,crimeId);
    }

    //Metodo que se ejecuta segun el ciclo de vida de fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Asocio la activity con el layout
        setContentView(R.layout.activity_contact_pager);

        //Declaro un UUID obtenido mediante el intent de listfragment del metodo onclick
        UUID contactId = (UUID) getIntent().getSerializableExtra(EXTRA_CONTACT_ID);

        //Asociamos los widgets locales a sus view mediante su ID
        Button buttonStart = (Button) findViewById(R.id.button_start);
        Button buttonFinal = (Button) findViewById(R.id.button_final);

        //Definimos el listener del buttonstart
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Defino que hacer al hacer click
                ContactPagerActivity.this.viewPager.setCurrentItem(0);

            }

        });

        //Definimos el listener del buttonfinal
        buttonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Utilizamos el button final para cambiar la pagina actual
                ContactPagerActivity.this.viewPager.setCurrentItem(Contact.contacts.size() - 1);

            }

        });

        //Asocio el widget con su ID en el layout
        this.viewPager =(ViewPager2) findViewById(R.id.activity_contact_view_pager);

        //Asocio un adapter al viewpager que servira para recorrer la lista mediante deslizar el dedo en la pantalla
        this.viewPager.setAdapter(new FragmentStateAdapter(this) {


            //Metodo que sirve para deslizar entre fragments cambiando su posicion e instaciando un nuevo fragment
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                //Instanciamos un nuevo ContactFragment cada vez que deslizamos
                return ContactFragment.newInstance(Contact.contacts.get(position).getId());
            }

            //Metodo que define el tamaño de las pages que podemos recorrer
            @Override
            public int getItemCount() {
                return Contact.contacts.size();
            }

        });

        //Defino un comportamiento al cambiar la pagina
        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                //Defino un index para obtener el indice actual en el que me encuentro
                int indexContact = ContactPagerActivity.this.viewPager.getCurrentItem();

                //Desativo el widget de button en funcion de donde me encuentre
                if(indexContact == 0){

                    buttonStart.setEnabled(false);
                    buttonFinal.setEnabled(true);

                } else if (indexContact == Contact.contacts.size() - 1){

                    buttonStart.setEnabled(true);
                    buttonFinal.setEnabled(false);

                } else {

                    buttonStart.setEnabled(true);
                    buttonFinal.setEnabled(true);

                }

            }

        });

        //Bucle que se encarga de buscar el contact con un UUID en la lista
        for(int i = 0 ; i < Contact.contacts.size(); i++){

            //Si el UUID coincide con el que se ha obtenido mediante el intent accedemos
            if(Contact.contacts.get(i).getId().equals(contactId)){

                //Desactivamos el button
                if(i == 0){buttonStart.setEnabled(false);}

                //Establemos el fragment actual de la lista que será que el hayamos pulsado
                this.viewPager.setCurrentItem(i);

            }

        }

    }

}