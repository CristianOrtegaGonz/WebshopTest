package se.grouprich.webshop.model;

import java.io.Serializable;
import java.util.UUID;

import se.grouprich.webshop.exception.CustomerRegistrationException;

public final class Customer implements Serializable
{
	private static final long serialVersionUID = 8550124813033398565L;
	private String email;
	private String password;
	private final String firstName;
	private final String lastName;
	private final UUID customerId;
	private boolean isLoggedIn;

	public Customer(String email, String password, String firstName, String lastName) throws CustomerRegistrationException
	{
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		customerId = UUID.randomUUID();
	}

	public UUID getCustomerId()
	{
		return customerId;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return firstName + " " + lastName;
	}

	public boolean isLoggedIn()
	{
		return isLoggedIn;
	}

	public void logIn(String email, String password)
	{
		isLoggedIn = true;
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
