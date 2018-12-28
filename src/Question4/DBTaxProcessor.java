package Question4;

import Question1.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Queue;
import java.util.LinkedList;

/*
+------------+-------------+------+-----+---------+-------+
| Field      | Type        | Null | Key | Default | Extra |
+------------+-------------+------+-----+---------+-------+
| name       | varchar(50) | YES  |     | NULL    |       |
| type       | varchar(20) | YES  |     | NULL    |       |
| price      | double      | YES  |     | 0       |       |
| quantity   | int(11)     | YES  |     | 0       |       |
| tax        | double      | YES  |     | 0       |       |
| totalPrice | double      | YES  |     | 0       |       |
+------------+-------------+------+-----+---------+-------+
 */

class DBTaxProcessor {

    Queue<Item> unprocessedItems;
    Queue<Item> processedItems;
    private boolean isComplete; // signals the writer to stop
    private boolean testMode;  // will not write to database if true
    private Connection conn = this.establishConnection();

    DBTaxProcessor(boolean testMode) {
        this.unprocessedItems = new LinkedList<>();
        this.processedItems = new LinkedList<>();
        this.isComplete = false;
        this.testMode = testMode;
    }

    Connection establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ODCEM", "root", "student");
        }
        catch (Exception e) {
            System.err.println("Could not establish connection. " + e);
            System.exit(1);
        }

        return null;
    }

    void display() {
        for (Item temp : this.processedItems) {
            temp.printOutput();
            System.out.println("----------");
        }
    }

    class Reader extends Thread {

        public void run() {

            System.out.println("Running Reader thread...");
            try {

                //collect all rows whose tax and total price have not been computed
                Statement stmt = conn.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT * from items WHERE tax = -1 AND totalPrice = -1;");
                while (rs != null && rs.next()) {
                    Item temp = new Item();
                    temp.name = rs.getString("name");
                    temp.type = rs.getString("type");
                    temp.price = rs.getDouble("price");
                    temp.quantity = rs.getInt("quantity");
                    temp.tax = rs.getDouble("tax");
                    temp.totalPrice = rs.getDouble("totalPrice");
                    unprocessedItems.add(temp);
                }
                isComplete = true; //signals the thread that unprocessedItems are loaded

                if (rs != null)
                    rs.close();

                stmt.close();
            }

            catch (Exception e) {
                System.err.println("91. Connection failed. " + e);
                System.exit(1);
            }
        }
    }

    class Writer extends Thread {

        public void run() {
            System.out.println("Running Writer thread...");

            try {

                Statement stmt = conn.createStatement();

                outer: while (!unprocessedItems.isEmpty() || !isComplete) {

                    while (unprocessedItems.isEmpty()) {
                        if (isComplete)
                            break outer;
                        Thread.sleep(16);  // wait for some unprocessed items
                    }

                    Item temp = unprocessedItems.remove();

                    if (temp.price == -1 || temp.quantity == -1) {
                        System.err.println("Insufficient data to compute tax for " + temp.name);
                    }
                    else if (temp.tax == -1 || temp.totalPrice == -1) {

                        //compute tax and write to db
                        temp.computeTax();
                        if (!testMode) {
                            stmt.executeUpdate("UPDATE items SET tax = " + temp.tax + " WHERE name = '" + temp.name + "';");
                            stmt.executeUpdate("UPDATE items SET totalPrice = " + temp.totalPrice + " WHERE name = '" + temp.name + "';");
                        }
                    }

                    processedItems.add(temp);
                }

                stmt.close();
            }

            catch (Exception e) {
                System.err.println("136. Connection failed. " + e);
                System.exit(1);
            }
        }
    }
}
