package se.grouprich.webshop.idgenerator;

import java.util.UUID;

public final class ECommerceIdGenerator implements IdGenerator<String>
{
	@Override
	public String getGeneratedId()
	{
		return UUID.randomUUID().toString();
	}
}
