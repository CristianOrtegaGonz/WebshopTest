package se.grouprich.webshop.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.model.Customer;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	Customer customer1, customer2;

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
