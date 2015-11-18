package se.grouprich.webshop.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.ECommerceService;
import se.grouprich.webshop.validation.Validator;

@RunWith(MockitoJUnitRunner.class)
public final class CustomerTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@SuppressWarnings("rawtypes")
	@Mock private Repository customerRepositoryMock;
	@Mock private Validator validatorMock;
	@InjectMocks
	Customer customer1, customer2;
	
	@InjectMocks private ECommerceService eCommerceService;
	
	private final String email = "arbieto@gmail.com";
	private final String password = "abcde";
	private final String firstName = "Haydee";
	private final String lastName = "Arbieto";

	@Test
	public void customerWithIdenticalValuesShouldBeEqual() throws CustomerRegistrationException
	{
		customer1 = new Customer(email, password, firstName, lastName);
		customer2 = new Customer(email, password, firstName, lastName);
		assertEquals("Two customers with same id and email should be equal", customer1, customer2);
	}

	@Test
	public void customerThatAreEqualShouldProduceSameHashCode() throws CustomerRegistrationException
	{
		customer1 = new Customer(email, password, firstName, lastName);
		customer2 = new Customer(email, password, firstName, lastName);

		assertEquals("Two customers with same email should be equal", customer1, customer2);
		assertEquals("Two customers that are equal shoul preoduce same hashCode", customer1.hashCode(), customer2.hashCode());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void customerShouldHaveSuitablePassword() throws CustomerRegistrationException
	{
		exception.expect(CustomerRegistrationException.class);
		exception.expectMessage("The password must contain at least a capital letter,"
				+ " two numbers and a special sign.");
		
		when(customerRepositoryMock.emailExists(email)).thenReturn(false);
		when(validatorMock.validatePassword(password)).thenReturn(false);
		
		eCommerceService.registerCustomer(email, password, firstName, lastName);
		
		verify(validatorMock).validatePassword(password);
	}

	@Test
	public void customerShouldNotHaveUsernameThatIsLongerThan30Characters() throws CustomerRegistrationException
	{
		
	}

}
