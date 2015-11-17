package se.grouprich.webshop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.ShoppingCart;

public class OrderTest
{
	Order order1, order2;
	Customer customer1, customer2;
	ShoppingCart shoppingCart1, shoppingCart2;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
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