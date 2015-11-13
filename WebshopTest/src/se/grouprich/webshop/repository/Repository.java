package se.grouprich.webshop.repository;

import java.util.Map;
import java.util.UUID;

import se.grouprich.webshop.exception.RepositoryException;

public interface Repository<T>
{
	void create(T value);
	void delete(UUID value);
	void uppdate(UUID id, T value);
	T read(UUID id) throws RepositoryException;
	Map<UUID, T> getAll();
}
