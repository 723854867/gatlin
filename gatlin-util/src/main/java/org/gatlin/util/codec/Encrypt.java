package org.gatlin.util.codec;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.gatlin.util.Consts;
import org.gatlin.util.bean.exception.CryptException;
import org.gatlin.util.codec.CryptConsts.SignatureAlgorithm;
import org.gatlin.util.codec.CryptConsts.Transformation;

public class Encrypt {

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	
	public static byte[] HmacSHA256(String data, String secretKey) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(secretKey.getBytes(Consts.UTF_8.displayName()), "HmacSHA256"));
			return mac.doFinal(data.getBytes(Consts.UTF_8.displayName()));
		} catch (Exception e) {
			throw new CryptException("HmacSHA256加密失败", e);
		} 
	}

	/**
	 * AES对称加密
	 * 
	 * @param aesKey
	 * @param content
	 * @return
	 */
	public static String AESEncode(String aesKey, String content) {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(aesKey.getBytes());
			keygen.init(128, random);
			SecretKey originalKey = keygen.generateKey();
			byte[] raw = originalKey.getEncoded();
			SecretKey key = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptData = cipher.doFinal(content.getBytes(Consts.UTF_8));
			return Base64.encodeBase64String(encryptData);
		} catch (Exception e) {
			throw new CryptException("AES加密失败", e);
		}
	}

	/**
	 * RSA 签名
	 * 
	 * @param data
	 * @param privateKey
	 */
	public static byte[] RSASign(byte[] data, String privateKey, SignatureAlgorithm algorithm) {
		try {
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(algorithm.name());
			signature.initSign(privateK);
			signature.update(data);
			return signature.sign();
		} catch (Exception e) {
			throw new CryptException("RSA签名失败", e);
		}
	}

	/**
	 * RSA 非对称加密
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 */
	public static byte[] RSAEncodeByPublicKey(byte[] data, String publicKey, Transformation transformation) {
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key key = keyFactory.generatePublic(keySpec);
			Cipher cipher = Cipher.getInstance(transformation.mark());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int i = 0;
			byte[] cache;
			int offSet = 0;
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				else
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptData = out.toByteArray();
			out.close();
			return encryptData;
		} catch (Exception e) {
			throw new CryptException("RSA加密失败", e);
		}
	}
}
