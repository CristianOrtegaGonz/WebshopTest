package se.grouprich.webshop.service;

import java.util.UUID;

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
	private final Repository<UUID, Order> orderRepository;
	private final Repository<UUID, Customer> customerRepository;
	private final Repository<UUID, Product> productRepository;

	public ECommerceService(Repository<UUID, Order> orderRepository, Repository<UUID, Customer> customerRepository, Repository<UUID, Product> productRepository)
	{
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}

	public Repository<UUID, Order> getOrderRepository()
	{
		return orderRepository;
	}

	public Repository<UUID, Customer> getCustomerRepository()
	{
		return customerRepository;
	}

	public Repository<UUID, Product> getProductRepository()
	{
		return productRepository;
	}

	public void registerCustomer(String email, String password, String firstName, String lastName) throws CustomerRegistrationException
	{
		for (Customer customer : customerRepository.getAll().values())
		{
			if (customer.getEmail().equals(email))
			{
				throw new CustomerRegistrationException("Customer with E-mail: " + email + " already exists");
			}
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

	public void deleteCustomer(UUID customerId)
	{
		customerRepository.delete(customerId);
	}

	public void deleteOrder(UUID orderId)
	{
		orderRepository.delete(orderId);
	}

	public void deleteProduct(UUID productId)
	{
		productRepository.delete(productId);
	}

	public void uppdateCustomer(UUID customerId, Customer customer)
	{
		customerRepository.uppdate(customerId, customer);
	}

	public void uppdateOrder(UUID orderId, Order order)
	{
		orderRepository.uppdate(orderId, order);
	}

	public void uppdateProduct(UUID productId, Product product)
	{
		productRepository.uppdate(productId, product);
	}

	public Customer getCustomer(UUID customerId) throws RepositoryException
	{
		return customerRepository.read(customerId);
	}

	public Order getOrder(UUID orderId) throws RepositoryException
	{
		return orderRepository.read(orderId);
	}

	public Product getProduct(UUID productId) throws RepositoryException
	{
		return productRepository.read(productId);
	}

	public void addProductInShoppingCart(ShoppingCart shoppingCart, UUID productId, int orderQuantity) throws RepositoryException, OrderException
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

	public void changeOrderQuantity(ShoppingCart shoppingCart, UUID productId, int orderQuantity) throws RepositoryException, OrderException
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

	public void pay(Order order) throws PaymentException
	{
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

	public Order getOrderByCustomerID(UUID customerId) throws RepositoryException
	{
		for (Order order : orderRepository.getAll().values())
		{
			if (order.getCustomer().getCustomerId().equals(customerId))
			{
				return order;
			}
		}
		return null;
	}
}