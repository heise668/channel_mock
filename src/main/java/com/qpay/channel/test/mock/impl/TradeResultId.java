package com.qpay.channel.test.mock.impl;

public class TradeResultId {
	
	
	public String resultIdByRequestNo(String request_no,int len){		
		String result_id="";		
		String result=request_no.substring(request_no.length()-len, request_no.length()); //订单号后N位
		int i=Integer.parseInt(result);				
		result_id=String.valueOf(i);		
		return result_id;
	}
	
	
	public String resultIdByAmount(String amount){		
		String result_id="";		
		String result=amount; //金额判断
		int i=Integer.parseInt(result);	
		result_id=String.valueOf(i);		
		return result_id;
	}
	

}
