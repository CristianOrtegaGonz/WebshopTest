package se.grouprich.webshop.validation;

import se.grouprich.webshop.model.Customer;

public interface Validator
{
	boolean checkPassword(Customer customer);
}
