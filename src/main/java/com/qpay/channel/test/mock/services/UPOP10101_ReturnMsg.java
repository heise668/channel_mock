package com.qpay.channel.test.mock.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.qpay.channel.test.mock.baseService.CommonTools;
import com.qpay.channel.test.mock.baseService.RSAUtil;

public class UPOP10101_ReturnMsg {

	private CommonTools ct;
	private RSAUtil rsautil;

	public String returnMsg(HashMap ResultMap) {

		String returnMsg = "";
		Iterator<Map.Entry<String, String>> it = ResultMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			// System.out.println("key= " + entry.getKey() + " and value= " +
			// entry.getValue());
			returnMsg = returnMsg + entry.getKey() + "=" + entry.getValue()+",";
		}
		
		returnMsg=returnMsg.substring(0, returnMsg.length()-1);		
		return returnMsg;
	}

	public String RsaMsg(String returnMsg, String rsaKey, String rsaType) {
		ct = new CommonTools();
		rsautil=new RSAUtil();
		String[] splitString = returnMsg.toString().split(",");
		String sortString = ct.sortStringWithSeparator(splitString, "&");

		String signString = ct.removeFromString(sortString, "&", "&", "startswith", "sign").replaceAll("replaceFlag",
				",");

		String sign = "";
		rsautil.setPrivateKey(rsaKey);
		try {
			sign = rsautil.sign(signString);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		String requestString = null;
		try {
			requestString = sortString + "&signature=" + ct.textEncode(sign, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return requestString;
	}

}
