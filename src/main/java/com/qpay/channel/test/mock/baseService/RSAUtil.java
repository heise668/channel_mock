package com.qpay.channel.test.mock.baseService;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

public class RSAUtil {

	private String privateKey;
	private String publicKey;

	public RSAUtil() {
	}

	public RSAUtil(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	
	private PrivateKey privateKey() throws Exception {
		byte[] keyBytes = new byte[Base64.decodeBase64(privateKey).length];
		DataInputStream privateKeyFile = new DataInputStream(
				new ByteArrayInputStream(Base64.decodeBase64(privateKey)));
		privateKeyFile.readFully(keyBytes);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	

	public String sign(String value) throws Exception {
		Signature signature = Signature.getInstance("SHA1withRSA");

		signature.initSign(privateKey());
		// 处理request字符串
		value = RSAUtil.removeNull(value);
		byte[] message = value.getBytes("UTF-8");
		signature.update(message);
		byte[] sigBytes = signature.sign();

		return (Base64.encodeBase64String(sigBytes));
	}

	public String getRSA(String value) throws Exception {
		Signature signature = Signature.getInstance("SHA1withRSA");

		signature.initSign(privateKey());
		//处理request字符串
		value = RSAUtil.removeNull(value);
		byte[] message = value.getBytes("UTF-8");
		signature.update(message);
		byte[] sigBytes = signature.sign();

		return (Base64.encodeBase64String(sigBytes));
	}

	public static String removeNull(String value) {
		value = value + "&";
		StringBuffer valueBuffer = new StringBuffer();
		int startIndex = 0;
		int tempIndex, equalIndex;
		while (true) {
			equalIndex = value.indexOf("=", startIndex + 1);
			tempIndex = value.indexOf("&", startIndex + 1);
			if (equalIndex + 1 != tempIndex && tempIndex > 0) {
				if (!value.substring(equalIndex + 1, tempIndex).toUpperCase()
						.equals("NULL")) {
					valueBuffer.append(value.substring(startIndex, tempIndex));
					valueBuffer.append("&");
				}
			}

			if (tempIndex == value.length() - 1) {
				valueBuffer.deleteCharAt(valueBuffer.length() - 1);
				break;
			}
			startIndex = tempIndex + 1;
		}
		return valueBuffer.toString();
	}


	public static String byte2hex(byte[] b) // 二行制转字符串
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + "";
			}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1) {
			return null;
		}
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	public String encrypt(String data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = data.getBytes("UTF-8");
		return Base64.encodeBase64String(cipher.doFinal(bytes));

	}
	
