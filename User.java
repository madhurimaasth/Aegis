import java.util.regex.Pattern;

/**
 * An abstract class for User
 * User parents Student, Companion, and Admin
 */

abstract class User {
    private String email;
    private String password;
    private String MAVID;
    private String firstname; 
    private String lastname;
    private String gender;

    /** Constructor for Class USer
     * @param email -> email must be @mavs.uta.edu or @uta.edu; used as a unique identifier
     * @param password -> unique password set by the user and stored in a file
     * @param MAVID -> unique MAV ID of every User (can be used as a key)
     * @param firstname -> first name of the user, entered by user
     * @param lastname -> last name of the user, entered by user
     * @param gender -> the user's self-declared gender
     */
    public User(String email, String password, String MAVID, String firstname, String lastname, String gender) {
        this.email = email;
        this.password = password;
        this.MAVID = MAVID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }

    //Abstract method for User class
    public abstract void viewDashboard();
    public abstract void chooseAction();
    public abstract void EmergencyIntervention();

    //Getters and Setters for User class
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getMAVID() { return MAVID; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getGender() { return gender; }

    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Email, password, and MAVID don't change but 
     * we declare setters incase they ever need to be updated
     * */
    public void setEmail(String email) { 
        this.email = email; 
        System.out.println("Email for " + firstname + " " + lastname + "has been updated successfully!");
    }  
    public void setPassword(String newPassword) { 
        this.password = newPassword;
        System.out.println("Password for " + firstname + " " + lastname  + " has been updated successfully! "); 
    }  
    public void setMAVID(String MAVID) {
        this.MAVID = MAVID;
        System.out.println("MAVID for " + firstname + " " + lastname  + " has been updated successfully! ");
    }

    /**
     * A method for email domain verification
     */
    public static boolean verifyUTADomain(String email) {
        return Pattern.compile(".*@(mavs\\.)?uta\\.edu$", Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }
}