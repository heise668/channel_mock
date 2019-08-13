package com.qpay.channel.test.mock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qpay.channel.test.mock.services.Test_xml_json_Users;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;

@RestController
public class TestXmlJson_User {

	   //http://localhost:8080/json
    @GetMapping(value = "/json",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
	public Test_xml_json_Users getUserBaseInfo1(HttpServletRequest request) {
    	String no=request.getParameter("no");
    	System.out.println("no==="+no);
    	Test_xml_json_Users user = new Test_xml_json_Users("dalaoyang", "26", "北京");
        return user;
    }


    //http://localhost:8080/xml
    @GetMapping(value = "/xml",produces = { "application/xml;charset=UTF-8" })
    public Test_xml_json_Users getUserBaseInfo2(HttpServletRequest request) {
    	String no=request.getParameter("no");
    	Test_xml_json_Users user = new Test_xml_json_Users("dalaoyang", "26", "beijing");
        return user;
    }

}
