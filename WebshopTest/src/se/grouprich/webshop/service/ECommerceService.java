package se.grouprich.webshop.service;

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
import se.grouprich.webshop.service.validation.CustomerValidator;
import se.grouprich.webshop.service.validation.DuplicateValidator;
import se.grouprich.webshop.service.validation.PasswordValidator;
import se.grouprich.webshop.service.validation.ProductValidator;

public final class ECommerceService
{
	private final Repository<String, Order> orderRepository;
	private final Repository<String, Customer> customerRepository;
	private final Repository<String, Product> productRepository;
	private final IdGenerator<String> idGenerator;
	private final PasswordValidator passwordValidator;
	private final DuplicateValidator customerDuplicateValidator;
	private final DuplicateValidator productDuplicateValidator;

	public ECommerceService(Repository<String, Order> orderRepository, Repository<String, Customer> customerRepository, Repository<String, Product> productRepository,
			IdGenerator<String> idGenerator, PasswordValidator eCommerceValidator, DuplicateValidator productDuplicateValidator)
	{
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.idGenerator = idGenerator;
		this.passwordValidator = eCommerceValidator;
		this.productDuplicateValidator = productDuplicateValidator;
		customerDuplicateValidator = new CustomerValidator();
	}

	public Repository<String, Order> getOrderRepository()
	{
		return orderRepository;
	}

	public Repository<String, Customer> getCustomerRepository()
	{
		return customerRepository;
	}

	public Repository<String, Product> getProductRepository()
	{
		return productRepository;
	}

	public IdGenerator<String> getIdGenerator()
	{
		return idGenerator;
	}

	public PasswordValidator geteCommerceValidator()
	{
		return passwordValidator;
	}

	public DuplicateValidator getDuplicateValidator()
	{
		return customerDuplicateValidator;
	}

	public DuplicateValidator getProductDuplicateValidator()
	{
		return productDuplicateValidator;
	}

	public ShoppingCart createShoppingCart()
	{
		return new ShoppingCart();
	}

	// fixat så att det är lättare att läsa
	public Customer createCustomer(String email, String password, String firstName, String lastName) throws CustomerRegistrationException
	{
		if (customerDuplicateValidator.alreadyExsists(email))
		{
			throw new CustomerRegistrationException("Customer with E-mail: " + email + " already exists");
		}
		if (email.length() > 30)
		{
			throw new CustomerRegistrationException("Email address that is longer than 30 characters is not allowed");
		}
		if (!passwordValidator.isValidPassword(password))
		{
			throw new CustomerRegistrationException("Password must have at least an uppercase letter, two digits and a special character such as !@#$%^&*(){}[]");
		}
		String id = idGenerator.getGeneratedId();
		Customer customer = new Customer(id, email, password, firstName, lastName);
		return customerRepository.create(customer);
	}

	public Product createProduct(String productName, double price, int stockQuantity) throws ProductRegistrationException, RepositoryException
	{
		if (productDuplicateValidator.alreadyExsists(productName))
		{
			throw new ProductRegistrationException("Product with name: " + productName + " already exists");
		}
		String id = idGenerator.getGeneratedId();
		Product product = new Product(id, productName, price, stockQuantity);
		return productRepository.create(product);
	}
	
	public Order checkOut(Customer customer, ShoppingCart shoppingCart) throws OrderException
	{
		if (shoppingCart.getProducts().isEmpty())
		{
			throw new OrderException("Shopping cart is empty");
		}
		String id = null;
		return new Order(id, customer, shoppingCart);
	}
	
	public Order createOrder(Order order) throws PaymentException
	{
		if (order.getShoppingCart().getTotalPrice() > 50000.00)
		{
			throw new PaymentException("We can not accept the total price exceeding SEK 50,000");
		}
		order.pay();
		String id = idGenerator.getGeneratedId();
		order.setId(id);
		return orderRepository.create(order);
	}

	public void deleteCustomer(String customerId)
	{
		customerRepository.delete(customerId);
	}

	public void deleteOrder(String orderId)
	{
		orderRepository.delete(orderId);
	}

	public void deleteProduct(String productId)
	{
		productRepository.delete(productId);
	}

	public void uppdateCustomer(String customerId, Customer customer)
	{
		customerRepository.uppdate(customerId, customer);
	}

	public void uppdateOrder(String orderId, Order order)
	{
		orderRepository.uppdate(orderId, order);
	}

	public void uppdateProduct(String productId, Product product)
	{
		productRepository.uppdate(productId, product);
	}

	public Customer fetchCustomer(String customerId) throws RepositoryException
	{
		return customerRepository.read(customerId);
	}

	public Order fetchOrder(String orderId) throws RepositoryException
	{
		return orderRepository.read(orderId);
	}

	public Product fetchProduct(String productId) throws RepositoryException
	{
		return productRepository.read(productId);
	}

	public void addProductInShoppingCart(ShoppingCart shoppingCart, String productId, int orderQuantity) throws RepositoryException, OrderException
	{
		if (productRepository.getAll().containsKey(productId))
		{
			Product product = productRepository.read(productId);
			if (shoppingCart.getProducts().contains(product) && product.getStockQuantity() >= product.getOrderQuantity() + orderQuantity)
			{
				product.addOrderQuantity(orderQuantity);
			}
			else if (product.getStockQuantity() >= orderQuantity)
			{
				shoppingCart.addProductInShoppingCart(product, orderQuantity);
			}
			else
			{
				throw new OrderException("Stock quantity is: " + product.getStockQuantity());
			}
		}
		else
		{
			throw new OrderException("Product with id: " + productId + "doesn't exists");
		}
	}

	public void changeOrderQuantity(ShoppingCart shoppingCart, String productId, int orderQuantity) throws RepositoryException, OrderException
	{
		if (productRepository.getAll().containsKey(productId))
		{
			Product product = productRepository.read(productId);
			if (shoppingCart.getProducts().contains(product) && product.getStockQuantity() >= orderQuantity)
			{
				product.setOrderQuantity(orderQuantity);
				shoppingCart.calculateTotalPrice();
			}
			else
			{
				throw new OrderException("Stock quantity is: " + product.getStockQuantity());
			}
		}
	}
}
