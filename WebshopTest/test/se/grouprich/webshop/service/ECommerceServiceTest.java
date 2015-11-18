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
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.IdGenerator;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.validation.DuplicateValidator;
import se.grouprich.webshop.service.validation.EmailValidator;
import se.grouprich.webshop.service.validation.PasswordValidator;
import se.grouprich.webshop.service.validation.ProductValidator;

@RunWith(MockitoJUnitRunner.class)
public class ECommerceServiceTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Mock(name = "orderRepository")
	private Repository<String, Order> orderRepositoryMock;
	@Mock(name = "customerRepository")
	private Repository<String, Customer> customerRepositoryMock;
	@Mock(name = "productRepository")
	private Repository<String, Product> productRepositoryMock;
	@Mock
	private IdGenerator<String> idGeneratorMock;
	@Mock
	private PasswordValidator passwordValidatorMock;
	private DuplicateValidator duplicateValidatorMock;
	private EmailValidator emailValidatorMock;
	private ECommerceService eCommerceService;
	private ProductValidator productValidatorMock;

	private String email = "aa@aa.com";
	private String password = "Aa12&";
	private String firstName = "Haydee";
	private String lastName = "Arbeito";
	private String id = "1002";
	private String productName = "Shampo";
	private double price = 50.00;
	private int stockQuantity = 10;
	private ShoppingCart shoppingCart;
	Customer customer; 
	Order order;

	@Before
	public void setup() throws CustomerRegistrationException
	{
		eCommerceService = new ECommerceService(orderRepositoryMock, customerRepositoryMock,
				                                productRepositoryMock, idGeneratorMock, passwordValidatorMock, productValidatorMock);
		customer = new Customer(id, email, password, firstName, lastName);
		shoppingCart = new ShoppingCart();
		order = new Order(id, customer, shoppingCart);	
	}
	
	@Test
	public void totalPriceShouldNotExceedSEK50000() throws ProductRegistrationException, OrderException, PaymentException, CustomerRegistrationException
	{
		exception.expect(PaymentException.class);
		exception.expectMessage(equalTo("We can not accept the total price exceeding SEK 50,000"));
		
		shoppingCart.setTotalPrice(50001.00);
		Order order = new Order(id, customer, shoppingCart);
		
		eCommerceService.createOrder(order);
	}

	@Test
	public void orderThatLacksOrderRowsShouldNotBeAccepted() throws CustomerRegistrationException, PaymentException, OrderException
	{
		exception.expect(OrderException.class);
		exception.expectMessage(equalTo("Shopping cart is empty"));
		
		ShoppingCart shoppingCart = new ShoppingCart();
		
		eCommerceService.checkOut(customer, shoppingCart);
	}

	@Test
	public void orderShouldHaveGotItsIdAssigned() throws CustomerRegistrationException, PaymentException, OrderException
	{
		Order order = new Order(id, customer, shoppingCart);
		when(idGeneratorMock.getGeneratedId()).thenReturn(id);
		
		eCommerceService.createOrder(order);
		
		assertEquals(id, order.getId());
		
		verify(idGeneratorMock).getGeneratedId();
	}

	@Test
	public void customerShouldNotHaveEmailAddressThatIsLongerThan30Characters() throws CustomerRegistrationException
	{
		exception.expect(CustomerRegistrationException.class);
		exception.expectMessage(equalTo("Email address that is longer than 30 characters is not allowed"));

		email = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aa.com";

		eCommerceService.createCustomer(email, password, firstName, lastName);
	}

	//Todo: en user minst ha ett losendord som innehåller mist ....
	
	
	@Test
	public void shouldFetchProductByID() throws ProductRegistrationException, RepositoryException
	{
		Product product1 = new Product(id, productName, price, stockQuantity);
		when(productRepositoryMock.read(id)).thenReturn(product1);
		
		Product product2 = eCommerceService.fetchProduct(id);
		
		assertEquals(product1, product2);
		
		verify(productRepositoryMock).read(id);
	}

	@Test
	public void shouldCreateProduct() throws ProductRegistrationException, RepositoryException
	{
		Product product1 = new Product(id, productName, price, stockQuantity);
		when(productValidatorMock.alreadyExsists(productName)).thenReturn(false);
		when(idGeneratorMock.getGeneratedId()).thenReturn(id);
		when(productRepositoryMock.create(product1)).thenReturn(product1);
		
		Product product2 = eCommerceService.createProduct(productName, price, stockQuantity);
		
		assertEquals(product1, product2);
		
		verify(productValidatorMock).alreadyExsists(productName);
		verify(idGeneratorMock).getGeneratedId();
		verify(productRepositoryMock).create(product1);
	}
	@Test
	public void shouldUpdateProduct()
	{
		
	}
	//ta bort en product
	
	@Test
	public void shouldFetchCustomerById() throws CustomerRegistrationException, RepositoryException
	{
		Customer customer1 = new Customer(id, email, password, firstName, lastName);
		when(customerRepositoryMock.read(id)).thenReturn(customer1);
		
		Customer customer2 = eCommerceService.fetchCustomer(id);
		
		assertEquals(customer1, customer2);
		
		verify(customerRepositoryMock).read(id);
	}
	//Todo hämta alla customer

	
	@Test
	public void shouldUpdateCustomer()
	{
		
	}	
	//ta bort en customer
		
	@Test
	public void shouldFetchOrderById() throws PaymentException, RepositoryException
	{
		Order order1 = new Order(id, customer, shoppingCart);
		when(orderRepositoryMock.read(id)).thenReturn(order1);

		Order order2 = eCommerceService.fetchOrder(id);

		assertEquals(order1, order2);

		verify(orderRepositoryMock).read(id);
	}
	//Todo hämta alla order
	//todo hämta alla order för en viss användare
	@Test
	public void shouldCreateOrder() throws CustomerRegistrationException, PaymentException
	{
		Customer customer = new Customer(id, email, password, firstName, lastName);
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setTotalPrice(200);
		Order order1 = new Order(id, customer, shoppingCart);
		when(idGeneratorMock.getGeneratedId()).thenReturn(id);
		when(orderRepositoryMock.create(order1)).thenReturn(order1);
		
		Order order2 = eCommerceService.createOrder(order1);
		
		assertEquals(order1, order2);
		
		verify(idGeneratorMock).getGeneratedId();
		verify(orderRepositoryMock).create(order1);
	}
	//Updatera en order
	//ta bort en order
		
}