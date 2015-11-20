package se.grouprich.webshop.service.validation;

public interface DuplicateValidator
{
	boolean alreadyExists(String email);
}
