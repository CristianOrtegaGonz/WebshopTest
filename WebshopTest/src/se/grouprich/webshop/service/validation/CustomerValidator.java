package se.grouprich.webshop.service.validation;

import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;

public final class CustomerValidator implements PasswordValidator, DuplicateValidator, EmailValidator
{
	private Repository<String, Customer> customerRepository;

	public CustomerValidator()
	{
		customerRepository = new FileRepository<>(Customer.class);
	}

	public boolean isValidPassword(final String password)
	{
		boolean digits = false;
		boolean versal = false;
		boolean specialCharacter = false;
		int counterNumbers = 0;

		if (password == null || password.trim().isEmpty())
		{
			return false;
		}

		for (int i = 0; i < password.length(); i++)
		{
			if (password.substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				if (password.substring(i, i + 1).matches("\\d+"))
				{
					counterNumbers++;

					if (counterNumbers >= 2)
					{
						digits = true;
					}
				}

				if (password.substring(i, i + 1).matches("[A-ZÅÄÖ]+"))
				{
					versal = true;
				}

				if (password.substring(i, i + 1).matches("\\p{Punct}+"))
				{
					specialCharacter = true;
				}
			}
		}
		return (digits && versal && specialCharacter);
	}

	@Override
	public boolean alreadyExists(final String email)
	{
		for (Customer customer : customerRepository.readAll().values())
		{
			if (customer.getEmail().equals(email))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isLengthWithinRange(final String email)
	{
		if (email != null && !email.trim().isEmpty())
		{
			if (email.length() <= 30)
			{
				return true;
			}
		}
		return false;
	}
}