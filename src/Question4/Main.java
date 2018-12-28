package Question4;

public class Main {
    public static void main(String[] args) {

        DBTaxProcessor dbObj = new DBTaxProcessor(false);

        DBTaxProcessor.Reader reader = dbObj.new Reader();
        DBTaxProcessor.Writer writer = dbObj.new Writer();

        reader.start();
        writer.start();

        try {
            reader.join();
            writer.join();
        }
        catch (Exception e) {
            System.err.println("Could not join threads. " + e);
            System.exit(1);
        }

        dbObj.display();

    }
}
