package com.mx.PruebaTecnica.seguidad;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesSeg {

	private static final String SECRET_KEY = "12345678901234567890123456789012";
	private static final String ALGORITHM = "AES";

	public static String encrypt(String value) {
		if (value == null)
			return null;
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace(); 
            throw new RuntimeException("Error al cifrar la contraseña", ex);
		}
	}

}