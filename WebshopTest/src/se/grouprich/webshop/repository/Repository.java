package se.grouprich.webshop.repository;

import java.util.Map;

import se.grouprich.webshop.exception.RepositoryException;

public interface Repository<K, T>
{
	T create(T value);
	void delete(K value);
	T update(K id, T value) throws RepositoryException;
	T read(K id) throws RepositoryException;
	Map<K, T> getAll();
}
