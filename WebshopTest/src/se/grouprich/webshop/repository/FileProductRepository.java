package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.repository.file.ProductFileInfo;

public final class FileProductRepository implements Repository<String, Product>
{
	private final Map<String, Product> products;
	private ProductFileInfo productFileInfo;

	public FileProductRepository()
	{
		products = new HashMap<>();
		productFileInfo = new ProductFileInfo();
		if ((productFileInfo.getPath()).exists())
		{
			productFileInfo.readFile(products);
		}
	}

	@Override
	public void create(Product product)
	{
		products.put(product.getProductId(), product);
		productFileInfo.createFile(products);
	}

	@Override
	public void delete(String productId)
	{
		if (products.containsKey(productId))
		{
			products.remove(productId);
		}
		productFileInfo.createFile(products);
	}

	@Override
	public void uppdate(String productId, Product product)
	{
		if (products.containsKey(productId))
		{
			products.replace(productId, product);
		}
		productFileInfo.createFile(products);
	}

	@Override
	public Product read(String productId) throws RepositoryException
	{
		if (products.containsKey(productId))
		{
			return products.get(productId);
		}
		throw new RepositoryException("Product with this id doesn't exists");
	}

	@Override
	public Map<String, Product> getAll()
	{
		return products;
	}
}
