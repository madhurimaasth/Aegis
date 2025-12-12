import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
* Represents a user in the Aegis system
* Inherits attributes and methods from the abstract User class
*/

public class Student extends User{
    Scanner input = new Scanner(System.in);

    //Student specific private fields
    private String currentLocation;
    private int numberOfReports;

    /**
     * Constructor for class Student
     * @param email -> email must be @mavs.uta.edu or @uta.edu; used as a unique identifier
     * @param password -> unique password set by the user and stored in a file
     * @param MAVID -> unique MAV ID of every User (can be used as a key)
     * @param firstname -> first name of the user, entered by user
     * @param lastname -> last name of the user, entered by user     
     * @param gender -> the user's self-declared gender
     * @param currentLocation -> current location on campus of the student
     * @param numberOfReports -> total number of reports submitted by the user
     */

     public Student(String email, String password, String MAVID, String firstname, String lastname, String gender, String currentLocation) {
        super(email, password, MAVID, firstname, lastname, gender);    //Calling constructor of base class User
        this.currentLocation = currentLocation;
        this.numberOfReports = 0;
     }

     //Getters and Setters
     public String getCurrentLocation() { return currentLocation; }
     public void setCurrentLocation (String currentLocation) {  //why not taking from enum?
        this.currentLocation = currentLocation; 
        System.out.println(getFirstname() + "'s location updated to " + currentLocation);
     }
     public int getNumberOfReports() { return numberOfReports; }
     public void setNumberofReports(int numberOfReports) { this.numberOfReports = numberOfReports; }
     public void incrementNumberOfReports(int numberOfReports) { this.numberOfReports++; }

     /**
      * Implementing the Abstract method viewDashboard() from the class User to display options
      */
      @Override
      public void viewDashboard() {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*                     Welcome to Aegis, " + getFirstname() + " " + getLastname() + "!                   *");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        System.out.println("   Which action would you like to perform?");
        System.out.println("1. Request a Companion");
        System.out.println("2. Initiate a Virtual Check-in");
        System.out.println("3. Report Hazard");
        System.out.println("4. Emergency Intervention");
        System.out.println("5. Exit");
        chooseAction();
      }

      @Override 
      public void chooseAction() {
        System.out.println("Enter your choice (1-5) ");
        String actionChoiceStr = input.nextLine().trim();
        int actionChoice = Integer.parseInt(actionChoiceStr);

        switch(actionChoice){
            case 1: CompanionWalk();        break;
            case 2: VirtualCheckIn();       break;
            case 3: ReportHazard();         break;
            case 4: EmergencyIntervention();break;
            case 5: System.exit(0);  break;
            default: 
            { System.out.println("Invalid choice."); chooseAction(); break;}
        }
     }

     /**
      * Methods for performing different actions for a Student
      */

      //An instance of the central data
      CentralDatabase userCentralDatabase = CentralDatabase.getInstance();

      /** A method to request comapanions to walk you across campus*/
      public void CompanionWalk() {
        System.out.println("Companion Request has been activated for " + getFirstname() + " " + getLastname());
        
        CampusLocations startLocation = null;
        CampusLocations endLocation = null;
        boolean validLocation = false;

        while(!validLocation) {
          System.out.println("Your options are:");
          for(CampusLocations location : CampusLocations.values()) {
                    System.out.println("- " + location);
                }
          System.out.println("What is your current location? ");
          String currentLocation = input.nextLine().trim().toUpperCase();
          System.out.println("Where are you walking to?");
          String destination = input.nextLine().trim().toUpperCase();
          try{
            startLocation = CampusLocations.valueOf(currentLocation);
            endLocation = CampusLocations.valueOf(destination);
            validLocation = true;
          } catch (IllegalArgumentException e) {
            System.err.println("Invalid Location. Please select a location from the list.");
          }
        }
       
        String requestedGender = getGender(); //No matter what the user requests, we apply same gender for now
        SafetyRequest newRequest = new SafetyRequest(this.getEmail(), this.getFirstname(), requestedGender, startLocation, endLocation);
        userCentralDatabase.submitSafetyRequest(newRequest);
        viewDashboard();
      }


