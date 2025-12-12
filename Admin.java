import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents all the admin staff and security staff 
 * oversee the approval, training and emergency intervention
 */

public class Admin extends User {
    Scanner input = new Scanner(System.in);

    private String department;

    /**
     * Constructor for Admin Class
     * @param email -> email must be @mavs.uta.edu or @uta.edu; used as a unique identifier
     * @param password -> unique password set by the user and stored in a file
     * @param MAVID -> unique MAV ID of every User (can be used as a key)
     * @param firstname -> first name of the user, entered by user
     * @param lastname -> last name of the user, entered by user
     * @param gender -> the user's self-declared gender
     * @param department -> the department of the admin staff (MANAGEMENT_ADMIN, SECURITY, UTA_PD)
     */
     public Admin (String email, String password, String MAVID, String firstname, String lastname, String gender, String department) {
        super(email, password, MAVID, firstname, lastname, gender);
        this.department = department;
     }

     //getters and setters
     public String getDepartment() { return department; }
     //No setters, department does not change once entered

     @Override
     public void viewDashboard() {
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*                   Welcome to Aegis, " + getFirstname() + " " + getLastname() + "!                 *");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        System.out.println("   Which action would you like to perform?");
        System.out.println("1. Review and Approve Companion Applications");
        System.out.println("2. Review Hazard Reports");
        System.out.println("3. Post Community Alerts");
        System.out.println("4. View User Statistics");
        System.out.println("5. Exit");
        chooseAction();
      }

      @Override
      public void chooseAction(){
        System.out.println("Enter your choice (1-4) ");
        String actionChoiceStr = input.nextLine();
        int actionChoice = Integer.parseInt(actionChoiceStr);

        switch(actionChoice){
            case 1: {try{ReviewNewApplications();}catch(IOException e){e.printStackTrace();} break;}
            case 2: {CentralDatabase database = CentralDatabase.getInstance();
                    ReviewHazardReports(database);            break; }
            case 3: ViewUserStatistics();                     break;
            case 4: System.exit(0);                    break;
            default: 
            { System.out.println("Invalid choice."); chooseAction(); break;}
        }
      }
      @Override
      public void EmergencyIntervention() { }

      /** To review the companion approval applications */
      public void ReviewNewApplications() throws IOException {
        System.out.println("Enter the email of the Companion that you would like to review.");
        String enteredEmail = input.nextLine().trim();
        boolean training = false, approval = false;

        List<String> newList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("companions.txt"));)
        {          
          newList = Files.readAllLines(Paths.get("companions.txt"));
          for(int i=0; i<newList.size(); i++) {
            String line = newList.get(i);
            if(line.isEmpty()) continue;           
            String[] tokens = line.split("[,]+");
            if(tokens.length != 10) continue;   //skipping malformed lines
            String email = tokens[0];
            String password = tokens[1];
            String MAVID = tokens[2];
            String firstname = tokens[3]; 
            String lastname = tokens[4];
            String gender = tokens[5];
            approval = Boolean.parseBoolean(tokens[6]);
            training = Boolean.parseBoolean(tokens[7]);
            boolean availability = Boolean.parseBoolean(tokens[8]);
            int walksCompleted = Integer.parseInt(tokens[9]);
            Companion currentCompanion = new Companion(email, password, MAVID, firstname, lastname, gender);
            if(enteredEmail.equals(email)) {
              System.out.println("Has " + firstname + " " + lastname);
              try {
                System.out.println(" --> passed UTA PD verification? (true/false) ");
                approval = input.nextBoolean();
                input.nextLine();
                currentCompanion.setApproved(approval);
              } catch(InputMismatchException e) {
                System.out.println("Invalid input. Please enter true/false (case-sensitive)");
                ReviewNewApplications();
              }
              try{
                System.out.println(" --> completed self-defense training?  (true/false) ");
                training = input.nextBoolean();
                input.nextLine();
                currentCompanion.setTrained(training);
              } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter true/false (case-sensitive)");
                ReviewNewApplications();
              }
                if(approval && training) {availability = true; }
                else {availability = false; }  
                String updatedLine = email + "," + password + "," + MAVID + "," + firstname + "," + lastname + "," + gender + 
                              "," + approval + "," + training + "," + availability + "," + walksCompleted ;
                newList.set(i, updatedLine);
             }
          }
          reader.close();
        } catch (IOException e) {
            System.out.println("Error opening the companion file");
            e.printStackTrace();
        } finally {} 
          
        try (PrintWriter writer = new PrintWriter(new BufferedWriter (new FileWriter("companions.txt"))); ) 
        {
          for(int i=0; i<newList.size(); i++) {
            writer.println(newList.get(i));
          }
        } catch (IOException e) {
          System.out.println("Error opening the companions.txt to write");
        }
      viewDashboard();
     }
      

      /** to review hazard reports posted */
      public void ReviewHazardReports(CentralDatabase userCentralDatabase) {
        Map<String, hazardReport> reports = userCentralDatabase.getAllHazardReports();
        System.out.println("Reviewing Hazard Reports. Total: " + reports.size());
        int pendingcount = 0;

        for(hazardReport report : reports.values()) {
          if(report.getStatus().equals("PENDING") || report.getStatus().equals("IN_REVIEW")) {
            System.out.println(report.toString());
            pendingcount++;
          }
        }

        if(pendingcount == 0) {
          System.out.println("All reports RESOLVED. Nothing to review at the moment.");
          return;
        }

        System.out.println("\n Enter the ID of the report to resolve or EXIT: ");
        String reportID = input.nextLine().trim();
        if(reportID.equalsIgnoreCase("EXIT")) { return; }

        hazardReport reportToResolve = reports.get(reportID);
        if(reportToResolve != null) {
          if(!reportToResolve.getStatus().equals("RESOLVED")) {
            reportToResolve.setStatus("RESOLVED"); viewDashboard();           
          } else {
            System.out.println("Report " + reportID + " resolved by " + getFirstname() + " " + getLastname() + "."); viewDashboard();
          }
        } else {
          System.err.println("Error: Report ID not found."); viewDashboard();
        }
      }
     
      public void ViewUserStatistics() {
        System.out.println("Which user file would you like to view? ");
        String role = input.nextLine().trim().toLowerCase();
        String filename = role + ".txt";

        try{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int i = 0;
        while(br.readLine() != null) {
          System.out.println(i++ + " " + br.readLine());
        }
        br.close();
        } catch (IOException e) {
          System.out.println("Error opening file.");
        }
        viewDashboard();
      }
  }



/**One major problem: the reports and all, they are not really being saved anywhere right now. 
 * so if i login as a user, I will be able to request a companion and all 
 * but I will not be able to (in the same session) go and login as a companion to view/approve any requests
 */
