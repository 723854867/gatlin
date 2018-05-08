package org.gatlin.sdk.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.util.codec.CryptConsts.SignatureAlgorithm;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.lang.StringUtil;

public class SignUtil {

	public static final String sign(String priKey, Map<String, String> params) {
		String signContent = _signContent(params);
		byte[] signData = Encrypt.RSASign(signContent.getBytes(), priKey, SignatureAlgorithm.SHA256WithRSA);
		return Base64.encodeBase64String(signData);
	}

	private static final String _signContent(Map<String, String> params) {
		StringBuilder content = new StringBuilder();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (StringUtil.hasText(value))
				content.append(key).append("=").append(value).append("&");
		}
		return content.deleteCharAt(content.length() - 1).toString();
	}

	public static String buildQuery(Map<String, String> params) {
		if (params == null || params.isEmpty())
			return null;
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> entry : params.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (!StringUtil.hasText(value))
				continue;
			try {
				query.append(name).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return query.deleteCharAt(query.length() - 1).toString();
	}
}
