package se.grouprich.webshop.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

	@InjectMocks
	ECommerceService eCommerceService;

	@Test
	public void customerShouldNotHaveUsernameThatIsLongerThan30Characters() throws CustomerRegistrationException
	{
		exception.expect(CustomerRegistrationException.class);
		exception.expectMessage(equalTo("You can't have a name that is longer than 30 characters"));

		String email = "aa@aa.com";
		String password = "secret";
		String firstName = "Haydeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
		String lastName = "Arbietoooooooooooooooooooooooooooooooo";

		eCommerceService.registerCustomer(email, password, firstName, lastName);

		assertTrue(firstName.length() > 30);
		assertTrue(lastName.length() > 30);
	}

	@Test
	public void orderThatLacksOrderRowsShouldNotBeAccepted() throws CustomerRegistrationException, PaymentException, OrderException
	{
		exception.expect(OrderException.class);
		exception.expectMessage(equalTo("Shopping cart is empty"));
		
		Customer customer = new Customer("aa@aa.com", "secret", "Haydee", "Arbeito");
		ShoppingCart shoppingCart = new ShoppingCart();
		eCommerceService.pay(customer, shoppingCart);
		
		assertTrue(shoppingCart.getProducts().isEmpty());
	}
}
