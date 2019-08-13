package com.qpay.channel.test.mock.baseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.qpay.channel.test.mock.baseService.CommonTools;
import com.qpay.channel.test.mock.baseService.RSAUtil;


/*
 * 作用1：对Map报文进行排序--returnMsg接口，返回(String)报文
 * 作用2：对排序的报文进行RSA加签---RsaMsg接口，返回(String) 加签签名 (URLEncode之后的签名)
 * 
 */

public class Return_RSAMsg {

	private CommonTools ct;
	private RSAUtil rsautil;

	
	
	
	public String returnMsg(HashMap ResultMap) {


		String returnMsg = "";
		
		Iterator<Map.Entry<String, String>> it = ResultMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			returnMsg = returnMsg + entry.getKey() + "=" + entry.getValue()+",";
		}
		
		returnMsg=returnMsg.substring(0, returnMsg.length()-1);	
		
		ct = new CommonTools();
		rsautil=new RSAUtil();
		String[] splitString = returnMsg.toString().split(",");
		String sortString = ct.sortStringWithSeparator(splitString, "&");
		
		return sortString;
	}

	public  String RsaMsg(String sortString, String rsaKey, String rsaType) {
		String signString = ct.removeFromString(sortString, "&", "&", "startswith", "sign").replaceAll("replaceFlag",",");
		String sign = "";
		String signEncode = "";
		rsautil.setPrivateKey(rsaKey);
		try {
			sign = rsautil.sign(signString);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			signEncode=ct.textEncode(sign, "UTF-8") ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return signEncode;
	}

}
