package se.grouprich.webshop.model;

import static org.junit.Assert.*;

import org.junit.Test;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;

public class OrderTest
{
	private Order order1, order2;
	private Customer customer1, customer2;
	private ShoppingCart shoppingCart1, shoppingCart2;
	
	@Test
	public void orderWithIdenticalValuesShouldBeEqual() throws CustomerRegistrationException, OrderException
	{		
		customer1 = new Customer("1001", "cristian@gmail.com", "C01#", "Cristian", "Ortega");
		customer2 = new Customer("1001", "cristian@gmail.com", "C01#", "Cristian", "Ortega");
		
		shoppingCart1 = new ShoppingCart();
		shoppingCart2 = new ShoppingCart();
		
		order1 = new Order("100", customer1, shoppingCart1);
		order2 = new Order("100", customer2, shoppingCart2);
		
		assertEquals("Two orders with same product och total price should be equal", order1, order2); 
	}
	
	@Test
	public void orderThatAreEqualShouldProduceSameHashCode() throws CustomerRegistrationException, OrderException
	{
		customer1 = new Customer("1001", "cristian@gmail.com", "C01#", "Cristian", "Ortega");
		customer2 = new Customer("1001", "cristian@gmail.com", "C01#", "Cristian", "Ortega");
		
		shoppingCart1 = new ShoppingCart();
		shoppingCart2 = new ShoppingCart();
		
		order1 = new Order("100", customer1, shoppingCart1);
		order2 = new Order("100", customer2, shoppingCart2);		
		
		assertEquals("Two orders with same product och total price should be equal", order1, order2);
		assertEquals("Two orders that are equal should produce same hashCode", order1.hashCode(), order2.hashCode());
	}	
}