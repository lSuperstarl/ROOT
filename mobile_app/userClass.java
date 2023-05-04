package com.example.miniface;

/*
    CPEN 410 - Mobile, Web, and Internet programming
    g.Resto & c.Roque THE ONE PIECE ITS REAL!!
    This class stores the name, email and date of birth of a user
*/
import java.io.Serializable;

//In order to share the class's objects between activities, it must be serializable
public class userClass implements Serializable {


    //User's name
    private String name;
    //user pass pass passwooooord
    private String hashbrown; //<---- este es el password XD
    //User's email
    private String email;
    //User's date of birth
    private String Dob;
    //User's gender
    private String gender;
    //User's Address
    private String address;

    /***
     * Class constructor
     * @param name : user's name
     * @param email : user's email address
     * @param hashbrown : user's password
     * @param DoB : user's date of birth
     */
    public userClass(String name, String email, String hashbrown, String DoB, String gender)
    {
        this.name = name;
        this.hashbrown=hashbrown;
        this.email = email;
        this.Dob = DoB;
        this.gender=gender;
        this.address=address;
    }

    /**
     *  Access to the user's name
     * @return user's name (String)
     */
    public String getName()
    {
        return name;
    }

    /**
     *  Access to the user's hashbrown
     * @return user's hash (String)
     */
    public String getHashbrown()
    {
        return hashbrown;
    }

    /***
     *  Access to the user's email address
     * @return user's email address (String)
     */
    public String getEmail()
    {
        return email;
    }

    /**
     *  Access to the user's date of birth
     * @return user's date of birth
     */
    public String getDoB()
    {
        return Dob;
    }

    /**
     *  Access to the user's gender
     * @return user's gender (String)
     */
    public String getGender()
    {
        return gender;
    }
    public String getAddress()
    {
        return address;
    }
}
