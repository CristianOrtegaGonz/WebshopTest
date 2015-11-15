package se.grouprich.webshop.model;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.model.Customer;

public class CustomerTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	Customer customer1, customer2;

	@Test
	public void customerWithIdenticalValuesShouldBeEqual() throws CustomerRegistrationException
	{
		customer1 = new Customer("arbieto@gmail.com", "abcde", "Haydee", "Arbieto");
		customer2 = new Customer("arbieto@gmail.com", "abcde", "Haydee", "Arbieto");
		assertEquals("Two customers with same id and email should be equal", customer1, customer2);
	}

	@Test
	public void customerThatAreEqualShouldProduceSameHashCode() throws CustomerRegistrationException
	{
		customer1 = new Customer("isumi@hotmail.com", "aaaaa", "isumi", "lindabjala");
		customer2 = new Customer("isumi@hotmail.com", "aaaaa", "isumi", "lindabjala");

		assertEquals("Two customers with same email should be equal", customer1, customer2);
		assertEquals("Two customers that are equal shoul preoduce same hashCode", customer1.hashCode(), customer2.hashCode());
	}

	@Test
	public void customerShouldNotHaveUsernameThatIsLongerThan30Characters() throws CustomerRegistrationException
	{
		
	}

}
