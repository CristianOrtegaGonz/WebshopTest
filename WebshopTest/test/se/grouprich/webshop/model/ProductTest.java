package se.grouprich.webshop.model;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import se.grouprich.webshop.exception.ProductRegistrationException;

public class ProductTest
{
	private Product product1, product2;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void productWithIdenticalValuesShouldBeEqual() throws ProductRegistrationException 
	{
		product1 = new Product("1001", "Shampoo", 200, 20);
		product2 = new Product("1001", "Shampoo", 200, 20);
		
		assertEquals("Two products with same product ID, name and price should be equal", product1, product2); 
	}
	
	@Test
	public void productThatAreEqualShouldProduceSameHashCode() throws ProductRegistrationException
	{
		product1 = new Product("1001", "Shampoo", 200, 20);
		product2 = new Product("1001", "Shampoo", 200, 20);		
		
		assertEquals("Two products with same product Id, name and price should be equal", product1, product2);
		assertEquals("Two products that are equal should produce same hashCode", product1.hashCode(), product2.hashCode());
	}
}
