package se.grouprich.webshop.validation;

public class PasswordValidation implements Validator {
	private boolean digits;
	private boolean versal;
	private boolean specialCharacter;
	private int counter;
	
	public PasswordValidation() {
		digits = false;
		versal = false;
		specialCharacter = false;
		counter = 0;
	}

	@Override
	public boolean validatePassword(String password) {
		if (password == null || password.trim().isEmpty())
		{
			return false;
		}
		
		for (int i = 0; i < password.length(); i++)
		{	
			//check that in password contains only letters, numbers and acceptable special characters
			if (password.substring(i, i + 1).matches("[A-ZÅÖÄa-zåöä\\d\\p{Punct}]+"))
			{
				if (password.substring(i, i + 1).matches("\\d+"))
				{
					counter++;

					if (counter >= 2)
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
	
	
}
