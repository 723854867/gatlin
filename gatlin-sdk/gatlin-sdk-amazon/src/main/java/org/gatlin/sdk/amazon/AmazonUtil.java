package org.gatlin.sdk.amazon;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.util.Consts;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.lang.StringUtil;

public class AmazonUtil {

	public static final Map<String, String> wrap(String prefix, Map<String, String> params) {
		Map<String, String> map = new HashMap<String, String>();
		params.entrySet().forEach(item -> map.put(prefix + "." + item.getKey(), item.getValue()));
		return map;
	}
	
	public static String stringToSign(String path, TreeMap<String, String> parameters) {
		StringBuilder data = new StringBuilder();
		data.append("POST\n");
		data.append(AmazonConfig.host());
		data.append("\n");
		if (StringUtil.hasText(path))
			data.append(path);
		else
			data.append("/");
		data.append("\n");
		Iterator<Entry<String, String>> pairs = parameters.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			if (pair.getValue() != null) 
				data.append(urlEncode(pair.getKey()) + "=" + urlEncode(pair.getValue()));
			else 
				data.append(pair.getKey() + "=");
			if (pairs.hasNext())
				data.append("&");
		}
		return data.toString();
	}

	public static String sign(String data) {
		byte[] signature = Encrypt.HmacSHA256(data, AmazonConfig.secretKey());
		return Base64.encodeBase64String(signature);
	}
	
	public static String urlEncode(String rawValue) {
		String value = !StringUtil.hasText(rawValue) ? StringUtil.EMPTY : rawValue;
		try {
			return URLEncoder.encode(value, Consts.UTF_8.displayName()).replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			throw new CodeException(e);
		}
	}
}
