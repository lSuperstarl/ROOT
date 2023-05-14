package com.example.minifacebook;

import org.json.JSONObject;
import java.io.Serializable;

public class userClass implements Serializable {
    private String name;
    private String email;
    private String userName;
    private String dob;
    private String gender;
    private String profilePicture;
    private String street;
    private String town;
    private String state;
    private String country;

    /**
     *  Special constructor using the actual values
     */
    public userClass(String name, String email, String userName, String dob, String gender, String profilePicture, String street, String town, String state, String country)
    {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.dob = dob;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.street = street;
        this.town = town;
        this.state = state;
        this.country = country;
    }



    /**
     *  Special constructor using a Json file with the following syntax:
     */
    public userClass(String jsonFile)
    {
        try{
            // Define a JSON object from the received data
            JSONObject jsonObj = new JSONObject(jsonFile);
            name = jsonObj.getString("name");
            userName = jsonObj.getString("userName");
            email = jsonObj.getString("email");
            dob = jsonObj.getString("dob");
            gender = jsonObj.getString("gender");
            profilePicture = jsonObj.getString("profilePicture");
            street = jsonObj.getString("street");
            town = jsonObj.getString("town");
            state = jsonObj.getString("state");
            country = jsonObj.getString("country");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Observer methods
     */

    public String getName() {
        return  name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
