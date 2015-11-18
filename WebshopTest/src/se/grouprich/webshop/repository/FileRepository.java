package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.Identifiable;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.repository.file.ECommerceFileInfo;
import se.grouprich.webshop.repository.file.FileManager;

public final class FileRepository<T extends Identifiable<String>> implements Repository<String, T>
{
	private final Map<String, T> values;
	private final FileManager<String, T> fileInfo;

	public FileRepository(Class<T> classType)
	{
		values = new HashMap<>();
		fileInfo = new ECommerceFileInfo<>(classType);
		if ((fileInfo.getPath()).exists())
		{
			fileInfo.readFile(values);
		}
	}

	@Override
	public T create(T value)
	{
		values.put(value.getId(), value);
		fileInfo.createFile(values);
		return value;
	}

	@Override
	public void delete(String id)
	{
		if (values.containsKey(id))
		{
			values.remove(id);
		}
		fileInfo.createFile(values);
	}

	@Override
	public void uppdate(String id, T value)
	{
		if (values.containsKey(id))
		{
			values.replace(id, value);
		}
		fileInfo.createFile(values);
	}

	@Override
	public T read(String id) throws RepositoryException
	{
		if (values.containsKey(id))
		{
			return values.get(id);
		}
		throw new RepositoryException("Id does not exists");
	}

	@Override
	public Map<String, T> getAll()
	{
		return values;
	}
}