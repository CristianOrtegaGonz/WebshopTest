package se.grouprich.webshop.idgenerator;
@Deprecated
public interface Identifiable<K>
{
	K getId();
	void setId(K id);
}