	public String dencrypt(String data, String key)
			throws Exception {
		// 对私钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(Base64.decodeBase64(data)), "UTF-8");

	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}
	
	
	public static void main(String[] args) throws Exception {
		// String base64Key =
		// "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMCvwa2sTQwLbEgRXZvcBFeyscCYUBF6+bGbVVIfp1c0bcf4PLUnz9P+ePpwraZKOqCRNtbE2EXjJnu9gehBxkBOWIYCuaeD7AY7hjs/PQyLcbB6/zuskOolPH45g1aTVVogyeN8eCd32I0pOHsdEJ820s10b2MtpTUPcG4yvdHdAgMBAAECgYB62NM7Xcm1byb2/5NVxj1CiFwJdVI/As9k66rG9AVldLi1ME/ME3jOKMSKrwIXLeYY7twuq0tTq1GivJyxLhYtVcbK4FgqusYzZr22Aw/9kbzDaloqYwOM46i4QNOydaa/1fgEHkHbTe7a92bQMzIncGycolhcItAMvHYuXgJZxQJBAP3vvXQ5QDDhQOnYvd1nDhVMF3qSN+JGfL4LK4o5H1e7jNkV/SGE+wQpK6FK4xhQnTjSa4SB1oIL8ZSd6EXk0JsCQQDCQJloy+FfWfdHMaLZecrpV7oaxJaNfnOPMQFC1otb6zs/T07V5G3L574uJu6GLCAMFK48ViHzdbr5v2I/QyLnAkEAk/qUTdlbBeEOQffTVOVMOK7586ynsk3fPaQmwErfb/HUd2Ev/MuQt/ECAuEwC6hWpplAnJxJE8nAAxouFCTuRwJBAJbY7Yj5EpomViW+QPVbZBySmJ4i3bshYIHpD06lJvGJmafPYawuSKlY3FIgv4gIChb3lFqclJ7oZPt/CL+R1i8CQQCLXUbjHiWhuAbqKLxCTKvjnwWmwmB+4t5OHKVuFNRmjNffhvwRvPtle2cfOY/77voZgwC4fuBsKxHJumFI5DI3";
		RSAUtil rsa = new RSAUtil();
		// rsa.setPrivateKey(base64Key);
		// System.out.println(rsa.sign("inputCharset=1&pageUrl=&bgUrl=http://10.65.209.15:8580/acquire测试-order-channel/mock/collectNotifyResult.htm&version=v2.1&language=1&signType=4&merchantAcctId=2001001001103100000001010101&payerName=&payerContactType=1&payerContact=&payerIdType=3&payerId=111&orderId=91341841768907&orderAmount=300&orderTime=20120523193041&productName=&productNum=&productId=&productDesc=&platformId=&platformSignType=&platformUrl=&ext1=&ext2=&payType=10&bankId=SDTBNK&cardIssuer=&cardNum=&remitType=&remitCode=&redoFlag=0&pid=103100000001&submitType="));
		// String value="p=2&e=null&a=&b=&c=null";
		// System.out.println(RSAUtil.removeNull(value));
//		rsa.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB");
		
		String prikey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC7QxCn3VkAxQgtpbYVjI4O/Mogo/73ckf1V/Bey6RYqGTXu/gRVyrOyc088VXX8hX/yKexI8D5Wci5OlX+seMVXrq1a2WGs9XX/UvxIxMmVtEV66laO+bBfRGFqiI44G478BYhef5Yda0aJ3pGah8EZQGJMvhunxEaLsGMd1DqzCW8LKB1+LrT1xxz8NhBln7YJfOKAruU0c1HzG1f3ErYP6C96KXP7q2p66GUlWmc+HL4sDZAu0UU12S8Xfrbp1Txm1fxKFiiMqFssrHkLPbUmLNUkKHURl0Z2U7+4as2jIQxvP9g/p76nlX35hAyQyiAzXgG04oX0iqF+RLc/3uBAgMBAAECggEAUWIKIQofnigDL+NuyNq2+mh+Z8UeD+hIwaWgP3J9UFKO0PtOCis53Xjyi2PPDPSW/vzzdBa3zSZqB6YErZYdvLHYrvOnwezyKNZaZMf7kJP2dnJevWp/FOQH83FHQjS3c7EZzR65nTQ3MMCrcWK5bC2Bh2DxexICWsCrOSK5tfCXd8L9i8/zu3RyAmSkqk3n89nxxSCTNDf6nGkrWx4IM4feSDoybOF0ub1G8+P5kK2KG42juMda9uyC6K01vRkwpm+hDG76xahHn/8grij09ms6bFFtT6bVwecK8cc950mDVMfyhK5sDRFI+BmFgeX1eLJX72p3Dj6wLmzS1L53iQKBgQDDCYeyNvlCXcecFP1DWiRxgQc3u6sjA+S+BcLs4h++EhA6vx2iyDl8zVOSpce3cQTG4M0Z7g/Q9yU2tjAMaAC97FNF8FQem/ts79OgAZuxJIAdEbvWC2eerr+ZcuNYYTBy1mAxo0rP+a9E3NB6rheZECO+mCxAO3f76XxL19vVBwKBgQD1y2AfrHEjeYdDnx7y6VPwhstF8amVLfAk4UE/mCGVJ7AS/31ia6OPR8AfB3DMrkmufgfTHqw1/M3dXzb83a8z67wYAbpCEml9uHlRcAILk4MWuTlpm0ftoqhqRdkv/Z0eTBI5+HC7ijl8/KVlz/+0MhbaQlIF2aPhhOAjkNbRNwKBgQCH5f8QVa/O54trnVBvesMD97Twx6pShqb/JQKSjmkxxmJanMpon303EdbWyv7jOjPq477l/3Qz1dGvUrkeG+XQcHgBtpjWjzdcy6s4xYYXo2hFWRL57Q5xii/lGyGAdpAMpIV4cOBzE/ZlVDtMEDxPIejWKDBFPWq4NBOwwSLjRwKBgEZvDRCGfbVIsfYyts+tBRy7w6w7X/kbvOEUm3n5wkXXpaEMDYYSikAYs3kwzKYam4qhGuTouTiPRP7Wtg8oB3i9aJ0IgbNFS213W2x2yF9A2iO82DLnCGmUXuZ1NZOG/WsdKWvrQYK8TVT/oPXX8NJWOC47Kv36K/gH96K5cpVpAoGARq/31k3Hzs2KlEDs+Y51oPgQGW5jJoZN1TelFJp1iI/1liMGJVHGk5wSiAwltPqCJ/cYLQvAd4YKrWAOUKlv53k5qmeU9dyAwDjc1IqzjJi+0qoLpUNABnveuoqh8lHQmHL5FoapGEtipAt3kxfnBL1ZCD9ZniiZzWFuT6JKn+U=";
		String pubkey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu0MQp91ZAMUILaW2FYyODvzKIKP+93JH9VfwXsukWKhk17v4EVcqzsnNPPFV1/IV/8insSPA+VnIuTpV/rHjFV66tWtlhrPV1/1L8SMTJlbRFeupWjvmwX0RhaoiOOBuO/AWIXn+WHWtGid6RmofBGUBiTL4bp8RGi7BjHdQ6swlvCygdfi609ccc/DYQZZ+2CXzigK7lNHNR8xtX9xK2D+gveilz+6tqeuhlJVpnPhy+LA2QLtFFNdkvF3626dU8ZtX8ShYojKhbLKx5Cz21JizVJCh1EZdGdlO/uGrNoyEMbz/YP6e+p5V9+YQMkMogM14BtOKF9IqhfkS3P97gQIDAQAB";
		
		rsa.setPrivateKey(pubkey);
		String rsaEny=rsa.encrypt("20180516171956", pubkey);
		System.out.println(rsaEny);
		
		rsa.setPrivateKey(prikey);
		String rsaDeny=rsa.dencrypt(rsaEny, prikey);
		System.out.println(rsaDeny);
	}

}
