package se.grouprich.webshop.service.validation;

import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;

public class ProductValidator implements DuplicateValidator
{
	private Repository<String, Product> productRepository;
	
	public ProductValidator()
	{
		productRepository = new FileRepository<>(Product.class);
	}
	
	@Override
	public boolean alreadyExsists(String productName)
	{
		for (Product product : productRepository.getAll().values())
		{
			if (product.getProductName().equals(productName))
			{
				return true;
			}
		}
		return false;
	}
}
