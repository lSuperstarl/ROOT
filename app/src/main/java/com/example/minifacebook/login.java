package com.example.minifacebook;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    public Activity activity;

    //Host address
    protected String hostAddress="192.168.48.129:1337";
    //protected String hostAddress="192.168.0.7:8088";

    //Autentication Servlet name
    protected String servletName="/appAuth";

    //protected userClass userObject;
    //metodo que va a correr cuando el activity se invoca
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//guardando el ultimo estado del app
        setContentView(R.layout.activity_login);//enlazando la clase de java con el layout
        activity = this;

        //Sincronizando el boton de login
        Button logInButton = (Button) findViewById(R.id.logInbutton);
        //You need to create the onClickListener and when clicked, it call the other activity
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create and execute a new GetItems thread
                //Every network transaction cannot be performed in the main thread
                new GetItems(activity).execute();
            }
        });
    }
    private class GetItems extends AsyncTask<Void, Void, Void> {

        // Context: every transaction in a Android application must be attached to a context
        private Activity activity;

        //Server response
        private String serverResponse;

        private String url;

        /***
         * Special constructor: assigns the context to the thread
         *
         * @param activity: Context
         */
        //@Override
        protected GetItems(Activity activity)
        {
            //Define the servlet URL
            url = "http://" + hostAddress + servletName;
            this.activity = activity;
        }

        /**
         *  on PreExecute method: runs after the constructor is called and before the thread runs
         */
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(login.this, "Authenticating..." + url, Toast.LENGTH_LONG).show();
        }

        /***
         *  Main thread
         * @param arg0
         * @return
         */
        protected Void doInBackground(Void... arg0) {

            //Read GUI inputs
            String userName, passWord;
            userName = ((EditText) findViewById(R.id.textUserNameLogin)).getText().toString();
            passWord = ((EditText) findViewById(R.id.textPassword)).getText().toString();

            //Define a HttpHandler
            HttpHandler handler = new HttpHandler();

            //perform the authentication process and capture the result in serverResponse variable
            serverResponse = handler.makeServiceCallPost(url, userName, passWord);

            return null;
        }


        /***
         *  This method verify the authentication result
         *  If authenticated, it creates an jsonPerson Object and open an authenticatedActivity
         *  otherwise, it shows a error message
         * @param result
         */
        protected void onPostExecute (Void result){
            String msgToast;

            //Verify the authentication result
            // not: the user could not be authenticated
            if (serverResponse.trim().compareTo("not")==0) {
                msgToast= "Wrong user or password";
                Toast.makeText(getApplicationContext(), msgToast, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "User will be logged in now", Toast.LENGTH_LONG).show();
                //Define a jsonPerson object using the http response
                userClass userclass = new userClass(serverResponse);
                //Create an intent in order to call the other activity
                Intent i = new Intent(login.this, homePage.class);

                //Add parameters such as objects
                i.putExtra("userclass", userclass);

                //Start the other activity
                startActivity(i);
            }
        }
    }
}

