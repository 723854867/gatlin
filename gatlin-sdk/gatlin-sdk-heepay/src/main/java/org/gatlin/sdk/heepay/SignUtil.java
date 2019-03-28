package org.gatlin.sdk.heepay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.sdk.heepay.bean.HeepayException;
import org.gatlin.sdk.heepay.bean.enums.Code;
import org.gatlin.util.codec.Encrypt;
import org.gatlin.util.codec.CryptConsts.SignatureAlgorithm;
import org.gatlin.util.lang.StringUtil;

/**
 * 汇付宝签名
 * @author fansd
 * @date 2019年3月26日 下午5:07:18
 */
public class SignUtil {

	/**
     * MD5加密
     * @param str 需要加密的值
     * @return 加密完成的32位值(小写)
     */
	public static final String MD5Sign(String str) {
		String re_md5 = new String();
		try {
			 MessageDigest md = MessageDigest.getInstance("MD5");
	            try {
					md.update(str.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw new HeepayException(Code.MD5_FAILE.mark(), Code.MD5_FAILE.desc());
				}
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0)
	                    i += 256;
	                if (i < 16)
	                    buf.append("0");
	                buf.append(Integer.toHexString(i));
	            }
	            re_md5 = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new HeepayException(Code.MD5_FAILE.mark(), Code.MD5_FAILE.desc());
		}
		return re_md5.toLowerCase();
	}

	public static final String _signContent(Map<String, String> params) {
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
     
  
     public static void main(String[] args) throws UnsupportedEncodingException {
//         // 添加新安全算法,如果用JCE就要把它添加进去
//         Security.addProvider(new com.sun.crypto.provider.SunJCE());
         // 24字节的密钥（我们可以取apk签名的指纹的前12个byte和后12个byte拼接在一起为我们的密钥）
         final String keyBytes = "JUNNET_123456_123456_COM";
         String szSrc = "560473954062434304^5^0^123^胡文^2.0^清退打款^省^市^123";

         System.out.println("加密前的字符串:" + szSrc);

         String encoded = Des.Encrypt3Des(szSrc, keyBytes,"ToHex16");
         System.out.println("加密后的字符串:" + encoded);

         String srcBytes = Des.Decrypt3Des(encoded, keyBytes,"ToHex16");

         System.out.println("解密后的字符串:" + srcBytes);
         
         System.out.println(MD5Sign("我"));
     }
	

}
