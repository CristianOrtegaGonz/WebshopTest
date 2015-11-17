package se.grouprich.webshop.service.validation;

public interface PasswordValidator
{
	boolean hasSecurePassword(String password);
}
