package org.example.Barnes;

import org.example.Barnes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class BarnesAndNobleTest {

    class FakeBookDatabase implements BookDatabase {
        private Map<String, Book> books = new HashMap<>();

        public void addBook(String isbn, Book book) {
            books.put(isbn, book);
        }

        @Override
        public Book findByISBN(String ISBN) {
            return books.get(ISBN);
        }
    }

    class FakeBuyBookProcess implements BuyBookProcess {
        public Book lastPurchasedBook = null;
        public int lastPurchasedAmount = 0;

        @Override
        public void buyBook(Book book, int amount) {
            lastPurchasedBook = book;
            lastPurchasedAmount = amount;
        }
    }

    @Test
    @DisplayName("specification-based: Test with valid input")
    public void testGetPriceForCartValid() {
        FakeBookDatabase fakeDB = new FakeBookDatabase();
        Book book1 = new Book("ISBN1", 10, 5);
        fakeDB.addBook("ISBN1", book1);

        FakeBuyBookProcess fakeProcess = new FakeBuyBookProcess();
        BarnesAndNoble barnes = new BarnesAndNoble(fakeDB, fakeProcess);

        Map<String, Integer> order = new HashMap<>();
        order.put("ISBN1", 3);

        PurchaseSummary summary = barnes.getPriceForCart(order);


        assertEquals(30, summary.getTotalPrice());

        assertTrue(summary.getUnavailable().isEmpty());
    }

    @Test
    @DisplayName("structural-based: when requested amount exceeds available")
    public void testGetPriceForCartExceedingQuantity() {

        FakeBookDatabase fakeDB = new FakeBookDatabase();
        Book book1 = new Book("ISBN1", 10, 2);
        fakeDB.addBook("ISBN1", book1);

        FakeBuyBookProcess fakeProcess = new FakeBuyBookProcess();
        BarnesAndNoble barnes = new BarnesAndNoble(fakeDB, fakeProcess);

        Map<String, Integer> order = new HashMap<>();
        order.put("ISBN1", 5);

        PurchaseSummary summary = barnes.getPriceForCart(order);

        assertEquals(20, summary.getTotalPrice());
        assertEquals(3, summary.getUnavailable().get(book1));
    }

    @Test
    @DisplayName("specification-based: Test getPriceForCart returns null when order is null")
    public void testGetPriceForCartNullOrder() {
        FakeBookDatabase fakeDB = new FakeBookDatabase();
        FakeBuyBookProcess fakeProcess = new FakeBuyBookProcess();
        BarnesAndNoble barnes = new BarnesAndNoble(fakeDB, fakeProcess);

        PurchaseSummary summary = barnes.getPriceForCart(null);


        assertNull(summary);
    }
}

