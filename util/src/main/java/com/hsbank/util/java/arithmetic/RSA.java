package com.hsbank.util.java.arithmetic;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加密算法: 公钥加密, 私钥解密
 * <p>可逆算法
 * @author york
 * 2010-12-16
 */
public class RSA {	
	public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 生成密钥对: {{公},{私}}
	 * @return
	 * @throws Exception
	 */
	public static byte[][] generateKeyPair() throws Exception {
		KeyPairGenerator keyServer = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyServer.initialize(1024);
		KeyPair keypair = keyServer.genKeyPair();
		return new byte[][]{keypair.getPublic().getEncoded(), keypair.getPrivate().getEncoded()};
	}

	/**
     * 用私钥对信息生成数字签名
     * @param data 			加密数据
     * @param privateKey 	私钥 
     * @return    
     * @throws Exception    
     */    
    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
        //构造PKCS8EncodedKeySpec对象     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        //KEY_ALGORITHM 指定的加密算法     
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取私钥匙对象     
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //用私钥对信息生成数字签名     
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return signature.sign();
    }
    
    
    /**     
     * 校验数字签名  
     * @param data 加密数据     
     * @param publicKey 公钥     
     * @param sign 数字签名   
     * @return 校验成功返回true 失败返回false     
     * @throws Exception 
     */     
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        //构造X509EncodedKeySpec对象     
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        //KEY_ALGORITHM 指定的加密算法     
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取公钥匙对象     
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        //验证签名是否正常     
        return signature.verify(sign);
    }
    
    /**     
     * 解密   
     * 用私钥解密
     * @param data     
     * @param key     
     * @return     
     * @throws Exception     
     */     
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        // 取得私钥     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    /**     
     * 解密<br>     
     * 用私钥解密   
     * @param data     
     * @param key     
     * @return     
     * @throws Exception     
     */     
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        // 取得公钥     
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
	
    /**     
     * 加密     
     * 用公钥加密   
     * @param data     
     * @param key     
     * @return     
     * @throws Exception     
     */     
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        // 取得公钥     
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
    
    /**     
     * 加密 
     * 用私钥加密   
     * @param data     
     * @param key     
     * @return     
     * @throws Exception     
     */     
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        // 取得私钥     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }    
}
