package se.grouprich.webshop.repository.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public final class ECommerceFileInfo<T> implements FileManager<String, T>
{
	private final String fileName;

	public ECommerceFileInfo(final Class<T> classType)
	{
		fileName = (classType.getSimpleName() + "s").toLowerCase();
	}

	@Override
	public File getDirectory()
	{
		return new File(fileName);
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}

	@Override
	public String getFileExtension()
	{
		return ".data";
	}

	@Override
	public File getPath()
	{
		return new File(getDirectory(), getFileName() + getFileExtension());
	}

	@Override
	public void createDirectory()
	{
		File dir = getDirectory();
		File destination = new File(dir, getFileName() + getFileExtension());
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		if (!destination.exists())
		{
			try
			{
				destination.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createFile(final Map<String, T> values)
	{
		createDirectory();
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getPath())))
		{
			out.writeObject(values);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readFile(final Map<String, T> values)
	{
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(getPath())))
		{
			values.putAll((Map<String, T>) in.readObject());
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new RuntimeException("Could not restore repository", e);
		}
	}
}
