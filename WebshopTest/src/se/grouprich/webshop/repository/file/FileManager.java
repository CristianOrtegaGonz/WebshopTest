package se.grouprich.webshop.repository.file;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public interface FileManager<T>
{
	File getDirectory();
	String getFileName();
	String getFileExtension();
	File getPath();
	void createDirectory();
	void createFile(Map<UUID, T> values);
	void readFile(Map<UUID, T> values);
}