      /**
       * A method to connect virtually to a friend or any contact for safety
       * Logs the start time and location
       * An option to log out safely at the destination
       * @param contactName -> name of the contact that they want to check-in with
       * @param contactType -> could be friend, parent, guardian, or other contact
       */
       //Only a simulation at this moment
      public void VirtualCheckIn() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss, MMM d");
        String formattedTime = now.format(formatter);

        System.out.println("*-*-*-*-*-*-*-* Virtual Check-in Initiated for " + getFirstname() + "*-*-*-*-*-*-*-*");
        System.out.println("Enter the name of the person you would like to contact.");
        String contactName = input.nextLine();
        System.out.println("Who is this person to you?");
        String contactType = input.nextLine();
        System.out.println("User: " + getFirstname() + " " + getLastname());
        System.out.println("Start location: " + getCurrentLocation());
        System.out.println("Start Time: " + formattedTime);
        System.out.println("Contact: " + contactName + ", " + contactType);
        System.out.println("Please enter Y when you are ready to stop the virtual check-in. ");
        String end = input.nextLine();
        if(end.toUpperCase().equals("Y")) { 
          System.out.println("Virtual Check-in has ended successfully.");
          LocalDateTime endTime = LocalDateTime.now();
          System.out.println("End Time: " + endTime.format(formatter));
          System.out.println("Stay Safe!\n\n");
        }
        viewDashboard();
      }


      /**
       * A method which allows a student to submit a non-emergency hazard or incident report
       * Also has keyword analysis for immediate escalation
       */
      public void ReportHazard() {
        System.out.println("*-*-*-*-*-*-*-* Hazard Report Initiated for " + getFirstname() + "*-*-*-*-*-*-*-*");

        HazardType hazard = null;
        boolean isValidHazard = false;
        System.out.println("Available Hazard Types: ");
        for(HazardType hazardType : HazardType.values()) {
            System.out.println("- " + hazardType);
          }
        
          while(!isValidHazard) {
            System.out.println("Enter the type of hazard.");
            String hazardstr = input.nextLine().trim().toUpperCase();
            try{
              hazard = HazardType.valueOf(hazardstr);
              isValidHazard = true;
            } catch (IllegalArgumentException e) {
              System.err.println("Invalid hazard type. Please enter one from the list.");
            }
          }

          CampusLocations loc = null;
          boolean isvalidLocation = false; 
          for(CampusLocations location : CampusLocations.values()) {
              System.out.println("- " + location);
            }
          while(!isvalidLocation){
            System.out.println("Enter the location of the hazard. ");
            String locationstr = input.nextLine().trim().toUpperCase();
            try {
              loc = CampusLocations.valueOf(locationstr);
              isvalidLocation = true;
            } catch (IllegalArgumentException e) {
              System.err.println("Invalid location. Please select one from the list.");
            }
          }

          System.out.println("Enter a detailed description of the hazard");
          String description = input.nextLine().trim();

          if(checkEmergencyKeywords(description)) {
            reportToUtaPD("Escalated Hazard Report");
          }

          hazardReport newReport = new hazardReport (this.getEmail(), this.getFirstname(), loc, hazard, description);
          userCentralDatabase.submitHazardReport(newReport);
          System.out.println("\n Report submitted successfully. Awaiting review from the admin staff.");
          viewDashboard();
      }

      /** Escalating the Hazard report to UTA PD if necessary */ 
      private void reportToUtaPD(String message) {
        System.out.println("EMERGENCY ESCALATION: \n" + message + "\n REPORTED TO UTA PD.");
      }
      /** to check for serious keywords */
      private boolean checkEmergencyKeywords(String description) {
        String lowerDesc = description.toLowerCase();
        return lowerDesc.contains("fire") ||
               lowerDesc.contains("gun") ||
               lowerDesc.contains("weapon") ||
               lowerDesc.contains("attack") ||
               lowerDesc.contains("injured");
      }

      @Override
      public void EmergencyIntervention() {
        System.out.println("\nAdmins have been notified.");
        System.out.println("Please call UTA PD at (817) 272-3003 to report emergency.\n");
        viewDashboard();
       }
  
}
