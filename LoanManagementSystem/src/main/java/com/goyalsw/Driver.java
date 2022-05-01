package main.java.com.goyalsw;

import main.java.com.goyalsw.services.LoanManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Driver {
    private static final LoanManager manager = LoanManager.getLoanManagerInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the path for the text file to read input from: ");
        String pathName = sc.next();

        try{
            File file = new File(pathName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while(line!=null) {
                String message = manager.getMessageFromOperation(line);
                if(!Objects.equals(message, ""))
                    System.out.println(message);

                line = bufferedReader.readLine();
            }

            fileReader.close();
        } catch(Exception ex) {
            System.out.println("Message: " + ex.getMessage() + "\n StackTrace: " + Arrays.toString(ex.getStackTrace()));
        }
    }
}
