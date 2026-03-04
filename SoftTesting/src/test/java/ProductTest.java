import static org.junit.jupiter.api.Assertions.*;

import execise1.Product;
import org.junit.jupiter.api.Test;

public class ProductTest {

    // Test case
    // Single scenario that tests a specific behavior or functionality of a unit
    @Test
    public void reduceStock_validQuantity_updatesStockCorrectly() {

        //Arrange: Set up the initial conditions (initialize a Product object with 10 units in stock).
        Product product = new Product(1, "Laptop", 999.99, 10);

        //Act: Perform the action to test (reduce stock by 3).
        product.reduceStock(3);

        //Assert: Verify the result (stock should now be 7).
        // Compare the real output with the expected result
        assertEquals(7, product.getStockQuantity(),
                () -> "Stock quantity should be 7 after reducing 3 units from an initial 10.");
    }

    @Test
    public void reduceStock_insufficientQuantity_throwsException() {
        //Arrange
        Product product = new Product(1, "Laptop", 1000.0, 5);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(6), ()-> "Should throw an exception when stock falls below 0");
    }

}
