package org.address.validator;

import java.math.BigInteger;

import org.junit.Test;

import static org.junit.Assert.*;

public class AddressValidatorTest {
	AddressValidator addressValidator = new AddressValidator();

	@Test(expected = IllegalArgumentException.class)
	public void testIP4AddressBlank() {
		addressValidator.convertIP4To32BitInt("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIP4AddressNull() {
		addressValidator.convertIP4To32BitInt(null);
	}

	@Test
	public void testIpv4AddressToInt() {
		// valid case
		assertEquals(3232235777L,
				addressValidator.convertIP4To32BitInt("192.168.1.1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIpv4AddrWithInvalidAddressRange() {
		addressValidator.convertIP4To32BitInt("192.168.1.876");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIpv4AddrWithMissingAddressSegment() {
		addressValidator.convertIP4To32BitInt("168.1.876");
	}

}
