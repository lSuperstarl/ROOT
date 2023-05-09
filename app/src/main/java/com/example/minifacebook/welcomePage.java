package com.example.minifacebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class welcomePage extends AppCompatActivity {
    //protected userClass userObject;
    //metodo que va a correr cuando el activity se invoca
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//guardando el ultimo estado del app
        setContentView(R.layout.first_activity);//enlazando la clase de java con el layout

        //Sincronizando el boton de login
        Button logInButton = (Button) findViewById(R.id.logInbutton);
        //You need to create the onClickListener and when clicked, it call the other activity
        logInButton.setOnClickListener(new View.OnClickListener() {

            //This code detects when the button is clicked
            @Override
            public void onClick(View v) {
                //Create an intent in order to call the other activity
                Intent i = new Intent(welcomePage.this, homePage.class);

                //Add parameters such as objects
               // Intent userObject = i.putExtra("userObject", userObject);

                //Start the other activity
                startActivity(i);
            }
        });

        Button createAccount = (Button) findViewById(R.id.createAccountbtn);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(welcomePage.this, signUpPage.class);

                startActivity(i);
            }
        });
    }
}

