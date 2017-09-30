package cf.tgtiger.express.Encrypt;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GenDesKey {
//    public static void main(String[] args) {
//		String password = "";
//		KeyGenerator keyGen = null;
//		try {
//			keyGen = KeyGenerator.getInstance("DES");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for(int i=0;i<10;i++){
//			keyGen.init(56);
//		    SecretKey secretKey = keyGen.generateKey();
//		    password = encryptBase64(secretKey.getEncoded());
//		    System.out.println(password);
//		}
//
//	}

	public static String genKey() {
        String encryptkey = "";
        KeyGenerator keyGen = null;

        try {
            keyGen = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        encryptkey = encryptBase64(secretKey.getEncoded());

        return encryptkey;
	}
	
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }
  
    /** 
     * BASE64 编码 
     * @param key 需要Base64编码的字节数组 
     * @return 字符串 
     */  
    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }
}
