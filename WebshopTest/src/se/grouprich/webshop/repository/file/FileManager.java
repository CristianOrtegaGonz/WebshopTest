package se.grouprich.webshop.repository.file;

import java.io.File;
import java.util.Map;

public interface FileManager<K, T>
{
	File getDirectory();
	String getFileName();
	String getFileExtension();
	File getPath();
	void createDirectory();
	void createFile(Map<K, T> values);
	void readFile(Map<K, T> values);
}
