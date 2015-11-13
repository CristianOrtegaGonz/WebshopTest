package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.PaymentException;

public final class Order implements Serializable
{
	private static final long serialVersionUID = 3380539865925002167L;
	private final UUID orderId;
	private List<Product> productsInShoppingCartList;
	private double totalPrice;
	private Customer customer;
	private boolean isPayed = false;

	public Order(Customer customer)
	{
		orderId = UUID.randomUUID();
		productsInShoppingCartList = new ArrayList<Product>();
		totalPrice = 0;
		this.customer = customer;
	}

	public UUID getOrderId()
	{
		return orderId;
	}

	public List<Product> getProductsInShoppingCartList()
	{
		return productsInShoppingCartList;
	}

	public Customer getCustomer()
	{
		return customer;
	}
	
	public void addProductInShoppingCart(Product productInShoppingCart)
	{
		productsInShoppingCartList.add(productInShoppingCart);
		calculateTotalPrice(productsInShoppingCartList);
	}

	public void deleteOneProduct(Product productInShoppingCart) throws OrderException
	{
		if (!productsInShoppingCartList.contains(productInShoppingCart))
		{
			throw new OrderException("Product doesn't exsists.");
		}
		productsInShoppingCartList.remove(productInShoppingCart);
	}

	public void emptyShoppingCart(List<Product> productInShoppingCart)
	{
		productInShoppingCart.removeAll(productInShoppingCart);
	}

	public double calculateTotalPrice(List<Product> productsInShoppingCart)
	{
		totalPrice = 0;
		for (Product product : productsInShoppingCart)
		{
			totalPrice = totalPrice + product.getPrice() * product.getOrderQuantity();
		}
		return totalPrice;
	}

	public void pay() throws PaymentException
	{
		if (customer.isLoggedIn())
		{
			isPayed = true;
			for (Product product : productsInShoppingCartList)
			{
				product.setStockQuantity(product.getStockQuantity() - product.getOrderQuantity());
			}
		}
		else
		{
			throw new PaymentException("You are not logged in. You must first log in to pay.");
		}
	}

	public boolean isPayed()
	{
		return isPayed;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Order)
		{
			Order otherShoppingCart = (Order) other;
			return orderId == otherShoppingCart.orderId && productsInShoppingCartList.equals(otherShoppingCart.productsInShoppingCartList);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += orderId.hashCode() * 37;
		result += productsInShoppingCartList.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return productsInShoppingCartList.toString();
	}

	public void setOrders(List<Product> orders)
	{
		this.productsInShoppingCartList = orders;
	}
}

