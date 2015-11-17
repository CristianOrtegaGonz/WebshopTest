package se.grouprich.webshop.service.validation;

import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;

public final class CustomerValidator implements PasswordValidator, DuplicateValidator
{
	private boolean digits;
	private boolean versal;
	private boolean specialCharacter;
	private int counterNumbers;
	private Repository<String, Customer> customerRepository;

	public CustomerValidator()
	{
		digits = false;
		versal = false;
		specialCharacter = false;
		counterNumbers = 0;
		customerRepository = new FileRepository<>(Customer.class);
	}

	public boolean hasSecurePassword(String password)
	{
		if (password == null || password.trim().isEmpty())
		{
			return false;
		}

		for (int i = 0; i < password.length(); i++)
		{
			// check that in password contains only letters, numbers and
			// acceptable special characters
			if (password.substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				// check for all decimal digits (0-9)
				if (password.substring(i, i + 1).matches("\\d+"))
				{
					counterNumbers++;

					if (counterNumbers >= 2)
					{
						digits = true;
					}
				}

				// check an uppercase letter
				if (password.substring(i, i + 1).matches("[A-ZÅÄÖ]+"))
				{
					versal = true;
				}

				// Special characters control
				if (password.substring(i, i + 1).matches("\\p{Punct}+"))
				{
					specialCharacter = true;
				}
			}
			else
			{
				return false;
			}
		}
		return specialCharacter;
	}
	
	@Override
	public boolean alreadyExsists(String email)
	{
		for (Customer customer : customerRepository.getAll().values())
		{
			if (customer.getEmail().equals(email))
			{
				return true;
			}
		}
		return false;
	}
}