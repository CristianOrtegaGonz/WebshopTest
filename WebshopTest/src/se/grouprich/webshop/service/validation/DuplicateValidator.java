package se.grouprich.webshop.service.validation;

public interface DuplicateValidator
{
	boolean alreadyExsists(String email);
}
