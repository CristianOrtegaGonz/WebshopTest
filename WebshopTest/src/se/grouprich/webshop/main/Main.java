package se.grouprich.webshop.main;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.PaymentException;
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.FileCustomerRepository;
import se.grouprich.webshop.repository.FileOrderRepository;
import se.grouprich.webshop.repository.FileProductRepository;
import se.grouprich.webshop.service.ECommerceService;

public final class Main
{

	public static final void main(String[] args)
			throws CustomerRegistrationException, ProductRegistrationException, RepositoryException, OrderException, PaymentException
	{
		FileProductRepository fileProductRepository = new FileProductRepository();
		FileCustomerRepository fileCustomerRepository = new FileCustomerRepository();
		FileOrderRepository fileOrderRepository = new FileOrderRepository();
		ECommerceService eCommerceService = new ECommerceService(fileOrderRepository, fileCustomerRepository, fileProductRepository);

		eCommerceService.registerCustomer("arbieto@mail.com", "arbieto", "Haydee", "DeAlvarado");
		eCommerceService.registerCustomer("qqqq@mail.com", "qqq", "hahaha", "hohoho");

		eCommerceService.registerProduct("Shampoo", 20.00, 6);
		eCommerceService.registerProduct("Treatment", 20.00, 10);
		eCommerceService.registerProduct("Eco Shampoo", 30.00, 100);

		Customer customer = eCommerceService.getCustomerByEmail("arbieto@mail.com");

		ShoppingCart shoppingCart1 = eCommerceService.makeShoppingCart();
		
		Order order = new Order(customer, shoppingCart1);

		System.out.println("Did " + customer.getName() + " pay?: " + order.isPayed());

		Product product1 = eCommerceService.getProductByName("Shampoo");
		Product product2 = eCommerceService.getProductByName("Treatment");

		System.out.println();
		System.out.println("Before update\n-----------------------");
		for (Product product : fileProductRepository.getAll().values())
		{
			System.out.println(product);
		}

		product1.setStockQuantity(50);

		System.out.println();
		System.out.println("After update in memory\n-----------------------");
		for (Product product : fileProductRepository.getAll().values())
		{
			System.out.println(product);
		}

		eCommerceService.uppdateProduct(product1.getProductId(), product1);

		System.out.println();
		System.out.println("After update in disk\n-----------------------");
		for (Product product : fileProductRepository.getAll().values())
		{
			System.out.println(product);
		}

		eCommerceService.addProductInShoppingCart(shoppingCart1, product1.getProductId(), 5);
		eCommerceService.addProductInShoppingCart(shoppingCart1, product2.getProductId(), 5);

		eCommerceService.changeOrderQuantity(shoppingCart1, product1.getProductId(), 2);

		System.out.println();
		System.out.println("Total price: " + eCommerceService.calculateTotalPrice(shoppingCart1) + " kr");

		eCommerceService.pay(order);

		System.out.println();
		System.out.println("Stock quantity of " + product1.getProductName() + ": " + product1.getStockQuantity());
		System.out.println("Stock quantity of " + product2.getProductName() + ": " + product2.getOrderQuantity());
		System.out.println("Did " + customer.getName() + " pay?: " + order.isPayed());

		System.out.println("customer: " + eCommerceService.getCustomer(customer.getCustomerId()));

		System.out.println();
		System.out.println("Before delete customer\n-----------------------");

		for (Customer customer1 : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer1);
		}

		eCommerceService.deleteCustomer(customer.getCustomerId());

		System.out.println();
		System.out.println("After delete customer\n-----------------------");
		for (Customer customer1 : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer1);
		}

		System.out.println();
	}
}
