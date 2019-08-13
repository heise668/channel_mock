package com.qpay.channel.test.mock.impl;

/*
 * t_return_code表字段
 */
public class ErrCode_Key {
	
	private String id;
	private String result_id;
	private String err_code;
	private String err_msg;
	private String channel_code;
	public String getId() {
		return id;
	}
	public String getResult_id() {
		return result_id;
	}
	public String getErr_code() {
		return err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setResult_id(String result_id) {
		this.result_id = result_id;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	

	public ErrCode_Key(){
		super();
	}


}
