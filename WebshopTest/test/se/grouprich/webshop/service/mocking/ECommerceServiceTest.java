package se.grouprich.webshop.service.mocking;

import static org.hamcrest.Matchers.equalTo;
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
import se.grouprich.webshop.idgenerator.IdGenerator;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.*;
import se.grouprich.webshop.service.validation.DuplicateValidator;
import se.grouprich.webshop.service.validation.EmailValidator;
import se.grouprich.webshop.service.validation.PasswordValidator;

@RunWith(MockitoJUnitRunner.class)
public class ECommerceServiceTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Mock private DuplicateValidator duplicateValidatorMock;
	@Mock private EmailValidator emailValidatorMock;
	@Mock private PasswordValidator passwordValidatorMock;
	@Mock(name = "orderRepository")
	private Repository<String, Order> orderRepositoryMock;
	@Mock(name = "customerRepository")
	private Repository<String, Customer> customerRepositoryMock;
	@Mock(name = "productRepository")
	private Repository<String, Product> productRepositoryMock;
	@Mock private IdGenerator<String> idGeneratorMock;
	@InjectMocks private ECommerceService eCommerceService;
	
	private final String email = "aa@aa.com";
	private final String password = "Aa12&";
	private final String firstName = "Haydee";
	private final String lastName = "Arbeito";
	private final String id = "1002";

	@Test 
	public void customerShouldHavePasswordWithTwoVersalTwoNumbersSpecialCharacter() throws CustomerRegistrationException
	{
		exception.expect(CustomerRegistrationException.class);
		exception.expectMessage(equalTo("Password must have at least an uppercase letter"
					+ ", two digits and a special character such as !@#$%^&*(){}[]"));
		
		when(duplicateValidatorMock.alreadyExsists(email)).thenReturn(false);
		when(emailValidatorMock.isLengthWithinRange(email)).thenReturn(false);
		when(passwordValidatorMock.isValidPassword(password)).thenReturn(false);

		eCommerceService.createCustomer(email, password, firstName, lastName);
		
		verify(passwordValidatorMock).isValidPassword(password);
	}
	
	@Test
	public void shouldCreateCustomer() throws CustomerRegistrationException
	{
		Customer customer1 = new Customer(id, email, password, firstName, lastName);
		when(duplicateValidatorMock.alreadyExsists(email)).thenReturn(false);
		when(emailValidatorMock.isLengthWithinRange(email)).thenReturn(true);
		when(passwordValidatorMock.isValidPassword(password)).thenReturn(true);
		when(idGeneratorMock.getGeneratedId()).thenReturn(id);
		when(customerRepositoryMock.create(customer1)).thenReturn(customer1);
		
		Customer customer2 = eCommerceService.createCustomer(email, password, firstName, lastName);
		
		assertEquals(customer1, customer2);
		
		verify(passwordValidatorMock).isValidPassword(password);
		verify(idGeneratorMock).getGeneratedId();
		verify(customerRepositoryMock).create(customer1);	
	}

}
