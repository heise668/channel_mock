package com.qpay.channel.test.mock.impl.qnuc;

public class MsgHeader {
	
	private String Sndt;	//报文发送日期时间
	private String MsgTp;	//报文编号--交易类型
	private String IssrId;	//发起机构标识
	private String SignSn;	//签名证书序列号
	
	
	
	public String getSndt() {
		return Sndt;
	}
	public String getMsgTp() {
		return MsgTp;
	}
	public String getIssrId() {
		return IssrId;
	}
	public String getSignSn() {
		return SignSn;
	}
	public void setSndt(String sndt) {
		Sndt = sndt;
	}
	public void setMsgTp(String msgTp) {
		MsgTp = msgTp;
	}
	public void setIssrId(String issrId) {
		IssrId = issrId;
	}
	public void setSignSn(String signSn) {
		SignSn = signSn;
	}
	
	
	
	

}
