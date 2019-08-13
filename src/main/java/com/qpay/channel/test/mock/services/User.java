package com.qpay.channel.test.mock.services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "User") //映射xml的根标签名称为User;

public class User {

    @JacksonXmlProperty(localName = "name")
    //映射每一个属性为一个标签，可指定生成的标签名
    private String name;
    @JacksonXmlProperty(localName = "age")
    private Integer age;
    
    
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

    
    
    
}
