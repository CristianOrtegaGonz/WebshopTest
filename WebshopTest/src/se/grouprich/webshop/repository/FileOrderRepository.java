package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.repository.file.OrderFileInfo;

public final class FileOrderRepository implements Repository<String, Order>
{
	private final Map<String, Order> orders;
	private OrderFileInfo orderFileInfo;

	public FileOrderRepository()
	{
		orders = new HashMap<String, Order>();
		orderFileInfo = new OrderFileInfo();
		if ((orderFileInfo.getPath()).exists())
		{
			orderFileInfo.readFile(orders);
		}
	}

	@Override
	public void create(Order order)
	{
		orders.put(order.getOrderId(), order);
		orderFileInfo.createFile(orders);
	}

	@Override
	public void delete(String orderId)
	{
		if (orders.containsKey(orderId))
		{
			orders.remove(orderId);
		}
		orderFileInfo.createFile(orders);
	}

	@Override
	public void uppdate(String orderId, Order order)
	{
		if (orders.containsKey(orderId))
		{
			orders.replace(orderId, order);
		}
		orderFileInfo.createFile(orders);
	}

	@Override
	public Order read(String orderId) throws RepositoryException
	{
		if (orders.containsKey(orderId))
		{
			return orders.get(orderId);
		}
		throw new RepositoryException("Order with this id doesn't exists");
	}

	@Override
	public Map<String, Order> getAll()
	{
		return orders;
	}
}
