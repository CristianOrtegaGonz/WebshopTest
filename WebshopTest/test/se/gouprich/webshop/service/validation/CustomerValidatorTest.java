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

	CustomerValidator customerValidator;

	@Before
	public void setup()
	{
		customerValidator = new CustomerValidator();
	}

	@Test
	public void customerShouldNotHaveEmailAddressThatIsLongerThan30Characters()
	{
		String email = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aa.com";

		boolean valid = customerValidator.isLengthWithinRange(email);

		assertFalse(valid);
		assertTrue(email.length() > 30);
	}

	@Test
	public void customerShouldHavePasswordWithTwoVersalTwoNumbersSpecialCharacter()
	{
		String password = "Aa12&";
		
		boolean valid = customerValidator.isValidPassword(password);
		
		assertTrue(valid);	
	}
}
