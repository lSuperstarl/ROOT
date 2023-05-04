package com.example.miniface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class signUpPage extends AppCompatActivity {
    //Shared object between activities
    protected userClass userObject;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//guardando el ultimo estado del app
        setContentView(R.layout.second_activity);//enlazando la clase de java con el layout

        //Sincronizando el boton de login
        Button btn = (Button)findViewById(R.id.createAccountUserbtn);
        btn.setOnClickListener(new View.OnClickListener() {

            //This code detects when the button is clicked
            @Override
            public void onClick(View v) {
                //Then the button is clicked, the activity creates an userClass's object called userObject
                createUserObject();

                //Create an intent in order to call the other activity
                Intent i = new Intent( signUpPage.this, welcomePage.class);//******

                //Add parameters such as objects
                i.putExtra("userObject", userObject);

                //Start the other activity
                startActivity(i);
            }
        });
    }

    //This method create an userObject from the userClass
    protected void createUserObject() {
        //Access  to the actity's textName and copy its value into the uname variable
        String uname = ((EditText) findViewById(R.id.createName)).getText().toString();
        //Access  to the actity's textEmail and copy its value into the uemail variable
        String uHashbrown = ((EditText) findViewById(R.id.createPassword)).getText().toString();
        String uEmailAddress = ((EditText) findViewById(R.id.createEmailAddress)).getText().toString();
        String uAddress = ((EditText) findViewById(R.id.createAddress)).getText().toString();
        String uDoB = ((EditText) findViewById(R.id.createDoB)).getText().toString();
        //String ugender=((Spinner) findViewById(R.id.genderSpinner)).getText().toString();
        /*falta anadir lo de la foto aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii
        *
        *
        *
        *
        *
        * */
        //Create the userClass object
        this.userObject = new userClass(uname, uHashbrown,uEmailAddress,uAddress,uDoB);//<--- recordar la foto y gender
    }

}

