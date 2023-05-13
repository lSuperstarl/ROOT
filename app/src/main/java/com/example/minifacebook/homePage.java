package com.example.minifacebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

        // Retrieve the shared object
        Intent i = getIntent();
        // Cast the serializable object as your Class
        userObject = (userClass) i.getSerializableExtra("userclass");

        // Obtain the user's date of birth as a string
        String fechaNacimiento = userObject.getDob();

        // Create a SimpleDateFormat object to parse the date of birth
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Convert the date of birth into a Date object
            Date fechaNac = sdf.parse(fechaNacimiento);

            // Calculate the difference between the current date and the date of birth
            long diff = new Date().getTime() - fechaNac.getTime();

            // Convert the difference into years
            long edad = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;
            // Display the calculated age in the DoB TextView
            ((TextView) findViewById(R.id.textViewDoB)).setText(String.valueOf(edad));

        } catch (ParseException e) {
            // Handle any exception that may occur when parsing the date of birth
            e.printStackTrace();
        }

        // Access to the object's methods
        ((TextView) findViewById(R.id.textViewUserName)).setText(userObject.getName());
        ((TextView) findViewById(R.id.textViewEmailAddress)).setText(userObject.getEmail());
        ((TextView) findViewById(R.id.textViewGender)).setText(userObject.getGender());
        ((TextView) findViewById(R.id.textViewStreet)).setText(userObject.getStreet());
        ((TextView) findViewById(R.id.textViewTown)).setText(userObject.getTown());
        ((TextView) findViewById(R.id.textViewState)).setText(userObject.getState());
        ((TextView) findViewById(R.id.textViewCountry)).setText(userObject.getCountry());

        // To set the profile picture, you'll have to use an image loading library such as Glide or Picasso
        // assuming you have the image in the drawables, here's how you can set it:
        // ImageView profilePicture = (ImageView) findViewById(R.id.imageView);
        // profilePicture.setImageResource(getResources().getIdentifier(userObject.getProfilePicture(), "drawable", getPackageName()));
    }
}
