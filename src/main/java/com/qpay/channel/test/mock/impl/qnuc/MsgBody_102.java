package com.qpay.channel.test.mock.impl.qnuc;

/*
 * 身份认证及签约回执报文 epcc.102.001.01
 */

public class MsgBody_102 {

	// SysRtnInf 系统响应信息
	private String SysRtnCd;
	private String SysRtnDesc;

	// BizInf
	private String SgnNo;
	private String BizStsCd;

	// SgnInf
	private String SgnAcctIssrId;
	private String SgnAcctTp;
	private String SgnAcctNm;
	private String SgnAcctLvl;

	// InstgInf 支付账户信息
	private String InstgId;
	private String InstgAcct;

	// OriTrxInf 原身份认证及签约信息
	private String TrxCtgy;
	private String TrxId;
	private String TrxDtTm;
	
	

	/*
	 * 
	 */
	
	public String getSysRtnCd() {
		return SysRtnCd;
	}
	public String getSysRtnDesc() {
		return SysRtnDesc;
	}
	
	
	public String getSgnNo() {
		return SgnNo;
	}
	public String getBizStsCd() {
		return BizStsCd;
	}
	
	
	public String getSgnAcctIssrId() {
		return SgnAcctIssrId;
	}
	public String getSgnAcctTp() {
		return SgnAcctTp;
	}
	public String getSgnAcctNm() {
		return SgnAcctNm;
	}
	public String getSgnAcctLvl() {
		return SgnAcctLvl;
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
	public void setSysRtnCd(String sysRtnCd) {
		SysRtnCd = sysRtnCd;
	}
	public void setSysRtnDesc(String sysRtnDesc) {
		SysRtnDesc = sysRtnDesc;
	}
	public void setSgnNo(String sgnNo) {
		SgnNo = sgnNo;
	}
	public void setBizStsCd(String bizStsCd) {
		BizStsCd = bizStsCd;
	}
	public void setSgnAcctIssrId(String sgnAcctIssrId) {
		SgnAcctIssrId = sgnAcctIssrId;
	}
	public void setSgnAcctTp(String sgnAcctTp) {
		SgnAcctTp = sgnAcctTp;
	}
	public void setSgnAcctNm(String sgnAcctNm) {
		SgnAcctNm = sgnAcctNm;
	}
	public void setSgnAcctLvl(String sgnAcctLvl) {
		SgnAcctLvl = sgnAcctLvl;
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


}
