import java.io.*;


//In order to share the class's objects between activities, it must be serializable
public class userClass implements Serializable {

    //User's name
    private String name;
    //User's email
    private String email;
    //User's date of birth
    private String Dob;

    /***
     * Class constructor
     * @param name: user's name
     * @param email: user's email address
     * @param DoB: user's date of birth
     */
        public userClass(String name, String email, String DoB)
        {
            this.name = name;
            this.email = email;
            this.Dob = DoB;
        }

    /**
     *  Access to the user's name
     * @return user's name (String)
     */
    public String getName()
        {
            return name;
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
}
