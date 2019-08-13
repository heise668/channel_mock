/**
 * 
 */
package com.qpay.channel.test.mock.impl;

/**
 * @author menghongtao
 *	交易表记录字段 channelmock.t_trade
 *
 *
 */
public class Trade_Key {

	private String request_no;  //交易订单号
	private String voucher_no;	//交易凭证号
	private String trade_type;	//交易类型
	private String card_no;	//交易卡号
	private String name;	//持卡人姓名
	private String cert_no;	//证件号
	private String phone_no;	//手机号
	private String amount;	//交易金额
	private String result_id;	//交易结果id
	private String request_time;	//交易时间
	private String merchant_id;	//商户号
	private String request_content; //交易其他内容信息
	
	
	
	public String getRequest_no() {
		return request_no;
	}
	public String getVoucher_no() {
		return voucher_no;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public String getCard_no() {
		return card_no;
	}
	public String getName() {
		return name;
	}
	public String getCert_no() {
		return cert_no;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public String getAmount() {
		return amount;
	}
	public String getResult_id() {
		return result_id;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}
	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setResult_id(String result_id) {
		this.result_id = result_id;
	}
	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
	

	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	

	public String getRequest_content() {
		return request_content;
	}
	public void setRequest_content(String request_content) {
		this.request_content = request_content;
	}
	
	public Trade_Key()
	{
		super();
	}
}
