package org.gatlin.util.codec;

public interface CryptConsts {

	public enum Transformation {
		RSA("RSA");
		private String mark;
		
		private Transformation(String mark) {
			this.mark = mark;
		}
		
		public String mark() {
			return mark;
		}
	}
	
	public enum SignatureAlgorithm {
		MD5withRSA,
		SHA1withRSA;
	}
}
