package com.example.minifacebook;



/* Codigo HomePage donde el usuario sera capaz de ver su
*  informacion completa y contiene dos botones: uno para
* buscar amigos y el otro para hacer signOut*/




import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class homePage extends AppCompatActivity {

        private userClass userObject;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.third_activity);

            //Retreive the shared object
            Intent i = getIntent();
            //Cast the serializable object as your Class
            userObject = (userClass) i.getSerializableExtra("userObject");

            /*El siguiente codigo calcula la edad del usuario
             *
             * @param sdf es un objeto de la clase SimpleDataFormat que nos ayuda a ver el format de la fecha
             * @param fechaNac es un objeto de la clase Date esto convierte la fecha en objeto date
             * @diff es para calcular la resta entre la fecha de hoy con la que el user puso
             * @edad es para colocar cual es la edad que tiene el user
             *
             * */

            // Obtener la fecha de nacimiento del usuario como una cadena de texto
            String fechaNacimiento = userObject.getDoB();

            // Crear un objeto SimpleDateFormat para analizar la fecha de nacimiento
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Convertir la fecha de nacimiento en un objeto de fecha
                Date fechaNac = sdf.parse(fechaNacimiento);

                // Calcular la diferencia entre la fecha actual y la fecha de nacimiento
                long diff = new Date().getTime() - fechaNac.getTime();

                // Convertir la diferencia en años
                long edad = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;
                //En el textView de la edad mostrara la edad calculada
                ((TextView) findViewById(R.id.textViewDoB)).setText((int) edad);

            } catch (ParseException e) {
                // Manejar cualquier excepción que pueda ocurrir al analizar la fecha de nacimiento
                e.printStackTrace();
            }
            /*Termina el calculo de la edad del usuario*/

            //Access to the object's methods
            ((TextView) findViewById(R.id.textViewUserName)).setText(userObject.getName());
            ((TextView) findViewById(R.id.textViewEmailAddress)).setText(userObject.getEmail());
            ((TextView) findViewById(R.id.textViewGender)).setText(userObject.getGender());
            ((TextView) findViewById(R.id.textViewAddress)).setText(userObject.getAddress());
            //anadir la foto
            //Button btn = (Button)findViewById(R.id.createAccountUserbtn);
        }
    }