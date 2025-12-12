import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LogIn {
    Scanner input = new Scanner(System.in);

    public void SignIn(String role){
        System.out.println("Please enter your email: ");
        String enteredEmail = input.nextLine().trim().toLowerCase();
        //Verifying that the entered email is a UTA email
        if(!User.verifyUTADomain(enteredEmail)) {
            System.out.println("Invalid UTA email domain. Please use a valid @mavs.uta.edu or @uta.edu email.");
            SignIn(role);
        }
        System.out.println("Please enter your password: ");
        String enteredPassword = input.nextLine().trim();

        //open a file to check credentials
        switch(role) {
            case "student":
                //first load all the info from the students.txt file and use class methods to verify
                try{
                    BufferedReader readBuffStudent = new BufferedReader(new FileReader("students.txt"));
                    String readLine;
                    while((readLine = readBuffStudent.readLine()) != null) {
                        readLine = readLine.trim();
                        if (readLine.isEmpty()) continue;
                        if(readLine.contains(enteredEmail)) {
                            String[] tokens = readLine.split("[,]+");
                            if(tokens.length != 8) continue;     //skipping malformed lines

                            String email = tokens[0];
                            String password = tokens[1];
                            String MAVID = tokens[2];
                            String firstname = tokens[3];
                            String lastname = tokens[4];
                            String gender = tokens[5];
                            String currentLocation = tokens[6];
                            int numberOfReports = Integer.parseInt(tokens[7]);

                            Student newStudent = new Student(email, password, MAVID, firstname, lastname, gender, currentLocation);
                            newStudent.setNumberofReports(numberOfReports);
                            System.out.println(newStudent.getEmail());
                            System.out.println(newStudent.getPassword());
                            
                            if(email.equals(enteredEmail) && password.equals(enteredPassword)) {
                                newStudent.viewDashboard();
                            } else {
                                System.out.println("Sorry, your email or password is not correct. Please try again.");
                                SignIn(role);
                            }
                                
                        }
                    }
                    readBuffStudent.close();
                } catch (IOException e) { 
                    System.out.println("An error occurred while opening files.");
                    e.printStackTrace(); 
                }
                break;

            case "companion":
                //first load all the info from the companions.txt file and use class methods to verify
                try (BufferedReader readBuffCompanion = new BufferedReader(new FileReader("companions.txt"));) 
                {
                    String readLine;
                    while((readLine = readBuffCompanion.readLine()) != null) {
                        readLine = readLine.trim();
                        if (readLine.isEmpty()) continue;
                        if(readLine.contains(enteredEmail)) {
                            String[] tokens = readLine.split("[,]+");

                            if(tokens.length != 10) continue;     //skipping malformed lines

                            String email = tokens[0];
                            String password = tokens[1];
                            String MAVID = tokens[2];
                            String firstname = tokens[3];
                            String lastname = tokens[4];
                            String gender = tokens[5];
                            String isApprovedStr = tokens[6];
                            String isTrainedStr = tokens[7];
                            String isAvailableStr = tokens[8];
                            int walksCompleted = Integer.parseInt(tokens[9]);

                            Companion newCompanion = new Companion(email, password, MAVID, firstname, lastname, gender);

                            if(isApprovedStr.equalsIgnoreCase("true")) { boolean isApproved = true; newCompanion.setApproved(isApproved); }
                            if(isApprovedStr.equalsIgnoreCase("false")) { boolean isApproved = false; newCompanion.setApproved(isApproved);}
                            if(isTrainedStr.equalsIgnoreCase("true")) { boolean isTrained = true; newCompanion.setTrained(isTrained);}
                            if(isTrainedStr.equalsIgnoreCase("false")) { boolean isTrained = false; newCompanion.setTrained(isTrained);}
                            if(isAvailableStr.equalsIgnoreCase("true")) { boolean isAvailable = true; newCompanion.setAvailable(isAvailable);}
                            if(isAvailableStr.equalsIgnoreCase("false")) { boolean isAvailable = false; newCompanion.setAvailable(isAvailable);}
                            newCompanion.setWalksCompleted(walksCompleted);

                            if(email.equals(enteredEmail) && password.equals(enteredPassword)) {
                                if(newCompanion.isApproved() && newCompanion.isTrained()) {
                                newCompanion.viewDashboard();
                                } else {
                                    System.out.println("Sorry you have not been approved or trained yet.");
                                    return;
                                }
                            } else {
                                System.out.println("Sorry, your email or password is not correct. Please try again.");
                                SignIn(role);
                            }
                        }
                     }
                    readBuffCompanion.close();
                } catch (IOException e) { 
                    System.out.println("An error occurred while opening files.");
                    e.printStackTrace(); 
                }
                break;

            case "admin":
                //first load all the info from the admins.txt file and use class methods to verify
                try{
                    BufferedReader readBuffAdmin = new BufferedReader(new FileReader("admins.txt"));
                    String readLine;
                    while((readLine = readBuffAdmin.readLine()) != null) {
                        readLine = readLine.trim();
                        if (readLine.trim().isEmpty()) continue;
                        if(readLine.contains(enteredEmail)) {
                            String[] tokens = readLine.split("[,]+");
                            if(tokens.length != 7) continue;     //skipping malformed lines

                            String email = tokens[0];
                            String password = tokens[1];
                            String MAVID = tokens[2];
                            String firstname = tokens[3];
                            String lastname = tokens[4];
                            String gender = tokens[5];
                            String department = tokens[6];

                            User newAdmin = new Admin(email, password, MAVID, firstname, lastname, gender, department);

                            if(email.equals(enteredEmail) && password.equals(enteredPassword)) {
                                newAdmin.viewDashboard();
                            } else {
                                System.out.println("Sorry, your email or password is not correct. Please try again.");
                                SignIn(role);
                            }
                        }
                    }
                    readBuffAdmin.close();
                } catch (IOException e) { 
                    System.out.println("An error occurred while opening files.");
                    e.printStackTrace(); 
                }
                break;
            default:
                System.out.println("Invalid role specified.");
                break;
        }
    }

    public void SignUp(String role) {
        System.out.println("Please enter your email: ");
        String email = input.nextLine().trim().toLowerCase();
        //Verifying that the entered email is a UTA email
        if(!User.verifyUTADomain(email)) {
            System.out.println("Invalid UTA email domain. Please use a valid @mavs.uta.edu or @uta.edu email.");
            SignUp(role);
        }
        System.out.println("Please enter your password: ");
        String password = input.nextLine().trim();
        System.out.println("Please enter your MAVID: ");
        String MAVID = input.nextLine().trim();
        System.out.println("Please enter your first name: ");
        String firstname = input.nextLine().trim();
        System.out.println("Please enter your last name: ");
        String lastname = input.nextLine().trim();
        System.out.println("Please enter your gender: ");
        String gender = input.nextLine().trim().toLowerCase();

        //open a file to check credentials
        switch(role) {
            case "student":
                System.out.println("Enter your current location. Your options are:  ");
                for(CampusLocations location : CampusLocations.values()) {
                    System.out.println("- " + location);
                }
                String currentLocation = input.nextLine().trim().toUpperCase();
                int numberOfReports = 0;
                
                try (BufferedReader readBuffStudent = new BufferedReader(new FileReader("students.txt"));) 
                {
                    String ReadLine;
                    while((ReadLine = readBuffStudent.readLine()) != null) {
                        if(ReadLine.contains(email)) {
                            System.out.println("Invalid email. Account already exists.");
                            SignIn(role);
                            return;
                        }
                    }
                    try{
                        BufferedWriter writeBuffStudent = new BufferedWriter(new FileWriter("students.txt", true));
                        writeBuffStudent.write(email + "," + password + "," + MAVID + "," + firstname + "," + lastname + "," + gender + ","  + currentLocation + "," + numberOfReports + "\n");
                        writeBuffStudent.close();
                        System.out.println("Student account created successfully! You can now log in.");
                        SignIn(role);
                    } catch (IOException e1) {
                        System.out.println("An error occurred while creating the files.");
                        e1.printStackTrace();
                    }
                readBuffStudent.close();
                } catch (IOException e) { 
                    System.out.println("An error occurred while creating files.");
                    e.printStackTrace(); 
                }
                break;

            case "companion":
                boolean isApproved = false;
                boolean isTrained = false;
                boolean isAvailable = false;
                int walksCompleted = 0;
                try(BufferedReader readBuffCompanion = new BufferedReader(new FileReader("companions.txt"));)
                {
                    String ReadLine;
                    while((ReadLine = readBuffCompanion.readLine()) != null) {
                        if(ReadLine.contains(email)) {
                            System.out.println("Invalid email. Account already exists.");
                            SignIn(role);
                            return;
                        }
                    }
                    try{
                        BufferedWriter writeBuffCompanion = new BufferedWriter(new FileWriter("companions.txt", true));
                        writeBuffCompanion.write(email + "," + password + "," + MAVID + "," + firstname + "," + lastname + "," + gender + 
                                        "," + isApproved + "," + isTrained + "," + isAvailable + "," + walksCompleted + "\n");
                        writeBuffCompanion.close();
                        System.out.println("Companion account created successfully! You can now log in.");
                        SignIn(role);
                    } catch (IOException e1) {
                        System.out.println("An error occurred while creating the files.");
                        e1.printStackTrace();
                    }
                readBuffCompanion.close();
                } catch (IOException e) { 
                    System.out.println("An error occurred while creating files.");
                    e.printStackTrace(); 
                }
                break;

            case "admin":
                System.out.println("Enter the department that you work for.");
                System.out.println("Your options are: \n1. Management \n2. Police \n3. Security");
                String department = input.nextLine().trim().toLowerCase();
                if(department.equals("management") || department.equals("police") || department.equals("security")) { 
                 try (BufferedReader readBuffAdmin = new BufferedReader(new FileReader("admins.txt"));) {
                        String ReadLine;
                        while((ReadLine = readBuffAdmin.readLine()) != null) {
                            if(ReadLine.contains(email)) {
                                System.out.println("Invalid email. Account already exists.");
                                SignIn(role);
                                return;
                            }
                        }
                    
                        try(BufferedWriter writeBuffAdmin = new BufferedWriter(new FileWriter("admins.txt", true));)
                        {
                            writeBuffAdmin.write(email + "," + password + "," + MAVID + "," + firstname + "," + lastname + "," + gender + ","  + department + "\n");
                            writeBuffAdmin.close();
                            System.out.println("Admin account created successfully! You can now log in.");
                            SignIn(role);
                        } catch (IOException e1) {
                            System.out.println("An error occurred while creating the files.");
                            e1.printStackTrace();
                        }
                    readBuffAdmin.close();
                    } catch (IOException e) { 
                        System.out.println("An error occurred while creating files.");
                        e.printStackTrace(); 
                    }
                    
                }
                else{
                    System.out.println("Invalid."); 
                    return;
                }
                break;
                
            default:
                System.out.println("Invalid role specified.");
                return;
        }
    }
    
}

