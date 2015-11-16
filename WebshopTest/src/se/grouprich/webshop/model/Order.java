package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.UUID;

import se.grouprich.webshop.exception.PaymentException;
import se.grouprich.webshop.idgenerator.Identifiable;

public final class Order implements Serializable, Identifiable<String>
{
	private static final long serialVersionUID = 3380539865925002167L;
	private String orderId;
	private final ShoppingCart shoppingCart;
	private Customer customer;
	private boolean isPayed = false;

	public Order(Customer customer, ShoppingCart shoppingCart)
	{
		orderId = null;
		this.customer = customer;
		this.shoppingCart = shoppingCart;
	}
	
	@Override
	public String getId()
	{
		return orderId;
	}

	@Override
	public void setId(String orderId)
	{
		this.orderId = orderId;
	}

	public ShoppingCart getShoppingCart()
	{
		return shoppingCart;
	}
	
	public Customer getCustomer()
	{
		return customer;
	}

	public void pay() throws PaymentException
	{
		isPayed = true;
		for (Product product : shoppingCart.getProducts())
		{
			product.setStockQuantity(product.getStockQuantity() - product.getOrderQuantity());
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
			Order otherOrder = (Order) other;
			return orderId == otherOrder.orderId && shoppingCart.equals(otherOrder.shoppingCart);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += orderId.hashCode() * 37;
		result += shoppingCart.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return shoppingCart.toString();
	}
}
