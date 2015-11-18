package se.grouprich.webshop.repository;

import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;

public interface Repository<K, T>
{
	T create(T value);
	void delete(K value);
	void uppdate(K id, T value);
	T read(K id) throws RepositoryException;
	Map<K, T> getAll();
	boolean emailExists(K value);
}
