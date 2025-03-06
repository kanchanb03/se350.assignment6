package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.RegularCost;
import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

@DisplayName("Amazon Integration Tests")
public class AmazonIntegrationTest {

    static Database db;
    ShoppingCartAdaptor cart;
    Amazon amazon;

    @BeforeAll
    public static void initDb() {
        db = new Database();
    }

    @BeforeEach
    public void setUp() {
        db.resetDatabase();
        cart = new ShoppingCartAdaptor(db);
        amazon = new Amazon(cart, Arrays.asList(
                new RegularCost(),
                new ExtraCostForElectronics(),
                new DeliveryPrice()
        ));
    }

    @AfterAll
    public static void tearDown() {
        db.close();
    }

    @Test
    @DisplayName("specification-based: Calculate total price")
    public void testCalculateTotalPrice() {
        Item book = new Item(ItemType.OTHER, "Book", 2, 10.0);
        Item laptop = new Item(ItemType.ELECTRONIC, "Laptop", 1, 20.0);
        amazon.addToCart(book);
        amazon.addToCart(laptop);
        double total = amazon.calculate();
        assertEquals(52.5, total, 0.001);
    }

    @Test
    @DisplayName("structural-based: Adding an item increases count")
    public void testAddToCartIncreasesCount() {
        int countBefore = cart.getItems().size();
        amazon.addToCart(new Item(ItemType.OTHER, "Pen", 1, 2.0));
        int countAfter = cart.getItems().size();
        assertEquals(countBefore + 1, countAfter);
    }
}
