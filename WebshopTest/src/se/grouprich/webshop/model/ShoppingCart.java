package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.grouprich.webshop.exception.OrderException;

public final class ShoppingCart implements Serializable
{
	private static final long serialVersionUID = 3865658878665558979L;
	private List<Product> products;
	private double totalPrice;

	public ShoppingCart()
	{
		products = new ArrayList<>();
		totalPrice = 0;
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public double getTotalPrice()
	{
		return totalPrice;
	}

	public double calculateTotalPrice(List<Product> products)
	{
		totalPrice = 0;
		for (Product product : products)
		{
			totalPrice = totalPrice + product.getPrice() * product.getOrderQuantity();
		}
		return totalPrice;
	}

	public void addProductInShoppingCart(Product product)
	{
		products.add(product);
		calculateTotalPrice(products);
	}

	public void deleteOneProduct(Product product) throws OrderException
	{
		if (!products.contains(product))
		{
			throw new OrderException("Product doesn't exsists.");
		}
		products.remove(product);
	}

	public void emptyShoppingCart(List<Product> products)
	{
		products.removeAll(products);
	}
}