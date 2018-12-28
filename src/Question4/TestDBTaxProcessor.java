package Question4;

import Question1.Item;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;

public class TestDBTaxProcessor {

    @Test
    public void testEstablishConnection() {
        DBTaxProcessor dtp = new DBTaxProcessor(false);
        Connection conn = dtp.establishConnection();
        assertNotNull(conn);
    }

    @Test
    public void testReaderWriter() {
        DBTaxProcessor dtp = new DBTaxProcessor(true);

        DBTaxProcessor.Reader reader = dtp.new Reader();
        DBTaxProcessor.Writer writer = dtp.new Writer();

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

        assertEquals(0, dtp.unprocessedItems.size());
        for (Item item : dtp.processedItems) {
            if (item.quantity != -1 && item.price != -1) {
                assertNotEquals(item.totalPrice, -1);
                assertNotEquals(item.tax, -1);
            }
        }
    }
}
