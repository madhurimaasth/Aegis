import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Management class for different methods
 */ 
public class CentralDatabase {
    public static final CentralDatabase INSTANCE = new CentralDatabase();
    private Map<String, User> userDatabase;     //key->email; Value-> User object
    private Map<String, hazardReport> hazardReports;
    private Map<String, SafetyRequest> safetyRequests;  

    Scanner input = new Scanner(System.in); 
    
    //Constructor
    public CentralDatabase() {
        this.userDatabase = new HashMap<>();
        this.hazardReports = new HashMap<>();
        this.safetyRequests = new HashMap<>();
    }
    public static CentralDatabase getInstance(){
        return INSTANCE;
    }

    // Core Management Methods
    //1. retrieve a user by email
    //2. Map of all companions for matching and admin review
    //3. to store a New Hazard Report
    //4. to store a New SafetyCompanion Request
    //5. view all active reports
    //6. Matching algorithm for students to companions

    /* 1. To retrieve a user object by its email */
    public User getUserByEmail(String email) {
        return userDatabase.get(email);
        
    }

    /** 2. To get a map of all companions */
    public Map <String, Companion> getAllCompanions() {
        Map<String, Companion> companions = new HashMap<>();
        for(User user: userDatabase.values()) {
            if(user instanceof Companion) {
                companions.put(user.getEmail(), (Companion) user);
            }
        }
        return companions;
    }

    /** 3. To store a new Hazard Report in the system (hashmap) */
    public void submitHazardReport(hazardReport report) {
        hazardReports.put(report.getReportID(), report);
        System.out.println("Report " + report.getReportID() + "submitted from " + report.getLocation() + " by " +
                            report.getReporterName() + "( " + report.getReporterEmail() + ") . \n status: " + report.getStatus());
    }
    
    /** 4. To store a new Safety Report in the system (hashmap*/
    public void submitSafetyRequest(SafetyRequest request) {
        safetyRequests.put(request.getRequestID(), request);
        System.out.println("Safety Request " + request.getRequestID() + "  submitted by " + request.getRequesterName() +
                     "(" + request.getRequesterEmail() + ")" + " at " + request.getStartLocation() + ". Searching for match.");
        matchCompanion(request);
    }

    /** 5. To view all active reports (for admins)*/
    public Map <String, SafetyRequest> getAllSafetyRequests() { return safetyRequests; } 

    /** 6. To view all active hazardReports */
    public Map<String, hazardReport> getAllHazardReports() { return hazardReports; }

    /** 7. Matching Algorithm for matching users to companions */
    public void matchCompanion (SafetyRequest request) {
        //Map <String, Companion> allCompanions = getAllCompanions();
        List <Companion> potentialMatches = new ArrayList<>();

        System.out.println("Do you want to request same-gender companion? Y/N");
        //String preference = input.nextLine().toUpperCase();

        try(BufferedReader reader = new BufferedReader(new FileReader("companions.txt")); ) 
        {
            String readLine;
            while((readLine = reader.readLine()) != null) {
                readLine = readLine.trim();
                if (readLine.isEmpty()) continue;
                String[] tokens = readLine.split("[,]+");
                if(tokens.length != 10) continue;     //skipping malformed lines
                String email = tokens[0];
                String password = tokens[1];
                String MAVID = tokens[2];
                String firstname = tokens[3];
                String lastname = tokens[4];
                String gender = tokens[5];
                Boolean isApproved = Boolean.parseBoolean(tokens[6]);
                Boolean isTrained = Boolean.parseBoolean(tokens[7]);;
                Boolean isAvailable = Boolean.parseBoolean(tokens[8]);;
                //int walksCompleted = Integer.parseInt(tokens[9]);

                if((isApproved==true) && (isTrained==true) && (isAvailable==true)) {
                    Companion companion = new Companion(email, password, MAVID, firstname, lastname, gender);
                    System.out.println(companion.getEmail() + companion.getEmail());
                    potentialMatches.add(companion);
                }
            }
        } catch (IOException e) {}

        if(potentialMatches.isEmpty()) 
        {
            request.setStatus("UNMATCHED");
            System.out.println("Sorry, all companions are busy at the moment. Please try again after 5 minutes.");
        } else {
            Companion matchedCompanion = potentialMatches.get(0);
            System.out.println("\nCompanion Found! Your Companion is: " + matchedCompanion.getFirstname() + " " + matchedCompanion.getLastname());
            matchedCompanion.setCurrentRequest(request);
            request.assignCompanion(matchedCompanion.getEmail());
            request.setStatus("ACCEPTED");
            matchedCompanion.setAvailable(false);
            matchedCompanion.incrementWalksCompleted();
        }
    }
}
