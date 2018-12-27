package Question2;

import java.io.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        UserList users;

        String filename = "src/Question2/UserList.ser";
        users = deserialize(filename);

        if (users == null) {
            users = new UserList();
        }


        int choice;
        boolean isComplete = false;

        while (!isComplete) {
            System.out.println("Choose an operation (1-5)");
            System.out.println("1. Add User details.");
            System.out.println("2. Display User details.");
            System.out.println("3. Delete User details");
            System.out.println("4. Save User details.");
            System.out.println("5. Exit");
            System.out.print("Your choice: ");

            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch (choice) {
                case 1: users.addUserDetails();
                    break;
                case 2: users.displayUserDetails();
                    break;
                case 3: users.deleteUser();
                    break;
                case 4: serialize(users, filename);
                    break;
                case 5: isComplete = true;
                    break;
                default: System.err.println("Invalid choice. Try again.");
                    break;
            }
        }
    }

    private static void serialize(UserList users, String filename) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(users);
            System.out.println("Data saved to disk successfully.");
        }

        catch (IOException e) {
            System.err.println("An error occurred " + e);
            System.err.println("Could not serialize data.");
        }
    }

    static UserList deserialize(String filename) {

        UserList users = null;

        try
        {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            users = (UserList) in.readObject();

            in.close();
            file.close();
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        return users;
    }
}
