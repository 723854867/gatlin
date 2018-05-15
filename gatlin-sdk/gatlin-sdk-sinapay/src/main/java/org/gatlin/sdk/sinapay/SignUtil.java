package org.gatlin.sdk.sinapay;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.sdk.sinapay.notice.SinaNotice;
import org.gatlin.util.codec.CryptConsts.SignatureAlgorithm;
import org.gatlin.util.codec.CryptConsts.Transformation;
import org.gatlin.util.codec.Decrypt;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.reflect.BeanUtil;

public class SignUtil {

	private static final Set<String> ENCRYPT_FIELDS = new HashSet<String>() {
		private static final long serialVersionUID = -6522560067080004018L;
		{
			add("cert_no");
			add("phone_no");
			add("real_name");
			add("account_name");
			add("validity_period");
			add("bank_account_no");
			add("verification_value");
			add("license_no");
			add("telephone");
			add("email");
			add("organization_no");
			add("legal_person");
			add("cert_no");
			add("legal_person_phone");
			add("bank_account_no");
		}
	};

	public static String fileMD5(InputStream in) throws Exception {
		MessageDigest digest = null;
		byte buffer[] = new byte[1024];
		int len;
		digest = MessageDigest.getInstance("MD5");
		while ((len = in.read(buffer, 0, 1024)) != -1)
			digest.update(buffer, 0, len);
		in.close();
		return _bytes2hex03(digest.digest());
	}

	private static String _bytes2hex03(byte[] bytes) {
		final String HEX = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(HEX.charAt((b >> 4) & 0x0f));
			sb.append(HEX.charAt(b & 0x0f));
		}
		return sb.toString();
	}

	/**
	 * 签名
	 */
	public static final String sign(Map<String, String> params) {
		byte[] sinData = Encrypt.RSASign(_signStr(params).getBytes(), SinapayConfig.priKey(), SignatureAlgorithm.SHA1withRSA);
		return Base64.encodeBase64String(sinData);
	}

	// 验签
	public static final boolean verify(SinaNotice notice, String pubKey) {
		String signResult = notice.getSign().toString();
		Map<String, Object> map = BeanUtil.beanToTreeMap(notice, false);
		map.remove("sign");
		map.remove("sign_type");
		map.remove("sign_version");
		String likeResult = _createLinkString(map, false);
		return Decrypt.RSASignVerify(likeResult, Base64.decodeBase64(signResult), pubKey, SignatureAlgorithm.SHA1withRSA);
	}

	/**
	 * 待签名字符串
	 */
	private static final String _signStr(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, String> entry : params.entrySet()) {
			if (entry.getKey().equals("sign") || entry.getKey().equals("sign_type")
					|| entry.getKey().equals("sign_version"))
				continue;
			builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * 敏感字段加密
	 */
	public static final void encrypt(Map<String, String> params) {
		for (Entry<String, String> entry : params.entrySet()) {
			if (!ENCRYPT_FIELDS.contains(entry.getKey()))
				continue;
			byte[] data = entry.getValue().getBytes();
			byte[] encryptData = Encrypt.RSAEncodeByPublicKey(data, SinapayConfig.pubKey(), Transformation.RSA);
			entry.setValue(Base64.encodeBase64String(encryptData));
		}
	}

	private static String _createLinkString(Map<String, Object> params, boolean encode) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		String charset = params.get("_input_charset").toString();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object object = params.get(key);
			if (null == object || !StringUtil.hasText(object.toString()))
				continue;
			String value = params.get(key).toString();
			if (encode) {
				try {
					value = URLEncoder.encode(URLEncoder.encode(value, charset), charset);
				} catch (UnsupportedEncodingException e) {
					throw new CodeException();
				}
			}
			prestr = prestr + key + "=" + value;
			if (i != keys.size() - 1)
				prestr += "&";
		}
		return prestr.trim();
	}
}
