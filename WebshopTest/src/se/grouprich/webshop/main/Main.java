package se.grouprich.webshop.main;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.PaymentException;
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.ECommerceIdGenerator;
import se.grouprich.webshop.idgenerator.IdGenerator;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;
import se.grouprich.webshop.service.ECommerceService;
import se.grouprich.webshop.service.validation.CustomerValidator;
import se.grouprich.webshop.service.validation.DuplicateValidator;
import se.grouprich.webshop.service.validation.PasswordValidator;
import se.grouprich.webshop.service.validation.ProductValidator;

public final class Main
{
	public static final void main(String[] args)
			throws CustomerRegistrationException, ProductRegistrationException, RepositoryException, OrderException, PaymentException
	{
		Repository<String, Product> fileProductRepository = new FileRepository<Product>(Product.class);
		Repository<String, Customer>  fileCustomerRepository = new FileRepository<Customer>(Customer.class);
		Repository<String, Order> fileOrderRepository = new FileRepository<Order>(Order.class);
		IdGenerator<String> idGenerator = new ECommerceIdGenerator();
		PasswordValidator eCommerceValidator = new CustomerValidator();
		DuplicateValidator productDuplicateValidator = new ProductValidator();
	
		ECommerceService eCommerceService = new ECommerceService(fileOrderRepository, fileCustomerRepository,
				fileProductRepository, idGenerator, eCommerceValidator, productDuplicateValidator);

		Customer customer1 = eCommerceService.createCustomer("arbieto@mail.com", "Arbieto12*", "Haydee", "DeAlvarado");
		Customer customer2 = eCommerceService.createCustomer("qqqq@mail.com", "Q#qq32", "hahaha", "hohoho");
		
		Product product1 = eCommerceService.createProduct("Shampoo", 20.00, 6);
		Product product2 = eCommerceService.createProduct("Treatment", 20.00, 10);
		Product product3 = eCommerceService.createProduct("Eco Shampoo", 30.00, 100);

		ShoppingCart shoppingCart1 = eCommerceService.createShoppingCart();

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
		
		System.out.println("What's in the shopping cart?: " + shoppingCart1);

		System.out.println();

		System.out.println("Total price: " + shoppingCart1.calculateTotalPrice() + " kr");	
		
		eCommerceService.createOrder(eCommerceService.checkOut(customer1, shoppingCart1));
		
		System.out.println("Order list: " + fileOrderRepository.getAll().toString());

		System.out.println();
		System.out.println("Stock quantity of " + product1.getProductName() + ": " + product1.getStockQuantity());
		System.out.println("Stock quantity of " + product2.getProductName() + ": " + product2.getOrderQuantity());

		System.out.println("customer: " + fileCustomerRepository.read(customer1.getId()));

		System.out.println();
		System.out.println("Before delete customer\n-----------------------");

		for (Customer customer : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer);
		}

		eCommerceService.deleteCustomer(customer1.getId());

		System.out.println();
		System.out.println("After delete customer\n-----------------------");
		for (Customer customer : fileCustomerRepository.getAll().values())
		{
			System.out.println(customer);
		}

		System.out.println();
	}
}