package se.grouprich.webshop.model;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.grouprich.webshop.exception.OrderException;
import se.grouprich.webshop.exception.ProductRegistrationException;
import se.grouprich.webshop.idgenerator.IdGenerator;
import se.grouprich.webshop.model.Customer;
import se.grouprich.webshop.model.Order;
import se.grouprich.webshop.model.Product;
import se.grouprich.webshop.model.ShoppingCart;
import se.grouprich.webshop.repository.FileRepository;
import se.grouprich.webshop.repository.Repository;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest
{
	@Rule
	public ExpectedException exception = ExpectedException.none();
}