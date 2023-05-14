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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class search_friends extends AppCompatActivity {
    private Button searchButton;
    private EditText genderInput;
    private EditText locationInput;
    private EditText ageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        genderInput = findViewById(R.id.txtGender);
        locationInput = findViewById(R.id.searchLocation);
        ageInput = findViewById(R.id.searchAge);
        searchButton = findViewById(R.id.searchButton);

        // Set the click listener on the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String gender = genderInput.getText().toString();
                String location = locationInput.getText().toString();
                String age = ageInput.getText().toString();

                // Create and execute a new SearchFriendsTask
                new SearchFriendsTask().execute(gender, location, age);
            }
        });
    }

    private class SearchFriendsTask extends AsyncTask<String, Void, Void> {

        // Server response
        private String serverResponse;

        /***
         *  Main thread
         * @param params: String array containing the search parameters (gender, location, age)
         * @return
         */
        protected Void doInBackground(String... params) {
            // Get the search parameters
            String gender = params[0];
            String location = params[1];
            String age = params[2];
            HttpHandler HttpHandler = new HttpHandler();

            // Make the HTTP request and receive the server response
            String response = HttpHandler.makeServiceCallPostSearch("http://192.168.48.129:1337/appAuth", gender, location, age);

            // Convert the JSON response into a UserClass object
            try {
                JSONObject jsonObject = new JSONObject(response);
                userClass friend = new userClass(jsonObject.toString());

                // Now, friend is a UserClass object that you can use to update your UI
                // For example, you can start a new Activity and pass the friend object as an extra

                Intent intent = new Intent(search_friends.this, friends_list.class);
                intent.putExtra("friend", friend);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /***
         *  This method runs after the doInBackground method is completed
         * @param result
         */
        protected void onPostExecute(Void result) {
            // Handle any UI updates or display a toast message
        }
    }
}
