package Question1;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestItem {

    @Test
    public void testVerifyInput() {
        Item item = new Item();
        item.type = "";
        assertFalse(item.verifyInput());
        item.type = "abc";
        assertFalse(item.verifyInput());
        item.type = "raw";
        assertTrue(item.verifyInput());
    }

    @Test
    public void testComputeTax() {
        Item item = new Item();
        item.price = 100;
        item.quantity = 100;

        //test raw
        item.type = "raw";
        item.computeTax();
        assertEquals(item.tax, 12.5, 1.0E-06);
        assertEquals(item.totalPrice, 11250, 1.0E-06);

        //test manufactured
        item.type = "manufactured";
        item.computeTax();
        assertEquals(item.tax, 14.75, 1.0E-06);
        assertEquals(item.totalPrice, 11475, 1.0E-06);

        //test imported
        item.type = "imported";
        item.computeTax();
        assertEquals(item.tax, 20, 1.0E-06);
        assertEquals(item.totalPrice, 12000, 1.0E-06);
    }
}
