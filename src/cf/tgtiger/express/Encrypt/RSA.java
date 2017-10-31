package cf.tgtiger.express.Encrypt;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import static cf.tgtiger.express.Encrypt.GenRsaKey.*;

public class RSA {
private static final String KEY_RSA = "RSA";

	public static void main(String[] args) {

		String info = "1";//info是加密的信息
		String en_info;//经过RSA加密后的密文信息
//		String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcdcljQpEASgN0SSEsLFVJT9Oc+7fJJVk6iqhfhMiJ6mpVIuV9ZYR1tQ31Z7mrD+6WW6jOAo8MOcJb0zFk+5r2I95UpOZFUXS9l8efzTjNCVCk+UMWztcLjA3uqdupZibVsBSnjRHsiQ0/CokylnzdbW98SYUk6LwiFWhAtm1+lQIDAQAB";
//		String sk = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANx1yWNCkQBKA3RJISwsVUlP05z7t8klWTqKqF+EyInqalUi5X1lhHW1DfVnuasP7pZbqM4Cjww5wlvTMWT7mvYj3lSk5kVRdL2Xx5/NOM0JUKT5QxbO1wuMDe6p26lmJtWwFKeNEeyJDT8KiTKWfN1tb3xJhSTovCIVaEC2bX6VAgMBAAECgYEAyX211Eywriz3Pz7fXLogMOMOq3m8ndDO3pyACt0XVEW1bZ55VMSF7fbOjS6CGYB0NWQMwkzAZRIdO0Oo73yuQPmKTRLmkOD03OSP7338t3sdgyFEwXRNjGIB8zVHPobfAxOmxhtYaH4myM9D/OQIkbqd7xPbnpmhtU4VwGgeDoECQQD98gPnYa2lTmPgC18lV5LgYAJSQBQhfB+XxtDo5jWrLGlysC+JykDPZCBiRa8z8H1GMU0knAoPAC6gohZiUyURAkEA3j5qPfN+UB/lwFS6qOeWzWnrgtRCVB6l1FU/y22RR6TTIKwZ1ymIHvkW53aWBxsILHEC1Tuh74V4sU44OChxRQJAY6E/xtebuO84X/4WJiBlj4IbZwmc3EZGXtWxwK4RAEc8V7Kn8/dV1ggCbIvKYSZI//D3wxU1HQ2FPaBaB9l3AQJBANlX+Na1NiZty2aYdiOQuO4SU/wSUPurLUnGRSlQ8e86xi8BoBPHzhNcToX2dypjyU6NBtQ6CVgRE8NyV5C0HHECQQDce0qNO+S1om7qo/QcyZryvXpOAqtYcFtwWccV2XkT66aCkYBXDjDhhMmjmV33qTbQGF4IWPknL0RRHgHiLC1i";
		Map<String, Object> map;
		map = init1();
		String pk = getPublicKey(map);
		String sk = getPrivateKey(map);
		System.out.println(pk);
		System.out.println(sk);


		en_info = encryptByPublic(info, pk);
		System.out.println("加密后的信息是" + en_info);
		System.out.println("解密后的信息是" + decryptByPrivate(en_info, sk));
	}
	
	//通过Base64 将String型的字符串转化成byte[]数组
	private static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }
	
	//通过Base64 将byte[]数组转换成String型数据
	private static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }
	
	public static String encryptByPublic(String encryptingStr, String publicKeyStr){
		try {
			// 将公钥由字符串转为UTF-8格式的字节数组
			byte[] publicKeyBytes = decryptBase64(publicKeyStr);
			// 获得公钥  
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
			// 取得待加密数据
	        byte[] data = encryptingStr.getBytes("UTF-8");
			KeyFactory factory;
			factory = KeyFactory.getInstance(KEY_RSA);
			PublicKey publicKey = factory.generatePublic(keySpec);
			// 对数据加密  
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // xxxxxxxxxxxxxxxx返回加密后由Base64编码的加密信息
			//反回加密后字符串
            return  encryptBase64(cipher.doFinal(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String decryptByPrivate(String encryptedStr, String privateKeyStr){
		try {
            // 对私钥解密  
			byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥 
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			// 获得待解密数据
	        byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 返回UTF-8编码的解密信息
            return new String(cipher.doFinal(data), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
}
