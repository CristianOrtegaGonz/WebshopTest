package se.grouprich.webshop.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.model.Order;

public final class OrderFileInfo implements FileManager<Order>
{
	@Override
	public File getDirectory()
	{
		return new File("orders");
	}

	@Override
	public String getFileName()
	{
		return "orders";
	}

	@Override
	public String getFileExtension()
	{
		return ".data";
	}

	public File getPath()
	{
		return new File(getDirectory(), getFileName() + getFileExtension());
	}

	public void createDirectory()
	{
		File dir = getDirectory();
		File destination = new File(dir, getFileName() + getFileExtension());
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		if (!destination.exists())
		{
			try
			{
				destination.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createFile(Map<UUID, Order> orders)
	{
		createDirectory();
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getPath())))
		{
			out.writeObject(orders);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readFile(Map<UUID, Order> orders)
	{
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getPath())))
		{
			orders.putAll((Map<UUID, Order>) in.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new RuntimeException("Could not restore order repository", e);
		}
	}
}
