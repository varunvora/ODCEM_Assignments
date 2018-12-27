package Question1;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
        do {

            Item item = new Item();
            item.scanInput(args);
            if (item.verifyInput()) {
                item.computeTax();
                item.printOutput();
            }
            else {
                System.err.println("Quitting due to invalid input");
                break;
            }

        } while (moreItems());
    }

    private static boolean moreItems() {
        /* Asks if user wants to add more items and returns boolean based on response */

        System.out.print("Do you want to enter details of any other item (y/n): ");

        Scanner sc = new Scanner(System.in);
        char response = sc.next().charAt(0);
        sc.close();

        if (response != 'y' && response != 'n')
            System.err.println("Invalid response");



        return response == 'y';
    }
}
