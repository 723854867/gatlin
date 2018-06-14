package org.gatlin.sdk.amazon;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.util.Consts;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.lang.StringUtil;

public class SignatureExample {
	
	public static void main(String[] args) throws Exception {
		String secretKey = "Your secret key";
		String serviceUrl = "https://mws.amazonservices.com/";
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("AWSAccessKeyId", urlEncode("Your Access Key Id"));
		parameters.put("Action", urlEncode("GetFeedSubmissionList"));
		parameters.put("MWSAuthToken", urlEncode("Your MWS Auth Token"));
		parameters.put("SellerId", urlEncode("Your Seller Id"));
		parameters.put("SignatureMethod", urlEncode("HmacSHA256"));
		parameters.put("SignatureVersion", urlEncode("2"));
		parameters.put("SubmittedFromDate", urlEncode("2013-05-01T12:00:00Z"));
		parameters.put("Timestamp", urlEncode("2013-05-02T16:00:00Z"));
		parameters.put("Version", urlEncode("2009-01-01"));
		String formattedParameters = calculateStringToSignV2(parameters, serviceUrl);
		String signature = sign(formattedParameters, secretKey);
		parameters.put("Signature", urlEncode(signature));
		System.out.println(calculateStringToSignV2(parameters, serviceUrl));
	}

	private static String calculateStringToSignV2(TreeMap<String, String> parameters, String serviceUrl) throws Exception {
		URI endpoint = new URI(serviceUrl.toLowerCase());
		StringBuilder data = new StringBuilder();
		data.append("POST\n");
		data.append(endpoint.getHost());
		data.append("\n/");
		data.append("\n");
		Iterator<Entry<String, String>> pairs = parameters.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			if (pair.getValue() != null) {
				data.append(pair.getKey() + "=" + pair.getValue());
			} else {
				data.append(pair.getKey() + "=");
			}
			if (pairs.hasNext())
				data.append("&");
		}
		return data.toString();
	}

	private static String sign(String data, String secretKey) {
		byte[] signature = Encrypt.HmacSHA256(data, secretKey);
		return Base64.encodeBase64String(signature);
	}
	
	private static String urlEncode(String rawValue) {
		String value = !StringUtil.hasText(rawValue) ? StringUtil.EMPTY : rawValue;
		try {
			return URLEncoder.encode(value, Consts.UTF_8.displayName()).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			throw new CodeException(e);
		}
	}
}
