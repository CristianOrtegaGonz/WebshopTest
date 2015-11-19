package se.gouprich.webshop.service.validation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.service.validation.CustomerValidator;

@RunWith(MockitoJUnitRunner.class)
public class CustomerValidatorTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();

	private CustomerValidator customerValidator;

	@Before
	public void setup()
	{
		customerValidator = new CustomerValidator();
	}

	@Test
	public void emailAddressShouldNotBeLongerThan30Characters()
	{
		String email = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aa.com";

		boolean valid = customerValidator.isLengthWithinRange(email);

		assertFalse(valid);
		assertTrue(email.length() > 30);
	}

	@Test
	public void passwordShouldHaveAtLeastOneVersalTwoNumbersAndOneSpecialCharacter()
	{
		String password1 = "Aa12&";
		String password2 = "aa12&";
		String password3 = "Aaa2&";
		String password4 = "Aa122";
		
		boolean valid1 = customerValidator.isValidPassword(password1);
		boolean valid2 = customerValidator.isValidPassword(password2);
		boolean valid3 = customerValidator.isValidPassword(password3);
		boolean valid4 = customerValidator.isValidPassword(password4);
		
		assertTrue(valid1);
		assertFalse(valid2);
		assertFalse(valid3);
		assertFalse(valid4);
	}
}
