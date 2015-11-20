package se.gouprich.webshop.service.validation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import se.grouprich.webshop.service.validation.CustomerValidator;

public class CustomerValidatorTest
{
	private CustomerValidator customerValidator;

	@Before
	public void setup()
	{
		customerValidator = new CustomerValidator();
	}

	@Test
	public void emailAddressShouldNotBeLongerThan30Characters()
	{
		String email1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aa.com";
		String email2 = " ";
		String email3 = null;
		String email4 = "aaaaaaa@aa.com";

		boolean valid1 = customerValidator.isLengthWithinRange(email1);
		boolean valid2 = customerValidator.isLengthWithinRange(email2);
		boolean valid3 = customerValidator.isLengthWithinRange(email3);
		boolean valid4 = customerValidator.isLengthWithinRange(email4);

		assertFalse(valid1);
		assertFalse(valid2);
		assertFalse(valid3);
		assertTrue(valid4);
		assertTrue(email1.length() > 30);
		assertTrue(email4.length() <= 30);
	}

	@Test
	public void passwordShouldHaveAtLeastOneVersalTwoNumbersAndOneSpecialCharacter()
	{
		String password1 = "Aa12&";
		String password2 = "aa12&";
		String password3 = "Aaa2&";
		String password4 = "Aa122";
		String password5 = " ";
		String password6 = null;
		
		boolean valid1 = customerValidator.isValidPassword(password1);
		boolean valid2 = customerValidator.isValidPassword(password2);
		boolean valid3 = customerValidator.isValidPassword(password3);
		boolean valid4 = customerValidator.isValidPassword(password4);
		boolean valid5 = customerValidator.isValidPassword(password5);
		boolean valid6 = customerValidator.isValidPassword(password6);
		
		assertTrue(valid1);
		assertFalse(valid2);
		assertFalse(valid3);
		assertFalse(valid4);
		assertFalse(valid5);
		assertFalse(valid6);
	}
}
