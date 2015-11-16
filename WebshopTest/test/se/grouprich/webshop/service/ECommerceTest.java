package se.grouprich.webshop.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.PaymentException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.Repository;

@RunWith(MockitoJUnitRunner.class)
public class ECommerceTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock(name = "orderRepository")
	private Repository<String, Order> orderRepositoryMock;
	@Mock(name = "customerRepository")
	private Repository<String, Customer> customerRepositoryMock;
	@Mock(name = "productRepository")
	private Repository<String, Product> productRepositoryMock;
	private ECommerceService eCommerceService;
	
//	@InjectMocks <-- funkar inte. Debuggade. Den fattar inte vilken Repository är orderRepository. Det verkar som att den tar den första Mocken i alfabetisk ordning.
//	private ECommerceService eCommerceService;

	@Before
	public void setup()
	{
		eCommerceService = new ECommerceService(orderRepositoryMock, customerRepositoryMock, productRepositoryMock);
	}

	@Test
	public void customerShouldNotHaveEmailAddressThatIsLongerThan30Characters() throws CustomerRegistrationException
	{
		exception.expect(CustomerRegistrationException.class);
		exception.expectMessage(equalTo("Email address that is longer than 30 characters is not allowed"));

		String email = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aa.com";
		String password = "secret";
		String firstName = "Haydee";
		String lastName = "Arbieto";

		eCommerceService.registerCustomer(email, password, firstName, lastName);

		assertTrue(email.length() > 30);
	}

	@Test
	public void orderThatLacksOrderRowsShouldNotBeAccepted() throws CustomerRegistrationException, PaymentException, OrderException
	{
		exception.expect(OrderException.class);
		exception.expectMessage(equalTo("Shopping cart is empty"));

		Customer customer = new Customer("aa@aa.com", "secret", "Haydee", "Arbeito");
		ShoppingCart shoppingCart = new ShoppingCart();
		eCommerceService.checkOut(customer, shoppingCart);

		assertTrue(shoppingCart.getProducts().isEmpty());
	}

	@Test
	public void orderShouldHaveGotItsIdAssigned() throws CustomerRegistrationException, PaymentException, OrderException
	{
		Customer customer = new Customer("aa@aa.com", "secret", "Haydee", "Arbeito");
		ShoppingCart shoppingCart = new ShoppingCart();
		Order order = new Order(customer, shoppingCart);
		eCommerceService.pay(order);

		verify(orderRepositoryMock).create(order);
	}
}
