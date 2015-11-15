package se.grouprich.webshop.repository;

import java.util.HashMap;
import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.ECommerceIdGenerator;
import se.grouprich.webshop.idgenerator.Identifiable;
import se.grouprich.webshop.repository.file.ECommerceFileInfo;
import se.grouprich.webshop.repository.file.FileManager;

public final class FileRepository<T extends Identifiable<String>> implements Repository<String, T>
{
	private final Map<String, T> values;
	private final FileManager<String, T> fileInfo;
	private final ECommerceIdGenerator idGenerator;

	public FileRepository(Class<T> classType)
	{
		values = new HashMap<>();
		fileInfo = new ECommerceFileInfo<>(classType);
		idGenerator = new ECommerceIdGenerator();
		if ((fileInfo.getPath()).exists())
		{
			fileInfo.readFile(values);
		}
	}

	public void create(T value)
	{
		String id = idGenerator.getGeneratedId();
		value.setId(id);
		values.put(id, value);
		fileInfo.createFile(values);
	}

	public void delete(String id)
	{
		if (values.containsKey(id))
		{
			values.remove(id);
		}
		fileInfo.createFile(values);
	}
	
	public void uppdate(String id, T value)
	{
		if (values.containsKey(id))
		{
			values.replace(id, value);
		}
		fileInfo.createFile(values);
	}
	
	public T read(String id) throws RepositoryException
	{
		if (values.containsKey(id))
		{
			return values.get(id);
		}
		throw new RepositoryException("This id doesn't exists");
	}
	
	@Override
	public Map<String, T> getAll()
	{
		return values;
	}	
}