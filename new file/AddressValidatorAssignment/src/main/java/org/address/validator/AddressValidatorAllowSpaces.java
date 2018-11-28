package org.address.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidatorAllowSpaces {
	private static final int IPV4_MAX_OCTET_VALUE = 255;
//	private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
	private static final String IPV4_REGEX = "^(\\s)*(\\d){1,3}(\\s)*\\.(\\s)*(\\d){1,3}(\\s)*\\.(\\s)*(\\d){1,3}(\\s)*\\.(\\s)*(\\d){1,3}(\\s)*$";
	Pattern ipv4ValidatorPattern = Pattern.compile(IPV4_REGEX);

	private boolean isValidIp4AddressSegment(String ip4AddrGroup) {
		if (ip4AddrGroup == null || ip4AddrGroup.length() == 0) {
			return false;
		}

		int iIpSegment = 0;
		try {
			iIpSegment = Integer.parseInt(ip4AddrGroup);
		} catch (NumberFormatException e) {
			return false;
		}

		if (iIpSegment > IPV4_MAX_OCTET_VALUE) {
			return false;
		}
		if (ip4AddrGroup.length() > 1 && ip4AddrGroup.startsWith("0")) {
			return false;
		}
		return true;
	}

	private String[] getMatchedpatterns(String ip4Address) {
		if (null == ip4Address || ip4Address.isEmpty()) {
			throw new IllegalArgumentException("Ip Address is invalid: " + ip4Address);
		}
		Matcher ip4Addressmatcher = ipv4ValidatorPattern.matcher(ip4Address);
		boolean isIP4address = ip4Addressmatcher.matches();
		if (!isIP4address) {
			throw new IllegalArgumentException("Ip Address is invalid: " + ip4Address);
		}

		if (ip4Addressmatcher.matches()) {
			return  ip4Address.split("\\.");
		} 

		return null;
	}

	public long convertIP4To32BitInt(String ip4Address) {
		if (null == ip4Address || ip4Address.isEmpty()) {
			throw new IllegalArgumentException("Ip Address is invalid: " + ip4Address);
		}
		
		String[] ip4GroupArray = getMatchedpatterns(ip4Address);
		if (null == ip4GroupArray || ip4GroupArray.length == 0) {
			throw new IllegalArgumentException("Ip Address is invalid: " + ip4Address);
		}
		long ipNumbers = 0;
		for (int i = 0; i < ip4GroupArray.length; i++) {
			String ip4SegmentTrim = ip4GroupArray[i].trim ();
			if (!isValidIp4AddressSegment(ip4SegmentTrim)) {
				throw new IllegalArgumentException("Ip Address Segment Is Out Of Range: " + ip4GroupArray[i]);
			}
			long ipSegment = Long.parseLong(ip4SegmentTrim);
			long shiftedIpSegment = ipSegment << (24 - (i * 8));
			// Use Bitwise OR to add each shifted segment to result
			ipNumbers |= shiftedIpSegment;
		}
		return ipNumbers;
	}

	public static void main(String[] args) {

		AddressValidatorAllowSpaces addrValidator = new AddressValidatorAllowSpaces();
		long testConversion = addrValidator.convertIP4To32BitInt(null);
		System.out.println("....testConversion: " + testConversion);
		
	}

}
