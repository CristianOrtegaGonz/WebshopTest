package se.grouprich.webshop.validation;

import se.grouprich.webshop.model.Customer;

public final class ECommerceValidator implements Validator
{
	private boolean digits;
	private boolean versal;
	private boolean specialCharacter;
	private int counterNumbers;
	
	public ECommerceValidator()
	{
		digits = false;
		versal = false;
		specialCharacter = false;
		counterNumbers = 0;
	}

	public boolean checkPassword(Customer customer)
	{
		if (customer.getPassword() == null || customer.getPassword().trim().isEmpty())
		{
			return false;
		}

		for (int i = 0; i < customer.getPassword().length(); i++)
		{
			// check that in password contains only letters, numbers and
			// acceptable special characters
			if (customer.getPassword().substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				// check for all decimal digits (0-9)
				if (customer.getPassword().substring(i, i + 1).matches("\\d+"))
				{
					counterNumbers++;

					if (counterNumbers >= 2)
					{
						digits = true;
					}
				}

				// check an uppercase letter
				if (customer.getPassword().substring(i, i + 1).matches("[A-ZÅÄÖ]+"))
				{
					versal = true;
				}

				// Special characters control
				if (customer.getPassword().substring(i, i + 1).matches("\\p{Punct}+"))
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
}