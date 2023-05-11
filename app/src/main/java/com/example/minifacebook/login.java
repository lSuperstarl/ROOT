package com.example.minifacebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    //protected userClass userObject;
    //metodo que va a correr cuando el activity se invoca
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//guardando el ultimo estado del app
        setContentView(R.layout.activity_login);//enlazando la clase de java con el layout

        // Define authentication endpoint
        String endpoint = "/appAuthentication";

        // Instantiate an HTTP Handler
        HttpHandler httpHandler = new HttpHandler();

        //Sincronizando el boton de login
        Button logInButton = (Button) findViewById(R.id.logInbutton);
        //You need to create the onClickListener and when clicked, it call the other activity
        logInButton.setOnClickListener(new View.OnClickListener() {

            //This code detects when the button is clicked
            @Override
            public void onClick(View v) {
                //Create an intent in order to call the other activity
                Intent i = new Intent(login.this, homePage.class);

                EditText editTextUserName = findViewById(R.id.textUserName);
                String username = editTextUserName.getText().toString();

                EditText editTextPassword = findViewById(R.id.textPassword);
                String password = editTextUserName.getText().toString();

                String response = httpHandler.makeServiceCallPost(endpoint, username, password);

                if (response.trim().compareTo("not")==0) {
                    String msgToast= "Wrong user or password";
                    Toast.makeText(getApplicationContext(),
                            msgToast,
                            Toast.LENGTH_LONG).show();
                } else {

                    //Start the other activity
                    startActivity(i);
                }

                //Add parameters such as objects
                // Intent userObject = i.putExtra("userObject", userObject);

                //Start the other activity
                startActivity(i);
            }
        });
    }
}

