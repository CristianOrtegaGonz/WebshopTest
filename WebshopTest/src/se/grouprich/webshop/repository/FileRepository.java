package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.Identifiable;
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
	public T create(final T value)
	{
		values.put(value.getId(), value);
		fileInfo.createFile(values);
		return value;
	}

	@Override
	public T delete(final String id) throws RepositoryException
	{
		T deletedValue = null;
		if (values.containsKey(id))
		{
			deletedValue = values.get(id);
			values.remove(id);
			fileInfo.createFile(values);
			return deletedValue;
		}
		throw new RepositoryException("Id does not exists");	
	}

	@Override
	public T update(final String id, final T value) throws RepositoryException
	{
		if (values.containsKey(id))
		{
			values.replace(id, value);
			fileInfo.createFile(values);
			return value;
		}
		throw new RepositoryException("Id does not exists");
	}

	@Override
	public T read(final String id) throws RepositoryException
	{
		if (values.containsKey(id))
		{
			return values.get(id);
		}
		throw new RepositoryException("Id does not exists");	
	}

	@Override
	public Map<String, T> readAll()
	{
		return values;
	}
}