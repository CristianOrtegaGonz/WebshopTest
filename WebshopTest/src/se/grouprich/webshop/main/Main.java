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
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.ECommerceService;

public final class Main
{

	public static final void main(String[] args)
			throws CustomerRegistrationException, ProductRegistrationException, RepositoryException, OrderException, PaymentException
	{
		Repository<String, Product> fileProductRepository = new FileRepository<Product>(Product.class);
		Repository<String, Customer>  fileCustomerRepository = new FileRepository<Customer>(Customer.class);
		Repository<String, Order> fileOrderRepository = new FileRepository<Order>(Order.class);
		ECommerceService eCommerceService = new ECommerceService(fileOrderRepository, fileCustomerRepository, fileProductRepository);

		eCommerceService.registerCustomer("arbieto@mail.com", "arbieto", "Haydee", "DeAlvarado");
		eCommerceService.registerCustomer("qqqq@mail.com", "qqq", "hahaha", "hohoho");
		
		eCommerceService.registerProduct("Shampoo", 20.00, 6);
		eCommerceService.registerProduct("Treatment", 20.00, 10);
		eCommerceService.registerProduct("Eco Shampoo", 30.00, 100);

		Customer customer = eCommerceService.getCustomerByEmail("arbieto@mail.com");
		System.out.println("Haydee's id: " + customer.getId());
		ShoppingCart shoppingCart1 = eCommerceService.makeShoppingCart();

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

		eCommerceService.uppdateProduct(product1.getId(), product1);

		System.out.println();
		System.out.println("After update in disk\n-----------------------");
		for (Product product : fileProductRepository.getAll().values())
		{
			System.out.println(product);
		}

		eCommerceService.addProductInShoppingCart(shoppingCart1, product1.getId(), 5);
		eCommerceService.addProductInShoppingCart(shoppingCart1, product2.getId(), 5);

		eCommerceService.changeOrderQuantity(shoppingCart1, product1.getId(), 2);

		System.out.println();
		System.out.println("Total price: " + eCommerceService.calculateTotalPrice(shoppingCart1) + " kr");


		eCommerceService.pay(eCommerceService.checkOut(customer, shoppingCart1));
		
		
		System.out.println("Order list: " + eCommerceService.getOrders().toString());

		System.out.println();
		System.out.println("Stock quantity of " + product1.getProductName() + ": " + product1.getStockQuantity());
		System.out.println("Stock quantity of " + product2.getProductName() + ": " + product2.getOrderQuantity());

		System.out.println("customer: " + eCommerceService.getCustomer(customer.getId()));

		System.out.println();
		System.out.println("Before delete customer\n-----------------------");

		for (Customer customer1 : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer1);
		}

		eCommerceService.deleteCustomer(customer.getId());

		System.out.println();
		System.out.println("After delete customer\n-----------------------");
		for (Customer customer1 : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer1);
		}

		System.out.println();
		
		System.out.println("get order by customer id: " + eCommerceService.getOrderByCustomerID(customer.getId()));
	}
}
