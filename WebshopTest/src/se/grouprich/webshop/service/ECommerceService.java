package se.grouprich.webshop.service;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.PaymentException;
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.Repository;

public final class ECommerceService
{
	private final Repository<String, Order> orderRepository;
	private final Repository<String, Customer> customerRepository;
	private final Repository<String, Product> productRepository;

	public ECommerceService(Repository<String, Order> orderRepository, Repository<String, Customer> customerRepository, Repository<String, Product> productRepository)
	{
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
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

	// fixat så att det är lättare att läsa
	public void registerCustomer(String email, String password, String firstName, String lastName) throws CustomerRegistrationException
	{
		for (Customer customer : customerRepository.getAll().values())
		{
			if (customer.getEmail().equals(email))
			{
				throw new CustomerRegistrationException("Customer with E-mail: " + email + " already exists");
			}
		}
		if (email.length() > 30)
		{
			throw new CustomerRegistrationException("Email address that is longer than 30 characters is not allowed");
		}		
		if (!checkPassword(password))
		{
//			ändrat message så att den visar vad som ska fixas tydligare
			throw new CustomerRegistrationException("Password must have at least an uppercase letter, two digits and a special character such as !@#$%^&*(){}[]");
		}
		Customer customer = new Customer(email, password, firstName, lastName);
		customerRepository.create(customer);
	}

	public void registerProduct(String productName, double price, int stockQuantity) throws ProductRegistrationException, RepositoryException
	{
		if (getProductByName(productName) == null)
		{
			Product product = new Product(productName, price, stockQuantity);
			productRepository.create(product);
		}
		else
		{
			throw new ProductRegistrationException("Product with name: " + productName + " already exists");
		}
	}

	public ShoppingCart makeShoppingCart()
	{
		return new ShoppingCart();
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

	public Customer getCustomer(String customerId) throws RepositoryException
	{
		return customerRepository.read(customerId);
	}

	public Order getOrder(String orderId) throws RepositoryException
	{
		return orderRepository.read(orderId);
	}

	public Product getProduct(String productId) throws RepositoryException
	{
		return productRepository.read(productId);
	}

	public void addProductInShoppingCart(ShoppingCart shoppingCart, String productId, int orderQuantity) throws RepositoryException, OrderException
	{
		if (productRepository.getAll().containsKey(productId))
		{
			Product product = productRepository.read(productId);
			if (shoppingCart.getProducts().contains(product))
			{
				product.addOrderQuantity(orderQuantity);
				return;
			}
			if (product.getStockQuantity() >= orderQuantity)
			{
				shoppingCart.addProductInShoppingCart(product);
				product.setOrderQuantity(orderQuantity);
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
			}
			else
			{
				throw new OrderException("Stock quantity is: " + product.getStockQuantity());
			}
		}
	}

	public double calculateTotalPrice(ShoppingCart shoppingCart)
	{
		double totalPrice = shoppingCart.calculateTotalPrice(shoppingCart.getProducts());
		return totalPrice;
	}

	public Order checkOut(Customer customer, ShoppingCart shoppingCart) throws OrderException
	{
		if (shoppingCart.getProducts().isEmpty())
		{
			throw new OrderException("Shopping cart is empty");
		}
		return new Order(customer, shoppingCart);
	}

	public void pay(Order order) throws PaymentException
	{
		if (order.getShoppingCart().getTotalPrice() > 50000.00)
		{
			throw new PaymentException("We can not accept the total price exceeding SEK 50,000");
		}
		order.pay();
		orderRepository.create(order);
	}

	public Product getProductByName(String productName) throws RepositoryException
	{
		for (Product product : productRepository.getAll().values())
		{
			if (product.getProductName().equals(productName))
			{
				return product;
			}
		}
		return null;
	}

	public Customer getCustomerByEmail(String email) throws RepositoryException
	{
		for (Customer customer : customerRepository.getAll().values())
		{
			if (customer.getEmail().equals(email))
			{
				return customer;
			}
		}
		return null;
	}

	public Order getOrderByCustomerID(String customerId) throws RepositoryException
	{
		for (Order order : orderRepository.getAll().values())
		{
			if (order.getCustomer().getId().equals(customerId))
			{
				return order;
			}
		}
		return null;
	}

	//kankse bör vi inte acceptera svenska tecken för password annars finns det risk att man inte kan logga in med tangentbord som saknar svenska
	private boolean checkPassword(String password)
	{
		if (password == null || password.trim().length() == 0)
		{
			return false;
		}

		boolean digits = false;
		boolean versal = false;
		boolean specialCharacter = false;
		int counterNumbers = 0;

		for (int i = 0; i < password.length(); i++)
		{
			// check that in password contains only letters, numbers and
			// acceptable special characters
			if (password.substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				// check for all decimal digits (0-9)
				if (password.substring(i, i + 1).matches("\\d+"))
				{
					counterNumbers++;

					if (counterNumbers >= 2)
					{
						digits = true;
					}
				}

				// check an uppercase letter
				if (password.substring(i, i + 1).matches("[A-ZÅÄÖ]+"))
				{
					versal = true;
				}

				// Special characters control
				if (password.substring(i, i + 1).matches("\\p{Punct}+"))
				{
					specialCharacter = true;
				}
			}
			else
			{
				return false;
			}
		}
		return (digits && versal && specialCharacter);
	}
}
