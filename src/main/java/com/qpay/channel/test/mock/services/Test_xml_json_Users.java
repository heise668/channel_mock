package com.qpay.channel.test.mock.services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * 练习返回xml格式报文 json格式报文
 */


@XmlRootElement
public class Test_xml_json_Users {
	
	String userName;
	String userAge;
	String userAddress;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	 @XmlElement
	public String getUserAge() {
		return userAge;
	}
	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}
	
	 @XmlElement
	public String getUserAddress() {
		return userAddress;
	}
	 
	 @XmlElement
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	 
	 
	public  Test_xml_json_Users(String userName,String userAge,String userAddress){
		this.userName=userName;
		this.userAge=userAge;
		this.userAddress=userAddress;
	}
	

}
