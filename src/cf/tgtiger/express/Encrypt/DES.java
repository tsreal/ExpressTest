package cf.tgtiger.express.Encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

public class DES {

//    public static void main(String[] args) {
//		String info="hello world";//info表示加密的内容
//		String des_key="12345678";
//		byte[] encryptionBytes = null;
//	    byte[] encryptionBase64Bytes = null;
//	    String encryptionStr = null;
//	    try {
//			encryptionBytes =encrypt(info.getBytes("UTF-8"), des_key);//以下四行为加密过程
//            encryptionBase64Bytes = Base64.getEncoder().encode(encryptionBytes);
//            encryptionStr = new String(encryptionBase64Bytes);
//            System.out.println("加密后的内容" + encryptionStr);
//
//            try {//此处为解密过程，即为加密的逆过程
//                System.out.println(new String(decrypt(Base64.getDecoder().decode(encryptionStr.getBytes()), des_key)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//		} catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//	}

    //通过Base64 将String型的字符串转化成byte[]数组
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    //通过Base64 将byte[]数组转换成String型数据
	public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

	public static String encrypt(byte[] datasource, String password) {

        try{
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        //现在，获取数据并加密
        //正式执行加密操作
        return encryptBase64(cipher.doFinal(datasource));
        }catch(Throwable e){
                e.printStackTrace();
        }
        return null;
}
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return cipher.doFinal(src);
        }

}