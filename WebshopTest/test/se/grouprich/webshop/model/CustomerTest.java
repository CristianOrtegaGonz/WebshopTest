package se.grouprich.webshop.model;

import static org.junit.Assert.*;

import org.junit.Test;

import se.grouprich.webshop.exception.CustomerRegistrationException;

public class CustomerTest
{
	private Customer customer1, customer2;

	@Test
	public void customerWithIdenticalValuesShouldBeEqual() throws CustomerRegistrationException 
	{
		customer1 = new Customer("1000", "arbieto@gmail.com", "H01&", "Haydee", "Arbieto");
		customer2 = new Customer("1000", "arbieto@gmail.com", "H01&", "Haydee", "Arbieto");	
		
		assertEquals("Two customers with same id and email should be equal", customer1, customer2); 
	}
	
	@Test
	public void customerThatAreEqualShouldProduceSameHashCode() throws CustomerRegistrationException
	{
		customer1 = new Customer("1001", "isumi@hotmail.com", "I55€", "isumi", "lindabjala");
		customer2 = new Customer("1001", "isumi@hotmail.com", "I55€", "isumi", "lindabjala");
		
		assertEquals("Two customers with same email should be equal", customer1, customer2);
		assertEquals("Two customers that are equal should produce same hashCode", customer1.hashCode(), customer2.hashCode());
	}	
}
