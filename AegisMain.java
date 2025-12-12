/* The beginning of the Aegis System. */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AegisMain {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        //creating .txt files according to user roles to store info 
        try{ 
            BufferedWriter buffStudent = new BufferedWriter(new FileWriter("students.txt", true));
            BufferedWriter buffCompanion = new BufferedWriter(new FileWriter("companions.txt", true));
            BufferedWriter buffAdmin = new BufferedWriter(new FileWriter("admins.txt", true));

            buffStudent.close();
            buffCompanion.close();
            buffAdmin.close();

        } catch (IOException e){
            System.out.println("An error occurred while creating files.");
            e.printStackTrace();
        }

        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("*                               Welcome to Aegis!                             *");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n");
        System.out.println("What best describes your role (Student, Companion, Admin)? ");
        String role = input.nextLine().trim().toLowerCase();

        if(role.equals("student") || role.equals("companion") || role.equals("admin")) {
            //proceed to login page
            System.out.println("Do you already have an account (Y/N) ");
            String answer = input.nextLine().trim().toUpperCase();
            char ans = answer.charAt(0);
            LogIn login = new LogIn();

            if(ans == 'Y') {
                //Log into an existing account
                login.SignIn(role);
            } else if(ans == 'N') {
                //Create a new account
                login.SignUp(role);
            } else {
                System.out.println("Your choice is invalid. Please enter a valid choice.");
                return;
            }
        } else {
            System.out.println("Please enter a valid role. Make sure your spelling is correct.");
            return;
        }
    }
}
