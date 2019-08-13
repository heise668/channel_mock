package com.qpay.channel.test.mock.impl.qnuc;

/*
 * 身份认证及签约申请报文：epcc.101.001.01
 */

public class MsgBody_101 {

	// SgnInf--签约人信息

	private String SgnAcctIssrId;
	private String SgnAcctTp;
	private String SgnAcctId;
	private String SgnAcctNm;
	private String IdTp;
	private String IdNo;
	private String MobNo;

	// InstgInf 支付账户信息
	private String InstgId;
	private String InstgAcct;

	// TrxInf 签约信息
	private String TrxCtgy;
	private String TrxId;
	private String TrxDtTm;
	private String AuthMsg;
	
	
	public String getSgnAcctIssrId() {
		return SgnAcctIssrId;
	}
	public String getSgnAcctTp() {
		return SgnAcctTp;
	}
	public String getSgnAcctId() {
		return SgnAcctId;
	}
	public String getSgnAcctNm() {
		return SgnAcctNm;
	}
	public String getIdTp() {
		return IdTp;
	}
	public String getIdNo() {
		return IdNo;
	}
	public String getMobNo() {
		return MobNo;
	}
	
	
	
	public String getInstgId() {
		return InstgId;
	}
	public String getInstgAcct() {
		return InstgAcct;
	}
	
	
	
	
	public String getTrxCtgy() {
		return TrxCtgy;
	}
	public String getTrxId() {
		return TrxId;
	}
	public String getTrxDtTm() {
		return TrxDtTm;
	}
	public String getAuthMsg() {
		return AuthMsg;
	}
	public void setSgnAcctIssrId(String sgnAcctIssrId) {
		SgnAcctIssrId = sgnAcctIssrId;
	}
	public void setSgnAcctTp(String sgnAcctTp) {
		SgnAcctTp = sgnAcctTp;
	}
	public void setSgnAcctId(String sgnAcctId) {
		SgnAcctId = sgnAcctId;
	}
	public void setSgnAcctNm(String sgnAcctNm) {
		SgnAcctNm = sgnAcctNm;
	}
	public void setIdTp(String idTp) {
		IdTp = idTp;
	}
	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
	public void setMobNo(String mobNo) {
		MobNo = mobNo;
	}
	public void setInstgId(String instgId) {
		InstgId = instgId;
	}
	public void setInstgAcct(String instgAcct) {
		InstgAcct = instgAcct;
	}
	public void setTrxCtgy(String trxCtgy) {
		TrxCtgy = trxCtgy;
	}
	public void setTrxId(String trxId) {
		TrxId = trxId;
	}
	public void setTrxDtTm(String trxDtTm) {
		TrxDtTm = trxDtTm;
	}
	public void setAuthMsg(String authMsg) {
		AuthMsg = authMsg;
	}

	
	
	
	
}
