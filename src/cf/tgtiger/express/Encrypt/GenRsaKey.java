package cf.tgtiger.express.Encrypt;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GenRsaKey {
    public static final String KEY_RSA = "RSA";
	//定义公钥关键词
	public static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
	//定义私钥关键词
	public static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";
//	public static void main(String[] args) {
//		for(int i=0;i<9;i++){
//		Map<String, Object> map = new HashMap<String, Object>();
//		map = init();
//		System.out.println("公钥："+getPublicKey(map));
//		System.out.println("私钥："+getPrivateKey(map));
//	}
//	}

	public static Map<String, Object> init() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥  
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 获取私钥  
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥对封装为Map
            map = new HashMap<String, Object>();
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return map;
    }
	
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
        str = encryptBase64(key.getEncoded());
        return str;  
    }
  
    /** 
     * 获取Base64编码的私钥字符串 
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
        str = encryptBase64(key.getEncoded());
        return str;
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
