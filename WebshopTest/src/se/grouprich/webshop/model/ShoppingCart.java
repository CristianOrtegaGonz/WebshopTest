package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.grouprich.webshop.exception.OrderException;

public final class ShoppingCart implements Serializable
{
	private static final long serialVersionUID = 3865658878665558979L;
	private final List<Product> products;
	private double totalPrice;

	public ShoppingCart()
	{
		products = new ArrayList<>();
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public double getTotalPrice()
	{
		return totalPrice;
	}
	
	public void setTotalPrice(final double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public double calculateTotalPrice()
	{
		totalPrice = 0;
		for (Product product : products)
		{
			totalPrice += (product.getPrice() * product.getOrderQuantity());
		}
		return totalPrice;
	}
	
	public void addProductInShoppingCart(final Product product, final int orderQuantity) throws OrderException
	{
		products.add(product);
		product.setOrderQuantity(orderQuantity);
		calculateTotalPrice();
	}

	public void deleteOneProduct(final Product product) throws OrderException
	{
		if (!products.contains(product))
		{
			throw new OrderException("Product doesn't exsists.");
		}
		products.remove(product);
		calculateTotalPrice();
	}

	public void emptyShoppingCart(final List<Product> products)
	{
		products.removeAll(products);
		calculateTotalPrice();
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof ShoppingCart)
		{
			ShoppingCart otherShoppingCart = (ShoppingCart) other;
			return products.equals(otherShoppingCart.products) && totalPrice == otherShoppingCart.totalPrice;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result += products.hashCode() * 37;
		result += totalPrice *37;
		return result;
	}

	@Override
	public String toString()
	{
		return "ShoppingCart [products=" + products + ", totalPrice=" + totalPrice + "]";
	}
}
