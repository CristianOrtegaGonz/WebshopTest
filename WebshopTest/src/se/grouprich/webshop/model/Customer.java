package se.grouprich.webshop.model;

import java.io.Serializable;

import se.grouprich.webshop.exception.CustomerRegistrationException;
import se.grouprich.webshop.idgenerator.Identifiable;

public final class Customer implements Serializable, Identifiable<String>
{
	private static final long serialVersionUID = 8550124813033398565L;
	private String customerId;
	private String email;
	private String password;
	private final String firstName;
	private final String lastName;

	public Customer(String customerId, String email, String password, String firstName, String lastName) throws CustomerRegistrationException
	{
		this.customerId = customerId;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Override
	public String getId()
	{
		return customerId;
	}

	@Override
	public void setId(final String customerId)
	{
		this.customerId = customerId;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return firstName + " " + lastName;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof Customer)
		{
			Customer otherCustomer = (Customer) other;
			return email.equals(otherCustomer.email) && password.equals(otherCustomer.password);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += email.hashCode() * 37;
		result += password.hashCode() * 37;

		return result;
	}

	@Override
	public String toString()
	{
		return "[Name] " + getName() + " [E-mail] " + email;
	}
}
