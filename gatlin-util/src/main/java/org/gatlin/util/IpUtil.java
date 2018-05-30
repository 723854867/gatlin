package org.gatlin.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

	public static final String hostname() { 
        InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return "localhost";
		}  
        return addr.getHostName().toString();
	}
}
