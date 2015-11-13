package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.repository.file.OrderFileInfo;

public final class FileOrderRepository implements Repository<Order, UUID>
{
	private final Map<UUID, Order> orders;
	private OrderFileInfo orderFileInfo;

	public FileOrderRepository()
	{
		orders = new HashMap<UUID, Order>();
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
	public void delete(UUID orderId)
	{
		if (orders.containsKey(orderId))
		{
			orders.remove(orderId);
		}
		orderFileInfo.createFile(orders);
	}

	@Override
	public void uppdate(UUID orderId, Order order)
	{
		if (orders.containsKey(orderId))
		{
			orders.replace(orderId, order);
		}
		orderFileInfo.createFile(orders);
	}

	@Override
	public Order read(UUID orderId) throws RepositoryException
	{
		if (orders.containsKey(orderId))
		{
			return orders.get(orderId);
		}
		throw new RepositoryException("Order with this id doesn't exists");
	}

	@Override
	public Map<UUID, Order> getAll()
	{
		return orders;
	}
}