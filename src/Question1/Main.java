package Question1;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {

            int totalItems = 0;
            do {

                Item item = new Item();

                if ((totalItems == 0)) {
                    item.scanArgs(args);
                } else {
                    item.scanInput();
                }

                if (item.verifyInput()) {
                    item.computeTax();
                    item.printOutput();
                } else {
                    System.err.println("Quitting due to invalid input");
                    System.exit(1);
                }
                totalItems++;

            } while (moreItems());
    }

    private static boolean moreItems() {
        /* Asks if user wants to add more items and returns boolean based on response */

        System.out.print("Do you want to enter details of any other item? (y/n): ");

        Scanner sc = new Scanner(System.in);
        char response = sc.next().charAt(0);

        if (response != 'y' && response != 'n')
            System.err.println("Invalid response");

        return response == 'y';
    }
}
