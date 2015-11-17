package se.grouprich.webshop.idgenerator;

public interface Identifiable<K>
{
	K getId();
	void setId(K id);
}
