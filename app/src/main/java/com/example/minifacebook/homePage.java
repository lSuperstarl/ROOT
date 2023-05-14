package com.example.minifacebook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class homePage extends AppCompatActivity {

    private userClass userObject;
    private Handler handler = new Handler(Looper.getMainLooper());
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
        ((TextView) findViewById(R.id.textViewUserName)).setText(userObject.getUserName());
        ((TextView) findViewById(R.id.textViewEmailAddress)).setText(userObject.getEmail());
        ((TextView) findViewById(R.id.textViewGender)).setText(userObject.getGender());
        ((TextView) findViewById(R.id.textViewStreet)).setText(userObject.getStreet());
        ((TextView) findViewById(R.id.textViewTown)).setText(userObject.getTown());
        ((TextView) findViewById(R.id.textViewState)).setText(userObject.getState());
        ((TextView) findViewById(R.id.textViewCountry)).setText(userObject.getCountry());
        //Create URL for each image
        String imageURL = "http://192.168.48.129:1337/cpen410/images/regularusers/" + userObject.getProfilePicture();

        // Start a new Thread for image downloading
        new Thread(() -> {
            final Drawable actualImage = LoadImageFromWebOperations(imageURL);
            // Use the Handler to post back to main thread
            handler.post(() -> {
                ImageView userImage = findViewById(R.id.imageView);
                if (actualImage != null) {
                    userImage.setImageDrawable(actualImage);
                } else {
                    Log.e("LoadImageFromWeb", "Failed to load image from " + imageURL);
                }
            });
        }).start();


        Button searchFriendsButton = findViewById(R.id.searchFriendsbtn);
        searchFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homePage.this, search_friends.class);
                startActivity(intent);
            }
        });

        Button signOutButton = findViewById(R.id.signOutbtn);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle session logout here if you're using any kind of session management.
                // This could be clearing shared preferences, firebase signout, etc.

                // After handling session logout, navigate back to the login activity
                Intent intent = new Intent(homePage.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();  // This call is optional, it finishes the current activity
            }
        });

    }

    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.e("LoadImageFromWeb", "Failed to load image from " + url, e);
            return null;
        }
    }
}
