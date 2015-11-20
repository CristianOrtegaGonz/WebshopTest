package se.grouprich.webshop.service.validation;

import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;

public final class ProductValidator implements DuplicateValidator
{
	private Repository<String, Product> productRepository;
	
	public ProductValidator()
	{
		productRepository = new FileRepository<>(Product.class);
	}
	
	@Override
	public boolean alreadyExists(final String productName)
	{
		for (Product product : productRepository.readAll().values())
		{
			if (product.getProductName().equals(productName))
			{
				return true;
			}
		}
		return false;
	}
}
