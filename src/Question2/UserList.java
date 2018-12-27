package Question2;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class UserList implements Serializable {
        ArrayList<User> list;

    UserList() {
        list = new ArrayList<>();
    }

    private boolean setValidInput(User tempUser, String attribute, char datatype) {
        /* return true if the input was not set else false */

        String errorString = "Invalid input";
        Class<?> c = tempUser.getClass();
        Field f;
        try {
            f = c.getDeclaredField(attribute);
            f.setAccessible(true);

            Scanner sc = new Scanner(System.in).useDelimiter("\n");
            switch (datatype) {
                case 'd': //integer
                    if (sc.hasNextInt()) {
                        f.set(tempUser, sc.nextInt());
                        return false;
                    } else {
                        System.err.println(errorString);
                    }
                    break;
                case 's': //string
                    if (sc.hasNext()) {
                        f.set(tempUser, sc.next());
                        return false;
                    } else {
                        System.err.println(errorString);
                    }
                    break;
                case 'S': //string array
                    if (sc.hasNext()) {
                        f.set(tempUser, sc.next().split(" "));
                        return false;
                    } else {
                        System.err.println(errorString);
                    }
                    break;
                default: break;
            }
        }

        catch (NoSuchFieldException ex) {
            System.err.println("Invalid attribute");
        }

        catch (IllegalAccessException ex) {
            System.err.println("Illegal access");
        }

        return true;
    }

    void addUserDetails() {
        User newUser = new User();

        System.out.print("Enter full name: ");
        if (setValidInput(newUser, "fullName", 's')) return;

        System.out.print("Enter age: ");
        if (setValidInput(newUser, "age", 'd')) return;

        System.out.print("Enter address: ");
        if (setValidInput(newUser, "address", 's')) return;

        System.out.print("Enter roll no: ");
        if (setValidInput(newUser, "rollNumber", 'd')) return;

        System.out.print("Enter course separated by space: ");
        if (setValidInput(newUser, "courses", 'S')) return;

        if (newUser.fullName.trim().equals("") || newUser.age <= 0 || newUser.rollNumber <= 0) {
            System.err.println("Invalid input");
            return;
        }

        for (User i : this.list)
            if (i.rollNumber == newUser.rollNumber) {
                System.err.println("User with this Roll Number already exists.");
                return;
            }

        this.list.add(newUser);
        this.list.sort(Comparator.comparing(User::getRollNumber));
        this.list.sort(Comparator.comparing(User::getFullName));
    }

    void displayUserDetails() {


        if (this.list.size() == 0) {
            System.out.println("No data so far.");
            return;
        }

        @SuppressWarnings("unchecked")
        ArrayList<User> clonedList = (ArrayList <User>)this.list.clone();

        System.out.println("How would you like to sort the data? (1-4)");
        System.out.println("1. By Full Name");
        System.out.println("2. By Roll Number");
        System.out.println("3. By Age");
        System.out.println("4. By Address");
        System.out.print("Your choice: ");

        int choice;
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
        }
        else {
            System.err.println("Invalid input");
            return;
        }

        switch (choice) {
            case 1: clonedList.sort(Comparator.comparing(User::getFullName));
                break;
            case 2: clonedList.sort(Comparator.comparing(User::getRollNumber));
                break;
            case 3: clonedList.sort(Comparator.comparing(User::getAge));
                break;
            case 4: clonedList.sort(Comparator.comparing(User::getAddress));
                break;
            default: System.err.println("Invalid input");
                break;
        }

        String header = "Name\t\tRoll\tAge\t\tAddress\t\t\tCourses";
        System.out.println(header);
        for (User i : clonedList) System.out.println(i);
    }

    void deleteUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter roll number of user to delete: ");
        int rollNumber;
        if (sc.hasNextInt()) {
            rollNumber = sc.nextInt();
        }
        else {
            System.err.println("Invalid input");
            return;
        }
        boolean exist = false;

        for (User i : this.list)
            if (i.rollNumber == rollNumber) {
                exist = true;
                this.list.remove(i);
                break;
            }

        if (!exist) System.err.println("User with given roll number does not exist");
    }


    class User implements Serializable {
        String fullName;
        String address;
        int rollNumber;
        int age;
        String[] courses = new String[6];

        public String toString() {
            return  String.join("\t\t",
                    this.fullName,
                    "" + this.rollNumber,
                    "" + this.age,
                    this.address,
                    String.join(", ", this.courses));
        }

        int getAge() {
            return this.age;
        }

        String getFullName() {
            return this.fullName;
        }

        String getAddress() {
            return this.address;
        }

        int getRollNumber() {
            return this.rollNumber;
        }
    }
}
