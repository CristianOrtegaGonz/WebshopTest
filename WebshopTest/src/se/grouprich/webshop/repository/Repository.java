package se.grouprich.webshop.repository;

import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.exception.RepositoryException;
import se.grouprich.webshop.idgenerator.IdGenerator;

public interface Repository<K, T>
{
	boolean create(T value);
	void delete(K value);
	void uppdate(K id, T value);
	T read(K id) throws RepositoryException;
	Map<K, T> getAll();
}
