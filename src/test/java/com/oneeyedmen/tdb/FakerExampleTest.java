package com.oneeyedmen.tdb;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@SuppressWarnings("UnusedDeclaration")
public class FakerExampleTest {

    public static class Customer {
        private final Integer id;
        private final String firstName, lastName;
        private final Address address;
        private final List<Order> orders;

        public Customer(Integer id, String firstName, String lastName, Address address, List<Order> orders) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.orders = orders;
        }

        public Integer getId() { return id; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public Address getAddress() { return address; }
        public List<Order> getOrders() { return orders; }
    }

    public static class Address {
        private final Integer id;
        private final String line1, line2, line3, postcode;

        public Address(Integer id, String line1, String line2, String line3, String postcode){
            this.id = id;
            this.line1 = line1;
            this.line2 = line2;
            this.line3 = line3;
            this.postcode = postcode;
        }

        public Integer getId() { return id; }
        public String getLine1() { return line1; }
        public String getLine2() { return line2; }
        public String getLine3() { return line3; }
        public String getPostcode() { return postcode; }
    }

    public static class Order {
        private final Integer id;
        private final Customer customer;
        private final Address shippedTo;
        private final BigDecimal shippingCost;
        private final List<OrderItem> items;

        public Order(Integer id, Customer customer, Address shippedTo, BigDecimal shippingCost, List<OrderItem> items) {
            this.id = id;
            this.customer = customer;
            this.shippedTo = shippedTo;
            this.shippingCost = shippingCost;
            this.items = items;
        }

        public Integer getId() { return id; }
        public Customer getCustomer() { return customer; }
        public Address getShippedTo() { return shippedTo; }
        public BigDecimal getShippingCost() { return shippingCost; }
        public List<OrderItem> getItems() { return items; }
    }

    public static class OrderItem {
        private final Integer id;
        private final Order order;
        private final Product product;
        private final BigDecimal quantity;
        private final BigDecimal net;
        private final BigDecimal gross;
        private final String notes;

        public OrderItem(Integer id, Order order, Product product, BigDecimal quantity, BigDecimal net, BigDecimal gross, String notes) {
            this.id = id;
            this.order = order;
            this.product = product;
            this.quantity = quantity;
            this.net = net;
            this.gross = gross;
            this.notes = notes;
        }

        public Integer getId() { return id; }
        public Order getOrder() { return order; }
        public Product getProduct() { return product; }
        public BigDecimal getQuantity() { return quantity; }
        public BigDecimal getNet() { return net; }
        public BigDecimal getGross() { return gross; }
        public String getNotes() { return notes; }
    }

    public static class Product {
        private final Integer id;
        private final String description;

        public Product(Integer id, String description, Object ... andSoOnAndSoOn) {
            this.id = id;
            this.description = description;
        }

        public Integer getId() { return id; }
        public String getDescription() { return description; }
    }


    private Customer customer = new Faker<Customer>() {
        List<Order> orders = asList(new Faker<Order>() {
            Customer getCustomer() {
                return customer;
            }
        }.get());
    }.get();


    @Test public void fake_a_customer() {
        assertEquals("lastName", customer.getLastName());
        assertEquals("line1", customer.getAddress().getLine1());
        assertEquals(Integer.valueOf(42), customer.getOrders().get(0).getId());
        assertEquals("postcode", customer.getOrders().get(0).getShippedTo().getPostcode());
        assertEquals(customer, customer.getOrders().get(0).getCustomer());
        assertEquals("notes", customer.getOrders().get(0).getItems().get(0).getNotes());
    }
}
