package org.example.Amazon;



import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@DisplayName("Amazon Unit Tests")
public class AmazonUnitTest {

    ShoppingCart mockCart;
    PriceRule rule1;
    PriceRule rule2;
    Amazon amazon;

    @BeforeEach
    public void setUp() {
        mockCart = mock(ShoppingCart.class);
        rule1 = mock(PriceRule.class);
        rule2 = mock(PriceRule.class);

        List<Item> dummyItems = new ArrayList<>();
        dummyItems.add(new Item(null, "Dummy", 1, 10.0));
        when(mockCart.getItems()).thenReturn(dummyItems);

        amazon = new Amazon(mockCart, Arrays.asList(rule1, rule2));
    }

    @Test
    @DisplayName("specification-based: Calculate returns sum of rule prices")
    public void testCalculateSumOfRules() {
        when(rule1.priceToAggregate(anyList())).thenReturn(15.0);
        when(rule2.priceToAggregate(anyList())).thenReturn(25.0);

        double total = amazon.calculate();
        assertEquals(40.0, total, 0.001);
    }

    @Test
    @DisplayName("structural-based: addToCart calls cart.add")
    public void testAddToCartDelegation() {
        Item testItem = new Item(null, "TestItem", 1, 5.0);
        amazon.addToCart(testItem);
        verify(mockCart).add(testItem);
    }
}
