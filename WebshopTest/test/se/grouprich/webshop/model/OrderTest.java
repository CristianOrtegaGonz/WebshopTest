package se.grouprich.webshop.model;


import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.ECommerceService;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest
{
	Repository<String, Product> fileProductRepository = new FileRepository<Product>(Product.class);
	Repository<String, Customer>  fileCustomerRepository = new FileRepository<Customer>(Customer.class);
	Repository<String, Order> fileOrderRepository = new FileRepository<Order>(Order.class);
	ECommerceService eCommerceService = new ECommerceService(fileOrderRepository, fileCustomerRepository, fileProductRepository);

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void aOrderShouldNotBeMoreThan5000() throws ProductRegistrationException, OrderException
	{
		double totalPrice;
		ShoppingCart shoppingCart = new ShoppingCart();
		Product product1 = new Product("Treatment", 2000, 20);
		Product product2 = new Product("Shampoo", 5000, 3);
		shoppingCart.addProductInShoppingCart(product1);
		shoppingCart.addProductInShoppingCart(product2);
	
		shoppingCart = eCommerceService.makeShoppingCart();
		eCommerceService.calculateTotalPrice(shoppingCart);
		
		when(eCommerceService.calculateTotalPrice(shoppingCart)).thenThrow(new OrderException("An Order can not be over 50000"));
		
		//verify(eCommerceService.calculateTotalPrice(shoppingCart));
	}
}