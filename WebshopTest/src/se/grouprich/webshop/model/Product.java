package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.UUID;

import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.ProductRegistrationException;

public final class Product implements Serializable
{
	private static final long serialVersionUID = 5072511887999675702L;
	private final UUID productId;
	private String productName;
	private double price;
	private int stockQuantity;
	private int orderQuantity;

	public Product(String productName, double price, int stockQuantity) throws ProductRegistrationException
	{
		productId = UUID.randomUUID();
		this.productName = productName;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public UUID getProductId()
	{
		return productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public int getStockQuantity()
	{
		return stockQuantity;
	}

	public double getPrice()
	{
		return price;
	}

	public void setStockQuantity(int stockQuantity)
	{
		this.stockQuantity = stockQuantity;
	}

	public void setOrderQuantity(int orderQuantity) throws OrderException
	{
		if (stockQuantity >= orderQuantity)
		{
			this.orderQuantity = orderQuantity;
		}
		else
		{
			throw new OrderException("Stock quantity is: " + stockQuantity);
		}
	}

	public void addOrderQuantity(int orderQuantity) throws OrderException
	{
		if (stockQuantity >= this.orderQuantity + orderQuantity)
		{
			this.orderQuantity = this.orderQuantity + orderQuantity;
		}
		else
		{
			throw new OrderException("Stock quantity is: " + stockQuantity);
		}
	}

	public int getOrderQuantity()
	{
		return orderQuantity;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Product)
		{
			Product otherProduct = (Product) other;
			return productId.equals(otherProduct.productId);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += productId.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return productName + "(" + productId + ") ---- Price: " + price + " kr ---- Stock quantity: " + stockQuantity;
	}
}

