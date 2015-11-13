package se.grouprich.webshop.repository;

import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.exception.RepositoryException;

public interface Repository<T, K>
{
	void create(T value);
	void delete(K value);
	void uppdate(K id, T value);
	T read(K id) throws RepositoryException;
	Map<K, T> getAll();
}
