package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.repository.file.CustomerFileInfo;

public final class FileCustomerRepository implements Repository<Customer, UUID>
{
	private final Map<UUID, Customer> customers;
	private CustomerFileInfo customerFileInfo;

	public FileCustomerRepository()
	{
		customers = new HashMap<>();
		customerFileInfo = new CustomerFileInfo();
		if ((customerFileInfo.getPath()).exists())
		{
			customerFileInfo.readFile(customers);
		}
	}

	@Override
	public void create(Customer customer)
	{
		customers.put(customer.getCustomerId(), customer);
		customerFileInfo.createFile(customers);
	}

	@Override
	public void delete(UUID customerId)
	{
		if (customers.containsKey(customerId))
		{
			customers.remove(customerId);
		}
		customerFileInfo.createFile(customers);
	}

	@Override
	public void uppdate(UUID customerId, Customer customer)
	{
		if (customers.containsKey(customerId))
		{
			customers.replace(customerId, customer);
		}
		customerFileInfo.createFile(customers);
	}

	@Override
	public Customer read(UUID customerId) throws RepositoryException
	{
		if (customers.containsKey(customerId))
		{
			return customers.get(customerId);
		}
		throw new RepositoryException("Customer with this id doesn't exists");
	}

	@Override
	public Map<UUID, Customer> getAll()
	{
		return customers;
	}
}